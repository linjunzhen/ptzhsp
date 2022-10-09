/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.core.util;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述 
 * @author Sundy Sun
 * @version v2.0
 *
 */
public class Item implements Comparable{
    /**value**/
    private String value;  
    /**key**/
    private String key;
    /**前继节点Item**/
    private Item preItem; 
    /**后续节点Item**/
    private List<Item> nextItem = new ArrayList<Item>();
    /**关联节点**/
    private Item sibling;   
    /**counter**/
    private int counter;
    
    public Item() {  
    }    
    public Item(String value) {  
        this.value = value;  
    }
  
    public void addCounter() {  
        this.counter += 1;  
    }  
  
    public Item getSibling() {  
        return sibling;  
    }  
  
    public void setSibling(Item sibling) {  
        this.sibling = sibling;  
    }  
  
    public void addNextItem(Item item) {  
        this.nextItem.add(item);  
    }  
  
    public List<Item> getNextItem() {  
        return this.nextItem;  
    }  
  
    public String getValue() {  
        return value;  
    }  
  
    public void setValue(String value) {  
        this.value = value;  
    }  
  
    public Item getPreItem() {  
        return preItem;  
    }  
  
    public void setPreItem(Item preItem) {  
        this.preItem = preItem;  
    }  
  
    public int getCounter() {  
        return counter;  
    }  
  
    public void setCounter(int counter) {  
        this.counter = counter;  
    }  
    @Override
    public int compareTo(Object o) {  
        int value;  
        Item i = (Item) o;  
  
        if (this.counter > i.counter) {  
            value = -1;  
        } else if (this.counter == i.counter) {  
            value = 0;  
        } else {  
            value = 1;  
        }  
        return value;  
    }

    /**
     * @param nextItem the nextItem to set
     */
    public void setNextItem(List<Item> nextItem) {
        this.nextItem = nextItem;
    }
    /**
     * @return the key
     */
    public String getKey() {
        return key;
    }
    /**
     * @param key the key to set
     */
    public void setKey(String key) {
        this.key = key;
    }  
    
    
    
}
