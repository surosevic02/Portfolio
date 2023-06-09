// FinalProjectRpg.cpp : Defines the entry point for the console application.
//
#include "stdafx.h"
#include <iostream>
#include <fstream>
#include <random>
#include <Windows.h>
#include <string>

using namespace std;

// Variables
int option = 0;
bool menu = true;
bool menuTwo = false;
int turn = 1;
int inputNum;
int genderOne;
int genderTwo;
int story;
string location;

// Player Variables
int expPlayer;
int playerHealth;
int playerStrength;
int playerArmour;
int playerSpeed;
int characterCreation;
string characterName;
char characterGender;
int playerCrit;
int playerTempStrength;
int playerTempSpeed;
int playerTempArmour;
int playerMaxHealth;
int armourIncrease;

// Enemy Varaiable
int damage;
string enemyName;
int enemyHealth;
int enemyStrength;
int enemyArmour;
int enemySpeed;
int enemyMoveset;
int enemyCrit;
int enemyTempArmour;
int eIncreaseArmour;
string eMoveOne;
string eMoveTwo;
string eMoveThree;
string eMoveFour;

// Enemies
// 0: HEALTH, 1: SPEED, 2: STRENGTH, 3: ARMOUR, 4: MOVESET
int batStats[5]{ 25, 10, 5, 5, 1 };
int soldierStats[5]{ 35, 5, 8, 7, 2 };

// Move Set
string moveSetOne[4]{"BITE", "SCREECH", "DIVE", "SCRATCH"};
string moveSetTwo[4]{"SWING", "BLOCK", "STAB", "CHARGE"};

// Gender Array
string genderNouns[4]{ "HE", "HIS", "SHE", "HER" };

HANDLE hConsole;

// Functions
void savePlayerStats(string characterName, int expPlayer, int playerStrength, int playerArmour, int playerHealth, int playerSpeed, int playerMaxHealth, char characterGender, int story);

void savePlayerStats(string characterName, int expPlayer, int playerStrength, int playerArmour, int playerHealth, int playerSpeed, int playerMaxHealth, char characterGender, int story) {
	fstream file;
	string filename = characterName + ".txt";
	filename = "C:\\Users\\iD Student\\Desktop\\Stefan U\\FinalProjectRpg\\FinalProjectRpg\\SaveData\\" + filename;
	file.open(filename, ios::out);

	cout << "Saving Character Data" << endl;

	file << characterName << endl;
	file << expPlayer << endl;
	file << playerStrength << endl;;
	file << playerArmour << endl;
	file << playerHealth << endl;
	file << playerSpeed << endl;
	file << characterGender << endl;;
	file << playerMaxHealth << endl;
	file << story << endl;

	file.close();
}

void setupPlayerStats(string &characterName, int &expPlayer, int &playerStrength, int &playerArmour, int &playerHealth, int &playerSpeed, int &playerMaxHealth, char &characterGender, int &story);

void setupPlayerStats(string &characterName, int &expPlayer, int &playerStrength, int &playerArmour, int &playerHealth, int &playerSpeed, int &playerMaxHealth, char &characterGender, int &story) {
	fstream file;
	string filename = characterName + ".txt";
	filename = "C:\\Users\\iD Student\\Desktop\\Stefan U\\FinalProjectRpg\\FinalProjectRpg\\SaveData\\" + filename;

	file.open(filename, ios::in);
	if (!file.is_open()) {
		cout << "File Does not Exist" << endl;
		menu = true;
		Sleep(1000);
	}
	else {
		cout << "Loading Character Data" << endl;

		file >> characterName;
		file >> expPlayer;
		file >> playerStrength;
		file >> playerArmour;
		file >> playerHealth;
		file >> playerSpeed;
		file >> characterGender;
		file >> playerMaxHealth;
		file >> story;

	}
}

void battle(string characterName, int &playerStrength, int &playerArmour, int &playerSpeed, string enemyName);

