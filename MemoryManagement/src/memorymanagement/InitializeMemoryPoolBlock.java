/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package memorymanagement;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author ayush
 */
public class InitializeMemoryPoolBlock {
    
    public Map<Integer,CircularLinkedList> memoryPoolMap;
    
    public InitializeMemoryPoolBlock()
    {
        memoryPoolMap = new TreeMap<Integer, CircularLinkedList>();
    }
            
    public void initialize()
    {
        Long startTime= System.currentTimeMillis();
        for(int j =1;j<=10;j++)
        {
           
            CircularLinkedList circularLinkedList = new CircularLinkedList();
        for(int i =0;i<1000;i++)
        {
          circularLinkedList.insert((int) Math.pow(2, j));
          
        }
       memoryPoolMap.put((int) Math.pow(2, j), circularLinkedList);
       Long endTime= System.currentTimeMillis();
       Long elapsedTime=(endTime-startTime);
            System.err.println("Time to initialize: "+(elapsedTime/1000)+" seconds");
        }
    }

    public Map<Integer, CircularLinkedList> getMemoryPoolMap() {
        return memoryPoolMap;
    }
    
    
    
    
}
