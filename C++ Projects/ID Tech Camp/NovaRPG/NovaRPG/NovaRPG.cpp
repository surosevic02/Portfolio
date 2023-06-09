// NovaRPG.cpp : Defines the entry point for the console application.

#include "stdafx.h"
#include "main.h"
using namespace std;

void setupPlayerStats(string &playerName, int &playerStrength, int &playerDefense, int &playerSpecial, int &playerWins, int &playerHealth, string &playerSpecialName);
void savePlayerStats(string playerName, int playerStrength, int playerDefense, int playerSpecial, int playerWins, int playerHealth, string playerSpecialName);
void resetPlayer(string playerSpecialName);

// Variables
int choice;
int selectCharacter;
bool mainMenu = true;
bool characterSelect = true;
bool skip = false;
int selectedPlayer = 1;
string selectedGame;
int option;

// Player Stats

// Player 1
int player1Health;
int player1Strength;
int player1Defense;
int player1Special;
int player1Wins;
string player1Name;
string player1SpecialName;
int player1SpecialUse = 0;
string player1Status = "Healthy";
bool turn1;

// Player 2
int player2Health;
int player2Strength;
int player2Defense;
int player2Special;
int player2Wins;
string player2Name;
string player2SpecialName;
int player2SpecialUse = 0;
string player2Status = "Healthy";
bool turn2;


// Mage
int mageHealth = 55;
int mageStrength = 10;
int mageDefense = 20;
int mageMagic = 5;

// Archer
int archerHealth = 55;
int archerStrength = 15;
int archerDefense = 5;
int archerSpeed = 30;

// Knight
int knightHealth = 55;
int knightStrength = 20;
int knightDefense = 20;
int knightArmour = 25;

string playerName;

// Set Up
int playerHealth;
int playerStrength;
int playerDefense;
int playerSpecial;
int playerWins;
string playerSpecialName;

