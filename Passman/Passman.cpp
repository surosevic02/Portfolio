#include "Passman.h"

// Definitions (Possibly move to Header)
#define UPLEN 64

// Definitions for the EncryptWrite and DecryptRead functions
#define UNAME 0b01
#define PASSW 0b10

// File stream for reading the file
std::fstream file;

// Struct for storing the accounts
struct accounts
{
	std::array<unsigned char, UPLEN> name;
	std::array<unsigned char, UPLEN> pass;
};

// Checks if file exists and does nothing else
extern bool FileExists(char *fileloc) {
	file.open(fileloc);
	
	if (file.fail()) {
		file.close();
		return false;
	}

	file.close();
	return true;
}

// Check if the file exists by attempting to open it
// If exists then close the file and return end of bits
// If it doesn't exist then create the file and return 0
int CheckFile(char* fileloc)
{
	file.open(fileloc, std::ios::in | std::ios::binary);
	if (file)
	{
		std::streampos eofl;
		eofl = file.tellg();
		file.seekg(0, std::ios::end);
		eofl = file.tellg() - eofl;
		file.close();
		return (int)eofl / sizeof(accounts);
	}
	else
	{
		std::ofstream { fileloc };
		return 0;
	}
}

// Open the binary file
// Read the password struct that is requested
// Close the binary file
accounts ReadAcc(char* fileloc, int pos, int amt)
{
	accounts ret;
	file.open(fileloc, std::ios::in | std::ios::binary);
	file.seekg(pos * sizeof(accounts), std::ios_base::beg);
	file.read((char*)&ret, sizeof(accounts) * amt);
	file.close();
	return ret;
}

// Open the file in read mode
// Extract the account struct
// Close the binary file
void WriteAcc(char* fileloc, accounts acc, int pos)
{
	std::ofstream ofile;
	ofile.open(fileloc, std::ios::in | std::ios::binary);
	ofile.seekp(pos * sizeof(accounts), std::ios_base::beg);
	ofile.write((char*)&acc, sizeof(accounts));
	ofile.close();
	return;
}

// This function will encrypt a string
// then return an encrypted string
// that will eventually be stored in a file
int Encrypt(unsigned char* text, unsigned char* key, unsigned char* cipher)
{
	int len = 0;
	int ciph_len = 0;

	EVP_CIPHER_CTX* ctx = EVP_CIPHER_CTX_new();

	EVP_EncryptInit_ex(ctx, EVP_aes_256_cbc(), NULL, key, NULL);
	EVP_EncryptUpdate(ctx, cipher, &len, text, strlen((char*)text));
	ciph_len += len;

	EVP_EncryptFinal_ex(ctx, cipher + len, &len);
	ciph_len += len;

	EVP_CIPHER_CTX_free(ctx);

	cipher[UPLEN-1] = ciph_len;
	return ciph_len;
}

// Decrypt string given a key
// return the length of the
// decrypted string
int Decrypt(unsigned char* cipher, unsigned char* key, unsigned char* text)
{
	int len = 0;
	int text_len = 0;
	int clen = (int)cipher[UPLEN - 1];

	EVP_CIPHER_CTX* ctx = EVP_CIPHER_CTX_new();

	EVP_DecryptInit_ex(ctx, EVP_aes_256_cbc(), NULL, key, NULL);
	//int l = strlen((char*)cipher) + 1;
	EVP_DecryptUpdate(ctx, text, &len, cipher, clen);
	text_len += len;

	EVP_DecryptFinal_ex(ctx, text + len, &len);
	text_len += len;

	EVP_CIPHER_CTX_free(ctx);

	return text_len;
}

// This function will encrypt the account
// and store it into a file provided
// the location within the file
// the key to lock it
// the desired password and username
extern void EncryptWrite(char* fileloc, int loc, unsigned char* key, unsigned char* acc, unsigned char* psw)
{
	struct accounts account;
	int ch_len = 0;
	unsigned char* cname = new unsigned char[UPLEN];
	unsigned char* cpass = new unsigned char[UPLEN];

	// Encrypt username
	ch_len = Encrypt(acc, key, cname);
	std::copy(&cname[0], &cname[UPLEN], account.name.begin());

	// Encrypt password
	ch_len = Encrypt(psw, key, cpass);
	std::copy(&cpass[0], &cpass[UPLEN], account.pass.begin());

	// Write to file
	CheckFile(fileloc);
	WriteAcc(fileloc, account, loc);
	return;
}

// This function will decrypty
// the account name and or password
// given the file
// the location within the file
// the key and the mode of operation
accounts DecryptRead(char* fileloc, int loc, unsigned char* key, int mode)
{
	struct accounts tmp;
	struct accounts account;
	int pl_len = 0;
	unsigned char pname[UPLEN];
	unsigned char ppass[UPLEN];

	// Check the file then read from it
	CheckFile(fileloc);
	tmp = ReadAcc(fileloc, loc, 1);
	std::fill(account.name.begin(), account.name.end(), 0x00);
	std::fill(account.pass.begin(), account.pass.end(), 0x00);

	if (mode & 0b01)
	{
		// Decrypt Name
		pl_len = Decrypt((unsigned char*)&tmp.name, key, pname);
		std::copy(&pname[0], &pname[pl_len], account.name.begin());
	}

	if (mode & 0b10)
	{
		// Decrypt Password
		pl_len = Decrypt((unsigned char*)&tmp.pass, key, ppass);
		std::copy(&ppass[0], &ppass[pl_len], account.pass.begin());
	}

	return account;
}

