// compile with: /D_UNICODE /DUNICODE /DWIN32 /D_WINDOWS /c
#include <windows.h>
#include <windowsx.h>
#include <stdlib.h>
#include <string.h>
#include <tchar.h>
#include <dwmapi.h>
#include <CommCtrl.h>
#include <commdlg.h>
#include "resource.h"
#include "Passman.h"

// Enabling Visual Styles

#pragma comment(linker,"\"/manifestdependency:type='win32' \
name='Microsoft.Windows.Common-Controls' version='6.0.0.0' \
processorArchitecture='*' publicKeyToken='6595b64144ccf1df' language='*'\"")

// Global defnitions
#ifndef DWMWA_USE_IMMERSIVE_DARK_MODE
#define DWMWA_USE_IMMERSIVE_DARK_MODE 20
#endif

// Macros
// Variables
#define KEY_LENGTH 32
#define ACCPASSLEN 24

// Macros for the different windows / buttons
#define DELETE_BUTTON (100) // Delete account password button
#define COPYAC_BUTTON (200) // Copy acc password button
#define ADDACC_BUTTON (300) // Add/Change account button
#define BROWSE_BUTTON (400) // Browse file button
#define CREATE_BUTTON (500) // This button will create a file
#define REENCRYPT_BUTTON (600) // Button for reencrypting
//#define PASSKEY (700) // Password key entry

// Global variables
// Key for accessing the file
std::string filekey = "";

// Boolean for checking if valid file is slected
bool fileset = false;

// Amount of accounts stored in the file
int accAmmount = 0;

// File location
std::string fileloc;

// The main window class name.
static char szWindowClass[] = "DesktopApp";

// The string that appears in the application's title bar.
static char szTitle[] = "PassMan";

// Stored instance handle for use in Win32 API calls such as FindResource
HINSTANCE hInst;

// Forward declarations of functions included in this code module:
LRESULT CALLBACK WndProc(HWND, UINT, WPARAM, LPARAM);

LRESULT CALLBACK PasswordProc(HWND, UINT, WPARAM, LPARAM);

// Default font
static HFONT defaultFont = NULL;

// Window handlers
HWND passLocEdit, passNewEdit, userNewEdit, accountsDropdown;

// Background color variable
HBRUSH bgcolor = (HBRUSH)(COLOR_BTNFACE + 1);

// Text color
COLORREF textColor = 0x00000000;

// Window color
COLORREF windowColor = COLOR_BTNFACE;

// Button color and brush
DWORD btnFaceCol = GetSysColor(COLOR_BTNFACE);
COLORREF buttonFaceColor = RGB(GetRValue(btnFaceCol), GetGValue(btnFaceCol), GetBValue(btnFaceCol));
COLORREF buttonColor = buttonFaceColor;

// Boolean for dark mode
bool darkMode = false;

// Function for checking dark mode
void CheckDarkmode(HWND hWnd)
{
    BOOL dark = FALSE; // Default dark mode off
    DWORD val; // Value of dark mode
    DWORD dataSize = sizeof(val);
    char subkey[] = "Software\\Microsoft\\Windows\\CurrentVersion\\Themes\\Personalize"; // Path of dark mode enable
    char name[] = "AppsUseLightTheme"; // Name of the key to check
    
    if (ERROR_SUCCESS == RegGetValueA(HKEY_CURRENT_USER, subkey, name, RRF_RT_DWORD, nullptr, &val, &dataSize)) {
        dark = !(bool)val;
    }
    
    if (dark) {
        bgcolor = CreateSolidBrush(RGB(25, 25, 25));
        windowColor = RGB(25, 25, 25);
        buttonColor = RGB(25, 25, 25);
        textColor = 0x00FFFFFF;
        darkMode = true;
    }
    else {
        bgcolor = (HBRUSH)(COLOR_BTNFACE + 1);
        windowColor = RGB(255, 255, 255);
        buttonColor = buttonFaceColor;
        textColor = 0x00000000;
        darkMode = false;
    }

    ::DwmSetWindowAttribute(hWnd, DWMWA_USE_IMMERSIVE_DARK_MODE, &dark, sizeof(dark)); // Apply the change to the title 
    return;
}