int main()
{
	while (selectedPlayer <= 2) {
		cout << "Welcome To Nova RPG Text Adventrure V1" << endl;
		cout << "Player " << selectedPlayer << " Please Select an Option" << endl;
		cout << "" << endl;
		cout << "New Character:  1" << endl;
		cout << "Load Character: 2" << endl;

		while (mainMenu == true) {
			cin >> choice;

			switch (choice) {
			case 1:
				cout << "NEW CHARACTER" << endl;
				selectedGame = "new";
				mainMenu = false;
				break;
			case 2:
				cout << "LOAD CHARACTER" << endl;
				selectedGame = "load";
				mainMenu = false;
				break;
			case 0:
				mainMenu = false;
				cout << "Unexpected Error" << endl;
				break;
			default:
				cout << "Please Pick A Valid Option" << endl;
			}
		}

		if (selectedGame == "new") {
			system("CLS");
			cout << "Choose your character" << endl;
			cout << "" << endl;
			cout << "MAGE: 1		ARCHER: 2          KNIGHT: 3" << endl;
			cout << "   ####          #####              /|\\" << endl;
			cout << " ##OOOO##         |   #             |||" << endl;
			cout << " ##OOOO##         |    #            |||" << endl;
			cout << "  ######        >>+----#->          |||" << endl;
			cout << "    ##            |    #            |||" << endl;
			cout << "    ##            |   #            #####" << endl;
			cout << "    ##           #####               |" << endl;

			while (characterSelect == true) {
				cin >> selectCharacter;

				switch (selectCharacter) {
				case 1:
					cout << "You have Choosen Mage" << endl;

					playerHealth = mageHealth;
					playerDefense = mageDefense;
					playerStrength = mageStrength;
					playerSpecial = mageMagic;
					playerSpecialName = "Mana";

					characterSelect = false;
					break;
				case 2:
					cout << "You have Choosen Archer" << endl;

					playerHealth = archerHealth;
					playerDefense = archerDefense;
					playerStrength = archerStrength;
					playerSpecial = archerSpeed;
					playerSpecialName = "Speed";

					characterSelect = false;
					break;
				case 3:
					cout << "You have Choosen Knight" << endl;

					playerHealth = knightHealth;
					playerDefense = knightDefense;
					playerStrength = knightStrength;
					playerSpecial = knightArmour;
					playerSpecialName = "Armour";

					characterSelect = false;
					break;
				case 0:
					characterSelect = false;
					cout << "Unexpected Error" << endl;
					break;

				default:
					cout << "Not a Valid Option" << endl;
				}
			}

			playerWins = 0;

			cout << "Please Enter A Name For Your Character: " << endl;
			cin >> playerName;

			savePlayerStats(playerName, playerStrength, playerDefense, playerSpecial, playerWins, playerHealth, playerSpecialName);

			if (selectedPlayer == 1) {
				player1Health = playerHealth;
				player1Strength = playerStrength;
				player1Defense = playerDefense;
				player1Special = playerSpecial;
				player1Name = playerName;
				player1SpecialName = playerSpecialName;
				player1Wins = playerWins;
			}
			else {
				player2Health = playerHealth;
				player2Strength = playerStrength;
				player2Defense = playerDefense;
				player2Special = playerSpecial;
				player2Name = playerName;
				player2SpecialName = playerSpecialName;
				player2Wins = playerWins;
			}

			selectedPlayer++;

			mainMenu = true;
			characterSelect = true;
			system("CLS");
		}

		if (selectedGame == "load") {
			cout << "Please Enter Name Of Your Previous Player" << endl;
			cin >> playerName;

			setupPlayerStats(playerName, playerStrength, playerDefense, playerSpecial, playerWins, playerHealth, playerSpecialName);

			if (skip == false) {
				if (selectedPlayer == 1) {
					player1Health = playerHealth;
					player1Strength = playerStrength;
					player1Defense = playerDefense;
					player1Special = playerSpecial;
					player1Name = playerName;
					player1SpecialName = playerSpecialName;
					player1Wins = playerWins;
				}
				else {
					player2Health = playerHealth;
					player2Strength = playerStrength;
					player2Defense = playerDefense;
					player2Special = playerSpecial;
					player2Name = playerName;
					player2SpecialName = playerSpecialName;
					player2Wins = playerWins;
				}
			}

			skip = false;

			selectedPlayer++;

			characterSelect = true;
			mainMenu = true;
			system("CLS");
		}
	}

	// Fight Info Board
	cout << "Wins: " << player1Wins << endl;
	cout << player1Name << endl;

	cout << "=== VS ===" << endl;

	cout << player2Name << endl;
	cout << "Wins: " << player2Wins << endl;

	Sleep(3000);

	system("CLS");

	//Mechanics
	random_device rd;
	int coinFlip = rd() % 2 + 1;

	cout << player1Name << " VS " << player2Name << endl;

	if (coinFlip == 1) {
		turn1 = true;
	}
	else if (coinFlip == 2) {
		turn2 = true;
	}

	while (player1Health > 0 && player2Health > 0) {

		if (player1SpecialUse >= 4) {
			player1SpecialUse = 4;
		}
		
		if (player2SpecialUse >= 4) {
			player2SpecialUse = 4;
		}

		if (player1Health <= 0) {
			break;
		}

		if (player2Health <= 0) {
			break;
		}

			if (turn1 == true) {

				if (player1Status == "Poisoned") {
					player1Health -= 5;
					cout << player1Name << " Took 5 Damage From Poisoning" << endl;
				}

				cout << " " << endl;
				cout << "-----------------------------------------------" << endl;
				cout << player1Name << "'s Turn" << endl;
				cout << "Choose Your Action" << endl;
				cout << "Health: " << player1Health << " Strength: " << player1Strength << " Defense: " << player1Defense << endl;
				cout << "Special: " << player1SpecialUse << " / 4 Status: " << player1Status << endl;

				if (player1SpecialName == "Armour") {
					cout << "1: Swing Sword" << endl;
					cout << "2: Block" << endl;
					cout << "3: Charge" << endl;
					cout << "4: Special" << endl;

					cin >> option;

					switch (option) {
					case 1:
						cout << " " << endl;
						cout << player1Name << " Swung at " << player2Name << endl;
						player2Health -= (player1Strength - (player2Defense / 5));
						cout << player1Name << " Dealt " << player1Strength << " Damage" << endl;
						player1SpecialUse++;
						turn1 = false;
						turn2 = true;
						break;

					case 2:
						cout << " " << endl;
						cout << player1Name << " uses Block" << endl;
						player1Defense += 5;
						cout << player1Name << " Incresed Their Defense By 5" << endl;
						player1SpecialUse++;
						turn1 = false;
						turn2 = true;
						break;

					case 3:
						cout << " " << endl;
						cout << player1Name << " Charged at " << player2Name << endl;
						player2Health -= ((player1Strength + 5) - (player2Defense / 5));
						cout << player1Name << " Dealt " << player1Strength + 5 << " Damage" << endl;
						player1SpecialUse++;
						turn1 = false;
						turn2 = true;
						break;

					case 4:
						if (player1SpecialUse == 4) {
							cout << " " << endl;
							cout << player1Name << " uses Their Special!!" << endl;
							player1Special -= 4;
							player1Defense += (player1Special / 2);
							cout << player1Name << " Increased Their Defense By " << player1Defense << endl;
							player1SpecialUse = 0;
							turn1 = false;
							turn2 = true;
							break;
						}
						else {
							cout << "Not Enough Power" << endl;
						}
						break;

					case 0:
						cout << "Unexpected Error" << endl;
						break;

					default:
						cout << "Unexpected Error" << endl;
						break;

					}

				}
				else if (player1SpecialName == "Mana") {
					cout << "1: Fireball" << endl;
					cout << "2: Heal" << endl;
					cout << "3: Shield" << endl;
					cout << "4: Special" << endl;

					cin >> option;

					switch (option) {
					case 1:
						cout << " " << endl;
						cout << player1Name << " uses fireball" << endl;
						player2Health -= (player1Strength - (player2Defense / 5));
						cout << player1Name << " Dealt " << player1Strength << " Damage" << endl;
						player1SpecialUse++;
						turn1 = false;
						turn2 = true;
						break;

					case 2:
						cout << " " << endl;
						cout << player1Name << " uses heal" << endl;
						player1Health += 5;
						cout << player1Name << " Increased Their Health By 5" << endl;
						player1SpecialUse++;
						turn1 = false;
						turn2 = true;
						break;

					case 3:
						cout << " " << endl;
						cout << player1Name << " uses shield" << endl;
						player1Defense += 5;
						cout << player1Name << " Increased Their Defense By 5" << endl;
						player1SpecialUse++;
						turn1 = false;
						turn2 = true;
						break;

					case 4:
						if (player1SpecialUse == 4) {
							cout << " " << endl;
							cout << player1Name << " uses Their Special!!" << endl;
							player1Special -= 4;
							player1Strength *= player1Special;
							player2Health -= (player1Strength - (player2Defense / 5));
							cout << player1Name << " Dealt " << player1Strength << " Damage" << endl;
							player1Strength = mageStrength;
							turn1 = false;
							turn2 = true;
							player1SpecialUse = 0;
							break;
						}
						else {
							cout << "Not Enough Power" << endl;
						}
						break;

					case 0:
						cout << "Unexpected Error" << endl;
						break;

					default:
						cout << "Unexpected Error" << endl;
						break;

					}

				}
				else if (player1SpecialName == "Speed") {

					cout << "1: Fire Arrow" << endl;
					cout << "2: Poison Arrow" << endl;
					cout << "3: Regular Arrow" << endl;
					cout << "4: Special" << endl;

					cin >> option;

					switch (option) {
					case 1:
						cout << " " << endl;
						cout << player1Name << " Shot a Fire Arrow at " << player2Name << endl;
						player2Health -= ((player1Strength + 5) - (player1Defense / 5));
						cout << player1Name << " Dealt " << player1Strength + 5 << " Damage" << endl;
						player1SpecialUse++;
						turn1 = false;
						turn2 = true;
						break;

					case 2:
						cout << " " << endl;
						cout << player1Name << " Shot a Poison Arrow at " << player2Name << endl;
						player2Health -= 5;
						player2Status = "Poisoned";
						cout << player1Name << " Dealt 5 Damage" << endl;
						cout << player2Name << " Is Now Poisoned! " << endl;
						player1SpecialUse++;
						turn1 = false;
						turn2 = true;
						break;

					case 3:
						cout << " " << endl;
						cout << player1Name << " Shot a Regular Arrow at " << player2Name << endl;
						player2Health -= (player1Strength - (player1Defense / 5));
						cout << player1Name << " Dealt " << player1Strength << " Damage" << endl;
						player1SpecialUse++;
						turn1 = false;
						turn2 = true;
						break;

					case 4:
						if (player1SpecialUse == 4) {
							cout << " " << endl;
							cout << player1Name << " uses Their Special!!" << endl;
							player1Special -= 4;
							player1Strength += player1Special;
							player2Health -= player1Strength * 2;
							cout << player1Name << " Dealt " << player1Strength * 2 << " Damage" << endl;
							player1Strength = archerStrength;
							player1SpecialUse = 0;
							turn1 = false;
							turn2 = true;
							break;
						}
						else {
							cout << "Not Enough Power" << endl;
						}
						break;

					case 0:
						cout << "Unexpected Error" << endl;
						break;

					default:
						cout << "Unexpected Error" << endl;
						break;

					}
				}
			}
		

			if (turn2 == true) {

				if (player2Status == "Poisoned") {
					player2Health -= 5;
					cout << player2Name << " Took 5 Damage From Poisoning" << endl;
				}

				cout << " " << endl;
				cout << "-----------------------------------------------" << endl;
				cout << player2Name << "'s Turn" << endl;
				cout << "Choose Your Action" << endl;
				cout << "Health: " << player2Health << " Strength: " << player2Strength << " Defense: " << player2Defense << endl;
				cout << "Special: " << player2SpecialUse << " / 4 Status: " << player2Status << endl;

				if (player2SpecialName == "Armour") {
					cout << "1: Swing Sword" << endl;
					cout << "2: Block" << endl;
					cout << "3: Charge" << endl;
					cout << "4: Special" << endl;

					cin >> option;

					switch (option) {
					case 1:
						cout << " " << endl;
						cout << player2Name << " Swung at " << player1Name << endl;
						player1Health -= (player2Strength - (player1Defense / 5));
						cout << player2Name << " Dealt " << player2Strength << " Damage" << endl;
						player2SpecialUse++;
						turn1 = true;
						turn2 = false;
						break;

					case 2:
						cout << " " << endl;
						cout << player2Name << " uses Block" << endl;
						player2Defense += 5;
						cout << player2Name << " Incresed Their Defense By 5" << endl;
						player2SpecialUse++;
						turn1 = true;
						turn2 = false;
						break;

					case 3:
						cout << " " << endl;
						cout << player2Name << " Charged at " << player1Name << endl;
						player1Health -= ((player2Strength + 5) - (player1Defense / 5));
						cout << player2Name << " Dealt " << player2Strength + 5 << " Damage" << endl;
						player2SpecialUse++;
						turn1 = true;
						turn2 = false;
						break;

					case 4:
						cout << " " << endl;
						if (player2SpecialUse == 4) {
							cout << player2Name << " uses Their Special!!" << endl;
							player2Special -= 4;
							player2Defense += (player2Special / 2);
							cout << player2Name << " Increased Their Defense By " << player2Defense << endl;
							player2SpecialUse = 0;
							turn1 = true;
							turn2 = false;
							break;
						}
						else {
							cout << "Not Enough Power" << endl;
						}
						break;

					case 0:
						cout << "Unexpected Error" << endl;
						break;

					default:
						cout << "Unexpected Error" << endl;
						break;

					}

				}
				else if (player2SpecialName == "Mana") {
					cout << "1: Fireball" << endl;
					cout << "2: Heal" << endl;
					cout << "3: Shield" << endl;
					cout << "4: Special" << endl;

					cin >> option;

					switch (option) {
					case 1:
						cout << " " << endl;
						cout << player2Name << " uses fireball" << endl;
						player1Health -= (player2Strength - (player1Defense / 5));
						cout << player2Name << " Dealt " << player2Strength << " Damage" << endl;
						player2SpecialUse++;
						turn1 = true;
						turn2 = false;
						break;

					case 2:
						cout << " " << endl;
						cout << player2Name << " uses heal" << endl;
						player2Health += 5;
						cout << player2Name << " Increased Their Health By 5" << endl;
						player2SpecialUse++;
						turn1 = true;
						turn2 = false;
						break;

					case 3:
						cout << " " << endl;
						cout << player2Name << " uses shield" << endl;
						player2Defense += 5;
						cout << player2Name << " Increased Their Defense By 5" << endl;
						player2SpecialUse = 0;
						turn1 = true;
						turn2 = false;
						break;

					case 4:
						cout << " " << endl;
						if (player2SpecialUse == 4) {
							cout << player2Name << " uses Their Special!!" << endl;
							player2Special -= 4;
							player2Strength *= player2Special;
							player2Health -= player1Strength - player2Strength;
							cout << player2Name << " Dealt " << player2Strength << " Damage" << endl;
							player2Strength = mageStrength;
							player2SpecialUse++;
							turn1 = true;
							turn2 = false;
							break;
						}
						else {
							cout << "Not Enough Power" << endl;
						}
						break;

					case 0:
						cout << "Unexpected Error" << endl;
						break;

					default:
						cout << "Unexpected Error" << endl;
						break;

					}

				}
				else if (player2SpecialName == "Speed") {

					cout << "1: Fire Arrow" << endl;
					cout << "2: Poison Arrow" << endl;
					cout << "3: Regular Arrow" << endl;
					cout << "4: Special" << endl;

					cin >> option;

					switch (option) {
					case 1:
						cout << " " << endl;
						cout << player2Name << " Shot a Fire Arrow at " << player2Name << endl;
						player1Health -= ((player2Strength + 5) - player1Defense);
						cout << player2Name << " Dealt " << player2Strength + 5 << " Damage" << endl;
						player2SpecialUse++;
						turn1 = true;
						turn2 = false;
						break;

					case 2:
						cout << " " << endl;
						cout << player1Name << " Shot a Poison Arrow at " << player2Name << endl;
						player1Health -= 5;
						player1Status = "Poisoned";
						cout << player2Name << " Dealt 5 Damage" << endl;
						cout << player1Name << " Is Now Poisoned! " << endl;
						player2SpecialUse++;
						turn1 = true;
						turn2 = false;
						break;

					case 3:
						cout << " " << endl;
						cout << player1Name << " Shot a Regular Arrow at " << player2Name << endl;
						player1Health -= (player2Strength - (player1Defense / 5));
						cout << player2Name << " Dealt " << player2Strength << " Damage" << endl;
						player2SpecialUse++;
						turn1 = true;
						turn2 = false;
						break;

					case 4:
						cout << " " << endl;
						if (player2SpecialUse == 4) {
							cout << player2Name << " uses Their Special!!" << endl;
							player2Special -= 4;
							player2Strength += player2Special;
							player1Health -= player2Strength - player1Defense;
							cout << player2Name << " Dealt " << player2Strength << " Damage" << endl;
							player2Strength = archerStrength;
							player2SpecialUse = 0;
							turn1 = true;
							turn2 = false;
							break;
						}
						else {
							cout << "Not Enough Power" << endl;
						}
						break;

					case 0:
						cout << "Unexpected Error" << endl;
						break;

					default:
						cout << "Unexpected Error" << endl;
						break;

					}
				}
			}
		
	}

	cout << " " << endl;

	if (player1Health <= 0 && player2Health <= 0) {
		cout << "*************************" << endl;
		cout << "DRAW" << endl;
		cout << "*************************" << endl;
	}
	else if (player1Health > 0 && player2Health <= 0) {
		cout << "*************************" << endl;
		cout << "Player1: " << player1Name << " Wins!" << endl;
		cout << "*************************" << endl;
		player1Wins++;
		playerName = player1Name;
		playerWins = player1Wins;
		playerSpecialName = player1SpecialName;
	}
	else if (player2Health > 0 && player1Health <= 0) {
		cout << "*************************" << endl;
		cout << "Player2: " << player2Name << " Wins!" << endl;
		cout << "*************************" << endl;
		player2Wins++;
		playerName = player2Name;
		playerWins = player2Wins;
		playerSpecialName = player2SpecialName;
	}

	resetPlayer(playerSpecialName);
	savePlayerStats(playerName, playerStrength, playerDefense, playerSpecial, playerWins, playerHealth, playerSpecialName);

}

