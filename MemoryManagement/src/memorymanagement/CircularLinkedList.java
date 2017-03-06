/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package memorymanagement;

/**
 *
 * @author ayush
 */
public class CircularLinkedList {
    
    private Node head;
    
    public CircularLinkedList()
    {
        head= null;
    }

    public Node getHead() {
        return head;
    }
    
    public void insert(int size)
    {
        Node newNode = new Node(size);
        Node temp = head;
        
        if(head == null)
        { 
            newNode.next = newNode;
            head = newNode;
        }
        else{
            do
            {
                temp =temp.next;
            }
            while(temp.next!=head);
            newNode.next = temp.next;
            temp.next = newNode;
        }
    }
    
    public void display()
    {
        Node temp = head;
        do
            {
                System.out.println("Size :"+temp.nodeData.getSize()+ "|| Data :"+temp.nodeData.getData()+ " || Available :"+temp.nodeData.isAvailable());
                temp =temp.next;
            }
            while(temp!=head);
        
    }

//    public void setHead(Node head) {
//        this.head = head;
//    }
//    
    
    
    
     public int sizeOfLinkedList()
     {
         Node temp=getHead();
         int count=0;
         do
            {
                count++;
                temp =temp.next;
            }
            while(temp!=head);
         
         return count;
     }
     
     public void delete()
     {
         Node follow = null;
         Node temp = getHead();
         do
         {
             if(temp.getNodeData().isAvailable())
             {
                 break;
             }
             follow = temp;
             temp = temp.next;
         }
         while(temp!=head);
         
         if(temp==head)
         {
             Node last = head;
             do
             {
                 last = last.next;
             }while(last.next!=head);
             last.next = temp.next;
             head = temp.next;
         }
         
         else
         {
             follow.next = temp.next;
            
         }
         
     }
}