// Main function
int WINAPI WinMain(
    _In_ HINSTANCE hInstance,
    _In_opt_ HINSTANCE hPrevInstance,
    _In_ LPSTR     lpCmdLine,
    _In_ int       nCmdShow
)
{
    // Accelerator table
    HACCEL haccel;

    // Window class
    WNDCLASSA wcex;

    // Window properties are defined here
    wcex.style = CS_HREDRAW | CS_VREDRAW;
    wcex.lpfnWndProc = WndProc;
    wcex.cbClsExtra = 0;
    wcex.cbWndExtra = 0;
    wcex.hInstance = hInstance;
    wcex.hIcon = LoadIcon(wcex.hInstance, MAKEINTRESOURCE(IDE_ICON));
    wcex.hCursor = LoadCursor(NULL, IDC_ARROW);
    wcex.hbrBackground = bgcolor;
    wcex.lpszMenuName = NULL;
    wcex.lpszClassName = szWindowClass;

    if (!RegisterClassA(&wcex))
    {
        MessageBoxA(NULL,
            "Call to RegisterClassA failed!",
            "Windows Desktop Guided Tour",
            NULL);

        return 1;
    }

    // Store instance handle in our global variable
    hInst = hInstance;

    // The parameters to CreateWindowEx explained:
    // WS_EX_OVERLAPPEDWINDOW : An optional extended window style.
    // szWindowClass: the name of the application
    // szTitle: the text that appears in the title bar
    // WS_OVERLAPPEDWINDOW: the type of window to create
    // CW_USEDEFAULT, CW_USEDEFAULT: initial position (x, y)
    // 500, 100: initial size (width, length)
    // NULL: the parent of this window
    // NULL: this application does not have a menu bar
    // hInstance: the first parameter from WinMain
    // NULL: not used in this application
    HWND hWnd = CreateWindowA(
        szWindowClass,
        szTitle,
        WS_SYSMENU | WS_MINIMIZEBOX | WS_CAPTION,
        CW_USEDEFAULT, CW_USEDEFAULT,
        500, 380,
        NULL,
        NULL,
        hInstance,
        NULL
    );

    if (!hWnd)
    {
        MessageBoxA(NULL,
            "Call to CreateWindow failed!",
            "Windows Desktop Guided Tour",
            NULL);

        return 1;
    }

    // Load Accelerator table
    haccel = LoadAcceleratorsA(hInstance, "FILEACCELERATORS");

    // The parameters to ShowWindow explained:
    // hWnd: the value returned from CreateWindow
    // nCmdShow: the fourth parameter from WinMain
    CheckDarkmode(hWnd);
    ShowWindow(hWnd, nCmdShow);
    UpdateWindow(hWnd);

    // Main message loop:
    MSG msg;
    while (GetMessageA(&msg, NULL, 0, 0))
    {
        if (!TranslateAcceleratorA(hWnd, haccel, &msg) && !IsDialogMessageA(hWnd, &msg)) {
            TranslateMessage(&msg);
            DispatchMessageA(&msg);
        }
    }

    return (int)msg.wParam;
}

