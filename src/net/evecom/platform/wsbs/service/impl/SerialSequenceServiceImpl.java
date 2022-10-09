/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.wsbs.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.platform.wsbs.dao.SerialNumberDao;
import net.evecom.platform.wsbs.service.SerialSequenceService;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

/**
 * 
 * 描述 序号配置管理服务 扩展对编号参数过滤时， 增加***SeqMgr方法，并在字典项SerialSeqType 增加配置即可
 * 
 * @author Derek Zhang
 * @created 2015年9月22日 下午6:02:44
 */
@SuppressWarnings("rawtypes")
@Service("serialSequenceService")
public class SerialSequenceServiceImpl extends BaseServiceImpl implements SerialSequenceService {
    /**
     * 所引入的dao
     */
    @Resource
    private SerialNumberDao dao;

    @Override
    protected GenericDao getEntityDao() {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * 描述 返回4位序号，左补0
     * 
     * @author Derek Zhang
     * @created 2015年10月7日 下午12:48:42
     * @param num
     * @return
     */
    @Override
    public String leftfourzeoSequence(Integer num) {
        return StringUtils.leftPad(String.valueOf(num), 4, '0');
    }

    /**
     * 描述 返回自然序列
     * 
     * @author Derek Zhang
     * @created 2015年10月7日 下午12:48:42
     * @param num
     * @return
     */
    @Override
    public String generalSequence(Integer num) {
        return String.valueOf(num);
    }

    /**
     * 
     * 描述 test
     * 
     * @author Derek Zhang
     * @created 2015年10月7日 下午1:57:47
     * @param s
     */
    public static void main(String[] s) {
        // TODO Auto-generated method stub
    }

    @Override
    public List<Map<String, Object>> getSequenceTypeDicList(String sequenceType) {
        return dao.getSequenceTypeDicList(sequenceType);
    }
}
