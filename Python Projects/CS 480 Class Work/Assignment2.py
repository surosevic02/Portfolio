import sys
import csv
import os.path
import numpy as np
import timeit

# var for node counting
nodeCount = 0

# == Application function for IO ==============================================
# -- Report Function -------------
def report(alg="TEST", nodes=0, stime=0, puzzle=[], solved=None):
    print("Algorithm: " + alg)
    print("")
    
    print("Input puzzle:")
    for i in puzzle:
        print(''.join(map(str,i)))
    
    print("")
    print("Number of search tree nodes generated: " + str(nodes))
    print("Search time: " + str(stime))
    
    if (solved != None):
        print("")
        print("Solved puzzle:")
        for i in solved:
            print(''.join(map(str,i)))
        
    print("")
    return True

# -- CSV File Read Function -------
def open_csv_file(name):
    column = []
    with open(name) as cfile:
        creader = csv.reader(cfile)
        for row in creader:
            ret = []
            for r in row:
                try:
                    int(r)
                    ret.append(r)
                except:
                    ret.append('X')
            column.append(ret)
    return column

# -- CSV File Write Function ------
def write_csv_file(name, table):
    filename = name[:-4] + "_SOLUTION.csv"
    tarr = np.asarray(table)
    np.savetxt(filename, tarr, fmt='%s', delimiter=",")
    return True

# -- Brute Force Search =======================================================
def brute_force(puzzleIn, name, stimer=0, originalP=[]):
    global  nodeCount

    for y in range(0, 9):
        for x in range(0, 9):
            if puzzleIn[y][x] == 'X':
                for n in range(1, 10):
                    nodeCount+=1
                    #valid = check_rule(puzzleIn, n, (x, y)) # IT TAKES FOREVER FOR THIS TO RUN WITHOUT THIS ADDED FOR TESTING
                    #if valid: # REMOVE THIS AND ADJUST FOR ACTUAL BRUTE FORCE!
                    puzzleIn[y][x] = str(n)
                    rec = brute_force(puzzleIn, name, stimer, originalP)
                    if rec:
                        return True
                    puzzleIn[y][x] = 'X'
                return False

    if test_puzzle(puzzleIn, False):
        report(name, nodeCount, timeit.default_timer()-stimer, originalP, puzzleIn)
        write_csv_file(name, puzzleIn)
        return True
    else:
        return False

# -- CSP back-tracking Search =================================================
def CSP_back(puzzleIn, name, stimer=0, originalP=[]):
    global nodeCount
    
    var = find_val(puzzleIn, 'X')
    if var[0] == -1 or var[1] == -1:
        if test_puzzle(puzzleIn, False):
            report(name, nodeCount, timeit.default_timer()-stimer, originalP, puzzleIn)
            write_csv_file(name, puzzleIn)
            return True
        else:
            return False
    
    for vals in available_numbers(puzzleIn, var[0], var[1]):
        puzzleIn[var[1]][var[0]] = str(vals)
        nodeCount += 1
        result = CSP_back(puzzleIn, name, stimer, originalP)
        if result != False:
            return True
        puzzleIn[var[1]][var[0]] = 'X'
    return False

# - CSP forward-checking and MRV ==============================================
def CSP_forward(puzzleIn, name, stimer=0, originalP=[]):
    global nodeCount
    
    var = find_val(puzzleIn, 'X')
    if var[0] == -1 or var[1] == -1:
        if test_puzzle(puzzleIn, False):
            report(name, nodeCount, timeit.default_timer()-stimer, originalP, puzzleIn)
            write_csv_file(name, puzzleIn)
            return True
        else:
            return False

    for vals in available_numbers(puzzleIn, var[0], var[1]):
        puzzleIn[var[1]][var[0]] = vals
        nodeCount += 1
        cps = copy_set(puzzleIn)
        if check_forward(cps, var[0], var[1], vals) != False:
            result = CSP_forward(puzzleIn, name, stimer, originalP)
            if result != False:
                return True
        puzzleIn[var[1]][var[0]] = 'X'
    return False

# - Copy Set - CSP_forward help
# Copys the board of sets for forward checking
def copy_set(puzzleIn):
    setsList=[]
    for y in range(0,9):
        r = []
        for x in range(0,9):
            if puzzleIn[y][x] == 'X':
                t = available_numbers(puzzleIn, x, y).copy()
                r.append(t)
            else:
                r.append('-')
        setsList.append(r)
    return setsList

