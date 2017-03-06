/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package memorymanagement;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

/**
 *
 * @author ayush
 */
public class MemoryManagement {

    /**
     * @param args the command line arguments
     */
    
    public static int uniqueId = 1;
    public static void main(String[] args) {
        // TODO code application logic here
        InitializeMemoryPoolBlock initializeMemoryPoolBlock = new InitializeMemoryPoolBlock();
        initializeMemoryPoolBlock.initialize();
        Scanner k = new Scanner(System.in);
        int failedRequest=0;
        
        while(true)
        {
        //Code to see all the available memory blocks
            System.out.println("Menu:\n1. Display the memory blocks with availabilty\n2. Request for a memory block\n3. Release a memory block\n4. Exit");
            int choice = k.nextInt();
            
            Map<Integer,CircularLinkedList> map = initializeMemoryPoolBlock.getMemoryPoolMap();
            
            switch(choice)
            {
                
                case 1:           
                    //Display the memory blocks with availabilty
                    
                    System.out.println("Please enter the memory block size you want to see the details for:");

                    int input = k.nextInt();
                    for(Entry<Integer,CircularLinkedList> entry : map.entrySet())
                    {
                       // System.out.println("Entry key:"+entry.getKey());
                        if(entry.getKey()==input)
                        {
                            CircularLinkedList CLL = entry.getValue();
                            System.out.println("Size of LL : "+CLL.sizeOfLinkedList());
                            CLL.display();
                        }
                    }
                    break;

                case 2:
                    //Request for a memory block
                    System.out.println("Please enter required memory size:");
                    int sizeRequired = k.nextInt();
                    System.out.println("Please enter the your data to store in the memory block:");
                    String dataToStore = k.next();
                    
                    boolean foundValue= false;
                    
                    for(Entry<Integer,CircularLinkedList> entry : map.entrySet())
                    {
                        if(entry.getKey()==sizeRequired)
                        {
                            CircularLinkedList CLL = entry.getValue();
                            Node temp=CLL.getHead();
                            //System.out.println("Head - > "+CLL.getHead().nodeData.getDataId());
                            do
                            {
                                if(temp.getNodeData().isAvailable())
                                {
                                    temp.nodeData.setData(dataToStore);
                                    temp.nodeData.setDataId(uniqueId++);
                                    temp.nodeData.setAvailable(false);
                                    //This is causing problem
                                    //CLL.setHead(temp);
                                    System.out.println("----------------------------------------------------------------------");
                                    System.out.println("Memory block of size : "+temp.nodeData.getSize()+" has been successfully allocated to your job."
                                            + "\nThe job ID generated for this request is :"+temp.nodeData.getDataId());
                                    System.out.println("----------------------------------------------------------------------");
                                   // System.out.println("Head - > "+CLL.getHead().nodeData.getDataId());
                                    foundValue=true;
                                    break;
                                }
                                else
                                {
                                    temp= temp.next;
                                }
                            } while(temp!=CLL.getHead());
                            
                            //if the above loop ends, it means that there is no more memory available 
                            //in that slot which can be allocated
//                            if(!foundValue)
//                            System.out.println("All the memory blocks have been utilized for that size! Try again after some time..");
                        }
                    }
                    if(!foundValue)
                        failedRequest++;
//-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------                    
                    //Spliting of memory blocks
                    boolean foundMemoryInSplit=false;
                     boolean foundMemoryInMerge=false;
                    if(!foundValue && sizeRequired!=2)
                        {
                           foundMemoryInMerge = mergeFunction(sizeRequired,map,dataToStore);
                        }
                    
                    if(!foundValue && !foundMemoryInMerge && sizeRequired!=1024)
                    {
                        foundMemoryInSplit = splitUpFunction(sizeRequired,map,dataToStore);
                    }
                    if(!foundValue && !foundMemoryInMerge && !foundMemoryInSplit)
                    {
                        System.out.println("Memory Full..!!");
                    }
                    System.out.println("Total Failed Request:"+failedRequest);
//-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------                        
                    break;
                
                case 3:    
                    // This code section is for memory deallocation
                    
                    System.out.println("Please enter the Job Id to be deallocated:");
                    int deallocMemory=k.nextInt();
                    boolean deallocated=false;
                    for(Entry<Integer,CircularLinkedList> entry : map.entrySet())
                    {
                       // System.out.println("Entry key:"+entry.getKey());
                            CircularLinkedList CLL = entry.getValue();
                            Node tempNode=CLL.getHead();
                            do
                            {
                            if(tempNode.nodeData.getDataId()==deallocMemory)
                            {
                                if(tempNode.nodeData.getAllocationInfo().equals(""))
                                {
                                    tempNode.nodeData.setAvailable(true);
                                    tempNode.nodeData.setData(null);
                                    tempNode.nodeData.setDataId(-1);
                                    System.out.println("----------------------------------------------------------------------");
                                    System.out.println("Memory has been deallocated successfully");
                                    System.out.println("----------------------------------------------------------------------");
                                    deallocated=true;
                                    rearrangeMemoryBlocks(map);
                                    break;
                                }
                                else
                                {
                                    
                                     String array[] = tempNode.nodeData.getAllocationInfo().split(",");
                                        for(String s:array)
                                        {
                                            String elements[] =s.split("/");
                                            int key =Integer.parseInt(elements[0]);
                                            int numberOfNodes =Integer.parseInt(elements[1]);
                                            CircularLinkedList insertIntoLinkedList = map.get(key);
                                            for(int i=0;i<numberOfNodes;i++)
                                            {
                                                insertIntoLinkedList.insert(key);
                                            }
                                        }
                                        
                                        CircularLinkedList deleteFromCircularLinkedList = map.get(tempNode.nodeData.getSize());
                                        deleteFromCircularLinkedList.delete();
                                        deallocated=true;
                                        rearrangeMemoryBlocks(map);
                                    System.out.println("----------------------------------------------------------------------");
                                    System.out.println("Memory has been deallocated successfully");
                                    System.out.println("----------------------------------------------------------------------");
                                    break;    

                                }
                            }    
                            else
                            {
                                tempNode=tempNode.next;
                            }
                            }while(tempNode!=CLL.getHead());
                            //CLL.display();
                    }
                    if(!deallocated)
                    {        
                        System.out.println("----------------------------------------------------------------------");
                        System.out.println("Sorry.. No such job is running");
                        System.out.println("----------------------------------------------------------------------");    
                    }
                    break;
                case 4:
                    System.exit(0);
                    break;
                    
                default:
                    System.out.println("Please enter a valid choice");
            //

            }
        }
    }
    
