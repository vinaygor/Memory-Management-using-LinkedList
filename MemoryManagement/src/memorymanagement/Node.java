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
public class Node {
    
    public NodeData nodeData;
    public Node next;
    
    public Node (int size)
    {
        nodeData = new NodeData();
         nodeData.setAvailable(true);
         nodeData.setSize(size);
         nodeData.setAllocationInfo("");
         this.next = null;
    }

    public NodeData getNodeData() {
        return nodeData;
    }

    
    
    
    
}
