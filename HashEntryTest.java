import org.junit.Assert;
import org.junit.Test;

public class HashEntryTest {
    @Test
    public void testHashEntry() {
        HashEntry entry1 = new HashEntry("entry1", 3);
        HashEntry entry2 = new HashEntry("entry2", 6);
        HashEntry entry3 = new HashEntry("entry3", 3);

        // testing get and set
        Assert.assertEquals(3, entry1.getValue());
        Assert.assertEquals("entry1", entry1.getKey());

        entry1.setNext(entry2);
        Assert.assertEquals("entry2", entry1.nextEntry().getKey());

        // testing compareTo
        Assert.assertEquals(true, entry1.compareTo(entry2) > 0);
        Assert.assertEquals(true, entry1.compareTo(entry3) < 0);  // alphabetical comparison if values are the same
    }
}
