import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Computes word statistics from a given text
 * 
 * @author <i>Charlie Lin</i>
 */
public class WordStat {
    /** word array for the text file or array input */
    private ArrayList<String> wordList;

    /** hash table containing single words and their frequencies */
    private HashTable wordTable;

    /** hash table containing word pairs and their frequencies */
    private HashTable pairTable;

    /** hash table containing single words and their ranks */
    private HashTable wordRankTable;

    /** hash table containing word pairs and their ranks */
    private HashTable pairRankTable;

    /** sorted array containing all single words in rank order */
    private ArrayList<HashEntry> wordRankList;

    /** sorted array containing all word pairs in rank order */
    private ArrayList<HashEntry> pairRankList;

    /**
     * Computes word statistics from a txt file
     * 
     * @param fileName the name of the txt file to get word statistics from
     */
    public WordStat(String fileName) {
        try {
            Tokenizer words = new Tokenizer(fileName);
            wordList = words.wordList();
            // initialize tables with respect to size of word list to reduce rehash operations
            wordTable = new HashTable(wordList.size());
            pairTable = new HashTable(wordList.size() * 8);
            // add individual words to table
            wordTable.put(wordList.get(0), 1); // have to put the first word "manually" due to offset for word pairs
            for (int i = 1; i < wordList.size(); i++) {
                // check if table has the word
                if (wordTable.get(wordList.get(i)) == -1)
                    wordTable.put(wordList.get(i), 1);
                // update word count by 1
                else
                    wordTable.update(wordList.get(i), wordTable.get(wordList.get(i)) + 1);
                /* handling word pairs */
                String pair = wordList.get(i - 1) + " " + wordList.get(i);
                if (pairTable.get(pair) == -1)
                    pairTable.put(pair, 1);
                else
                    pairTable.update(pair, pairTable.get(pair) + 1);
            }

            /* construct ranking arrays */
            wordRankList = new ArrayList<HashEntry>(wordList.size() / 2);
            pairRankList = new ArrayList<HashEntry>(wordList.size() * 2);
            HashTable tracker = new HashTable(wordList.size() * 16); // keeps track of what has already been added to arrays
            HashEntry currentWord = new HashEntry(wordList.get(0), wordTable.get(wordList.get(0)));
            HashEntry currentPair;
            wordRankList.add(currentWord);
            tracker.put(currentWord.getKey(), 1); // value is arbitrary when putting into tracker
            for (int i = 1; i < wordList.size(); i++) {
                currentWord = new HashEntry(wordList.get(i), wordTable.get(wordList.get(i)));
                currentPair = new HashEntry(wordList.get(i - 1) + " " + wordList.get(i),
                        pairTable.get(wordList.get(i - 1) + " " + wordList.get(i)));
                // only add word or pair to lists if not already in the hash table
                if (tracker.get(wordList.get(i)) == -1) {
                    tracker.put(currentWord.getKey(), 1);
                    wordRankList.add(currentWord);
                }
                if (tracker.get(wordList.get(i - 1) + " " + wordList.get(i)) == -1) {
                    tracker.put(currentPair.getKey(), 1);
                    pairRankList.add(currentPair);
                }
            }
            // sort arrays
            Collections.sort(wordRankList);
            Collections.sort(pairRankList);
            wordRankTable = new HashTable(wordList.size());
            pairRankTable = new HashTable(wordList.size() * 8);
            // put array values into rank tables and account for duplicate ranks
            int currentRank = 1;
            wordRankTable.put(wordRankList.get(0).getKey(), 1);
            for (int i = 1; i < wordRankList.size(); i++) {
                if (wordTable.get(wordRankList.get(i).getKey()) != wordTable.get(wordRankList.get(i - 1).getKey())) {
                    currentRank = i + 1;
                }
                wordRankTable.put(wordRankList.get(i).getKey(), currentRank);
            }
            currentRank = 1;
            pairRankTable.put(pairRankList.get(0).getKey(), 1);
            for (int i = 1; i < pairRankList.size(); i++) {
                if (pairTable.get(pairRankList.get(i).getKey()) != pairTable.get(pairRankList.get(i - 1).getKey())) {
                    currentRank = i + 1;
                }
                pairRankTable.put(pairRankList.get(i).getKey(), currentRank);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Computes word statistics from String array
     * 
     * @param wordList the String array to get word statistics from
     */
    public WordStat(String[] wordArray) {
        Tokenizer words = new Tokenizer(wordArray);
        wordList = words.wordList();
        // initialize tables with respect to size of word list to reduce rehash operations
        wordTable = new HashTable(wordList.size());
        pairTable = new HashTable(wordList.size() * 8);
        // add individual words to table
        wordTable.put(wordList.get(0), 1); // have to put the first word "manually" due to offset for word pairs
        for (int i = 1; i < wordList.size(); i++) {
            // check if table has the word
            if (wordTable.get(wordList.get(i)) == -1)
                wordTable.put(wordList.get(i), 1);
            // update word count by 1
            else
                wordTable.update(wordList.get(i), wordTable.get(wordList.get(i)) + 1);
            /* handling word pairs */
            String pair = wordList.get(i - 1) + " " + wordList.get(i);
            if (pairTable.get(pair) == -1)
                pairTable.put(pair, 1);
            else
                pairTable.update(pair, pairTable.get(pair) + 1);
        }

        /* construct ranking arrays */
        wordRankList = new ArrayList<HashEntry>(wordList.size() / 2);
        pairRankList = new ArrayList<HashEntry>(wordList.size() * 2);
        HashTable tracker = new HashTable(wordList.size() * 16); // keeps track of what has already been added to arrays
        HashEntry currentWord = new HashEntry(wordList.get(0), wordTable.get(wordList.get(0)));
        HashEntry currentPair;
        wordRankList.add(currentWord);
        tracker.put(currentWord.getKey(), 1); // value is arbitrary when putting into tracker
        for (int i = 1; i < wordList.size(); i++) {
            currentWord = new HashEntry(wordList.get(i), wordTable.get(wordList.get(i)));
            currentPair = new HashEntry(wordList.get(i - 1) + " " + wordList.get(i),
                    pairTable.get(wordList.get(i - 1) + " " + wordList.get(i)));
            // only add word or pair to lists if not already in the hash table
            if (tracker.get(wordList.get(i)) == -1) {
                tracker.put(currentWord.getKey(), 1);
                wordRankList.add(currentWord);
            }
            if (tracker.get(wordList.get(i - 1) + " " + wordList.get(i)) == -1) {
                tracker.put(currentPair.getKey(), 1);
                pairRankList.add(currentPair);
            }
        }
        // sort arrays
        Collections.sort(wordRankList);
        Collections.sort(pairRankList);
        wordRankTable = new HashTable(wordList.size());
        pairRankTable = new HashTable(wordList.size() * 8);
        // put array values into rank tables and account for duplicate ranks
        int currentRank = 1;
        wordRankTable.put(wordRankList.get(0).getKey(), 1);
        for (int i = 1; i < wordRankList.size(); i++) {
            if (wordTable.get(wordRankList.get(i).getKey()) != wordTable.get(wordRankList.get(i - 1).getKey())) {
                currentRank = i + 1;
            }
            wordRankTable.put(wordRankList.get(i).getKey(), currentRank);
        }
        currentRank = 1;
        pairRankTable.put(pairRankList.get(0).getKey(), 1);
        for (int i = 1; i < pairRankList.size(); i++) {
            if (pairTable.get(pairRankList.get(i).getKey()) != pairTable.get(pairRankList.get(i - 1).getKey())) {
                currentRank = i + 1;
            }
            pairRankTable.put(pairRankList.get(i).getKey(), currentRank);
        }
    }

    /**
     * Returns the count of the specified word
     * 
     * @param word the word to get the count of
     * @return the number of occurrences of the specified word, 0 if the word does not appear
     */
    public int wordCount(String word) {
        int count = wordTable.get(word);
        if (count > 0)
            return count;
        return 0;
    }

    /**
     * Returns the count of a specified word pair (two words in succession)
     * 
     * @param w1 the first word in the pair
     * @param w2 the second word in the pair
     * @return the count of the specified word pair, 0 if word pair does not appear
     */
    public int wordPairCount(String w1, String w2) {
        int count = pairTable.get(w1 + " " + w2);
        if (count > 0)
            return count;
        return 0;
    }

    /**
     * Returns the rank of a word, rank 1 being the most common word
     * 
     * @param word the word to return the rank of
     * @return the rank of the specified word, 0 if the word does not appear
     */
    public int wordRank(String word) {
        int rank = wordRankTable.get(word);
        if (rank > 0)
            return rank;
        return 0;
    }

    /**
     * Returns the rank of a word pair, rank 1 being the most common word pair
     * 
     * @param w1 the first word in the pair
     * @param w2 the second word in the pair
     * @return the rank of the specified word pair, 0 if the word pair does not appear
     */
    public int wordPairRank(String w1, String w2) {
        int rank = pairRankTable.get(w1 + " " + w2);
        if (rank > 0)
            return rank;
        return 0;
    }

    /**
     * Returns a list of the <i>k</i> most common words in greatest to least order
     * of their count
     * 
     * @param k the size of the list
     * @return a list of the most common words in ascending order of count
     */
    public String[] mostCommonWords(int k) {
        String[] kMostCommonWords;
        // edge case (k too small)
        if (k <= 0)
            kMostCommonWords = new String[0];
        // edge case (k too large)
        else if (k >= wordRankList.size()) {
            kMostCommonWords = new String[wordRankList.size()];
            for (int i = 0; i < wordRankList.size(); i++) {
                kMostCommonWords[i] = wordRankList.get(i).getKey();
            }
        }
        // general case, 0 < k < number of words
        else {
            kMostCommonWords = new String[k];
            for (int i = 0; i < k; i++)
                kMostCommonWords[i] = wordRankList.get(i).getKey();
        }
        return kMostCommonWords;
    }

    /**
     * Returns a list of the <i>k</i> least common words in least to greatest order
     * of their count
     * 
     * @param k the size of the list
     * @return a list of the least common words in descending order of count
     */
    public String[] leastCommonWords(int k) {
        String[] kLeastCommonWords;
        // edge case (k too small)
        if (k <= 0)
            kLeastCommonWords = new String[0];
        // edge case (k too large)
        else if (k >= wordRankList.size()) {
            kLeastCommonWords = new String[wordRankList.size()];
            for (int i = 0; i < wordRankList.size(); i++) {
                kLeastCommonWords[i] = wordRankList.get(wordRankList.size() - 1 - i).getKey();
            }
        }
        // general case, 0 < k < number of words
        else {
            kLeastCommonWords = new String[k];
            for (int i = 0; i < k; i++)
                kLeastCommonWords[i] = wordRankList.get(wordRankList.size() - 1 - i).getKey();
        }
        return kLeastCommonWords;
    }

    /**
     * Returns the <i>k</i> most common word pairs in greatest to least order of
     * their count
     * 
     * @param k the size of the list
     * @return a list of the most common word pairs in decreasing order of count
     */
    public String[] mostCommonWordPairs(int k) {
        String[] kMostCommonWordPairs;
        // edge case (k too small)
        if (k <= 0)
            kMostCommonWordPairs = new String[0];
        // edge case (k too large)
        else if (k >= pairRankList.size()) {
            kMostCommonWordPairs = new String[pairRankList.size()];
            for (int i = 0; i < pairRankList.size(); i++) {
                kMostCommonWordPairs[i] = pairRankList.get(i).getKey();
            }
        }
        // general, 0 < k < number of pairs
        else {
            kMostCommonWordPairs = new String[k];
            for (int i = 0; i < k; i++)
                kMostCommonWordPairs[i] = pairRankList.get(i).getKey();
        }
        return kMostCommonWordPairs;
    }

    /**
     * Returns the <i>k</i> least common word pairs in least to greatest order of
     * their count
     * 
     * @param k the size of the list
     * @return a list of the most common word pairs in increasing order of count
     */
    public String[] leastCommonWordPairs(int k) {
        String[] kLeastCommonWordPairs;
        // edge case (k too small)
        if (k <= 0)
            kLeastCommonWordPairs = new String[0];
        // edge case (k too large)
        else if (k >= pairRankList.size()) {
            kLeastCommonWordPairs = new String[pairRankList.size()];
            for (int i = 0; i < pairRankList.size(); i++)
                kLeastCommonWordPairs[i] = pairRankList.get(pairRankList.size() - 1 - i).getKey();
        }
        // general case
        else {
            kLeastCommonWordPairs = new String[k];
            for (int i = 0; i < k; i++)
                kLeastCommonWordPairs[i] = pairRankList.get(pairRankList.size() - 1 - i).getKey();
        }
        return kLeastCommonWordPairs;
    }

    /**
     * Returns the <i>k</i> most common collocations which are <i>i</i> distance
     * from <i>baseWord</i>
     * Implementation only accounts for <i>i</i> being 1 or -1
     * 
     * @param k        number of words to return
     * @param baseWord the base word to calculate collocations from
     * @param i        either 1 or -1, specifies either preceding or following word from base word
     * @return an array of the most common single increment collocations in
     *         decreasing order of count
     */
    public String[] mostCommonCollocs(int k, String baseWord, int i) {
        // edge case (k too small)
        if (k <= 0)
            return new String[0];
        ArrayList<HashEntry> collocRank = new ArrayList<HashEntry>();
        HashTable tracker = new HashTable(); // keeps track of keys that have already been inserted into the array
        // word comes after baseWord
        if (i == 1) {
            String pair; // current collocation to check
            for (int j = 0; j < wordList.size() - 1; j++) {
                if (wordList.get(j).equals(baseWord)) {
                    pair = wordList.get(j) + " " + wordList.get(j + 1);
                    HashEntry currentWord = new HashEntry(wordList.get(j + 1), pairTable.get(pair));
                    if (tracker.get(pair) == -1) { // if current key has not been added to array, add, otherwise do nothing
                        tracker.put(pair, currentWord.getValue());
                        collocRank.add(currentWord);
                    }
                }
            }
            Collections.sort(collocRank);
            String[] mostCommonCollocs;
            // return entire array if k exceeds number of collocations
            if (k >= collocRank.size()) {
                mostCommonCollocs = new String[collocRank.size()];
                for (int j = 0; j < collocRank.size(); j++)
                    mostCommonCollocs[j] = collocRank.get(j).getKey();
            } else {
                mostCommonCollocs = new String[k];
                for (int j = 0; j < k; j++)
                    mostCommonCollocs[j] = collocRank.get(j).getKey();
            }
            return mostCommonCollocs;
        }
        // word comes before baseWord (same process as above)
        else if (i == -1) {
            String pair;
            for (int j = 1; j < wordList.size(); j++) {
                if (wordList.get(j).equals(baseWord)) {
                    pair = wordList.get(j - 1) + " " + wordList.get(j);
                    HashEntry currentWord = new HashEntry(wordList.get(j - 1), pairTable.get(pair));
                    if (tracker.get(pair) == -1) { // if current key has not been added to array, add, otherwise do nothing
                        tracker.put(pair, currentWord.getValue());
                        collocRank.add(currentWord);
                    }
                }
            }
            Collections.sort(collocRank);
            String[] mostCommonCollocs;
            // construct String array
            if (k >= collocRank.size()) {
                mostCommonCollocs = new String[collocRank.size()];
                for (int j = 0; j < collocRank.size(); j++)
                    mostCommonCollocs[j] = collocRank.get(j).getKey();
            } else {
                mostCommonCollocs = new String[k];
                for (int j = 0; j < k; j++)
                    mostCommonCollocs[j] = collocRank.get(j).getKey();
            }
            return mostCommonCollocs;
        }
        return new String[0];
    }

    /**
     * Returns a String array of the most common collocations excluding words specified in <i>exclusions</i>
     * 
     * @param k          number of words to return
     * @param baseWord   the base word to calculate collocations from
     * @param i          either 1 or -1, specifies either preceding or following word from base word
     * @param exclusions an array of words to exclude from the results
     * @return an array of the most common collocations excluding specified words
     */
    public String[] mostCommonCollocsExc(int k, String baseWord, int i, String[] exclusions) {
        // edge case
        if (k <= 0)
            return new String[0];
        ArrayList<HashEntry> collocRank = new ArrayList<HashEntry>();
        List<String> excList = Arrays.asList(exclusions);
        HashTable tracker = new HashTable(); // keeps track of keys that have already been inserted into the array
        // word comes after baseWord
        if (i == 1) {
            String pair;
            for (int j = 0; j < wordList.size() - 1; j++) {
                // additional condition to check if the collocation is excluded
                if (wordList.get(j).equals(baseWord) && !(excList.contains(wordList.get(j + 1)))) { 
                    pair = wordList.get(j) + " " + wordList.get(j + 1);
                    HashEntry currentWord = new HashEntry(wordList.get(j + 1), pairTable.get(pair));
                    if (tracker.get(pair) == -1) { // if current key has not been added to array, add, otherwise do nothing
                        tracker.put(pair, currentWord.getValue());
                        collocRank.add(currentWord);
                    }
                }
            }
            Collections.sort(collocRank);
            String[] mostCommonCollocs;
            // construct String array
            if (k >= collocRank.size()) {
                mostCommonCollocs = new String[collocRank.size()];
                for (int j = 0; j < collocRank.size(); j++)
                    mostCommonCollocs[j] = collocRank.get(j).getKey();
            } else {
                mostCommonCollocs = new String[k];
                for (int j = 0; j < k; j++)
                    mostCommonCollocs[j] = collocRank.get(j).getKey();
            }
            return mostCommonCollocs;
        }
        // word comes before baseWord
        else if (i == -1) {
            String pair;
            for (int j = 1; j < wordList.size(); j++) {
                // additional condition to check if the collocation is excluded
                if (wordList.get(j).equals(baseWord) && !(excList.contains(wordList.get(j - 1)))) {
                    pair = wordList.get(j - 1) + " " + wordList.get(j);
                    HashEntry currentWord = new HashEntry(wordList.get(j - 1), pairTable.get(pair));
                    if (tracker.get(pair) == -1) { // if current key has not been added to array, add, otherwise do nothing
                        tracker.put(pair, currentWord.getValue());
                        collocRank.add(currentWord);
                    }
                }
            }
            Collections.sort(collocRank);
            String[] mostCommonCollocs;
            // construct String array 
            if (k >= collocRank.size()) {
                mostCommonCollocs = new String[collocRank.size()];
                for (int j = 0; j < collocRank.size(); j++)
                    mostCommonCollocs[j] = collocRank.get(j).getKey();
            } else {
                mostCommonCollocs = new String[k];
                for (int j = 0; j < k; j++)
                    mostCommonCollocs[j] = collocRank.get(j).getKey();
            }
            return mostCommonCollocs;
        }
        return new String[0];
    }

    /**
     * Generates a String of words separated by spaces, each word is followed by its
     * most common following word
     * 
     * @param k         number of words in the String
     * @param startWord the starting word for the String
     * @return a String of words, each followed by its most common following word
     */
    public String generateWordString(int k, String startWord) {
        StringBuilder wordString = new StringBuilder();
        while (k > 0) {
            // new start word every iteration
            startWord = mostCommonCollocs(1, startWord, 1)[0];
            wordString.append(startWord);
            wordString.append(" ");
            k--;
        }
        return wordString.toString().trim();
    }

    public static void main(String[] args) {
        String[] theRaven = {"Once", "upon", "a", "midnight", "dreary", "while", "I", "pondered,", "weak", "and",
                "weary,", "Over", "many", "a", "quaint", "and", "curious", "volume", "of", "forgotten", "lore—",
                "While", "I", "nodded,", "nearly", "napping,", "suddenly", "there", "came", "a", "tapping,", "As", "of",
                "some", "one", "gently", "rapping,", "rapping", "at", "my", "chamber", "door.", "\"Tis", "some",
                "visitor,\"", "I", "muttered,", "\"tapping", "at", "my", "chamber", "door—", "Only", "this", "and",
                "nothing", "more.\"", "Ah,", "distinctly", "I", "remember", "it", "was", "in", "the", "bleak",
                "December;", "And", "each", "separate", "dying", "ember", "wrought", "its", "ghost", "upon", "the",
                "floor.", "Eagerly", "I", "wished", "the", "morrow;—", "vainly", "I", "had", "sought", "to", "borrow",
                "From", "my", "books", "surcease", "of", "sorrow—", "sorrow", "for", "the", "lost", "Lenore—", "For",
                "the", "rare", "and", "radiant", "maiden", "whom", "the", "angels", "name", "Lenore—", "Nameless",
                "here,", "for", "evermore."};
        WordStat raven = new WordStat(theRaven);
        System.out.println("Demonstration of WordStat using the first two stanzas from \'The Raven\' by Edgar Allan Poe");
        System.out.println("Number of times \"chamber\" appears:\n" + raven.wordCount("chamber"));
        System.out.println("Number of times \"at my\" appears:\n" + raven.wordPairCount("at", "my"));
        System.out.println("Rank of \"while\":\n" + raven.wordRank("while"));
        System.out.println("Rank of \"chamber door\":\n" + raven.wordPairRank("chamber", "door"));
        System.out.println("The 10 most common words are:\n" + Arrays.toString(raven.mostCommonWords(10)));
        System.out.println("\nThe 10 least common words are:\n" + Arrays.toString(raven.leastCommonWords(10)));
        System.out.println("\nThe 5 most common word pairs are:\n" + Arrays.toString(raven.mostCommonWordPairs(5)));
        System.out.println("\nThe 5 least common word pairs are:\n" + Arrays.toString(raven.leastCommonWordPairs(5)));
        System.out.println("\nThe 3 most common following collocations for \"a\" are:\n" + Arrays.toString(raven.mostCommonCollocs(3, "a", 1)));

    }
}