void battle(string characterName, int &playerStrength, int &playerArmour, int &playerSpeed, string enemyName) {
	hConsole = GetStdHandle(STD_OUTPUT_HANDLE);
	random_device rd;

	if (enemyName == "SOLDIER") {
		enemyHealth = soldierStats[0];
		enemySpeed = soldierStats[1];
		enemyStrength = soldierStats[2];
		enemyArmour = soldierStats[3];
		enemyMoveset = soldierStats[4];
	}

	if (enemyName == "BAT") {
		enemyHealth = batStats[0];
		enemySpeed = batStats[1];
		enemyStrength = batStats[2];
		enemyArmour = batStats[3];
		enemyMoveset = batStats[4];
	}

	if (enemyMoveset == 2) {
		eMoveOne = moveSetTwo[0];
		eMoveTwo = moveSetTwo[1];
		eMoveThree = moveSetTwo[2];
		eMoveFour = moveSetTwo[3];
	}

	if (enemyMoveset == 1) {
		eMoveOne = moveSetOne[0];
		eMoveTwo = moveSetOne[1];
		eMoveThree = moveSetOne[2];
		eMoveFour = moveSetOne[3];
	}
	
	system("CLS");

	playerTempArmour = playerArmour;
	enemyTempArmour = playerArmour;

	if (enemySpeed > playerSpeed) {
		turn = 1;
	}
	else if (enemySpeed <= playerSpeed) {
		turn = 2;
	}

	while (playerHealth > 0 && enemyHealth > 0) {
		if (turn == 1) {

			SetConsoleTextAttribute(hConsole, 4);
			cout << "ENEMY ENCOUNTER" << endl;
			cout << "OPPONENT: " << enemyName << endl;
			cout << " " << endl;
			SetConsoleTextAttribute(hConsole, 7);
			cout << "+-----------------------------------------------------+" << endl;
			cout << " " << characterName << "'s STATS" << endl;
			cout << " HEALTH:   "; SetConsoleTextAttribute(hConsole, 10); cout << playerHealth << endl; SetConsoleTextAttribute(hConsole, 7);
			cout << " STRENGTH: "; SetConsoleTextAttribute(hConsole, 10); cout << playerStrength << endl; SetConsoleTextAttribute(hConsole, 7);
			cout << " ARMOUR:   "; SetConsoleTextAttribute(hConsole, 10); cout << playerArmour << endl; SetConsoleTextAttribute(hConsole, 7);
			cout << "+-----------------------------------------------------+" << endl;
			cout << enemyName << "'s STATS" << endl;
			cout << " HEALTH:   "; SetConsoleTextAttribute(hConsole, 4); cout << enemyHealth << endl; SetConsoleTextAttribute(hConsole, 7);
			cout << " STRENGTH: "; SetConsoleTextAttribute(hConsole, 4); cout << enemyStrength << endl; SetConsoleTextAttribute(hConsole, 7);
			cout << " ARMOUR:   "; SetConsoleTextAttribute(hConsole, 4); cout << enemyArmour << endl; SetConsoleTextAttribute(hConsole, 7);
			cout << "+-----------------------------------------------------+" << endl;
			cout << " " << endl;
			cout << "OPPONENTS TURN" << endl;
			Sleep(2000);

			int eAttack = rd() % 4 + 0;
			int eCritical = rd() % 6 + 1;

			if (eCritical == 6 || eCritical == 3) {
				eCritical = 2;
			}
			else {
				eCritical = 0;
			}

			switch (eAttack) {
			case 0:
				SetConsoleTextAttribute(hConsole, 4);  cout << enemyName; SetConsoleTextAttribute(hConsole, 7);  cout << " USES "; SetConsoleTextAttribute(hConsole, 14); cout << eMoveOne << endl; SetConsoleTextAttribute(hConsole, 7);
				if ((enemyStrength + eCritical) - (playerArmour / 2) <= 2) {
					damage = 2;
					playerHealth -= 2;
				} 
				else {
					damage = (enemyStrength + eCritical) - (playerArmour / 2);
					playerHealth -= (enemyStrength + eCritical) - (playerArmour / 2);
				}
				SetConsoleTextAttribute(hConsole, 4);  cout << enemyName; SetConsoleTextAttribute(hConsole, 7); cout << " DEALT " << damage << " DAMAGE" << endl;
				if (eCritical == 2) {
					SetConsoleTextAttribute(hConsole, 4);  cout << enemyName; SetConsoleTextAttribute(hConsole, 7); cout << " DEALT + "; SetConsoleTextAttribute(hConsole, 4); cout << eCritical; SetConsoleTextAttribute(hConsole, 7); cout << " CRITICAL DAMAGE" << endl;
				}
				turn = 2;
				break;

			case 1:
				SetConsoleTextAttribute(hConsole, 4);  cout << enemyName; SetConsoleTextAttribute(hConsole, 7);  cout << " USES "; SetConsoleTextAttribute(hConsole, 14); cout << eMoveTwo << endl; SetConsoleTextAttribute(hConsole, 7);
				eIncreaseArmour = (5 + (enemyArmour / 5));
				enemyArmour += eIncreaseArmour;
				SetConsoleTextAttribute(hConsole, 4);  cout << enemyName; SetConsoleTextAttribute(hConsole, 7); cout << " INCREASED THEIR "; SetConsoleTextAttribute(hConsole, 6); cout << "ARMOUR "; SetConsoleTextAttribute(hConsole, 7); cout << "BY " << eIncreaseArmour << " POINTS" << endl;
				if (enemyArmour >= enemyTempArmour + 20) {
					enemyArmour = (enemyTempArmour + 20);

				}
				turn = 2;
				break;

			case 2:
				SetConsoleTextAttribute(hConsole, 4);  cout << enemyName; SetConsoleTextAttribute(hConsole, 7);  cout << " USES "; SetConsoleTextAttribute(hConsole, 14); cout << eMoveThree << endl; SetConsoleTextAttribute(hConsole, 7);
				if ((enemyStrength + eCritical) - (playerArmour / 2) <= 2) {
					damage = 2;
					playerHealth -= 2;
				}
				else {
					damage = (enemyStrength + eCritical) - (playerArmour / 2);
					playerHealth -= (enemyStrength + eCritical) - (playerArmour / 2);
				}
				SetConsoleTextAttribute(hConsole, 4);  cout << enemyName; SetConsoleTextAttribute(hConsole, 7); cout << " DEALT " << damage << " DAMAGE" << endl;
				if (eCritical == 2) {
					SetConsoleTextAttribute(hConsole, 4);  cout << enemyName; SetConsoleTextAttribute(hConsole, 7); cout << " DEALT + "; SetConsoleTextAttribute(hConsole, 4); cout << eCritical; SetConsoleTextAttribute(hConsole, 7); cout << " CRITICAL DAMAGE" << endl;
				}
				turn = 2;
				break;

			case 3:
				SetConsoleTextAttribute(hConsole, 4);  cout << enemyName; SetConsoleTextAttribute(hConsole, 7);  cout << " USES "; SetConsoleTextAttribute(hConsole, 14); cout << eMoveFour << endl; SetConsoleTextAttribute(hConsole, 7);
				if ((enemyStrength + 5 + eCritical) - (playerArmour / 2) <= 2) {
					damage = 2;
					playerHealth -= 2;
				}
				else {
					damage = (enemyStrength + 5 + eCritical) - (playerArmour / 2);
					playerHealth -= (enemyStrength + 5 + eCritical) - (playerArmour / 2);
				}
				SetConsoleTextAttribute(hConsole, 4);  cout << enemyName; SetConsoleTextAttribute(hConsole, 7); cout << " DEALT " << damage << " DAMAGE" << endl;
				if (eCritical == 2) {
					SetConsoleTextAttribute(hConsole, 4);  cout << enemyName; SetConsoleTextAttribute(hConsole, 7); cout << " DEALT + "; SetConsoleTextAttribute(hConsole, 4); cout << eCritical; SetConsoleTextAttribute(hConsole, 7); cout << " CRITICAL DAMAGE" << endl;
				}
				turn = 2;
				break;

			default:
				SetConsoleTextAttribute(hConsole, 12);
				cout << "AN ERROR HAS OCCURED!" << endl;
				Sleep(1000);
				break;
			}
			system("pause");
			system("CLS");
		}
		else if (turn == 2) {

			SetConsoleTextAttribute(hConsole, 4);
			cout << "ENEMY ENCOUNTER" << endl;
			cout << "OPPONENT: " << enemyName << endl;
			cout << " " << endl;
			SetConsoleTextAttribute(hConsole, 7);
			cout << "+-----------------------------------------------------+" << endl;
			cout << " " << characterName << "'s STATS" << endl;
			cout << " HEALTH:   "; SetConsoleTextAttribute(hConsole, 10); cout << playerHealth << endl; SetConsoleTextAttribute(hConsole, 7);
			cout << " STRENGTH: "; SetConsoleTextAttribute(hConsole, 10); cout << playerStrength << endl; SetConsoleTextAttribute(hConsole, 7);
			cout << " ARMOUR:   "; SetConsoleTextAttribute(hConsole, 10); cout << playerArmour << endl; SetConsoleTextAttribute(hConsole, 7);
			cout << "+-----------------------------------------------------+" << endl;
			cout << enemyName << "'s STATS" << endl;
			cout << " HEALTH:   "; SetConsoleTextAttribute(hConsole, 4); cout << enemyHealth << endl; SetConsoleTextAttribute(hConsole, 7);
			cout << " STRENGTH: "; SetConsoleTextAttribute(hConsole, 4); cout << enemyStrength << endl; SetConsoleTextAttribute(hConsole, 7);
			cout << " ARMOUR:   "; SetConsoleTextAttribute(hConsole, 4); cout << enemyArmour << endl; SetConsoleTextAttribute(hConsole, 7);
			cout << "+-----------------------------------------------------+" << endl;
			cout << " " << endl;
			cout << "YOUR TURN" << endl;
			cout << "SELECT AN ATTACK" << endl;
			cout << "1: SWING" << endl;
			cout << "2: BLOCK" << endl;
			cout << "3: STAB" << endl;
			cout << "4: CHARGE" << endl;

			cin >> option;

			int pCritChance = rd() % 6 + 1;
			
			if (pCritChance == 6) {
				playerCrit = 2;
			}
			else {
				playerCrit = 0;
			}

			switch (option) {
			case 1:
				SetConsoleTextAttribute(hConsole, 10);  cout << characterName; SetConsoleTextAttribute(hConsole, 7);  cout << " USES "; SetConsoleTextAttribute(hConsole, 14); cout << "SWING" << endl; SetConsoleTextAttribute(hConsole, 7);
				enemyHealth -= (playerStrength + playerCrit) - (enemyArmour / 5);
				SetConsoleTextAttribute(hConsole, 10);  cout << characterName; SetConsoleTextAttribute(hConsole, 7); cout << " DEALT " << playerStrength - (enemyArmour / 5) << " DAMAGE" << endl;
				if (playerCrit == 2) {
					SetConsoleTextAttribute(hConsole, 10);  cout << characterName; SetConsoleTextAttribute(hConsole, 7); cout << " DEALT + "; SetConsoleTextAttribute(hConsole, 10); cout << playerCrit; SetConsoleTextAttribute(hConsole, 7); cout << " CRITICAL DAMAGE" << endl;
				}
				turn = 1;
				break;

			case 2:
				SetConsoleTextAttribute(hConsole, 10);  cout << characterName; SetConsoleTextAttribute(hConsole, 7);  cout << " USES "; SetConsoleTextAttribute(hConsole, 14); cout << "BLOCK" << endl; SetConsoleTextAttribute(hConsole, 7);
				armourIncrease = (5 + (playerArmour / 5));
				playerArmour += armourIncrease;
				SetConsoleTextAttribute(hConsole, 10);  cout << characterName; SetConsoleTextAttribute(hConsole, 7); cout << " INCREASED THEIR "; SetConsoleTextAttribute(hConsole, 6); cout << "ARMOUR "; SetConsoleTextAttribute(hConsole, 7); cout << "BY " << armourIncrease << " POINTS" << endl;
				if (playerArmour >= (playerTempArmour + 20)) {
					playerArmour = (playerTempArmour + 20);

				}
				turn = 1;
				break;

			case 3:
				SetConsoleTextAttribute(hConsole, 10);  cout << characterName; SetConsoleTextAttribute(hConsole, 7);  cout << " USES "; SetConsoleTextAttribute(hConsole, 14); cout << "STAB" << endl; SetConsoleTextAttribute(hConsole, 7);
				enemyHealth -= (playerStrength + playerCrit)- (enemyArmour / 5);
				SetConsoleTextAttribute(hConsole, 10);  cout << characterName; SetConsoleTextAttribute(hConsole, 7); cout << " DEALT " << playerStrength - (enemyArmour / 5) << " DAMAGE" << endl;
				if (playerCrit == 2) {
					SetConsoleTextAttribute(hConsole, 10);  cout << characterName; SetConsoleTextAttribute(hConsole, 7); cout << " DEALT + "; SetConsoleTextAttribute(hConsole, 10); cout << playerCrit; SetConsoleTextAttribute(hConsole, 7); cout << " CRITICAL DAMAGE" << endl;
				}
				turn = 1;
				break;

			case 4:
				SetConsoleTextAttribute(hConsole, 10);  cout << characterName; SetConsoleTextAttribute(hConsole, 7);  cout << " USES "; SetConsoleTextAttribute(hConsole, 14); cout << "CHARGE" << endl; SetConsoleTextAttribute(hConsole, 7);
				enemyHealth -= (((playerStrength + 5)+ playerCrit) - (enemyArmour / 5));
				SetConsoleTextAttribute(hConsole, 10);  cout << characterName; SetConsoleTextAttribute(hConsole, 7); cout << " DEALT " << ((playerStrength + 5) - (enemyArmour / 5)) << " DAMAGE" << endl;
				if (playerCrit == 2) {
					SetConsoleTextAttribute(hConsole, 10);  cout << characterName; SetConsoleTextAttribute(hConsole, 7); cout << " DEALT + "; SetConsoleTextAttribute(hConsole, 10); cout << playerCrit; SetConsoleTextAttribute(hConsole, 7); cout << " CRITICAL DAMAGE" << endl;
				}
				turn = 1;
				break;

			case 0:
				cin >> option;

			default:
				SetConsoleTextAttribute(hConsole, 12);
				cout << "AN ERROR HAS OCCURED!" << endl;
				Sleep(1000);
				break;
			}
			system("pause");
			system("CLS");
		}

		system("CLS");

		int expGained = rd() % 4 + 2;

		if (enemyHealth <= 0 && playerHealth <= 0) {
			system("CLS");
			SetConsoleTextAttribute(hConsole, 12);
			cout << "YOU DIED" << endl;
			system("pause");
			exit(0);
		}
		else if (enemyHealth <= 0) {
			playerArmour = playerTempArmour;
			cout << "ENEMY DEFEATED" << endl;
			cout << "EXP GAINED: "; SetConsoleTextAttribute(hConsole, 10); cout << expGained << endl; SetConsoleTextAttribute(hConsole, 7);
			expPlayer += expGained;
			cout << "TOTAL EXP : "; SetConsoleTextAttribute(hConsole, 10); cout << expPlayer; SetConsoleTextAttribute(hConsole, 7);
			cout << " " << endl;
			menu = true;
			menuTwo = true;
			system("pause");

			while (menu == true) {
				system("CLS");
				while (menuTwo = true) {
					playerTempSpeed = 0;
					playerTempArmour = 0;
					playerTempStrength = 0;
					cout << "EXP MANAGER " << endl;
					cout << "DISTRIBUTE YOUR EXP POINTS (TYPE '0' IF YOU DONT WANT TO ADD POINTS)" << endl;

					cout << "YOU HAVE "; SetConsoleTextAttribute(hConsole, 10); cout << expPlayer; SetConsoleTextAttribute(hConsole, 7); cout << " POINTS" << endl;
					cout << "STRENGTH "; SetConsoleTextAttribute(hConsole, 10); cin >> inputNum; SetConsoleTextAttribute(hConsole, 7);
					playerTempStrength = inputNum;
					if (playerTempStrength > expPlayer) {
						playerTempStrength = expPlayer;
						menu = false;
					}
					expPlayer -= playerTempStrength;
					playerTempStrength *= 2;
					
			


					if (expPlayer <= 0) {
						menu = false;
						inputNum = 0;
						break;
					}

					cout << " " << endl;
					cout << "YOU HAVE "; SetConsoleTextAttribute(hConsole, 10); cout << expPlayer; SetConsoleTextAttribute(hConsole, 7); cout << " POINTS" << endl;
					cout << "ARMOUR "; SetConsoleTextAttribute(hConsole, 10); cin >> inputNum; SetConsoleTextAttribute(hConsole, 7);
					playerTempArmour = inputNum;
					if (playerTempArmour > expPlayer) {
						playerTempArmour = expPlayer;
						menu = false;
					}
					expPlayer -= playerTempArmour;
					playerTempArmour *= 2;
						
					


					if (expPlayer <= 0) {
						inputNum = 0;
						menu = false;
						break;
					}

					cout << " " << endl;
					cout << "YOU HAVE "; SetConsoleTextAttribute(hConsole, 10); cout << expPlayer; SetConsoleTextAttribute(hConsole, 7); cout << " POINTS" << endl;
					cout << "SPEED "; SetConsoleTextAttribute(hConsole, 10); cin >> inputNum; SetConsoleTextAttribute(hConsole, 7);
					playerTempSpeed = inputNum;
					if (playerTempSpeed > expPlayer) {
						playerTempSpeed = expPlayer;
						menu = false;
					}
					expPlayer -= playerTempSpeed;
					playerTempSpeed *= 2;

					if (expPlayer <= 0) {
						inputNum = 0;
						menu = false;
						break;
					}
				}

				system("CLS");

				cout << "THE FOLLOWING STATS HAVE BEEN SELECTED FOR THIS CHARACTER" << endl;
				cout << "DO YOU WISH TO CONTINUE WITH THESE STATS?" << endl;
				cout << "STRENGTH :"; SetConsoleTextAttribute(hConsole, 10); cout << playerStrength + playerTempStrength << endl; SetConsoleTextAttribute(hConsole, 7);
				cout << "ARMOUR   :"; SetConsoleTextAttribute(hConsole, 10); cout << playerArmour + playerTempArmour << endl; SetConsoleTextAttribute(hConsole, 7);
				cout << "SPEED    :"; SetConsoleTextAttribute(hConsole, 10); cout << playerSpeed + playerTempSpeed<< endl; SetConsoleTextAttribute(hConsole, 7);
				cout << "HEALTH   :"; SetConsoleTextAttribute(hConsole, 10); cout << playerHealth + 5 << endl; SetConsoleTextAttribute(hConsole, 7);
				cout << " " << endl;
				cout << "1: YES" << endl;
				cout << "2: NO" << endl;
				cin >> option;

				switch (option) {
				case 0:
					SetConsoleTextAttribute(hConsole, 12);
					cout << "AN ERROR HAS OCCURED!" << endl;
					Sleep(1000);
					break;

				case 1:
					menu = false;
					menuTwo = false;
					playerArmour += playerTempArmour;
					playerStrength += playerTempStrength;
					playerSpeed += playerTempSpeed;
					playerMaxHealth += 5;
					playerHealth += 5;
					savePlayerStats(characterName, expPlayer, playerStrength, playerArmour, playerHealth, playerSpeed, playerMaxHealth, characterGender, story);
					Sleep(1000);
					system("CLS");
					break;

				case 2:
					expPlayer = expGained;
					playerTempStrength = 0;
					playerTempSpeed = 0;
					playerTempArmour = 0;
					system("CLS");
					menu = true;
					menuTwo = true;
					break;

				default:
					SetConsoleTextAttribute(hConsole, 12);
					cout << "AN ERROR HAS OCCURED!" << endl;
					Sleep(1000);
					break;
				}

			}
		}
		else if (playerHealth <= 0) {
			system("CLS");
			SetConsoleTextAttribute(hConsole, 12);
			cout << "YOU DIED" << endl;
			system("pause");
			exit(0);
		}
	}
}

