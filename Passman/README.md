
# PassMan

This is a password manager written in C++. The project consists of a backend that utilizes the OpenSSL API to encrypt and securely store the passwords. The front end utilizes the Win32 API to create an executable and window the user can interact with. This program is only compatible with Windows 10 and onwards.
## Lessons Learned

This project taught me how to write better code and to apply coding standards to debug my program more efficiently. I have a better understanding of the Win32 API, since I read about it extensively. I created a backend separately from the frontend as a way to ensure compatibility across platforms if I decide to extend to other platforms.

## Features

- Light/Dark mode based on system theme
- Re-Encrypt existing password files
- Add and Delete passwords
- Password encryption


## Screenshots

![Demo](https://github.com/surosevic02/Portfolio/blob/main/Passman/Screenshots/Demo.png?raw=true)
![CreateFile](https://github.com/surosevic02/Portfolio/blob/main/Passman/Screenshots/CreateFile.png?raw=true)
![Dark Mode](https://github.com/surosevic02/Portfolio/blob/main/Passman/Screenshots/Dark.png?raw=true)
![Light Mode](https://github.com/surosevic02/Portfolio/blob/main/Passman/Screenshots/Light.png?raw=true)


## Notes

For those who wish to compile this project on their own, this project was compiled on Visual Studio 2022. You will need to download and Compile the OpenSSL library (x64 Debug) and (x64 Release) (There are tutorials online on how to do this). You will also need to add the following libraries to the linker:

- windowsapp.lib
- dwmapi.lib
- Comctl32.lib
- ComDlg32.lib
- libcrypto.lib (OpenSSL)
- libssl.lib (OpenSSL)