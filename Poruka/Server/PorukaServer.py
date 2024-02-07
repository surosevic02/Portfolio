from flask import Flask, render_template, request, redirect, url_for, session
from flask_socketio import SocketIO, send, join_room, leave_room, close_room
from flask_bcrypt import Bcrypt
import os
import json
import random
import string
import colorsys
import getpass
import re
import configparser

# Setting up the app
app = Flask(__name__)
bcrypt = Bcrypt(app)
socketio = SocketIO(app)

# Recreate a secret key every boot (Users will need to re-log everytime the server is restarted)
app.config["SECRET_KEY"] = os.urandom(24)

# Read from config file
configurations = configparser.ConfigParser()
configurations.read('config.ini')

# Check if the username is valid
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
d = get_json() # Dictonary containing user data for the app
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
    ownerPerm = int(configurations['PERMS']['owner'])
    hashed = bcrypt.generate_password_hash(adminPass) # Hash admin password
    adminPass = hashed.decode('utf-8') # Decode owner password
    color = colorsys.hls_to_rgb(random.uniform(0.00, 1.00), 0.5, 1.0) # Generate color tuple
    color = tuple(round(i * 255) for i in color) # Convert color tuble to RGB values
    d[adminName] = [adminPass, str(color), 0, False] # create owner entry perm 0 is owner permission
    write_json(d) # Write admin account to file
    print("Owner account created and saved successfully")

# Variales used for instantiating server functions
userPerms = int(configurations['PERMS']['regulars']) # Permission for general users
maxUsers = int(configurations['MAX']['users']) # Unused for now get total number of color combos
maxInvites = int(configurations['MAX']['invites']) # How many active invites are allowed at a time
maxPrivateChats = int(configurations['MAX']['privatechats']) # Maximum amount of private chats allowed at a time
regCodeLen = int(configurations['SECURITY']['registerkeylen']) # Length of the register link/code that is generated
adminKeyLen = int(configurations['SECURITY']['adminkeylen']) # Length of the admin key

# Data structures and variables for server operation
d = None # Json dictionary containing account details
chats = {"msg-0" : 0} # Chat rooms that are active (msg-0) is the public chat room by default
invites = set() # Set of invites
activeUsers = dict() # Dictionary containing active users and their socket id
availableRooms = [r for r in range(maxPrivateChats, 0, -1)] # List containing available room numbers
userRooms = dict() # Dictionary that contains all the rooms that a specific user is in
adminKey = None # Special admin key if the user is an admin

# Checks if the room is empty if it is remove it
# then add the room number to availalbe rooms list
def checkRoomClose(room):
    if chats[room] <= 0 and room != "msg-0":
            close_room(room)
            chats.pop(room, None) # Remove the room from the chats dictionary
            availableRooms.append(int(room[-1])) # Get the chat room number by using the last character (msg-0) = 0

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

        # Check if the user is aready connected
        if join != False:
            if not valid_username(username) or username not in d:
                return render_template('index.html', error="Not a valid Username", user=username, code=passcode)
            else:
                compare = d[username][0]

            if not valid_password(passcode) or not bcrypt.check_password_hash(compare, passcode):
                return render_template('index.html', error="Not a valid Password", user=username, code=passcode)
            
            if d[username][3]:
                return render_template('index.html', error="Account is banned", user=username, code=passcode)

            session["name"] = username
            session["color"] = d[username][1]
            session["adminKey"] = "1"

            # If the user is an owner append admin key to their session
            if d[username][2] == int(configurations['PERMS']['owner']):
                global adminKey
                adminKey = ''.join(random.choices(string.ascii_letters + string.digits, k=adminKeyLen))
                session["adminKey"] = adminKey

            return redirect(url_for("chatroom"))
        
        if register != False:
            if invcode not in invites:
                return render_template('index.html', error="Invalid invite code", ivitecode="invcode")
            
            return redirect(url_for("register", inv=invcode))

    return render_template('index.html', address=configurations['SERVER']['address'])

@app.route('/register/<inv>', methods = ['POST', 'GET'])
def register(inv):
    if inv not in invites:
        return 'bad request!', 400
    
    if request.method == 'POST':
        username = request.form.get("username")
        passcode = request.form.get("passcode")
        cpasscode = request.form.get("cpasscode")

        # Checking for a valid username
        d = get_json()
        if not valid_username(username) or username in d:
            return render_template('register.html', msg="", error="Not a valid Username", uname="", pcode="", cpcode ="")

        # Check valid passcode
        if not valid_password(passcode):
            return render_template('register.html', msg="", error="Not a valid Password", uname=username, pcode="", cpcode="")
        
        # Confirm passcode
        if passcode != cpasscode:
            return render_template('register.html', msg="", error="Passwords do not match", uname=username, pcode="", cpcode="")
        
        temp = set()
        for k in d:
            temp.add(d[k][1])

        color = None
        while color in temp or not color:
            color = colorsys.hls_to_rgb(random.uniform(0.00, 1.00), 0.5, 0.5)
            color = tuple(round(i * 255) for i in color)

        hashed = bcrypt.generate_password_hash(passcode)
        d[username] = [hashed.decode('utf-8'), str(color), userPerms, False]
        invites.discard(inv)
        write_json(d)
        return render_template('register.html', msg="Account created successfully", error="", uname="", pcode="", cpcode="")

    return render_template('register.html')

