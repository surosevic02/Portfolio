# Poruka
Poruka is a chat messaging app I created to allow users to message one another on a private server. The back end was developed with Python using Flask and Socketio APIs. Other libraries/APIs, like Bcrypt, were included for the security and configuration of the server. The front end was written in HTML/CSS, with JavaScript added to handle client-side methods. Both ends utilized Django to reduce code redundancy and simplify debugging. Finally, the application can be Dockerized with the included `Dockerfile` so it can be deployed on other machines (Note: this application can only be run on Linux because of the Gunicorn library).
## Lessons Learned
This project taught me the importance of reducing code redundancy when creating an application. Reducing code redundancy helps to easily debug code when a project is large and bugs become harder to find. Furthermore, this project also helped me to gain insight into the back-end and front-end of a server application and how APIs exist to interface between the two. Finally, this project taught me about deploying servers and technologies like Docker and Gunicorn.
## Features
For Everyone
 - Messaging - enjoy the ability to message users who are currently connected
 - Private Messaging - message individuals privately connected to the server
 - Invites - share a unique invite code and the server address to invite others
 - Links - ability to share links and have them automatically appear
 - Colors - each user has a unique color generated for them

For Owner/Admin
 - Ban and un-ban users
 - Clear all private chats
 - Clear all active invite codes that aren't used

Server Deployment
 - Dockerizeable server
 - Event logging via console and/or file
## Screenshots
![Login Screenshot](https://github.com/surosevic02/Portfolio/blob/main/Poruka/Poruka%20Screenshots/login.png)
![Chat Room](https://github.com/surosevic02/Portfolio/blob/main/Poruka/Poruka%20Screenshots/chatroom.png)
![Private Chat Room](https://github.com/surosevic02/Portfolio/blob/main/Poruka/Poruka%20Screenshots/privateChat.png)
![Invite Link](https://github.com/surosevic02/Portfolio/blob/main/Poruka/Poruka%20Screenshots/Invite.png)
## Setup Instructions
Prerequisites:
 - Computer with Linux installed
 - Docker
 - Knowledge of Linux and Docker

Instructions:
1. First clone the files from this repository
2. Open `Dockerfile` and change the port and the IP address the docker container will listen on and save the changes (or leave it as is)
   `CMD ["gunicorn", "--bind", "[DOCKER IP ADDRESS]:[DOCKER PORT]", "-k", "gevent", "-w", "1", "PorukaServer:app"]`
3. Next open `config.ini` and make any changes needed to the file. Values are explained below:
	- `address` - This is the header in the login screen that can display whatever the owner wants ideally it should be used to display the website address
	- `users` - Sets the maximum amount of users that can join the server should be between 0 - 100
	- `invites` - Sets the maximum amount of invites that can be active in a server
	- `privatechats` - Sets the maximum amount of private chats that can exist on a server
	- `adminkeylen` - The length of the Admin key that is used to identify admin users (this is stored both on the server and the session data of the admin)
	- `registerkeylen` - The length of the registration code the server generates
	- `consolelogging` - Enable server logging to the console
	- `filelogging` - Enable logging to a file stored on the server as `plog.txt`
	- `owner` - Owner permission level (this number needs to be lower than all other permission levels!)
	- `regulars` - User permission level (this number should be the highest as it's for general users)
4. Build the docker image with the following command: `docker image build -t [IMAGE_NAME] .`
5. Run the docker container with the following command: `docker run -it -p [IPADDRESS]:[SERVER PORT]:[DOCKER PORT] [IMAGE_NAME]` (Warning: you must run the docker image with `-it` flag for first time use to configure the admin account in the image!)
6. Finally login to the website to confirm that the server is up and running
## Notes
To view or edit user information that is stored on the server, add a shell by running run: `docker exect -it [CONTAINER_NAME] bash`, then whilst in the shell you can navigate to the file: `users.json`
