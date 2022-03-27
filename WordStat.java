import java.io.File;
import java.io.FileNotFoundException;

/**
 * Computes word statistics
 * @author <i>Charlie Lin</i>
 */
public class WordStat {
    /**
     * Computes word statistics from a txt file
     * @param fileName
     */
    public WordStat(String fileName) {

    }

    /**
     * Computes word statistics from String array
     * @param wordList
     */
    public WordStat(String[] wordList) {

    }

    /**
     * Returns the count of the specified word
     * @param word the word to get the count of
     * @return the number of occurrences of the specified word, 0 if the word does not appear
     */
    public int wordCount(String word) {
        // TODO
        return 0;
    }

    /**
     * Returns the count of a specified word pair (two words in succession)
     * @param w1 the first word in the pair
     * @param w2 the seccond word in the pair
     * @return the count of the specified word pair, 0 if word pair does not appear
     */
    public int wordPairCount(String w1, String w2) {
        // TODO
        return 0;
    }

    /**
     * Returns the rank of a word, rank 1 being the most common word
     * @param word the word to return the rank of
     * @return the rank of the specified word, 0 if the word does not appear
     */
    public int wordRank(String word) {
        // TODO
        return 0;
    }

    /**
     * Returns the rank of a word pair, rank 1 being the most common word pair
     * @param w1 the first word in the pair
     * @param w2 the second word in the pair
     * @return the rank of the specified word pair, 0 if the word pair does not appear
     */
    public int wordPairRank(String w1, String w2) {
        // TODO
        return 0;
    }

    /**
     * Returns a list of the <i>k</i> most common words in greatest to least order of their count
     * @param k the size of the list
     * @return a list of the most common words in decreasing order of count
     */
    public String[] mostCommonWords(int k) {
        // TODO
        return null;
    }

    /**
     * Returns a list of the <i>k</i> least common words in least to greatest order of their count
     * @param k the size of the list
     * @return a list of the least common words in increasing order of count
     */
    public String[] leastCommonWords(int k) {
        // TODO
        return null;
    }

    /**
     * Returns the <i>k</i> most common word pairs in greatest to least order of their count
     * @param k the size of the list
     * @return a list of the most common word pairs in decreasing order of count
     */
    public String[] mostCommonWordPairs(int k) {
        // TODO
        return null;
    }

    /**
     * Returns the <i>k</i> most common collocations which are <i>i</i> distance from <i>baseWord</i>
     * Implementation only accounts for <i>i</i> being 1 or -1
     * @param k the size of the list
     * @param baseWord the base word to calculate collocations from
     * @param i 
     * @return
     */
    public String[] mostCommonCollocs(int k, String baseWord, int i) {
        // TODO
        return null;
    }
}
