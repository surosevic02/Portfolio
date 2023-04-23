:: This is a script that I created in batch
:: It basically navigates to the directory where my python
:: file is then it executes the file

@echo off

D:
cd "\PythonProj\Urosevic_Stefan_CS480_Programming02"
call  C:\Users\dabro\anaconda3\condabin\activate.bat


:: Here I run the file with parameters
:: and it continuously loops until finished
:loop
	cls
	set /P file="Enter Input File: "
	set /P option="Enter Run Option (1-4): "
	echo.
	python cs480_P02_A20464817.py %file% %option%
	echo.

set /P cont="Would You Like to Continue? (y/n): "
if %cont%==y call :loop
else exit