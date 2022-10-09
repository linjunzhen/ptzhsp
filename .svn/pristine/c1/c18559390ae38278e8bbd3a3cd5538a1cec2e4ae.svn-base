/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.wsbs.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.util.StringUtil;
import net.evecom.platform.wsbs.service.SerialParamsService;
import net.evecom.platform.wsbs.service.SerialSequenceService;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

/**
 * 
 * 描述 编号配置管理服务 扩展对编号参数过滤时，增加***Execute方法，并在字典项SerialParameter 增加配置即可
 * 
 * @author Derek Zhang
 * @created 2015年9月22日 下午6:02:44
 */
@SuppressWarnings("rawtypes")
@Service("serialParamsService")
public class SerialParamsServiceImpl extends BaseServiceImpl implements SerialParamsService {
    /**
     * log声明
     */
    private static Log log = LogFactory.getLog(SerialParamsServiceImpl.class);
    /**
     * 所引入的dao
     */
    @Resource
    private SerialSequenceService serialSequenceService;

    /**
     * 
     * 描述 【行政区划】的实现
     * 
     * @author Derek Zhang
     * @created 2015年9月30日 下午5:02:25
     * @param serialNumber
     * @param param
     * @return
     * @see net.evecom.platform.wsbs.service.SerialParamsService#xzqhExecute(java.lang.String,
     *      java.lang.String[])
     */
    @Override
    public String xzqhExecute(String serialNumber, String param, Map<String, String> otherParam) {
        String xzqh = otherParam.get("xzqh");
        if (StringUtils.isBlank(xzqh)) {
            return serialNumber;
        } else {
            return serialNumber.replaceAll(param, xzqh);
        }
    }

    @Override
    protected GenericDao getEntityDao() {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * 
     * 描述 【年】的实现
     * 
     * @author Derek Zhang
     * @created 2015年9月30日 下午5:02:25
     * @param serialNumber
     * @param param
     * @return
     * @see net.evecom.platform.wsbs.service.SerialParamsService#xzqhExecute(java.lang.String,
     *      java.lang.String[])
     */
    @Override
    public String yearExecute(String serialNumber, String param, Map<String, String> otherParam) {
        return serialNumber.replaceAll(param, new SimpleDateFormat("yyyy").format(new Date()));
    }

    /**
     * 
     * 描述 【月】的实现
     * 
     * @author Derek Zhang
     * @created 2015年9月30日 下午5:02:25
     * @param serialNumber
     * @param param
     * @return
     * @see net.evecom.platform.wsbs.service.SerialParamsService#xzqhExecute(java.lang.String,
     *      java.lang.String[])
     */
    @Override
    public String monthExecute(String serialNumber, String param, Map<String, String> otherParam) {
        return serialNumber.replaceAll(param, new SimpleDateFormat("MM").format(new Date()));
    }

    /**
     * 
     * 描述 【日】的实现
     * 
     * @author Derek Zhang
     * @created 2015年9月30日 下午5:02:25
     * @param serialNumber
     * @param param
     * @return
     * @see net.evecom.platform.wsbs.service.SerialParamsService#xzqhExecute(java.lang.String,
     *      java.lang.String[])
     */
    @Override
    public String dayExecute(String serialNumber, String param, Map<String, String> otherParam) {
        return serialNumber.replaceAll(param, new SimpleDateFormat("dd").format(new Date()));
    }

    /**
     * 
     * 描述 【年月日】的实现
     * 
     * @author Derek Zhang
     * @created 2015年9月30日 下午5:02:25
     * @param serialNumber
     * @param param
     * @return
     * @see net.evecom.platform.wsbs.service.SerialParamsService#xzqhExecute(java.lang.String,
     *      java.lang.String[])
     */
    @Override
    public String ymdExecute(String serialNumber, String param, Map<String, String> otherParam) {
        return serialNumber.replaceAll(param, new SimpleDateFormat("yyyyMMdd").format(new Date()));
    }

    /**
     * 
     * 描述 【年月日时分秒】的实现
     * 
     * @author Derek Zhang
     * @created 2015年9月30日 下午5:02:25
     * @param serialNumber
     * @param param
     * @return
     * @see net.evecom.platform.wsbs.service.SerialParamsService#xzqhExecute(java.lang.String,
     *      java.lang.String[])
     */
    @Override
    public String ymdhmsExecute(String serialNumber, String param, Map<String, String> otherParam) {
        return serialNumber.replaceAll(param, new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
    }

    /**
     * 
     * 描述 【序列码】的实现
     * 
     * @author Derek Zhang
     * @created 2015年9月30日 下午5:02:25
     * @param serialNumber
     * @param param
     * @return
     * @see net.evecom.platform.wsbs.service.SerialParamsService#xzqhExecute(java.lang.String,
     *      java.lang.String[])
     */
    @Override
    public String seqExecute(String serialNumber, String param, Map<String, String> otherParam) {
        Integer maxSequence = otherParam.get("maxSequence") == null ? 1 : (Integer.parseInt((String) otherParam
                .get("maxSequence")) + 1);
        String sequenceType = otherParam.get("sequenceType");
        List<Map<String, Object>> dicList = serialSequenceService.getSequenceTypeDicList(sequenceType);
        String seqStr = "";
        if (dicList == null) {
            return serialNumber;
        } else {
            for (Map<String, Object> map : dicList) {
                String dicDesc = (String) map.get("DIC_DESC");
                if (StringUtils.isNotBlank(dicDesc) && !dicDesc.equals("null")) {
                    try {
                        Method method = serialSequenceService.getClass().getMethod(dicDesc + "Sequence", Integer.class);
                        seqStr = (String) method.invoke(serialSequenceService, maxSequence);
                    } catch (SecurityException e) {
                        log.error(e.getMessage());
                    } catch (NoSuchMethodException e) {
                        log.error(e.getMessage());
                    } catch (IllegalArgumentException e) {
                        log.error(e.getMessage());
                    } catch (IllegalAccessException e) {
                        log.error(e.getMessage());
                    } catch (InvocationTargetException e) {
                        log.error(e.getMessage());
                    }
                    if (StringUtils.isNotBlank(seqStr)) {
                        serialNumber = serialNumber.replaceAll(param, seqStr);
                    }
                } else {
                    serialNumber = serialNumber.replaceAll(param, String.valueOf(maxSequence));
                }
            }

        }
        return serialNumber.replaceAll(param, seqStr);
    }

}
