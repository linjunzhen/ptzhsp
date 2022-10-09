/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.core.util;

import java.util.Comparator;

/**
 * 描述 流程实体排序
 * @author Sundy Sun
 * @version v2.0
 *
 */
public class FlowBeanComparator  implements Comparator{

    @Override
    public int compare(Object o1, Object o2) {
        FlowNodeBean u1 = (FlowNodeBean)o1;
        FlowNodeBean u2 = (FlowNodeBean)o2;
        String[] locs1=u1.getLoc().split(" ");
        String[] locs2=u2.getLoc().split(" ");
        float y1=Integer.parseInt(locs1[1]);
        float y2=Integer.parseInt(locs2[1]);
        if(y1>y2){
            return 1;
        }else if(y1<y2){
            return -1;
        }else{
         //利用String自身的排序方法。
         //如果年龄相同就按名字进行排序
         //return u1.name.compareTo(u2.name);
            float x1=Integer.parseInt(locs1[0]);
            float x2=Integer.parseInt(locs2[0]);
            if(x1>x2){
                return 1;
            }
            return -1;
        }
    }
    
}