void DrawElements(HWND hWnd) {
    // Window Standardize sizes
    int buttonHeight = 22;
    int buttonWidth = 75;
    int textBoxHeight = 21;
    int staticHeight = 16;
    int staticWidth = 200;
    int etchedHeight = 5;
    int comboBoxHeight = 100;
    int windowWidth = 465;

    // X position for the entire application and spacing for different parts of the application
    int startX = 10;
    int partSpace = 50;
    int labelSpace = 16;
    int boxSpace = 20;
    int textBoxWidth = 380;
    int comboBoxWidth = 300;
    int buttonDelete = comboBoxWidth + 20;
    int buttonTextBoxX = startX + textBoxWidth + 10;

    // First part of the application ==============================================================
    // Positions for first part of the application
    int retriY = 10;
    int accY = retriY + labelSpace;
    int accSelY = accY + boxSpace;

    // List of accounts to get password from
    HWND rtl = CreateWindowA(
        "STATIC",
        "Retrieve:",
        WS_VISIBLE | WS_CHILD,
        startX, retriY,
        staticWidth, staticHeight,
        hWnd,
        NULL,
        (HINSTANCE)GetWindowLongPtr(hWnd, GWLP_HINSTANCE),
        NULL
    );
    SendMessageA(rtl, WM_SETFONT, (WPARAM)defaultFont, (LPARAM)MAKELONG(TRUE, 0));

    HWND acl = CreateWindowA(
        "STATIC",
        "Accounts",
        WS_VISIBLE | WS_CHILD,
        startX, accY,
        staticWidth, staticHeight,
        hWnd,
        NULL,
        (HINSTANCE)GetWindowLongPtr(hWnd, GWLP_HINSTANCE),
        NULL
    );
    SendMessageA(acl, WM_SETFONT, (WPARAM)defaultFont, (LPARAM)MAKELONG(TRUE, 0));

    // Drop down
    accountsDropdown =  CreateWindowA(
        "COMBOBOX",
        "",
        CBS_DROPDOWNLIST | CBS_HASSTRINGS | WS_OVERLAPPED | WS_CHILD | WS_VISIBLE | WS_TABSTOP,
        startX, accSelY,
        comboBoxWidth, comboBoxHeight,
        hWnd,
        NULL,
        (HINSTANCE)GetWindowLongPtr(hWnd, GWLP_HINSTANCE),
        NULL
    );
    SendMessageA(accountsDropdown, WM_SETFONT, (WPARAM)defaultFont, (LPARAM)MAKELONG(TRUE, 0));

    // Delete Button
    HWND dlb = CreateWindowA(
        "BUTTON",
        "Delete",
        WS_VISIBLE | WS_CHILD | WS_TABSTOP | BS_PUSHBUTTON,
        buttonDelete, accSelY,
        buttonWidth, buttonHeight,
        hWnd,
        (HMENU)DELETE_BUTTON,
        (HINSTANCE)GetWindowLongPtr(hWnd, GWLP_HINSTANCE),
        NULL
    );
    SendMessageA(dlb, WM_SETFONT, (WPARAM)defaultFont, (LPARAM)MAKELONG(TRUE, 0));

    // Copy Button
    HWND cpb = CreateWindowA(
        "BUTTON",
        "Copy",
        WS_VISIBLE | WS_CHILD | WS_TABSTOP | BS_PUSHBUTTON,
        buttonTextBoxX, accSelY,
        buttonWidth, buttonHeight,
        hWnd,
        (HMENU)COPYAC_BUTTON,
        (HINSTANCE)GetWindowLongPtr(hWnd, GWLP_HINSTANCE),
        NULL
    );
    SendMessageA(cpb, WM_SETFONT, (WPARAM)defaultFont, (LPARAM)MAKELONG(TRUE, 0));

    // Second part of the application ===========================================================
    // Positioning for the second part of the application
    int secondY = accSelY + partSpace;
    int accNameY = secondY + labelSpace;
    int accBoxY = accNameY + boxSpace;
    int passLabelY = accBoxY + labelSpace + 10;
    int passBoxY = passLabelY + boxSpace;

    // Etched divider
    CreateWindowA(
        "STATIC",
        NULL,
        WS_VISIBLE | WS_CHILD | SS_ETCHEDHORZ,
        startX, accSelY + buttonHeight + 15,
        windowWidth, etchedHeight,
        hWnd,
        NULL,
        (HINSTANCE)GetWindowLongPtr(hWnd, GWLP_HINSTANCE),
        NULL
    );

    // Add/Update label
    HWND aul = CreateWindowA(
        "STATIC",
        "Add/Update Password:",
        WS_VISIBLE | WS_CHILD,
        startX, secondY,
        staticWidth, staticHeight,
        hWnd,
        NULL,
        (HINSTANCE)GetWindowLongPtr(hWnd, GWLP_HINSTANCE),
        NULL
    );
    SendMessageA(aul, WM_SETFONT, (WPARAM)defaultFont, (LPARAM)MAKELONG(TRUE, 0));

    // Account name label
    std::string accLabel = "Account (" + std::to_string(ACCPASSLEN) + " Characters Max)";
    HWND anl = CreateWindowA(
        "STATIC",
        &accLabel[0],
        WS_VISIBLE | WS_CHILD,
        startX, accNameY,
        staticWidth, staticHeight,
        hWnd,
        NULL,
        (HINSTANCE)GetWindowLongPtr(hWnd, GWLP_HINSTANCE),
        NULL
    );
    SendMessageA(anl, WM_SETFONT, (WPARAM)defaultFont, (LPARAM)MAKELONG(TRUE, 0));

    // Add account name
    userNewEdit = CreateWindowA(
        "EDIT",
        "",
        WS_VISIBLE | WS_CHILD | WS_BORDER | WS_TABSTOP,
        startX, accBoxY,
        windowWidth, textBoxHeight,
        hWnd,
        NULL,
        (HINSTANCE)GetWindowLongPtr(hWnd, GWLP_HINSTANCE),
        NULL
    );
    SendMessageA(userNewEdit, WM_SETFONT, (WPARAM)defaultFont, (LPARAM)MAKELONG(TRUE, 0));

    // Password label
    std::string passLabel = "Password (" + std::to_string(ACCPASSLEN) + " Characters Max)";
    HWND pwl = CreateWindowA(
        "STATIC",
        &passLabel[0],
        WS_VISIBLE | WS_CHILD,
        startX, passLabelY,
        staticWidth, staticHeight,
        hWnd,
        NULL,
        (HINSTANCE)GetWindowLongPtr(hWnd, GWLP_HINSTANCE),
        NULL
    );
    SendMessageA(pwl, WM_SETFONT, (WPARAM)defaultFont, (LPARAM)MAKELONG(TRUE, 0));

    // Password box
    passNewEdit = CreateWindowA(
        "EDIT",
        "",
        WS_VISIBLE | WS_CHILD | WS_BORDER | WS_TABSTOP | ES_PASSWORD,
        startX, passBoxY,
        textBoxWidth, textBoxHeight,
        hWnd,
        NULL,
        (HINSTANCE)GetWindowLongPtr(hWnd, GWLP_HINSTANCE),
        NULL
    );
    SendMessageA(passNewEdit, WM_SETFONT, (WPARAM)defaultFont, (LPARAM)MAKELONG(TRUE, 0));

    // Add account button
    HWND adb = CreateWindowA(
        "BUTTON",
        "Add",
        WS_VISIBLE | WS_CHILD | WS_TABSTOP | BS_PUSHBUTTON,
        buttonTextBoxX, passBoxY,
        buttonWidth, buttonHeight,
        hWnd,
        (HMENU)ADDACC_BUTTON,
        (HINSTANCE)GetWindowLongPtr(hWnd, GWLP_HINSTANCE),
        NULL
    );
    SendMessageA(adb, WM_SETFONT, (WPARAM)defaultFont, (LPARAM)MAKELONG(TRUE, 0));

    // Third part of the application =======================================================
    // Positioning for the third part of the application
    int thirdY = passBoxY + partSpace;
    int fileLocY = thirdY + boxSpace;
    int fileLocButtonsY = fileLocY + boxSpace + 10;

    // Etched divider
    CreateWindowA(
        "STATIC",
        NULL,
        WS_VISIBLE | WS_CHILD | SS_ETCHEDHORZ,
        startX, passBoxY + buttonHeight + 15,
        windowWidth, etchedHeight,
        hWnd,
        NULL,
        (HINSTANCE)GetWindowLongPtr(hWnd, GWLP_HINSTANCE),
        NULL
    );

    // Location label
    HWND lol = CreateWindowA(
        "STATIC",
        "Password(s) File:",
        WS_VISIBLE | WS_CHILD,
        startX, thirdY,
        staticWidth, staticHeight,
        hWnd,
        NULL,
        (HINSTANCE)GetWindowLongPtr(hWnd, GWLP_HINSTANCE),
        NULL
    );
    SendMessageA(lol, WM_SETFONT, (WPARAM)defaultFont, (LPARAM)MAKELONG(TRUE, 0));

    // File location box
    passLocEdit = CreateWindowA(
        "Edit",
        "",
        ES_AUTOHSCROLL | ES_MULTILINE | WS_VISIBLE | WS_CHILD | WS_BORDER,
        startX, fileLocY,
        windowWidth, textBoxHeight,
        hWnd,
        NULL,
        (HINSTANCE)GetWindowLongPtr(hWnd, GWLP_HINSTANCE),
        NULL
    );
    SendMessageA(passLocEdit, WM_SETFONT, (WPARAM)defaultFont, (LPARAM)MAKELONG(TRUE, 0));

    // Renecrypt password file
    HWND reb = CreateWindowA(
        "BUTTON",
        "Re-Encrypt",
        WS_VISIBLE | WS_CHILD | WS_TABSTOP | BS_PUSHBUTTON,
        startX, fileLocButtonsY,
        buttonWidth, buttonHeight,
        hWnd,
        (HMENU)REENCRYPT_BUTTON,
        (HINSTANCE)GetWindowLongPtr(hWnd, GWLP_HINSTANCE),
        NULL
    );
    SendMessageA(reb, WM_SETFONT, (WPARAM)defaultFont, (LPARAM)MAKELONG(TRUE, 0));

    // Create file at location
    HWND nwb = CreateWindowA(
        "BUTTON",
        "New",
        WS_VISIBLE | WS_CHILD | WS_TABSTOP | BS_PUSHBUTTON,
        buttonDelete, fileLocButtonsY,
        buttonWidth, buttonHeight,
        hWnd,
        (HMENU)CREATE_BUTTON,
        (HINSTANCE)GetWindowLongPtr(hWnd, GWLP_HINSTANCE),
        NULL
    );
    SendMessageA(nwb, WM_SETFONT, (WPARAM)defaultFont, (LPARAM)MAKELONG(TRUE, 0));

    // Browse file location
    HWND ldb = CreateWindowA(
        "BUTTON",
        "Load",
        WS_VISIBLE | WS_CHILD | WS_TABSTOP | BS_PUSHBUTTON,
        buttonTextBoxX, fileLocButtonsY,
        buttonWidth, buttonHeight,
        hWnd,
        (HMENU)BROWSE_BUTTON,
        (HINSTANCE)GetWindowLongPtr(hWnd, GWLP_HINSTANCE),
        NULL
    );
    SendMessageA(ldb, WM_SETFONT, (WPARAM)defaultFont, (LPARAM)MAKELONG(TRUE, 0));
    
}

