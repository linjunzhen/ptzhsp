/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.core.util;

import java.util.Arrays;

/**
 * 描述
 * 
 * @author Flex Hu
 * @version 1.0
 * @created 2014年10月3日 下午12:37:28
 */
public class ArrayUtil {

    /**
     * 
     * 描述 获取数组中的最大值
     * 
     * @author Flex Hu
     * @created 2014年10月3日 下午12:38:04
     * @param arr
     * @return
     */
    public static int getMax(int[] arr) {
        int max = arr[0];
        for (int x = 1; x < arr.length; x++) {
            if (arr[x] > max)
                max = arr[x];
        }
        return max;
    }
    /**
     * 
     * 描述 获取数组中最小值
     * @author Flex Hu
     * @created 2014年10月3日 下午12:38:33
     * @param arr
     * @return
     */
    public static int getMin(int[] arr) {
        int min = 0;
        for (int x = 1; x < arr.length; x++) {
            if (arr[x] < arr[min])
                min = x;
        }
        return arr[min];
    }
    /**
     * 
     * 描述 对数组进行升序排
     * @author Flex Hu
     * @created 2014年10月3日 下午12:49:25
     * @param arr
     * @return
     */
    public static int[] sortByAsc(int[] arr){
        Arrays.sort(arr);
        return arr;
    }
    /**
     * 
     * 描述 对数组进行降序排
     * @author Flex Hu
     * @created 2014年10月3日 下午12:53:01
     * @param arr
     * @return
     */
    public static int[] sortByDesc(int[] arr){
        Arrays.sort(arr);
        int[] nums = new int[arr.length];
        int j =0;
        for(int i =arr.length-1;i>=0;i--){
            nums[j] = arr[i];
            j++;
        }
        return nums;
    }
}
