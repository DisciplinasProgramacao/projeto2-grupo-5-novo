package main;

import java.util.Map;
import java.util.TreeMap;

public class ABB<T> {

    private TreeMap<Integer, T> data;

    public ABB(){
        this.data = new TreeMap<>();
    }

    public T find(int key){
        return this.data.get(key);
    }

    public boolean add(int key, T newElement){
        boolean result = false;
        if(!this.data.containsKey(key)){
            this.data.put(key, newElement);
            result = true;
        }
        return result;
    }

    public boolean del(int key) {
        boolean result = false;

        if(this.data.containsKey(key)) {
            this.data.remove(key);
            result = true;
        }

        return result;
    }
    
    public int size(){
        return this.data.size();
    }

    public T[] allElements(T[] array){
        T[] allData = this.data.values().toArray(array);
        return allData;
    }

    public Integer[] allKeys() {
        Integer[] keys = new Integer[this.data.size()];
        int i = 0;

        for(Map.Entry<Integer, T> entryData : this.data.entrySet()) {
            keys[i] = entryData.getKey();
            i++;
        }

        return keys;
    }
}