# - Sets checker - CSP_forward help
def check_forward(setslist, x, y, var):
    setslist[y][x] = '-'

    row = setslist[y]
    col = [r[x] for r in setslist]
    
    cy = (y//3)*3
    cx = (x//3)*3
    
    cell = []
    br = setslist[cy:cy+3]
    for r in br:
        cell += r[cx:cx+3]

    # Check Row
    for r in row:
        if r != '-':
            r -= {str(var)}
            if len(r) == 0:
                return False

    # Check Column
    for c in col:
        if c != '-':
            c -= {str(var)}
            if len(c) == 0:
                return False
    
    # Check Cell
    for ce in cell:
        if ce != '-':
            ce -= {str(var)}
            if len(ce) == 0:
                return False

    return True

# - Check Rule if number can be there
# THIS WAS ONLY ADDED TO SPEED UP BRUTE
def check_rule(board, val, loc):
    if str(val) in board[loc[1]]:
        return False
    
    if str(val) in [c[loc[0]] for c in board]:
        return False
    
    cy = (loc[1]//3)*3
    cx = (loc[0]//3)*3
    
    cell = []
    rows = board[cy:cy+3]
    for c in rows:
        cell += c[cx:cx+3]
    
    if str(val) in cell:
        return False
    return True

# - Find Val In Puzzle -----------
# This function finds the first place 'X' appears in the board
def find_val(board, value):
    n = -1
    y = -1
    for r, row in enumerate(board):
        try:
            y = r
            n = row.index(value)
            break
        except:
            continue
    
    return (n, y)

# - Calculate possible number ---- FOR BOTH CSP
# Used for reducing the scope of possible numbers
def available_numbers(board, x, y):    
    ret = {'1', '2', '3', '4', '5', '6', '7', '8', '9'}
    
    brow = board[y]
    bcol = [row[x] for row in board]
    
    # Row
    ret -= set(brow)
    
    # Column
    ret -= set(bcol)
    
    # Cell
    cellLi = []
    cy = ((y//3)*3)
    cx = ((x//3)*3)
    rowBoard = board[cy:cy+3]
    
    for row in rowBoard:
        cellLi += row[cx:cx+3]
    ret -= set(cellLi)
    
    return ret

# == Test Puzzle ==============================================================
def test_puzzle(puzzleIn, output=True):
    if output:
        report("TEST", 0, 0, puzzleIn)
    
    if(check_size(puzzleIn) and check_row(puzzleIn) and check_column(puzzleIn) and check_cells(puzzleIn)):
        if output:
            print("This is a valid, solved, Sudoku puzzle.")
        return True
    else:
        if output:
            print("ERROR: This is NOT a solved Sudoku puzzle.")
        return False

# -- Check Size ---
def check_size(puzzleIn):
    if (len(puzzleIn) != 9):
        return False
    return True

# -- Check Row ----
def check_row(puzzleIn):
    rowSet = set()
    for i in puzzleIn:
        for r in i:
            if (r == 'X'):
                return False
            if (int(r) > 0 and int(r) < 10 and (r not in rowSet)):
                rowSet.add(r)
            else:
                return False
        if (len(rowSet) != 9):
            return False
        rowSet.clear()       
    return True

# -- Check Column -
def check_column(puzzleIn):
    colSet = set()
    for c in range(0, 8):
        for i in puzzleIn:
            if (i[c] == 'X'):
                return False
            if (int(i[c]) > 0 and int(i[c]) < 10 and (i[c] not in colSet)):
                colSet.add(i[c])
            else:
                return False
        if (len(colSet) != 9):
            return False
        colSet.clear() 
    return True

# -- Check Cells --
def check_cells(puzzleIn):
    cellSet1 = set()
    cellSet2 = set()
    cellSet3 = set()
    
    yStart = 0
    
    for rep in range(0, 3): # Three Vertical chunks
        yStart = rep * 3
        for y in range (yStart, yStart + 3): # Starting point to Ending Vert
            for r in range(0, len(puzzleIn[y])): # Row
                item = puzzleIn[y][r]
                if r <= 2:
                    if(int(item) > 0 and int(item) < 10 and (item not in cellSet1)):
                        cellSet1.add(item)
                if r > 2 and r <= 5:
                    if(int(item) > 0 and int(item) < 10 and (item not in cellSet2)):
                        cellSet2.add(item)
                if r > 5:
                    if(int(item) > 0 and int(item) < 10 and (item not in cellSet3)):
                        cellSet3.add(item)    

        if (len(cellSet1) != 9 or len(cellSet2) != 9 or len(cellSet3) != 9):
            return False
        
        cellSet1.clear()
        cellSet2.clear()
        cellSet3.clear()
        
    return True

# -- Main Function ============================================================
invalidMessage = "ERROR: Not enough/too many/illegal input arguments."

if (len(sys.argv) != 3):
    sys.exit(invalidMessage)

try:
    int(sys.argv[2])
except:
    sys.exit(invalidMessage)

if (sys.argv[2].isdigit() and (int(sys.argv[2]) < 1 or int(sys.argv[2]) > 4)):
    sys.exit(invalidMessage)

if (sys.argv[1][-4:] != ".csv"):
    sys.exit(invalidMessage)

if (not os.path.isfile(sys.argv[1])):
    sys.exit(invalidMessage)

else:
    print("Urosevic, Stefan, A20464817 solution:")
    print("Input file: " + sys.argv[1])
    
    if int(sys.argv[2]) == 1:
        brute_force(open_csv_file(sys.argv[1]), sys.argv[1], timeit.default_timer(), originalP=open_csv_file(sys.argv[1]))
        
    if int(sys.argv[2]) == 2:
        CSP_back(open_csv_file(sys.argv[1]), sys.argv[1], timeit.default_timer(), originalP=open_csv_file(sys.argv[1]))
            
    if int(sys.argv[2]) == 3:
        CSP_forward(open_csv_file(sys.argv[1]), sys.argv[1], timeit.default_timer(), originalP=open_csv_file(sys.argv[1]))
            
    if int(sys.argv[2]) == 4:
        test_puzzle(open_csv_file(sys.argv[1]), sys.argv[1])
