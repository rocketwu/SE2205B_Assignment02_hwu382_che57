package birds;

public class DataKey {
    
    private final String birdName;
    private final int birdSize;

    public DataKey(String name, int size)
    {
        birdName=name;
        birdSize=size;
    }
    
    public String getBirdName(){
        return birdName;
    }
    
    public int getBirdSize(){
        return birdSize;
    }
    

    // other constructors

    /**
     * Returns 0 if this DataKey is equal to k, returns -1 if this DataKey is smaller
     * than k, and it returns 1 otherwise. 
     */
    public int compareTo(DataKey k) {
       if (this.birdSize==k.birdSize)
       {
           int temp=this.birdName.compareTo(k.birdName);
           return temp/Math.abs(temp);
       }
       else{
            return ((Integer)this.birdSize).compareTo(k.birdSize);
        }
    }
}
