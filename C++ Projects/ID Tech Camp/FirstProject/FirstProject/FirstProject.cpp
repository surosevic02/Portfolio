// FirstProject.cpp : Defines the entry point for the console application.
//

#include "stdafx.h"
#include <string>
#include <iostream>
#include <random>
using namespace std;

int squareSize;
//string square = "# ";

int main(){
	cout << "Input Size of Square" << endl;
	cin >> squareSize;

	for (int k = 0; k < squareSize; k++){
		for (int i = 0; i < squareSize; i++) {
			cout << "# ";
		}
		cout << "" << endl;
	}
}