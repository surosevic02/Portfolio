# In this assignment I had to create a script that required us to guess each letter
# of the encrypted message using frequency analysis
# The Answer: common sense is not so common

# Initial variables for the cipher
c = "XWEEWM,IBMIB,TI,MWK,IW,XWEEWM"
d = {}
key = {}
# Used this link: https://www3.nd.edu/~busiforc/handouts/cryptography/letterfrequencies.html
freq = {
    'A' : 8.4966,
    'B' : 2.0720,
    'C' : 4.5388,
    'D' : 3.3844,
    'E' : 11.1607,
    'F' : 1.8121,
    'G' : 2.4705,
    'H' : 3.0034,
    'I' : 7.5448,
    'J' : 0.1965,
    'K' : 1.1016,
    'L' : 5.4893,
    'M' : 3.0129,
    'N' : 6.6544,
    'O' : 7.1635,
    'P' : 3.1671,
    'Q' : 0.1962,
    'R' : 7.5809,
    'S' : 5.7351,
    'T' : 6.9509,
    'U' : 3.6308,
    'V' : 1.0074,
    'W' : 1.2899,
    'X' : 0.2902,
    'Y' : 1.7779,
    'Z' : 0.2722,
    ',' : 0
}

# Storing values of the amount of items in the dictionary
for ch in c:
    try:
        d[ch] += 1
    except:
        d[ch] = 1

print(str(d))

# Compare the frequency o the letters in cipher to known letters
for i in d:    
    print(i + ' ' + str(d[i]) + ' ' + str(freq[i]))

# Ask use to guess what they think each letter is
for k in d:
    i = input("What do you think '" + k + "' corresponds to in the English Language? ")
    key[k] = i
    
print(str(key))

# Print out the users guess as plaintext
ret = ""
for ch in c:
    ret += key[ch]

print(ret)