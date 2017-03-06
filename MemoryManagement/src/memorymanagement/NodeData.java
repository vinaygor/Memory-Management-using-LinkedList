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
public class NodeData {
    
    private int size;
    private boolean available;
    private String data;
    private int dataId;
    private String allocationInfo;

    public String getAllocationInfo() {
        return allocationInfo;
    }

    public void setAllocationInfo(String allocationInfo) {
        this.allocationInfo = allocationInfo;
    }
    

    public int getDataId() {
        return dataId;
    }

    public void setDataId(int dataId) {
        this.dataId = dataId;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
    
    
    
}
