import sys
import csv
from time import time
from queue import PriorityQueue

# Report Function --------------------------------------
def report_results(alg="",
                   path=["FAILURE: NO PATH FOUND"],
                   number=0,
                   cost=0,
                   time=0,
                   expanded=None):
    pathString = ', '.join(path)
    
    print("")
    print(alg)
    print("Solution path: " + pathString)
    print("Number of states on a path: " + str(number))
    
    if expanded != None:
        print("Number of expanded nodes: " + str(expanded))
    
    print("Path cost: " + str(cost))
    print("Execution time: " + str(time) + " seconds")

# Get Adjacent States ----------------------------------
def get_adjacent_nodes(currNode):
    adjacentNodes = []
    
    # Load Driving CSV and get dictionary return as tuples in list
    drreader = None
    with open('driving.csv') as drfile:
        drreader = csv.DictReader(drfile)
        
        for row in drreader:
            if (row['STATE'] == currNode):
                for item in row.items():
                    if (item[0] != 'STATE' and int(item[1]) > 0):
                        adjacentNodes.append((int(item[1]), item[0]))
    
    drfile.close()
    return adjacentNodes

# Expand -----------------------------------------------
def expand_gbs(node):
    adj = get_adjacent_nodes(node[1])
    moves = []
    
    for a in adj:
        cost = node[0] + a[0]
        moves.append((cost, a[1], node[1]))
        
    return moves

# Greedy Best First Search -----------------------------
def greedy_best_search(initial, goal):
    tStart = time()
    expandedNodes = 0
    frontier = PriorityQueue()
    frontier.put((0, initial, None))
    reached = {initial: (0, None)}
    
    while frontier.empty() == False:
        node = frontier.get()
        expandedNodes += 1
        
        if (node[1] == goal):
            tFound = time()
            
            path = [node[1]]
            parent = node[2]
            while (parent != None):
                path.append(parent)
                parent = reached[parent][1]          
            path.reverse()
            
            report_results("Greedy Best First Search:", path, len(path), 
                           node[0], (tFound - tStart), expandedNodes)
            return
        
        for n in expand_gbs(node):
            if ((n[1] not in reached.keys()) or (n[0] < reached[n[1]][0])):
                reached[n[1]] = (n[0], node[1])
                frontier.put(n)
                
    tFail = time()
    report_results("Greedy Best First Search:", time=(tFail-tStart))

# Get Distance ----------------------------------------
def get_distance(inState):
    distance = -1
    with open('straightline.csv') as slfile:
        sldreader = csv.DictReader(slfile)
        
        # Get distance between the two points
        for row in sldreader:
            if (row['STATE'] == inState):
                if (sys.argv[2] in row.keys()):
                    distance = int(row[sys.argv[2]])
                    
    slfile.close()
    return distance
    
# A* Search -------------------------------------------
def a_star_search(initial, goal):
    tStart = time()
    frontier = PriorityQueue()
    frontier.put((0, initial))
    cost_currently = {initial: 0}
    reached_from = {initial: None}
    expandedNodes = 0
    
    while frontier.empty() == False:
        node = frontier.get()
        expandedNodes += 1
        
        if (node[1] == goal):
            tFound = time()
            
            path = [node[1]]
            parent = reached_from[node[1]]
            while parent != None:
                path.append(parent)
                parent = reached_from[parent]
            path.reverse()
            
            report_results("A* Search:", path, len(path), cost_currently[node[1]],
                           (tFound-tStart), expandedNodes)
            return
        
        for n in get_adjacent_nodes(node[1]):
            cost = cost_currently[node[1]] + n[0]
            if ((n[1] not in cost_currently.keys()) or (cost < cost_currently[n[1]])):
                cost_currently[n[1]] = cost
                p = cost + get_distance(n[1])
                frontier.put((p, n[1]))
                reached_from[n[1]] = node[1]
    
    tFail = time()
    report_results("A* Search:", time=(tFail-tStart))
    
# MAIN -------------------------------------------------
# Checks if there are a valid amount of args then runs
if (len(sys.argv) == 3):
    # Print Initial Info
    print("Urosevic, Stefan, A20464817 solution:")
    print("Initial State: " + sys.argv[1])
    print("Goal State: " + sys.argv[2])
    
    # Run the Algorithms
    greedy_best_search(sys.argv[1], sys.argv[2])
    a_star_search(sys.argv[1], sys.argv[2])

    
else:
    sys.exit("ERROR: Not enough or too many input arguments")