    public static boolean splitUpFunction(int sizeRequired,Map<Integer,CircularLinkedList> map,String dataToStore)
    {
            
                            boolean flag=false;
                            
                            int number = sizeRequired;
                            
                            while(number!=1024)
                            {
                                number=number*2;
                            for(Entry<Integer,CircularLinkedList> entry : map.entrySet())
                            {
                                if(entry.getKey()==number)
                                {
                                    CircularLinkedList CLL = entry.getValue();
                                    Node temp=CLL.getHead();
                                    
                                    do
                                    {
                                        if(temp.nodeData.isAvailable())
                                        {
                                            int keyNumber = entry.getKey();
                                            CircularLinkedList deleteLinkedList = map.get(keyNumber);
                                                deleteLinkedList.delete();
                                            while(keyNumber!=sizeRequired)
                                            {
                                                keyNumber=keyNumber/2;
                                                CircularLinkedList linkedList = map.get(keyNumber);
                                                linkedList.insert(keyNumber);
                                            }
                                            
                                            if(keyNumber==sizeRequired)
                                            {
                                                CircularLinkedList linkedList = map.get(keyNumber);
                                                linkedList.insert(keyNumber);
                                                
                                                Node tempNode=linkedList.getHead();
                                                //System.out.println("Head - > "+CLL.getHead().nodeData.getDataId());
                                                do
                                                {
                                                    if(tempNode.getNodeData().isAvailable())
                                                    {
                                                        tempNode.nodeData.setData(dataToStore);
                                                        tempNode.nodeData.setDataId(uniqueId++);
                                                        tempNode.nodeData.setAvailable(false);
                                                        //This is causing problem
                                                        //CLL.setHead(temp);
                                                        System.out.println("----------------------------------------------------------------------");
                                                        System.out.println("Memory block of size : "+tempNode.nodeData.getSize()+" has been successfully allocated to your job."
                                                                + "\nThe job ID generated for this request is :"+tempNode.nodeData.getDataId());
                                                        System.out.println("----------------------------------------------------------------------");
                                                       // System.out.println("Head - > "+CLL.getHead().nodeData.getDataId());
                                                        flag=true;
                                                        return true;
                                                    }
                                                    else
                                                    {
                                                        tempNode= tempNode.next;
                                                    }
                                                } while(tempNode!=linkedList.getHead());

                                            }
                                            
//                                            System.out.println("Map Key: "+entry.getKey());
//                                            System.out.println("Searching for memory in different memory pool..and Got it!!");
                                           
                                            break;
                                        }
                                        else
                                            temp=temp.next;
                                    }
                                    while(temp!=CLL.getHead());
                                }
                                if(flag)
                                {
                                    System.out.println("System running out of space...No memory Blocks available!!!");
                                    return false;
                                }
                                }
                            }
                    
        
        return false;
    }

