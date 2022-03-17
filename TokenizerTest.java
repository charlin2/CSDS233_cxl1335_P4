import java.io.FileNotFoundException;
import java.util.ArrayList;
import org.junit.Assert;
import org.junit.Test;

/** Testing class for Tokenizer */
public class TokenizerTest {
    // Testing file input constructor
    @Test
    public void testTokenizerText() {
        // Invalid input
        try {
            Tokenizer testNull = new Tokenizer("null.txt");
        } catch (FileNotFoundException e) {
            Assert.assertTrue(true);
        }

        // small sample text file
        try {
            Tokenizer testMacbeth = new Tokenizer("C:\\Users\\clin1\\Desktop\\CSDS_233_Data_Structures\\P4\\Macbeth.txt");
            ArrayList<String> words = testMacbeth.wordList();
            Assert.assertEquals("william", words.get(7));
            Assert.assertEquals("ebooks", words.get(words.size()-1));
        } catch (FileNotFoundException e) {
            Assert.assertTrue(false);
        }
    }

    // Testing array input constructor
    @Test
    public void testTokenizerArray() {
        String[] wordArray = {"ThiS,  ", " 1s", "a", "Str1nG", "Arr4y!!!"};
        Tokenizer testArray = new Tokenizer(wordArray);
        ArrayList<String> words = testArray.wordList();
        Assert.assertEquals("this", words.get(0));
        Assert.assertEquals("str1ng", words.get(3));
        Assert.assertEquals("arr4y", words.get(4));
    }
}
