# In this Assignment, I used a bruteforce algorithm to find the encrypted
# message from the cyphertext CSYEVIXIVQMREXIH

# Function that maps a letter to another letter based on a key
def map_func(k, m):
    
    if (m == ' '):
        return m
    
    if (ord(m) - k) < ord('A'):
        return chr((ord(m) - k) + 26)
        
    return chr(ord(m) - k)

# Input cipher check if it's a valid cipher
while (True):
    c = input("Enter the Ciphertext? ")
    
    res = c != '' and all(chr.isalpha() or chr.isspace() for chr in c)
    if res:
        break;

c = c.upper()

# Brute force using all possible keys to get plaintext message
for i in range(0, 26):
    p = ""
    for m in c:
        p += map_func(i, m)
    print("Key: " + str(i) + " Plaintext: " + p)
    
# CSYEVIXIVQMREXIH use this Cipher to test it out answer should be key 4