// This function sets up the open file dialog
// the int option will determine if we are
// opening an existing file or creating a new one
std::string OpenFile(HWND hWnd, int option)
{
    OPENFILENAMEA ofn = {0};
    char filename[MAX_PATH] = "";

    ZeroMemory(&ofn, sizeof(OPENFILENAMEA));

    ofn.lStructSize = sizeof(OPENFILENAMEA);
    ofn.hwndOwner = hWnd;
    ofn.lpstrFile = filename;
    ofn.lpstrFile[0] = '\0';
    ofn.nMaxFile = MAX_PATH;
    ofn.lpstrFilter = "Binary Files (*.bin)\0*.bin*\0\0";
    ofn.nFilterIndex = 1;
    ofn.Flags = OFN_PATHMUSTEXIST;
    ofn.nFileExtension = true;

    if (option == 1) {
        ofn.Flags |= OFN_FILEMUSTEXIST;
        if (!GetOpenFileNameA(&ofn)) return "";
    }

    if (option == 2) {
        ofn.Flags |= OFN_CREATEPROMPT;
        if (!GetSaveFileNameA(&ofn)) return "";
    }
    
    return filename;
}

// This is the password box function
// that will be called to configure
// the password box
bool PassWordBox(HWND hWnd) {
    return DialogBoxA(hInst, MAKEINTRESOURCEA(IDD_PASSWORDDLG), hWnd, PasswordProc);
}