    public static boolean mergeFunction(int sizeRequired, Map<Integer, CircularLinkedList> map, String dataToStore) 
    {
        String allocationInfo="";
        int number = sizeRequired;
        boolean flagToExit= false;
        int sum =0;
        do
        {
            int count=0;
            number = number/2;
            CircularLinkedList linkedList = map.get(number);
            Node temp = linkedList.getHead();
            do
            {
                if(temp.nodeData.isAvailable())
                {
                    count++;
                    sum = sum + number;
                }
                if(sum==sizeRequired)
                {
                    flagToExit = true;
                    break;
                }
                temp = temp.next;
            }
            while(temp!=linkedList.getHead());
            if(count!=0)
            {
                allocationInfo =allocationInfo+number+"/"+count+",";
            }
        }
        while(!flagToExit&&number!=2);
        if(flagToExit)
        {
            String array[] = allocationInfo.split(",");
            for(String s:array)
            {
                String elements[] =s.split("/");
                int key =Integer.parseInt(elements[0]);
                int numberOfNodes =Integer.parseInt(elements[1]);
                CircularLinkedList deleLinkedList = map.get(key);
                for(int i=0;i<numberOfNodes;i++)
                {
                    deleLinkedList.delete();
                }
            }
            CircularLinkedList addCircularLinkedList = map.get(sizeRequired);
            addCircularLinkedList.insert(sizeRequired);
             Node temp=addCircularLinkedList.getHead();
                            //System.out.println("Head - > "+CLL.getHead().nodeData.getDataId());
                            do
                            {
                                if(temp.getNodeData().isAvailable())
                                {
                                    temp.nodeData.setData(dataToStore);
                                    temp.nodeData.setDataId(uniqueId++);
                                    temp.nodeData.setAvailable(false);
                                    temp.nodeData.setAllocationInfo(allocationInfo);
                                    //This is causing problem
                                    //CLL.setHead(temp);
                                    System.out.println("----------------------------------------------------------------------");
                                    System.out.println("Memory block of size : "+temp.nodeData.getSize()+" has been successfully allocated to your job."
                                            + "\nThe job ID generated for this request is :"+temp.nodeData.getDataId());
                                    System.out.println("----------------------------------------------------------------------");
                                   // System.out.println("Head - > "+CLL.getHead().nodeData.getDataId());
                                    
                                    return true;
                                }
                                else
                                {
                                    temp= temp.next;
                                }
                            } while(temp!=addCircularLinkedList.getHead());
                            
        }
  
        return false;
    }

    public static void rearrangeMemoryBlocks(Map<Integer,CircularLinkedList> map) {

        for(Entry<Integer,CircularLinkedList> entry : map.entrySet())
        {
            CircularLinkedList circularLinkedList = entry.getValue();
            int keySize = entry.getKey();
            int sizeOfLinkedList = circularLinkedList.sizeOfLinkedList();
            int countOfAvailable=0;
            
            if(circularLinkedList.sizeOfLinkedList()>3)
            {
                Node tempNode=circularLinkedList.getHead();
                do
                {
                    if(tempNode.nodeData.isAvailable())
                    {
                        countOfAvailable++;
                        if(countOfAvailable==2)
                        {
                            circularLinkedList.delete();
                            circularLinkedList.delete();
                            countOfAvailable=0;
                            CircularLinkedList newCircularLinkedList=map.get(keySize*2);
                            newCircularLinkedList.insert(keySize*2);
                        }
                    }
                    tempNode=tempNode.next;
                }while(tempNode!=circularLinkedList.getHead());
            }
        }
    
    }
    
}
