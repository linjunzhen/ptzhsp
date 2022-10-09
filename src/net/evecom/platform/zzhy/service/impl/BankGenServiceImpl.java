/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.zzhy.service.impl;

import net.evecom.core.dao.GenericDao;
import net.evecom.core.poi.WordReplaceUtil;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.platform.system.service.DictionaryService;
import net.evecom.platform.zzhy.dao.DomesticControllerDao;
import net.evecom.platform.zzhy.service.BankGenService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 描述
 * @author Danto Huang
 * @created 2017年12月1日 下午3:44:12
 */
@Service("bankGenService")
public class BankGenServiceImpl extends BaseServiceImpl implements BankGenService {
    /**
     * 所引入的dao
     */
    @Resource
    private DomesticControllerDao dao;
    /**
     * 引入Service
     */
    @Resource
    private DictionaryService dictionaryService;
    /**
     * 覆盖获取实体dao方法
     * 描述
     * @author Rider Chen
     * @created 2014年9月11日 上午9:14:37
     * @return
     * @see net.evecom.core.service.impl.GenericServiceImpl#getEntityDao()
     */
    @Override
    protected GenericDao getEntityDao() {
        return dao;
    }
    
    /**
     * 
     * 描述 银行开户申请表
     * @author Danto Huang
     * @created 2017年12月1日 下午3:48:33
     * @param param
     * @param filepathString
     * @param destpathString
     */
    public void genBankApply(Map<String, Object> param, String filepathString, String destpathString){
        String tableName = param.get("tableName").toString();
        if(tableName.equals("T_COMMERCIAL_SOLELYINVEST")){
            String idType = dictionaryService.get("DocumentType", param.get("INVESTOR_IDTYPE").toString())
                    .get("DIC_NAME").toString();
            param.put("LEGAL_NAME", param.get("INVESTOR_NAME"));
            param.put("LEGAL_IDTYPE", idType);
            param.put("LEGAL_IDNO", param.get("INVESTOR_IDCARD"));
        }else{
            String idType = dictionaryService.get("DocumentType", param.get("LEGAL_IDTYPE").toString()).get("DIC_NAME")
                    .toString();
            param.put("LEGAL_IDTYPE", idType);
        }
        param.put("DEP_ADDRESS", param.get("DEPOSITOR_ADDR"));
        if(param.get("SOCIAL_CREDITUNICODE")!=null){
            String socialCode = param.get("SOCIAL_CREDITUNICODE").toString();
            param.put("orgCode", socialCode.substring(8, socialCode.length()-1));
        }
        String bankType = param.get("BANK_TYPE").toString();
        if(bankType.equals("pdb")||bankType.equals("rcb")||bankType.equals("boc")||bankType.equals("abc")
                ||bankType.equals("comm")){
            param.put("INDA", " ");
            param.put("INDB", " ");
            param.put("INDC", " ");
            param.put("INDD", " ");
            param.put("INDE", " ");
            param.put("INDF", " ");
            param.put("INDG", " ");
            param.put("INDH", " ");
            param.put("INDI", " ");
            param.put("INDJ", " ");
            param.put("INDK", " ");
            param.put("INDL", " ");
            param.put("INDM", " ");
            param.put("INDN", " ");
            param.put("INDO", " ");
            param.put("INDP", " ");
            param.put("INDQ", " ");
            param.put("INDR", " ");
            param.put("INDS", " ");
            param.put("INDT", " ");

            if(null != param.get("INDUSTRY_CATEGORY")){
                String[] indus = param.get("INDUSTRY_CATEGORY").toString().split(",");
                /*for(int i=0;i<indus.length;i++){
                    String key = "IND".concat(indus[i]);
                    param.put(key, "√");
                }*/
                String key = "IND".concat(indus[0]);
                param.put(key, "√");                
            }
        }else if(bankType.equals("ccb")){
            if(null != param.get("INDUSTRY_CATEGORY")){
                String[] indus = param.get("INDUSTRY_CATEGORY").toString().split(",");
                param.put("INDUSTRY_CATEGORY", indus[0]);                
            }else{
                param.put("INDUSTRY_CATEGORY", "");                
            }
        }
        WordReplaceUtil.replaceWord(param, filepathString, destpathString);
    }
}