// This function creates a dummy account
// that is used to check to see if the key
// the user provided is valid
extern void SetCheck(char* fileloc, unsigned char* key)
{
	unsigned char* ac = (unsigned char*)"Username Decryption Test! Check";
	unsigned char* pw = (unsigned char*)"Password Decryption Test! Check";

	CheckFile(fileloc);
	EncryptWrite(fileloc, 0, key, ac, pw);
	return;
}

// This function checks if the key
// provided is valid by attempting to
// decrypt the dummy file
extern bool CheckKey(char* fileloc, unsigned char* key)
{
	struct accounts testAccount;
	unsigned char* ac = (unsigned char*)"Username Decryption Test! Check";
	unsigned char* pw = (unsigned char*)"Password Decryption Test! Check";

	testAccount = DecryptRead(fileloc, 0, key, UNAME | PASSW);
	
	for (int i = 0; i < 31; i++)
	{
		if ((int)testAccount.name[i] == 0x00 || (int)testAccount.pass[i] == 0x00) break;
		if ((int)testAccount.name[i] != (int)ac[i]) return false;
		if ((int)testAccount.pass[i] != (int)pw[i]) return false;
	}

	return true;
}

// This function gets the list of
// accounts that the user can choose from
extern std::vector<std::string> GetAccounts(char* fileloc, unsigned char* key)
{
	struct accounts tmp;
	std::vector<std::string> availableAcc;
	int fend = CheckFile(fileloc);

	if (fend <= 1) return availableAcc;
	
	for (int i = 1; i < fend; i++)
	{
		tmp = DecryptRead(fileloc, i, key, UNAME);

		std::string nm;
		for (int j = 0; j < tmp.name.size(); j++)
		{
			if ((int)tmp.name[j] == 0x00) break;
			nm.push_back(tmp.name[j]);
		}

		availableAcc.push_back(nm);
	}

	return availableAcc;
}

// This function will return the password
// of an account given the file location
// the key and the location iwthin the file
extern std::string GetPassword(char* fileloc, unsigned char* key, int loc) 
{
	struct accounts tmp;
	int fend = CheckFile(fileloc);

	if (fend <= 1) return "";

	tmp = DecryptRead(fileloc, loc, key, PASSW);

	std::string ret;
	for (int i = 0; i < tmp.pass.size(); i++) {
		if ((int)tmp.pass[i] == 0x00) break;
		ret.push_back(tmp.pass[i]);
	}

	return ret;
}

// This function will go through each
// account, decrypt them, reencrypt them
// then check if the encryption is valid
// in the end returning a boolean
extern bool ReEncrypt(char* fileloc, unsigned char* oldkey, unsigned char* newkey)
{
	int fend = CheckFile(fileloc);
	struct accounts tmp;

	// If there is nothing in the file then return true
	if (fend == 0) return true;

	// Otherwise start from the beginning and decrypt the encrypt
	// This for loop goes through each of the structs
	for (int i = 0; i < fend; i++)
	{
		tmp = DecryptRead(fileloc, i, oldkey, UNAME | PASSW);
		int nameLoc = 0;
		int passLoc = 0;

		// This for loop goes through the usernames and passwords
		for (int j = 0; j < UPLEN; j++)
		{
			if (((int)tmp.name[nameLoc] == 0x00) && ((int)tmp.pass[nameLoc] == 0x00)) break;

			if ((int)tmp.name[nameLoc] != 0x00)
			{
				nameLoc++;
			}

			if ((int)tmp.pass[passLoc] != 0x00)
			{
				passLoc++;
			}
		}
		// Encrypt the username and password
		EncryptWrite(fileloc, i, newkey, (unsigned char*)&tmp.name[0], (unsigned char*)&tmp.pass[0]);
	}

	return CheckKey(fileloc, newkey);
}

// This function will delete the
// account at the specified location
// then it will move any data under it
// to its position
extern bool DeleteAccount(char* fileloc, int loc)
{
	accounts tmp;
	std::vector<accounts> accs;
	int fend = CheckFile(fileloc);

	if (loc <= 0) return true;
	if (loc >= fend) return false;

	for (int i = 0; i < fend; i++) 
	{
		if (i == loc) continue;

		tmp = ReadAcc(fileloc, i, 1);
		accs.push_back(tmp);
	}

	std::ofstream wfile;
	wfile.open(fileloc, std::ios::binary);
	for (int j = 0; j < accs.size(); j++)
	{
		wfile.write((char*)&accs[j], sizeof(accs[j]));
	}

	wfile.close();

	return true;
}