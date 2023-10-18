# CSDS 233 Project 4 - HashMap

HashMap implementation in Java.  HashMap is used to store and calculate various word statistics read from txt file.

Note that this HashMap implementation is specifically for <String, Integer> key-value pairs.

## Features
- HashMap with standard operations
    - put: checks for collisions
    - update
    - get
- Tokenizer for calculating word statistics
    - wordCount: return the number of occurrences for a specified word
    - wordPairCount: return the number of occurrences of a pair of words
    - wordRank: returns the rank of a specified word (rank 1 being the most frequent word)
    - wordPairRank: returns the rank of a pair of words (rank 1 being the most frequent word)
    - mostCommonWords: returns a list of the *k* most common words
    - leastCommonWords: returns a list of the *k* least common words
    - mostCommonWordPairs: returns a list of the *k* most common word pairs
    - leastCommonWordPairs: returns a list of the *k* least common word pairs
    - mostCommonCollocs: returns a list of the *k* most common words preceeding or following the specified base word
    - mostCommonCollocsExc: returns a list of the *k* most common words preceeding or following the base word excluding words specified in the exclusion list
    - generateWordString: returns a String of *k* words starting with a specified *startWord* with each word followed by its most common following word
 
Unit testing and demonstrations included.