# Script for the chat room
@app.route('/chatroom')
def chatroom():
    # Check if the user is an admin and load the proper web page
    global adminKey
    if session.get("adminKey") == adminKey:
        return render_template('chat.html', admin=True)

    return render_template('chat.html')

# Script for sending essages
@socketio.on("message")
def message(data):
    # Get the messages details from the sender
    name = session.get("name")
    if name not in activeUsers:
        return

    color = session.get("color")
    chat = data["room"]

    # Shorten the name if needed
    if len(name) > 5:
        name = name[0:5] + "+"

    # Check the message to see if it contains a link
    regex = r"(?i)\b((?:https?://|www\d{0,3}[.]|[a-z0-9.\-]+[.][a-z]{2,4}/)(?:[^\s()<>]+|\(([^\s()<>]+|(\([^\s()<>]+\)))*\))+(?:\(([^\s()<>]+|(\([^\s()<>]+\)))*\)|[^\s`!()\[\]{};:'\".,<>?«»“”‘’]))"
    word = re.split(regex, data["data"])
    url = re.findall(regex, data["data"])
    url = [x[0] for x in url]
    
    # Parse the two list with the appropriate tags
    msg = []
    messageString = ""
    for w in word:
        if w is None:
            continue
        
        if w in url:
            msg.append(["p",messageString])
            messageString = ""
            if w.startswith("https://") or w.startswith("http://") or w.startswith("//"):
                msg.append(["a",w])
            else:
                w = "//" + w
                msg.append(["a",w])
        else:
            messageString += w
    
    if messageString != "":
        msg.append(["p",messageString])

    # Create the message structure and send the message
    content = {
        "name" : "message",
        "user" : name,
        "message" : msg,
        "color" : color,
        "chat": chat
    }
    send(content, to=chat)
    return

# Script for creating a unique invite link
@socketio.on("invite")
def invite():
    name = session.get("name")
    if name not in activeUsers:
        return
    
    # If active invites haven't passed maxInvites
    # create a new invite by randomizing numbers and letters
    if len(invites) < maxInvites:
        link = None
        while link in invites or not link:
            link = ''.join(random.choices(string.ascii_letters + string.digits, k=regCodeLen))
        invites.add(link)

    # Too many links don't create one and create a message
    else:
        link = "Too many invite links!"
    
    # Sed the link to the user
    send({"name":"link", "invite":link}, to=request.sid)
    return

# Script for creating a new private chat
@socketio.on("newchat")
def newchat(data):
    # If there aren't enough rooms don't do anything and notify admin
    if len(availableRooms) <= 0:
        print("Room limit hit!")
        return

    # Get the required data for setting up a private chat room
    creator = session.get("name")
    if creator not in activeUsers:
        return

    invitees = data["data"]
    roomNum = availableRooms.pop()
    roomName = "msg-"+str(roomNum) 
    chats[roomName] = 1
    
    # Go through each of the users in the room and add them to it
    for i, u in enumerate(invitees):
        if u in activeUsers:
            chats[roomName] += 1
            userRooms[u].append(roomName)
            join_room(roomName, sid=activeUsers[u])
        else:
            invitees.pop(i)
    
    # If only the creator would be in the room add the room number
    # back to the availalbeRooms list remove the chat from chat rooms and return
    if chats[roomName] <= 1:
        availableRooms.append(roomNum)
        chats.pop(roomName, None)
        return

    # Creator joins the room and room
    join_room(roomName)

    # Create the rooms on the front end
    roomInfo = {
        "name" : "newchat",
        "rmnum" : roomNum,
        "rmname" : roomName,
        "creator" : creator
    }
    send(roomInfo, to=roomName)

    # Notify the users in the private chat who created the chat and who was invited
    message = creator + " created private chatroom " + str(roomNum) + ", the following users were added: " + ', '.join(invitees)
    send({"name":"server", "message":message, "chat":roomName}, to=roomName)
    return

# Script for clearing all invites
@socketio.on("purgeinvites")
def purgeinvites():
    global adminKey
    if adminKey == session.get("adminKey"):
        invites.clear()
    return

