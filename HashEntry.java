/**
 * Represents an entry in a hash table
 * @author <i>Charlie Lin</i>
 */
public class HashEntry implements Comparable<HashEntry> {
    /** Key to access hash entry */
    private String key;

    /** Value stored in hash entry */
    private int value;

    /** Pointer with access to next hash entry for collisions */
    private HashEntry nextEntry;

    /**
     * Initializes new HashEntry
     * @param key key associated with this HashEntry
     * @param value value stored in this HashEntry
     */
    public HashEntry(String key, int value) {
        this.key = key;
        this.value = value;
    }

    /**
     * Returns the key of this HashEntry
     * @return the key of this HashEntry
     */
    public String getKey() {
        return key;
    }

    /**
     * Returns the value stored in this HashEntry
     * @return the value stored in this HashEntry
     */
    public int getValue() {
        return value;
    }

    /**
     * Updates the value stored in this HashEntry
     * @param value the new value to be stored
     */
    public void setValue(int value) {
        this.value = value;
    }

    /**
     * Returns the next entry in the chain
     * @return the next entry in the chain
     */
    public HashEntry nextEntry() {
        return nextEntry;
    }

    public void setNext(HashEntry nextEntry) {
        this.nextEntry = nextEntry;
    }

    @Override
    public int compareTo(HashEntry compareEntry) {
        if (value < compareEntry.value) 
            return 1;
        else if (value > compareEntry.value)
            return -1;
        return key.compareTo(compareEntry.key);
    }
}
