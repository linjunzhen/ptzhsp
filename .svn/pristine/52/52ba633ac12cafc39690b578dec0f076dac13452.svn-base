/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.core.service.impl;

import net.evecom.core.service.BaseService;
import net.evecom.core.util.ThreadPoolUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.concurrent.*;

/**
 * 描述
 * @author Flex Hu
 * @version 1.0
 * @created 2014年9月6日 上午8:25:59
 */
public abstract class BaseServiceImpl<T> extends GenericServiceImpl<T, String> implements BaseService<T> {

    /**
     * log4J声明
     */
    private static Log log = LogFactory.getLog(BaseServiceImpl.class);

    /**
     * 线程执行--无返回值
     * @param runnable
     */
    public void runnable(Runnable runnable){
        ExecutorService executor = ThreadPoolUtils.getInstance();//Executors.newCachedThreadPool();
        try{
            executor.execute(runnable);
        }catch (Exception e){
            log.error(e);
        }finally {
            //executor.shutdown();//一般不用关闭线程池
        }
    }

    /**
     * 线程提交
     *
     * @param callable
     * @param <T>
     * @return
     */
    public <T> T callable(Callable callable) {
        T result = null;
        FutureTask<T> futureTask = new FutureTask<>(callable);
        ExecutorService executor = ThreadPoolUtils.getInstance();//Executors.newCachedThreadPool();
        try {
            executor.submit(futureTask);
            result = futureTask.get();
        } catch (InterruptedException e) {
            log.error(e);
        } catch (ExecutionException e) {
            log.error(e);
        } finally {
            //executor.shutdown();//一般不用关闭线程池
        }

        return result;
    }
}
