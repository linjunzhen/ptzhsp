/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.core.service;

import java.util.concurrent.Callable;

/**
 * 描述
 * @author Flex Hu
 * @version 1.0
 * @created 2014年9月6日 上午8:25:24
 */
public interface BaseService<T> extends GenericService<T, String> {

    /**
     * 线程执行--无返回值
     * @param runnable
     */
    void runnable(Runnable runnable);

    /**
     * 线程提交
     *
     * @param callable
     * @param <T>
     * @return
     */
    <T> T callable(Callable callable);
}