void playerNameGreen(string characterName);

void playerNameGreen(string characterName) {
	SetConsoleTextAttribute(hConsole, 10); cout << characterName; SetConsoleTextAttribute(hConsole, 7);
}

void locationColor(string location);

void locationColor(string location) {
	SetConsoleTextAttribute(hConsole, 3); cout << location << endl; SetConsoleTextAttribute(hConsole, 7);
}

int main()
{
	// Console Settings
	hConsole = GetStdHandle(STD_OUTPUT_HANDLE);

	//COORD cur = { 0, 0 };
	//SetConsoleCursorPosition(GetStdHandle(STD_OUTPUT_HANDLE), cur);
	cout << "FINAL PROJECT RPG" << endl;
	cout << "BY: STEFAN UROSEVIC" << endl;
	cout << "V 1" << endl;
	cout << " " << endl;

	while (menu == true) {
		cout << "Please Enter Your Option" << endl;
		cout << "1: NEW GAME" << endl;
		cout << "2: LOAD GAME" << endl;

		cin >> option;
		if (option == 1) {
			system("CLS");
			characterCreation = 10;
			// Character Creation
			menu = true;
			cout << "XX CHARACTER CREATION MENU XX" << endl;
			cout << " " << endl;
			cout << "ENTER YOUR NAME: ";
			cin >> characterName;
			if (characterName == " " || characterName == "Back" || characterName == "back") {
				characterName = "JOE";
			}
			cout << "ENTER YOUR GENDER (M - F): ";
			while (characterGender != 'M' || characterGender != 'm' || characterGender != 'F' || characterGender != 'f') {
				cin >> characterGender;
				if (characterGender == 'M' || characterGender == 'm' || characterGender == 'F' || characterGender == 'f') {
					break;
				}
			}
			system("CLS");

			cout << "XX CHARACTER CREATION MENU XX" << endl;
			cout << " " << endl;
			cout << "YOU HAVE BEEN GIVEN 10 POINTS TO CREATE A CHARACTER, CHOOSE WISELY!" << endl;
			cout << "YOU HAVE POINTS TO ASSIGN TO THREE DIFFERENT CATEGORIES: STRENGTH, ARMOUR, & SPEED" << endl;

			while (characterCreation > 0 && menu == true) {
				menuTwo = true;
				while (menuTwo == true) {
					cout << "YOU HAVE: "; SetConsoleTextAttribute(hConsole, 10); cout << characterCreation; SetConsoleTextAttribute(hConsole, 7); cout << " POINT(S) LEFT" << endl;
					cout << " " << endl;
					cout << "STRENGTH: "; SetConsoleTextAttribute(hConsole, 10);  cin >> playerStrength; SetConsoleTextAttribute(hConsole, 7);
					if (playerStrength > characterCreation) {
						playerStrength = characterCreation;
						playerStrength *= 2;
						menuTwo = false;
						break;
					}

					characterCreation -= playerStrength;
					playerStrength *= 2;

					if (characterCreation <= 0) {
						system("CLS");
						menuTwo = false;
						break;
					}

					cout << "YOU HAVE: "; SetConsoleTextAttribute(hConsole, 10); cout << characterCreation; SetConsoleTextAttribute(hConsole, 7); cout << " POINT(S) LEFT" << endl;
					cout << " " << endl;
					cout << "ARMOUR: "; SetConsoleTextAttribute(hConsole, 10);  cin >> playerArmour; SetConsoleTextAttribute(hConsole, 7);
					if (playerArmour > characterCreation) {
						playerArmour = characterCreation;
						playerArmour *= 2;
						menuTwo = false;
						break;
					}

					characterCreation -= playerArmour;
					playerArmour *= 2;

					if (characterCreation <= 0) {
						system("CLS");
						menuTwo = false;
						break;
					}

					cout << "YOU HAVE: "; SetConsoleTextAttribute(hConsole, 10); cout << characterCreation; SetConsoleTextAttribute(hConsole, 7); cout << " POINT(S) LEFT" << endl;
					cout << " " << endl;
					cout << "SPEED: "; SetConsoleTextAttribute(hConsole, 10); cin >> playerSpeed; SetConsoleTextAttribute(hConsole, 7);
					if (playerSpeed > characterCreation) {
						playerSpeed = characterCreation;
						playerSpeed *= 2;
						menuTwo = false;
						break;
					}

					characterCreation -= playerSpeed;
					playerSpeed *= 2;
					system("CLS");

					if (characterCreation <= 0) {
						system("CLS");
						menuTwo = false;
						break;
					}
				}

				playerHealth = 40;
				playerMaxHealth = playerHealth;

				cout << "THE FOLLOWING STATS HAVE BEEN SELECTED FOR THIS CHARACTER" << endl;
				cout << "DO YOU WISH TO CONTINUE WITH THESE STATS?" << endl;
				cout << "STRENGTH :"; SetConsoleTextAttribute(hConsole, 10); cout << playerStrength << endl; SetConsoleTextAttribute(hConsole, 7);
				cout << "ARMOUR   :"; SetConsoleTextAttribute(hConsole, 10); cout << playerArmour << endl; SetConsoleTextAttribute(hConsole, 7);
				cout << "SPEED    :"; SetConsoleTextAttribute(hConsole, 10); cout << playerSpeed << endl; SetConsoleTextAttribute(hConsole, 7);
				cout << "Health   :"; SetConsoleTextAttribute(hConsole, 10); cout << playerHealth << endl; SetConsoleTextAttribute(hConsole, 7);
				cout << " " << endl;
				cout << "1: YES" << endl;
				cout << "2: NO" << endl;
				cin >> option;

				switch (option) {
				case 0:
					SetConsoleTextAttribute(hConsole, 12);
					cout << "AN ERROR HAS OCCURED!" << endl;
					Sleep(1000);
					break;

				case 1:
					menu = false;
					menuTwo = false;
					story = 1;
					savePlayerStats(characterName, expPlayer, playerStrength, playerArmour, playerHealth, playerSpeed, playerMaxHealth, characterGender, story);
					system("CLS");
					break;

				case 2:
					characterCreation = 10;
					playerStrength = 0;
					playerSpeed = 0;
					playerArmour = 0;
					system("CLS");
					break;

				default:
					SetConsoleTextAttribute(hConsole, 12);
					cout << "AN ERROR HAS OCCURED!" << endl;
					Sleep(1000);
					break;
				}

				//cout << "SPECIAL:     < " << " >" << endl; - Maybe;
			}
			break;
		}

		if (option == 2) {
			system("CLS");
			cout << "PLEASE ENTER YOUR CHARACTERS NAME" << endl;
			cout << "IF YOU DON'T HAVE A CHARACTER TYPE 'Back' or 'back'" << endl;
			cin >> characterName;

			// Checks for file or if back has been selected.
			if (characterName == "back" || characterName == "Back") {
				option = 0;
				system("CLS");
			}
			else {
				setupPlayerStats(characterName, expPlayer, playerStrength, playerArmour, playerHealth, playerSpeed, playerMaxHealth, characterGender, story);
				system("CLS");
				menu = false;
			}
		}
	}

	if (characterGender == 'M' || characterGender == 'm') {
		genderOne = 0;
		genderTwo = 1;
	}
	else if (characterGender == 'F' || characterGender == 'f') {
		genderOne = 2;
		genderTwo = 3;
	}

	if (story == 1) {
		system("CLS");
		cout << "THE NIGHT IS COLD AND RAINY, ";
		playerNameGreen(characterName); cout << " COULDN'T SLEEP WELL." << endl;
		cout << "AS ALL SEEMED EERIE AND QUIET AN EXPLOSION WAS HEARD OFF IN THE DISTANCE." << endl;
		playerNameGreen(characterName); cout << " SAT UP IN " << genderNouns[genderTwo] << " BED, OUT IN THE DISTANCE THE KINGS CASTLE WAS UNDER SIEGE" << endl;
		playerNameGreen(characterName); cout << " KNEW, " << genderNouns[genderOne] << " HAD TO MAKE " << genderNouns[genderTwo] << " WAY TO TOWN." << endl;
		cout << "FOR " << genderNouns[genderOne] << " WAS THE KINGS BEST SOLDIER!" << endl;
		system("pause");

		system("CLS");
		cout << "ALL GEARED UP AND READY TO GO "; playerNameGreen(characterName); cout << " LEFT THE HOUSE" << endl;
		cout << "RIGHT AS OUR HERO LEFT THE HOUSE HE WAS ATTACKED BY A BAT!" << endl;
		system("PAUSE");
		system("CLS");

		enemyName = "BAT";
		battle(characterName, playerStrength, playerArmour, playerSpeed, enemyName);

		system("CLS");
		playerNameGreen(characterName); cout << " THOUGHT THAT THE ROAD WILL BE SWARMING WITH ENEMIES" << endl;
		cout << genderNouns[genderOne] << " DECIDED IT WOULD BE BEST TO GO THROUGH THE CAVES" << endl;
		system("PAUSE");
		story++;
		savePlayerStats(characterName, expPlayer, playerStrength, playerArmour, playerHealth, playerSpeed, playerMaxHealth, characterGender, story);
		system("CLS");
	}
	if (story == 2) {
		playerNameGreen(characterName); cout << " MADE " << genderNouns[genderTwo] << " WAY TO THE CAVE HE MARVELED AT THE ENTRANCE" << endl;
		system("pause");
		system("CLS");

		location = "DUSTY CAVE";
		locationColor(location);
		cout << " " << endl;
		cout << "+--------------------------+" << endl;
		cout << "        #######  " << endl;
		cout << "     ###      ## " << endl;
		cout << " #####         # " << endl;
		cout << " ##           ## " << endl;
		cout << " #             # " << endl;
		cout << " ##            # " << endl;
		cout << " #_____________# " << endl;
		cout << "+--------------------------+" << endl;
		cout << " " << endl;
		playerNameGreen(characterName); cout << " ENTERED THE CAVE." << endl;
		cout << genderNouns[genderOne] << " WALKED FORWARD INTO THE CAVE AND" << endl;
		cout << "WAS IMMEDIATLY ATTACKED BY A BAT!" << endl;
		system("pause");
		system("CLS");

		enemyName = "BAT";
		battle(characterName, playerStrength, playerArmour, playerSpeed, enemyName);

		system("CLS");
		playerNameGreen(characterName); cout << " CONTINUED FORWARD UNTIL " << genderNouns[genderOne] << " A FORK IN THE ROAD." << endl;
		cout << "WHICH WAY SHOULD OUR HERO GO?" << endl;
		cout << " " << endl;
		cout << "1: LEFT" << endl;
		cout << "2: FORWARD" << endl;

		cin >> option;

		switch (option) {
		case 1:
			system("CLS");

			playerNameGreen(characterName); cout << " WENT LEFT." << endl;
			playerNameGreen(characterName); cout << " WALKED FORWARD FOR A BIT UNTIL " << genderNouns[genderOne] << " ALERTED" << endl;
			cout << "A COLONY OF BATS" << endl;
			system("pause");
			system("CLS");

			enemyName = "BAT";
			battle(characterName, playerStrength, playerArmour, playerSpeed, enemyName);

			system("CLS");
			cout << "AFTER THE FIGHT "; playerNameGreen(characterName); cout << " WALKED FORWARD AND EXITED THE CAVE" << endl;
			system("pause");
			system("CLS");

			story++;
			savePlayerStats(characterName, expPlayer, playerStrength, playerArmour, playerHealth, playerSpeed, playerMaxHealth, characterGender, story);
			system("CLS");
			break;
		case 2:
			system("CLS");

			playerNameGreen(characterName); cout << " WENT FORWARD." << endl;
			playerNameGreen(characterName); cout << " WALKED FORWARD FOR A BIT UNTIL " << genderNouns[genderOne] << " ALERTED" << endl;
			cout << "A COLONY OF BATS" << endl;
			system("pause");
			system("CLS");

			enemyName = "BAT";
			battle(characterName, playerStrength, playerArmour, playerSpeed, enemyName);

			system("CLS");

			playerNameGreen(characterName); cout << " WALKED FORWARD AFTER HIS ENCOUTER AND EVERYTHING SEEMED FINE" << endl;
			system("pause");
			system("CLS");

			cout << "BUT THEN AGAIN OUR HERO ALERTED ANOTHER COLONY" << endl;
			system("pause");
			system("CLS");

			enemyName = "BAT";
			battle(characterName, playerStrength, playerArmour, playerSpeed, enemyName);

			playerNameGreen(characterName); cout << " WALKED FORWARD AND FOUND A CHEST, " << genderNouns[genderOne] << " OPENED IT" << endl;
			system("pause");
			system("CLS");

			cout << "YOU FOUND A HEALTH RESTORE POTION" << endl;
			playerHealth = playerMaxHealth;
			cout << "YOUR HEALTH IS NOW: "; SetConsoleTextAttribute(hConsole, 10); cout << playerHealth; SetConsoleTextAttribute(hConsole, 7); cout << " / " << playerMaxHealth << endl;

			system("pause");
			system("CLS");

			cout << "EVENTUALLY OUR HERO MADE IT OUT OF THE CAVE" << endl;

			story++;
			savePlayerStats(characterName, expPlayer, playerStrength, playerArmour, playerHealth, playerSpeed, playerMaxHealth, characterGender, story);
			system("CLS");
			break;
		default:
			SetConsoleTextAttribute(hConsole, 12);
			cout << "AN ERROR HAS OCCURED!" << endl;
			Sleep(1000);
			break;
		}
	}
	if (story == 3) {
		location = "SWEST TOWN";
		locationColor(location);

		cout << " " << endl;
		cout << "+--------------------------+" << endl;
		cout << "       ________        " << endl;
		cout << "       |  @@  |        " << endl;
		cout << "   __  |  @@  |        " << endl;
		cout << "  /##\\ |      |        " << endl;
		cout << " /####\\|      |  @   @   " << endl;
		cout << " ######|  __  | @@@ @@@ " << endl;
		cout << " ##||##|  ||  | @ @ @ @" << endl;
		cout << "+--------------------------+" << endl;
		cout << " " << endl;

		playerNameGreen(characterName);  cout << " WALKED INTO " << location << ", IT WAS A RATHER PEACEFUL VILLAGE" << endl;
		system("pause");
		system("CLS");

		if (playerHealth != playerMaxHealth) {
			cout << "WOULD YOU LIKE TO HEAL UP?" << endl;
			cout << " " << endl;
			cout << "1: YES" << endl;
			cout << "2: NO" << endl;

			cin >> option;

			switch (option) {
			case 1:
				playerNameGreen(characterName); cout << " HAS BEEN HEALED" << endl;
				playerHealth = playerMaxHealth;
				system("pause");
				system("CLS");
				break;
			case 2:
				cout << "CONTINUING" << endl;
				system("pause");
				system("CLS");
				break;
			default:
				SetConsoleTextAttribute(hConsole, 12);
				cout << "AN ERROR HAS OCCURED!" << endl;
				Sleep(1000);
				break;
			}
		}

		system("CLS");

		playerNameGreen(characterName); cout << " DID NOTICE SOMETHING, " << genderNouns[genderOne] << " NOTICED THAT VILLAGE LIFE HAS CHANGED" << endl;
		cout << genderNouns[genderOne] << " SAW PEOPLE LEAVING AND FREAKING OUT ABOUT THE RECENT WAR" << endl;
		cout << "AS IT TURNS OUT THE NORTHERN KINGDOM ATTACKED THE VOLO KINGDOM (WHERE OUR HERO COMES FROM)" << endl;
		system("pause");
		system("CLS");

		cout << "AS IF THINGS COULD GET INTENSE A SQUAD OF FIVE SOLDIERS APPEARED." << endl;
		cout << "THEY ALL SPLIT UP AND CAUSED PANIC THROUGHOUT THE TOWN." << endl;
		playerNameGreen(characterName); cout << " KNEW THAT " << genderNouns[genderOne] << " HAD TO KILL THE SOLDIERS IN ORDER TO SAVE THE VILLAGE!" << endl;
		system("pause");
		system("CLS");

		cout << "AN ENEMY SOLDIER APPROACHED OUR HERO" << endl;
		playerNameGreen(characterName); cout << " DREW " << genderNouns[genderTwo] << " SWORD, " << genderNouns[genderOne] << " WAS READY TO FIGHT!" << endl;
		system("pause");
		system("CLS");

		enemyName = "SOLDIER";
		battle(characterName, playerStrength, playerArmour, playerSpeed, enemyName);

		cout << "ONE SOLDIER DOWN 4 MORE TO GO." << endl;

		playerNameGreen(characterName); cout << " WALKED TO THE VILLAGE SQUARE, " << genderNouns[genderOne] << " SAW A SOLDIER" << endl;
		cout << "RAISING THE NORTH KINGDOM BANNER OVER THE TOWN HALL." << endl;
		cout << "THEIR GAZES MET, THE TWO CHARGED AT EACHOTHER" << endl;
		system("pause");
		system("CLS");

		enemyName = "SOLDIER";
		battle(characterName, playerStrength, playerArmour, playerSpeed, enemyName);
		system("CLS");

		cout << "ONE SOLDIER DOWN 3 MORE TO GO." << endl;
		playerNameGreen(characterName); cout << " WENT EAST ON KINGS WAY" << endl;
		system("pause");
		system("CLS");

		enemyName = "SOLDIER";
		battle(characterName, playerStrength, playerArmour, playerSpeed, enemyName);
		system("CLS");

		cout << "ONE SOLDIER DOWN 2 MORE TO GO." << endl;

		playerNameGreen(characterName); cout << " WENT WEST ON KINGS WAY" << endl;
		system("pause");
		system("CLS");

		enemyName = "SOLDIER";
		battle(characterName, playerStrength, playerArmour, playerSpeed, enemyName);
		system("CLS");

		cout << "ONE SOLDIER DOWN 1 MORE TO GO." << endl;

		playerNameGreen(characterName); cout << " WENT NORTH ON VILLAGE ROAD" << endl;
		system("pause");
		system("CLS");

		enemyName = "SOLDIER";
		battle(characterName, playerStrength, playerArmour, playerSpeed, enemyName);
		system("CLS");

		cout << "THERE ALL GONE AND DEFEATED" << endl;
		playerNameGreen(characterName); cout << " WAS HEALED AND WENT NORTH" << endl;
		playerHealth = playerMaxHealth;
		story++;
		savePlayerStats(characterName, expPlayer, playerStrength, playerArmour, playerHealth, playerSpeed, playerMaxHealth, characterGender, story);
	}
	cout << "UNTIL NEXT TIME..." << endl;
}