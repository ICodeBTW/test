def levenshtein_distance(s, t):
    # Create a matrix of distances
    m = len(s)
    n = len(t)
    d = [[0] * (n + 1) for i in range(m + 1)]
    
    # Initialize the matrix with distances between prefixes
    for i in range(m + 1):
        d[i][0] = i
    for j in range(n + 1):
        d[0][j] = j
        
    # Fill the matrix
    for j in range(1, n + 1):
        for i in range(1, m + 1):
            if s[i - 1] == t[j - 1]:
                d[i][j] = d[i - 1][j - 1]
            else:
                d[i][j] = min(d[i - 1][j] + 1,  # Deletion
                              d[i][j - 1] + 1,  # Insertion
                              d[i - 1][j - 1] + 1)  # Substitution
                
    # Return the final value in the matrix
    return d[m][n]
Here's how you can use this function:

python
Copy code
s1 = "kitten"
s2 = "sitting"
distance = levenshtein_distance(s1, s2)
print(distance)  # Output: 3
In this example, the Levenshtein distance between "kitten" and "sitting" is 3, which means that three operations (insertions, deletions, or substitutions) are required to transform one string into the other.





