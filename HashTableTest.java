import org.junit.Assert;
import org.junit.Test;

public class HashTableTest {
    HashTable table = new HashTable();

    @Test
    public void testTable() {
        // test 0 - getting key that does not exist
        Assert.assertEquals(-1, table.get("Null"));

        // test 1 - single put
        table.put("Aardvark", 1);
        Assert.assertEquals(1, table.get("Aardvark"));

        // test many - multiple puts and collisions
        table.put("Baby", 3, 57);
        Assert.assertEquals(3, table.get("Baby", 57));

        table.put("Babylon", 6, 57);
        Assert.assertEquals(6, table.get("Babylon", 57));

        table.put("Bear", 1, 57);
        Assert.assertEquals(1, table.get("Bear", 57));

        /* put many values to test resizing */
        for (int i = 1; i < 200; i++) {
            table.put("item" + i, i, i);
        }

        Assert.assertEquals(27, table.get("item27"));
        Assert.assertEquals(127, table.get("item127"));
        Assert.assertEquals(-1, table.get("item201"));
        Assert.assertEquals(57, table.get("item57"));
        Assert.assertEquals(157, table.get("item157"));
        
        // test 0 - update key that doesn't exist
        table.update("Chinchilla", 3);
        Assert.assertEquals(3, table.get("Chinchilla"));

        // general update test
        table.update("Aardvark", 2);
        Assert.assertEquals(2, table.get("Aardvark"));

    }
}
