package src;

import java.util.Hashtable;

public class Toolkit {

    Hashtable<Integer, Integer> mapTagsToPrims;

    public Toolkit(){
        mapTagsToPrims = new Hashtable<Integer,Integer>();
        mapTagsToPrims.put(39, 1);
        mapTagsToPrims.put(40, 3);
        mapTagsToPrims.put(41, 2);
        mapTagsToPrims.put(42, 4);
    }

    public int tagToPrimative(int tagVal){
        return mapTagsToPrims.get(tagVal);
    }
}
