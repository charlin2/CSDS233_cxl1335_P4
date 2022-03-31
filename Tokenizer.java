import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Reads, extracts, and normalizes words from txt file and stores
 * Strings in an Array
 * @author <i>Charlie Lin</i>
 */
public class Tokenizer {
    /** String ArrayList containing the normalized words from a txt file */
    private ArrayList<String> wordList;
    
    /**
     * Creates a <i>Tokenizer</i> from a specified .txt file
     * @param fileName the txt file to read from
     * @throws FileNotFoundException invalid file input
     */
    public Tokenizer(String fileName) throws FileNotFoundException {
        wordList = new ArrayList<String>();
        // read from file
        Scanner read = new Scanner(new File(fileName));
        try {
            String currentWord;
            // iterate through individual words
            while (read.hasNext()) {
                currentWord = read.next();
                wordList.add(normalize(currentWord));
            }
        } finally {
            read.close();
        }
    }
    /**
     * Creates a <i>Tokenizer</i> from a String array
     * @param wordList
     */
    public Tokenizer(String[] wordList) {
        // convert string array into arraylist
        this.wordList = new ArrayList<String>(wordList.length);
        for (int i = 0; i < wordList.length; i++) {
            this.wordList.add(normalize(wordList[i]));
        }
    }

    /**
     * Returns the list of words from the specified file or array
     * @return this tokenizer's word list
     */
    public ArrayList<String> wordList() {
        return wordList;
    }

    /**
     * Normalizes a word by making it lower case and removing punctuation and spaces
     * @param word the word to be normalized
     * @return the normalized word
     */
    private String normalize(String word) {
        word = word.toLowerCase();
        word = word.strip();         // removes trailing and leading whitespace
        StringBuilder normalizedWord = new StringBuilder();
        // iterate through word to find and remove non-letters
        for (int i = 0; i < word.length(); i ++) {
            // only append letter or digit characters
            if (Character.isLetterOrDigit(word.charAt(i))) {
                normalizedWord.append(word.charAt(i));
            }
        }
        return normalizedWord.toString();
    }
}