# Script for clearing all chats
# This script clears all the chat rooms and any data structures
# that contain meta data for the chat rooms
@socketio.on("purgechats")
def purgechats():
    global adminKey
    if adminKey == session.get("adminKey"):
        
        # Go through the active chats close them then add available
        # resets the available rooms it also sends the client data to clear the rooms
        for keys in list(chats):
            if keys == "msg-0":
                continue

            send({"name":"delchat", "chatnum":int(keys[-1]), "chatname":keys}, to=keys)
            availableRooms.append(int(keys[-1]))
            close_room(keys)
            chats.pop(keys, None)

        # clear the user rooms data structure
        for keys in userRooms:
            userRooms[keys] = ["msg-0"]
    return

# Script for getting user status
# This function will get a list of users that
# aren't or are banned
@socketio.on("getuserstat")
def getuserstat():
    global adminKey
    if adminKey == session.get("adminKey"):
        name=session.get("name")
        d = get_json()
        permissionLevel = d[name][2]
        notbanned = []
        banned = []

        for key in d:
            # Get users who are less privileged
            if permissionLevel >= d[key][2]:
                continue
            elif d[key][3]:
                banned.append(key)
            else:
                notbanned.append(key)
        
        send({"name":"userbanstat", "unbanned":notbanned, "banned":banned}, to=request.sid)
    return

# Script for kicking and banning a user
@socketio.on("ban")
def ban(name):
    global adminKey
    if adminKey == session.get("adminKey"):
        admin = session.get("name")
        
        # Set the banned flag in the json file to true
        d = get_json()
        d[name][3] = True
        write_json(d)
        message = name + " was banned from the server by " + admin

        if name in activeUsers:
            # Remove the user from any existing rooms and notify them
            for r in userRooms[name]:
                chats[r] -= 1
                send({"name":"server", "message":message, "chat":r}, to=r)
                leave_room(r, sid=activeUsers[name])
                checkRoomClose(r)
            userRooms.pop(name, None)
            
            # Send show the div overlay indicating the user has been banned then remove them from active users
            # Send a message to the admin for confimation
            activeUsers.pop(name, None)

        else:
            send({"name":"server", "message":message, "chat":"msg-0"}, to="msg-0")
    return

# Script for unbanning users  
@socketio.on("unban")
def unban(name):
    global adminKey
    if adminKey == session.get("adminKey"):
        admin = session.get("name")
        # Get the json and set the flag to unban
        d = get_json()
        d[name][3] = False
        write_json(d)
        message = name + " was unbanned from the server by " + admin

        # Send a message to the admin for confirmation
        send({"name":"server", "message":message, "chat":"msg-0"}, to="msg-0")
    return

# Script for connecting to the chat
@socketio.on("connect")
def connect(auth):
    # Check if the session is valid
    if session.get("name") == None:
        return

    # Get the username join the chatroom and let existing users know
    name = session.get("name")

    # If the user is banned disconnect them
    d = get_json()
    if d[name][3]:
        disconnect()
        return

    # Connect the user if not banned
    chats["msg-0"] += 1
    send({"name":"connect", "uname":name, "chat":"msg-0", "online":chats["msg-0"]}, to="msg-0")
    join_room("msg-0")

    # If the user is not in active users then it's the user that just connected
    if request.sid not in activeUsers.values():
        activeUsers.pop(name, None) # Remove the user from the dictionary if it has old data
        userRooms.pop(name, None) # Removes any old data from user rooms
        send({"name":"server", "message":"you joined the server", "chat":"msg-0"}, to=request.sid)
        send({"name":"activeuser", "au":list(activeUsers.keys()), "online":chats["msg-0"]}, to=request.sid)
        userRooms[name] = ["msg-0"] # add the main chat to the user rooms parameter
        activeUsers[name] = request.sid
    return


# Script for disconnecting from the chat room
@socketio.on("disconnect")
def disconnect():
    # Include method to have user leave all rooms
    name = session.get("name")
    rooms = ["msg-0"]
    if name in userRooms:
        rooms = userRooms[name]
    chat = "msg-0"

    # Check if it is the admin
    d = get_json()
    if d[name][2] == 0: # This can also be changed based on the system
        global adminKey
        adminKey = None

    # Leave every room that the user is in
    for r in rooms:
        leave_room(r)
        chats[r] -= 1
        message = name + " has left the chat"
        if r != "msg-0":
            send({"name":"server", "message":message, "chat":r}, to=r)
        
        checkRoomClose(r)

    # Notify the public chat and remove the user from the active users dictionary, clear userRooms entry
    userRooms.pop(name, None)
    send({"name":"disconnect", "uname":name, "chat":chat, "online":chats["msg-0"]}, to=chat)
    activeUsers.pop(name, None)
    return

# Change this so that I can edit this
#app.run(host="0.0.0.0", port=80)