// This function will parse the password
// that is recieved from the dialog box
//void ParsePass(char *key) {
//    filekey = key;
//}

// This is the process for the password dialog box
// This function will create a bassword box based
// on the users actions and process the password
INT_PTR PasswordProc(HWND hDlg, UINT message, WPARAM wParam, LPARAM lParam)
{
    char lpszPassword[KEY_LENGTH];
    WORD cchPassword;

    switch (message)
    {
        case WM_INITDIALOG:
            SendDlgItemMessageA(
                hDlg,
                IDE_PASSWORDEDIT,
                EM_SETPASSWORDCHAR,
                (WPARAM)'*',
                (LPARAM)0);

            SendMessageA(hDlg,
                DM_SETDEFID,
                (WPARAM)IDCANCEL,
                (LPARAM)0);

            return true;
        
        case WM_COMMAND:
        {
            
            if (HIWORD(wParam) == EN_CHANGE &&
                LOWORD(wParam) == IDE_PASSWORDEDIT)
            {
                SendMessageA(hDlg, DM_SETDEFID, (WPARAM)IDOK, (LPARAM)0);
            }
            switch (wParam)
            {
            case IDOK:
                {
                // First get length of key
                cchPassword = (WORD)SendDlgItemMessageA(hDlg,
                    IDE_PASSWORDEDIT,
                    EM_LINELENGTH,
                    (WPARAM)0,
                    (LPARAM)0);

                if (cchPassword >= KEY_LENGTH || cchPassword == 0)
                {
                    MessageBoxA(hDlg,
                        "Invalid character length.",
                        "Error",
                        MB_OK);

                    EndDialog(hDlg, FALSE);
                    return FALSE;
                }

                *((LPWORD)lpszPassword) = cchPassword;

                SendDlgItemMessageA(hDlg,
                    IDE_PASSWORDEDIT,
                    EM_GETLINE,
                    (WPARAM)0,
                    (LPARAM)lpszPassword);

                lpszPassword[cchPassword] = 0;

                filekey = lpszPassword;

                EndDialog(hDlg, TRUE);
                return TRUE;
                }

            case IDCANCEL:
                EndDialog(hDlg, FALSE);
                return FALSE;
            }
        }
        return 0;
    }
    return FALSE;

    UNREFERENCED_PARAMETER(lParam);
}