void setupPlayerStats(string &playerName, int &playerStrength, int &playerDefense, int &playerSpecial, int &playerWins, int &playerHealth, string &playerSpecialName)
{
	fstream file;

	if (playerName == "" || playerName == "back") {
		playerName = "Bot";
	}
	string filename = playerName + ".txt";
	filename = "C:\\Users\\iD Student\\Desktop\\Stefan U\\NovaRPG\\NovaRPG\\SaveData\\" + filename;

	file.open(filename, ios::in);
	if (!file.is_open()) {
		cout << "File Does not Exist" << endl;
		skip = true;
		selectedPlayer--;
		Sleep(1000);
	}
	else {
		cout << "Loading Character Data" << endl;

		file >> playerName;
		file >> playerWins;
		file >> playerStrength;
		file >> playerDefense;
		file >> playerHealth;
		file >> playerSpecial;
		file >> playerSpecialName;

	}
}

void savePlayerStats(string playerName, int playerStrength, int playerDefense, int playerSpecial, int playerWins, int playerHealth, string playerSpecialName)
{
	fstream file;
	string filename = playerName + ".txt";
	filename = "C:\\Users\\iD Student\\Desktop\\Stefan U\\NovaRPG\\NovaRPG\\SaveData\\" + filename;

	file.open(filename, ios::out);

	cout << "Saving Character Data" << endl;

	file << playerName << endl;
	file << playerWins << endl;
	file << playerStrength << endl;
	file << playerDefense << endl;
	file << playerHealth << endl;
	file << playerSpecial << endl;
	file << playerSpecialName << endl;

	file.close();
}

void resetPlayer(string playerSpecialName) {
	if (playerSpecialName == "Mana") {
		playerHealth = mageHealth;
		playerDefense = mageDefense;
		playerStrength = mageStrength;
		playerSpecial = mageMagic;
	}
	else if (playerSpecialName == "Speed") {
		playerHealth = archerHealth;
		playerDefense = archerDefense;
		playerStrength = archerStrength;
		playerSpecial = archerSpeed;
	} 
	else if (playerSpecialName == "Armour") {
		playerHealth = knightHealth;
		playerDefense = knightDefense;
		playerStrength = knightStrength;
		playerSpecial = knightArmour;
	}
}