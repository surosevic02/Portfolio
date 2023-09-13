#pragma once

#ifndef PASSMAN_H
#define PASSMAN_H

#include <iostream>
#include <fstream>
#include <array>
#include <vector>
#include <string>
#include <openssl/evp.h>
#include <openssl/conf.h>

extern std::vector<std::string> GetAccounts(char* fileloc, unsigned char* key);
extern std::string GetPassword(char* fileloc, unsigned char* key, int loc);
extern bool FileExists(char* fileloc);
int CheckFile(char* fileloc);
extern void EncryptWrite(char* fileloc, int loc, unsigned char* key, unsigned char* acc, unsigned char* psw);
extern void SetCheck(char* fileloc, unsigned char* key);
extern bool CheckKey(char* fileloc, unsigned char* key);
extern bool ReEncrypt(char* fileloc, unsigned char* oldkey, unsigned char* newkey);
extern bool DeleteAccount(char* fileloc, int loc);

#endif PASSMAN_H