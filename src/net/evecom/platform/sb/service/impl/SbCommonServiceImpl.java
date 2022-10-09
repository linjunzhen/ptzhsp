/*
 * Copyright (c) 2005, 2020, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.sb.service.impl;

import javax.annotation.Resource;

import com.alibaba.fastjson.JSON;
import net.evecom.core.util.AjaxJson;
import net.evecom.core.util.StringUtil;
import net.evecom.platform.sb.util.SbCommonUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import net.evecom.core.dao.GenericDao;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.platform.sb.dao.SbCommonDao;
import net.evecom.platform.sb.service.SbCommonService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 描述  城乡居民社会养老保险业务通用操作services
 * @author Allin Lin
 * @created 2020年2月18日 下午2:39:54
 */
@Service("/sbCommonService")
public class SbCommonServiceImpl extends BaseServiceImpl implements SbCommonService{
    
    /**
     * log
     */
    private static Log log = LogFactory.getLog(SbCommonServiceImpl.class);
    
    /**
     * 所引入的dao
     */
    @Resource
    private SbCommonDao dao;
    
    /**
     * 描述 覆盖获取实体dao方法
     * @author Allin Lin
     * @created 2020年2月18日 下午2:43:47
     * @return
     * @see net.evecom.core.service.impl.GenericServiceImpl#getEntityDao()
     */
    @Override
    protected GenericDao getEntityDao() {
        return dao;
    }

    /**
     *判断人员是否进行参保
     * @param idNo  身份证号
     * @param cbxzDicCode  参保险种：110-养老；410-工伤
     * @param isAlreadyNew 是否校验人员存在新参保申报业务
     * @return
     */
    public AjaxJson isValidForNew(String idNo, String cbxzDicCode, String isAlreadyNew)  {
        AjaxJson ajaxJson=new AjaxJson();
        String token = SbCommonUtil.getRealToken();
        Map<String,Object> info=new HashMap<String,Object>();
        info.put("aac002",idNo);
        info.put("aae140",cbxzDicCode);
        info.put("aae013",isAlreadyNew);
        Map<String,Object> result = null;
        try {
            result = SbCommonUtil.sbCommonGet(info, "isValidForNew",token);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        if("200".equals(result.get("code"))){
            ajaxJson.setSuccess(true);
        }else{
            ajaxJson.setSuccess(false);
        }
        ajaxJson.setMsg(StringUtil.getString(result.get("message")));
        return ajaxJson;
    }
    /**
     *判断人员是否是最后一人
     * @param dwNum
     * @param idNo  身份证号
     * @param cbxzDicCode  参保险种：110-养老；410-工伤
     * @return
     */
    public boolean isLastMan(String dwNum,String idNo,String cbxzDicCode)  {
        String token = SbCommonUtil.getRealToken();
        Map<String,Object> info=new HashMap<String,Object>();
        info.put("aab001",dwNum);
        info.put("aac002",idNo);
        info.put("aae140",cbxzDicCode);
        Map<String,Object> result = null;
        try {
            result = SbCommonUtil.sbCommonPost(info, "isLastMan",token,"2");
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        if("200".equals(result.get("code"))){
            return true;
        }
        return false;
    }
}
