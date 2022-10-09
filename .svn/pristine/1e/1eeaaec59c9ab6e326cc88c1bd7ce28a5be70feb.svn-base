/*
 * Copyright (c) 2005, 2019, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.core.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 描述 单例模式创建线程池 该模式可以保证程序中只创建一个线程池
 * @author Flex Hu
 * @version 1.0
 * @created 2015年8月6日 下午5:41:30
 */
public class ThreadPoolUtils {

    /**
     * 复用可缓存线程池 创建60s的线程池
     */
    private static volatile ExecutorService instance = null;

    /**
     * 构造方法
     */
    private ThreadPoolUtils() {
    }

    /**
     * 运行时加载对象
     * @return
     */
    public static ExecutorService getInstance() {
        if (instance == null) {
            synchronized(ThreadPoolUtils.class){
                if(instance == null){
                    instance = Executors.newCachedThreadPool();
                }
            }
        }
        return instance;
    }
}
















