//  FUNCTION: WndProc(HWND, UINT, WPARAM, LPARAM)
//
//  PURPOSE:  Processes messages for the main window.
//
//  WM_PAINT    - Paint the main window
//  WM_DESTROY  - post a quit message and return
LRESULT CALLBACK WndProc(HWND hWnd, UINT message, WPARAM wParam, LPARAM lParam)
{
    PAINTSTRUCT ps;
    HDC hdc;
    HWND editBoxHandle;
    HFONT fontMain;
    HBRUSH solidBrush = CreateSolidBrush(windowColor);
    HBRUSH buttonBrush = CreateSolidBrush(buttonColor);
    INITCOMMONCONTROLSEX icex;
    icex.dwSize = sizeof(INITCOMMONCONTROLSEX);
    icex.dwICC = ICC_LISTVIEW_CLASSES;
    InitCommonControlsEx(&icex);

    //if (darkMode) {solidBrush = CreateSolidBrush(windowColor);}

    switch (message)
    {
        case WM_CREATE:
        {
            hdc = GetDC(hWnd);

            LOGFONTA logfont = { 0 };
            logfont.lfHeight = -MulDiv(9, GetDeviceCaps(hdc, LOGPIXELSY), 72);
            logfont.lfWeight = FW_LIGHT;
            strcpy_s(logfont.lfFaceName, "Segoe UI");

            defaultFont = CreateFontIndirectA(&logfont);
            ReleaseDC(hWnd, hdc);
            DrawElements(hWnd);
            break;
        }

        // This is where the application is drawn
        case WM_PAINT:
        {
            hdc = BeginPaint(hWnd, &ps);
            EndPaint(hWnd, &ps);
            break;
        }

        // Clear the background and redraw it for
        // Different application mode
        case WM_ERASEBKGND:
        {
            hdc = (HDC)(wParam);
            RECT rc; 
            GetClientRect(hWnd, &rc);
            FillRect(hdc, &rc, bgcolor);
            return 1;
        }

        // Commands sent to the window
        case WM_COMMAND:
        {
            switch (LOWORD(wParam)) {
                // Select all in edit box
                case ID_SALL:
                {
                    editBoxHandle = GetFocus();
                    SendMessageA(editBoxHandle, EM_SETSEL, 0, -1);
                    break;
                }

                // Delete all in edit box
                case ID_DALL:
                {
                    editBoxHandle = GetFocus();
                    char wClassName[5];
                    GetClassNameA(editBoxHandle, wClassName, 5);

                    if (strcmp(wClassName, "Edit") == 0) {
                        SetWindowTextA(editBoxHandle, 0);
                    }
                    break;
                }

                // Button to delete the account in the combo box
                case DELETE_BUTTON:
                {
                    fileset = FileExists(&fileloc[0]);
                    if (!fileset) {
                        MessageBoxA(
                            hWnd,
                            "No file selected!",
                            "No File Selected",
                            MB_ICONEXCLAMATION | MB_OK
                        );
                        break;
                    }

                    // Get the index of the account
                    int idx = SendMessageA(accountsDropdown, CB_GETCURSEL, 0, 0);
                    if (idx < 0) break;

                    // Prompt user if they want to delete their account
                    int result = MessageBoxA(
                        hWnd,
                        "Are you sure you want to delete the selected account?",
                        "Delete Account",
                        MB_ICONQUESTION | MB_YESNO
                    );

                    if (result == IDNO) break;

                    // Delete the account
                    if (DeleteAccount(&fileloc[0], idx+1)) {
                        MessageBoxA(
                            hWnd,
                            "Account deleted!",
                            "Success",
                            MB_ICONINFORMATION | MB_OK
                        );
                        ComboBox_DeleteString(accountsDropdown, idx);
                        ComboBox_SetCurSel(accountsDropdown, idx-1);
                        accAmmount -= 1;
                    }

                    // If unable to prompt error
                    else {
                        MessageBoxA(
                            hWnd,
                            "Unable to delete account!",
                            "Error",
                            MB_ICONERROR | MB_OK
                        );
                    }

                    break;
                }

                // Copy the account password from combo box
                case COPYAC_BUTTON:
                {
                    fileset = FileExists(&fileloc[0]);
                    if (!fileset) {
                        MessageBoxA(
                            hWnd,
                            "No file selected!",
                            "No File Selected",
                            MB_ICONEXCLAMATION | MB_OK
                        );
                        break;
                    }

                    int idx = SendMessageA(accountsDropdown, CB_GETCURSEL, 0, 0);
                    if (idx < 0) break;

                    std::string pwd = GetPassword(&fileloc[0], (unsigned char*)&filekey[0], idx+1);

                    HGLOBAL hMem = GlobalAlloc(GMEM_MOVEABLE, ACCPASSLEN);
                    memcpy(GlobalLock(hMem), &pwd[0], ACCPASSLEN);
                    GlobalUnlock(hMem);

                    OpenClipboard(0);
                    EmptyClipboard();
                    SetClipboardData(CF_TEXT, hMem);
                    CloseClipboard();

                    break;
                }

                // Add account to file and combobox
                case ADDACC_BUTTON:
                {
                    if (fileset = FileExists(&fileloc[0])) {
                        WORD accLen;
                        WORD passLen;

                        accLen = (WORD)SendMessageA(userNewEdit, EM_LINELENGTH, 0, 0);
                        passLen = (WORD)SendMessageA(passNewEdit, EM_LINELENGTH, 0, 0);

                        if (passLen > ACCPASSLEN || accLen > ACCPASSLEN || passLen <= 0 || accLen <= 0) {
                            std::string lenMsg = "The account name and password length must be between 1 and " + std::to_string(ACCPASSLEN);
                            MessageBoxA(
                                hWnd,
                                &lenMsg[0],
                                "Invalid Length",
                                MB_ICONERROR | MB_OK
                            );
                            break;
                        }

                        else {
                            // Vars for the username and password to be added
                            unsigned char accName[ACCPASSLEN];
                            unsigned char accPass[ACCPASSLEN];

                            GetWindowTextA(userNewEdit, (LPSTR)&accName[0], ACCPASSLEN);
                            GetWindowTextA(passNewEdit, (LPSTR)&accPass[0], ACCPASSLEN);

                            // Add the account to the drop down
                            SendMessageA(accountsDropdown, CB_ADDSTRING, 0, (LPARAM)&accName[0]);

                            // Add the account to the file
                            EncryptWrite(&fileloc[0], accAmmount, (unsigned char*)&filekey[0], &accName[0], &accPass[0]);

                            // Set cursor in Combo box to currently added item
                            ComboBox_SetCurSel(accountsDropdown, accAmmount-1);

                            accAmmount += 1;

                            // Cleare the textboxes
                            SetWindowTextA(userNewEdit, "");
                            SetWindowTextA(passNewEdit, "");

                            // Inform the user account has been added
                            MessageBoxA(
                                hWnd,
                                "Account added successfully",
                                "Account Added",
                                MB_ICONINFORMATION | MB_OK
                            );
                        }

                    } else {
                        MessageBoxA(
                            hWnd,
                            "No file selected!",
                            "No File Selected",
                            MB_ICONEXCLAMATION | MB_OK
                        );
                    }

                    break;
                }

                // Re-Encrypt the current password file
                case REENCRYPT_BUTTON:
                {
                    if (fileset = FileExists(&fileloc[0])) {
                        int result = MessageBoxA(
                            hWnd,
                            "Are you sure you want to Re-Encrypt the selected file?",
                            "Re-Encrypt File",
                            MB_ICONQUESTION | MB_YESNO
                        );

                        if (result == IDNO) break;

                        std::string oldkey = filekey;

                        switch (PassWordBox(hWnd)) {
                        case true:
                            if (ReEncrypt(&fileloc[0], (unsigned char*)&oldkey[0], (unsigned char*)&filekey[0])) {
                                MessageBoxA(
                                    hWnd,
                                    "File has been Re-Encrypted successfully!",
                                    "Re-Encryption",
                                    MB_ICONINFORMATION | MB_OK
                                );
                            }

                            else {
                                MessageBoxA(
                                    hWnd,
                                    "Unable to Re-Encrypt file.",
                                    "Error",
                                    MB_ICONERROR | MB_OK
                                );
                            }
                            break;

                        default:
                        MessageBoxA(
                            hWnd,
                            "Invalid Re-Encryption key entered!",
                            "File Key",
                            MB_ICONERROR | MB_OK
                        );
                        break;
                        }

                    }

                    else {
                        MessageBoxA(
                            hWnd,
                            "No file selected!",
                            "No File Selected",
                            MB_ICONEXCLAMATION | MB_OK
                        );
                    }

                    break;
                }

                // Look for a file to load
                case BROWSE_BUTTON:
                {
                    std::vector<std::string> accs;

                    fileloc = OpenFile(hWnd, 1);

                    if (fileloc.length() < 4) {
                        fileset = false;
                        break;
                    }

                    if (fileloc.substr(fileloc.length() - 4) != ".bin") {
                        fileset = false;
                        break;
                    }

                    SetWindowTextA(passLocEdit, fileloc.c_str());

                    accAmmount = CheckFile(&fileloc[0]);
                    if (1 > accAmmount) {
                        MessageBoxA(
                            hWnd,
                            "The selected file is not a password file!",
                            "Unknown File",
                            MB_ICONEXCLAMATION | MB_OK
                        );
                        fileset = false;
                        break;
                    } 

                    else {
                        if (PassWordBox(hWnd) && CheckKey(&fileloc[0], (unsigned char*)&filekey[0])) {
                            fileset = true;
                            SendMessageA(accountsDropdown, CB_RESETCONTENT, 0, 0);

                            accs = GetAccounts(&fileloc[0], (unsigned char*)&filekey[0]);
                            for (int i = 0; i < accs.size(); i++) {
                                SendMessageA(accountsDropdown, CB_ADDSTRING, 0, (LPARAM)accs[i].c_str());
                            }

                            if (accs.size() >= 1) { ComboBox_SetCurSel(accountsDropdown, 0); }

                            break;
                        }

                        else {
                            fileset = false;
                            SendMessageA(accountsDropdown, CB_RESETCONTENT, 0, 0);
                            filekey = "";
                            MessageBoxA(
                                hWnd,
                                "Invalid key entered!",
                                "File Key",
                                MB_ICONERROR | MB_OK
                            );
                            break;
                        }
                    }
                    break;
                }

                // Create a new file
                case CREATE_BUTTON:
                {
                    fileloc = OpenFile(hWnd, 2);

                    if (fileloc == "") {
                        fileset = false;
                        break;
                    }

                    if (fileloc.length() < 4) {
                        fileloc += ".bin";
                    }

                    else if (fileloc.substr(fileloc.length() - 4) != ".bin") {
                        fileloc += ".bin";
                    }

                    SetWindowTextA(passLocEdit, fileloc.c_str());

                    // Existing password file may only be reincrypted
                    accAmmount = CheckFile(&fileloc[0]);
                    if (1 <= accAmmount) {
                        fileset = false;
                        MessageBoxA(
                            hWnd,
                            "Existing password file may only be reincrypted!",
                            "File Is Protected",
                            MB_ICONEXCLAMATION | MB_OK
                        );
                        break;
                    }

                    // Create file key
                    else {
                        MessageBoxA(
                            hWnd,
                            "File key is required for new file.",
                            "File Key",
                            MB_ICONINFORMATION | MB_OK
                        );

                        switch (PassWordBox(hWnd)) {
                        case true:
                            fileset = true;
                            accAmmount += 1;
                            SetCheck(&fileloc[0], (unsigned char*)&filekey[0]);
                            break;

                        default:
                            fileset = false;
                            MessageBoxA(
                                hWnd,
                                "Failed to create file key!",
                                "File Key",
                                MB_ICONEXCLAMATION | MB_OK
                            );
                            break;
                        }
                    }
                    break;
                }
                default:
                    break;
            }
        }
        break;

        // Static Color
        case WM_CTLCOLORSTATIC:
            SetBkMode((HDC)wParam, TRANSPARENT);
            SetTextColor((HDC)wParam, textColor);
            return (LRESULT)(HBRUSH)GetStockObject(HOLLOW_BRUSH);

        // Combobox List box
        case WM_CTLCOLORLISTBOX:
        // Edit Color
        case WM_CTLCOLOREDIT:
            SetBkColor((HDC)wParam, windowColor);
            SetTextColor((HDC)wParam, textColor);
            return (LRESULT)(HBRUSH)solidBrush;

        // Edit Button
        case WM_CTLCOLORBTN:
        {
            SetBkColor((HDC)wParam, buttonColor);
            return (LRESULT)(HBRUSH)buttonBrush;
        }

        case WM_SETTINGCHANGE:
            // Check when a setting is changed especially for dark mode
            CheckDarkmode(hWnd);
            break;

        case WM_DESTROY:
            // This is where the application is closed out of
            DeleteObject(solidBrush);
            DeleteObject(buttonBrush);
            DeleteObject(defaultFont);
            PostQuitMessage(0);
            break;

        default:
            return DefWindowProcA(hWnd, message, wParam, lParam);
            break;
    }

    return 0;
}