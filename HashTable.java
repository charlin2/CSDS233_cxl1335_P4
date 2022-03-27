/**
 * HashTable implementation utilizing HashEntry
 * @author <i>Charlie Lin</i>
 */
public class HashTable {
    /** array used to implement hash table */
    private HashEntry[] items;

    /** the current number of words in the hash table */
    private int numWords = 0;
    
    /** the capacity of the array */
    private int maxWords;

    /**
     * Constructs a new Hash Table
     */
    public HashTable() {
        items = new HashEntry[100];
        maxWords = 100;
    }

    /**
     * Constructs a new Hash Table of specified size
     * @param size the size of the Hash Table
     */
    public HashTable(int size) {
        items = new HashEntry[size];
        maxWords = size;
    }

    /**
     * Hashes the given String input
     * @param key the String to be hashed
     * @return the hashed key
     */
    private int hash(String key) {
        return Math.abs(key.hashCode()) % maxWords;
    }

    /**
     * Inserts a new key-value pair in the hash table
     * @param key the key associated with the value
     * @param value the value to be inserted into the hash table
     */
    public void put(String key, int value) {
        HashEntry newEntry = new HashEntry(key, value);
        int hashIndex = hash(key);
        // check for collision
        if (items[hashIndex] == null)
            items[hashIndex] = newEntry;
        else {
            HashEntry trav = items[hashIndex];
            // traverse down chain
            while (trav.nextEntry() != null)
                trav = trav.nextEntry();
            trav.setNext(newEntry);
        }
        numWords++;
        // calculate load factor, rehash if necessary
        if ((double)numWords/maxWords > 0.9) {
            HashEntry[] oldTable = items;
            // double size of new table
            maxWords *= 2;
            numWords = 0;
            items = new HashEntry[maxWords];
            for (int i = 0; i < oldTable.length; i++) {
                HashEntry trav = oldTable[i];
                // copy values into new table
                while (trav != null) {
                    put(trav.getKey(), trav.getValue());
                    trav = trav.nextEntry();
                }
            }
        }
    }

    /**
     * Inserts a new key-value pair in the hash table with a specified hash value (bypass hash function)
     * @param key the key associated with the value
     * @param value the value to be inserted into the hash table
     * @param hashCode the specfied hash code
     */
    public void put(String key, int value, int hashCode) {
        HashEntry newEntry = new HashEntry(key, value);
        int hashValue = hashCode % maxWords;
        // no collision
        if (items[hashValue] == null)
            items[hashValue] = newEntry;
        // handle collision
        else {
            HashEntry trav = items[hashValue];
            // traverse down chain
            while (trav.nextEntry() != null)
                trav = trav.nextEntry();
            trav.setNext(newEntry);
        }
        numWords++;
        // calculate load factor, rehash if necessary
        if ((double)numWords/maxWords > 0.9) {
            HashEntry[] oldTable = items;
            maxWords *= 2;
            numWords = 0;
            items = new HashEntry[maxWords];
            for (int i = 0; i < oldTable.length; i++) {
                HashEntry trav = oldTable[i];
                // copy values into new hash table
                while (trav != null) {
                    put(trav.getKey(), trav.getValue());
                    trav = trav.nextEntry();
                }
            }
        }
    }

    /**
     * Updates value of the specified key, inserts the key if it doesn't exist
     * @param key the key of the value to be updated
     * @param value the new value
     */
    public void update(String key, int value) {
        int hashValue = hash(key);
        HashEntry trav = items[hashValue];
        // try to find key
        while (trav != null && !trav.getKey().equals(key)) {
            trav = trav.nextEntry();
        }
        // if key does not exist, put
        if (trav == null)
            put(key, value);
        // update existing key
        else
            trav.setValue(value);
    }

    /**
     * Returns the value associated with the specified key
     * @param key the key of the value to be returned
     * @return value associated with the key, -1 if key does not exist
     */
    public int get(String key) {
        int hashValue = hash(key);
        HashEntry trav = items[hashValue];
        // iterate through specified chain
        while (trav != null) {
            if (trav.getKey().equals(key))
                return trav.getValue();
            trav = trav.nextEntry();
        }
        return -1;
    }

    /**
     * Returns the value associated with the specified key and hash code
     * @param key the key of the value to be returned
     * @param hashCode the specified hash code
     * @return value associated with the key/hash code, -1 if key does not exist
     */
    public int get(String key, int hashCode) {
        int hashValue = hashCode % maxWords;
        HashEntry trav = items[hashValue];
        while (trav != null) {
            if (trav.getKey().equals(key))
                return trav.getValue();
            trav = trav.nextEntry();
        }
        return -1;
    }
}
