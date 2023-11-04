from flask import Flask, render_template, request, redirect, url_for, session
from flask_socketio import SocketIO, send, join_room, leave_room
from flask_bcrypt import Bcrypt
import os
import json
import random
import string
import colorsys
import getpass

# Application functions
app = Flask(__name__)
app.config["SECRET_KEY"] = "asdsasad" # change this to be set by security
bcrypt = Bcrypt(app)
socketio = SocketIO(app)

# Check if the user name is valid
#def create_app():
    #return app

def valid_username(checkUser):
    return (len(checkUser) > 0) and (len(checkUser) <= 32) and (" " not in checkUser)

# Check if the password is valid
def valid_password(checkPass):
    return (len(checkPass) >= 8) and (len(checkPass) <= 32) and (" " not in checkPass)

# Return json object
def get_json(): 
    with open("users.json", "r") as usersfile:
        jsonDictionary = {}
        try:
            jsonDictionary = json.load(usersfile)
        except:
            print("- - - Json file formated incorrectly or Missing - - -")
            raise

    return jsonDictionary

# Write json object to file
def write_json(dictionaryInp):
    jsonObj = json.dumps(dictionaryInp, indent=3)
    with open("users.json", "w") as usersfile:
        try:
            usersfile.write(jsonObj)
        except:
            print("- - - Failed to write to Json or File Missing - - -")
    
    return

# First time boot tasks
# Create the users file if it hasn't been created
if not os.path.isfile("users.json"):
    with open("users.json","x") as userJson:
        json.dump({}, userJson)

# Prompt admin to create an admin account
d = get_json()
if not d:
    print("Creating Admin Account")
    print("Username - 1 to 32 chars no spaces")
    print("Password - 8 to 32 chars no spaces")

    # Entry loop for user entry
    adminName = adminPass = confirmPass = ""
    while not valid_username(adminName) or not valid_password(adminPass) or confirmPass != adminPass:
        adminName = input("Please enter username: ")
        adminPass = getpass.getpass("Please enter password: ")
        confirmPass = getpass.getpass("Confirm password: ")

        if not valid_username(adminName) or not valid_password(adminPass) or confirmPass != adminPass:
            print("Invalid entries")
    
    confirmPass = None
    hashed = bcrypt.generate_password_hash(adminPass) # Hash admin password
    adminPass = hashed.decode('utf-8') # Decode admin password
    color = colorsys.hls_to_rgb(random.uniform(0.00, 1.00), 0.5, 1.0) # Generate color tuple
    color = tuple(round(i * 255) for i in color) # Convert color tuble to RGB values
    d[adminName] = [adminPass, str(color)] # create admin entry
    write_json(d) # Write admin account to file

    print("Admin account created and saved successfully")

d = None
chats = {"public" : 0}
invites = set()

# Script for the login page
@app.route('/', methods = ['POST', 'GET'])
def index():
    session.clear()
    if request.method == 'POST':
        username = request.form.get("user")
        passcode = request.form.get("code")
        invcode = request.form.get("invitecode")
        join = request.form.get("join", False)
        register = request.form.get("register", False)
        d = get_json()

        if join != False:
            if not valid_username(username) or username not in d:
                return render_template('index.html', error="Not a valid Username", user=username, code=passcode)
            else:
                compare = d[username][0]

            if not valid_password(passcode) or not bcrypt.check_password_hash(compare, passcode):
                return render_template('index.html', error="Not a valid Password", user=username, code=passcode)

            session["chats"] = "public" # possibly edit this for private chats
            session["name"] = username
            session["color"] = d[username][1]

            return redirect(url_for("chatroom"))
        
        if register != False:
            if invcode not in invites:
                return render_template('index.html', error="Invalid invite code", ivitecode="invcode")
            
            return redirect(url_for("register", inv=invcode))
        

    return render_template('index.html')

@app.route('/register/<inv>', methods = ['POST', 'GET'])
def register(inv):
    if inv not in invites:
        return 'bad request!', 400
    
    if request.method == 'POST':
        username = request.form.get("username")
        passcode = request.form.get("passcode")
        cpasscode = request.form.get("cpasscode")
        create = request.form.get("create", False)

        # Checking for a valid username
        d = get_json()
        if not valid_username(username) or username in d:
            return render_template('register.html', error="Not a valid Username", uname="", pcode="", cpcode ="")

        # Check valid passcode
        if not valid_password(passcode):
            return render_template('register.html', error="Not a valid Passwords", uname=username, pcode="", cpcode="")
        
        # Confirm passcode
        if passcode != cpasscode:
            return render_template('register.html', error="Passwords do not match", uname=username, pcode="", cpcode="")
        
        temp = set()
        for k in d:
            temp.add(d[k][1])

        color = None
        while color in temp or not color:
            color = colorsys.hls_to_rgb(random.uniform(0.00, 1.00), 0.5, 0.5)
            color = tuple(round(i * 255) for i in color)

        hashed = bcrypt.generate_password_hash(passcode)
        d[username] = [hashed.decode('utf-8'), str(color)]
        invites.remove(inv)
        write_json(d)
        return redirect(url_for("index"))

    return render_template('register.html')

# Script for the chat room
@app.route('/chatroom')
def chatroom():
    #room = session.get("room")
    #if room None or session.get("Names") is None 
    return render_template('chat.html')

# Script for sending essages
@socketio.on("message")
def message(data):
    chat = session.get("chats") # might remove this
    name = session.get("name")
    color = session.get("color")

    if len(name) > 5:
        name = name[0:5] + "+"

    content = {
        "name" : name,
        "message" : data["data"],
        "color" : color
    }
    
    send(content, to=chat) # set this to public

# Script for creating an invite
@socketio.on("invite")
def invite():
    link = None
    while link in invites or not link:
        link = ''.join(random.choices(string.ascii_letters + string.digits, k=8))

    invites.add(link)
    send({"name":"link", "invite":link})


# Script for connecting to the chat
@socketio.on("connect")
def connect(auth):
    name = session.get("name")
    join_room("public")
    chats["public"] += 1
    send({"name":"server", "message": name + " has entered the chat", "online":chats["public"]}, to="public")

# Script for disconnecting from the chat room
@socketio.on("disconnect")
def disconnect():
    name = session.get("name")
    leave_room("public")
    chats["public"] -= 1
    send({"name":"server", "message":name + " has left the chat", "online":chats["public"]}, to="public")

# Change this so that I can edit this
#app.run(host="0.0.0.0", port=80)