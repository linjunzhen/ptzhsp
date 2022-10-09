/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.hflow.service.impl;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.itextpdf.text.pdf.PdfStructTreeController.returnType;

import net.evecom.core.dao.GenericDao;
import net.evecom.core.model.FileInfoMsg;
import net.evecom.core.service.impl.BaseServiceImpl;
import net.evecom.core.sm.Sm4Utils;
import net.evecom.core.util.*;
import net.evecom.core.web.paging.PagingBean;
import net.evecom.core.web.servlet.DownLoadServlet;
import net.evecom.platform.bsfw.model.PtspFile;
import net.evecom.platform.bsfw.service.CreditService;
import net.evecom.platform.commercial.service.CommercialSetService;
import net.evecom.platform.hflow.dao.ExecutionDao;
import net.evecom.platform.hflow.service.ExeDataService;
import net.evecom.platform.system.service.DictionaryService;
import net.evecom.platform.zzhy.model.TableNameEnum;
import net.evecom.platform.zzhy.model.exesuccess.ExeContent;
import net.evecom.platform.zzhy.model.exesuccess.ExeMsgFactory;
import net.evecom.platform.zzhy.model.sign.SignContent;
import net.evecom.platform.zzhy.model.sign.SignMsgFactory;
import net.evecom.platform.zzhy.util.MsgSendUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.annotation.Resource;
import javax.imageio.stream.FileImageInputStream;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 描述 流程实例数据操作service
 * @author Flex Hu
 * @version 1.0
 * @created 2014年9月11日 上午9:13:33
 */
@Service("exeDataService")
public class ExeDataServiceImpl extends BaseServiceImpl implements ExeDataService {
    /**
     * 所引入的dao
     */
    @Resource
    private ExecutionDao dao;
    /**
     * 所引入的dao
     */
    @Resource
    private CreditService creditService;
    /**
     * dictionaryService
     */
    @Resource
    private DictionaryService dictionaryService;
    
    /**
     * commercialSetService
     */
    @Resource
    private CommercialSetService commercialSetService;
    /**
     * log
     */
    private static Log log = LogFactory.getLog(ExeDataServiceImpl.class);
    /**
     *
     * @return
     */
    @Override
    protected GenericDao getEntityDao() {
        return dao;
    }

    /**
     *
     * @param sqlFilter
     * @return
     */
    @Override
    public List<Map<String, Object>> findCompanyName(SqlFilter sqlFilter) {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("SELECT * FROM JBPM6_EXECUTION E");
        sql.append("  WHERE E.SQJG_NAME IS NOT NULL ");
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql, params.toArray(), sqlFilter.getPagingBean());
        return list;
    }

    /**
     * 上传并解码成图片保存
     * @param token
     * @param baseCode
     * @param type
     * @return
     */
    public AjaxJsonCode uploadAndSaveImg(String token,String baseCode,String type){
        AjaxJsonCode j=new AjaxJsonCode();
        Map<String,Object> sign=dao.getByJdbc("T_COMMERCIAL_SIGN",
                new String[]{"TOKEN"},new Object[]{token});
        String signName=StringUtil.getString(sign.get("SIGN_NAME"));
        String sid=StringUtil.getString(sign.get("SIGN_ID"));
        if(sign==null){
            j.setCode("003");
            j.setMsg("token不合法");
            return j;
        }
        //判断是否符合上传类型
        Set<String> signCol=new HashSet<String>();
        signCol.add("SIGN_WRITE");
        signCol.add("SIGN_IDPHOTO_FRONT");
        signCol.add("SIGN_IDPHOTO_BACK");
        signCol.add("SIGN_VIDEO");
        if(!signCol.contains(type)){
            j.setCode("003");
            j.setMsg("上传类型不合法");
            return j;
        }
        //文件名
        String fileName="";
        String typeId="";
        if("SIGN_VIDEO".equals(type)){
            fileName=signName+".mp4";
             typeId="3";
        }else{
            fileName=signName+".png";
            typeId="0";
        }
        PtspFile ptspFile=creditService.uploadBase64AndGetPtspfile(fileName,baseCode,typeId);
        sign.put(type,ptspFile.getFileId());
        //当上传类型为手写时，代表是面签结束，标志位置为1
        if("SIGN_WRITE".equals(type)){
            sign.put("SIGN_FLAG","1");
        }
        dao.saveOrUpdate(sign,"T_COMMERCIAL_SIGN",sid);
        //当上传类型为手写时，判断是否全部面签结束
        if("SIGN_WRITE".equals(type)){
            String exeId=StringUtil.getString(sign.get("EXE_ID"));
            int all=getSignPeopleByTableName(exeId).size();
            int signed=findSignedListById(exeId).size();
            int remain=all-signed;
            //全部面签结束，把办件基本表状态置为已面签
            if(remain==0){
                List<Map<String,Object>>  legalOfCompany=findLegalCompanyByexeId(exeId);
                //当面签未法定股东时，需要上传公司公章，才算面签通过。
                if(legalOfCompany==null||legalOfCompany.size()==0){
                    changeSignStatus(exeId,"1");
                }else{
                    //先清除办件营业执照信息
                    dao.remove("T_COMMERCIAL_COMPANY_LEGALFILE",new String[]{"EXE_ID"},new Object[]{exeId});
                    for(Map<String,Object> map:legalOfCompany){
                        Map<String,Object> leagalCompany=new HashMap<>();
                        leagalCompany.putAll(map);
                        leagalCompany.put("UPLOAD_FLAG",0);
                        dao.saveOrUpdate(leagalCompany,"T_COMMERCIAL_COMPANY_LEGALFILE","");
                    }
                }
                j.setMsg("面签全部完成");
            }else{
                j.setMsg(String.format("面签成功,未面签%s人",remain));
            }
            j.setCode("001");
            return j;
        }
        j.setCode("001");
        j.setMsg("上传成功");
        return j;
    }
    /**
     * 根据token获取公司名称
     * @param token
     * @return
     */
    public AjaxJsonCode getCompanyNameByToken(String token){
        AjaxJsonCode j=new AjaxJsonCode();
        Map<String,Object> sign=dao.getByJdbc("T_COMMERCIAL_SIGN",
                new String[]{"TOKEN"},new Object[]{token});
        if(sign==null){
            j.setCode("002");
            j.setMsg("token不合法");
        }
        String exeId=StringUtil.getString(sign.get("EXE_ID"));
        String companyName=getCompanyNameByExeId(exeId);
        j.setCode("001");
        j.setMsg(companyName);
        return j;
    }
    /**
     * 商事意见栏jsonlist
     * @param sqlFilter
     * @return
     */
    public List<Map<String,Object>> findCommercialOpinionList(SqlFilter sqlFilter){
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("");
        sql.append(" SELECT * ");
        sql.append(" FROM T_COMMERCIAL_OPINION  T ");
        String exeSql = dao.getQuerySql(sqlFilter, sql.toString(), params);
        List<Map<String, Object>> list = dao.findBySql(exeSql,
                params.toArray(), sqlFilter.getPagingBean());
        return list;
    }
    /**
     * 根据办件id获取公司名称
     * @param exeId
     * @return
     */
    public String getCompanyNameByExeId(String exeId){
        Map<String,Object> buscordMap=getBuscordMap(exeId);
        String companyName="";
        companyName=StringUtil.getString(buscordMap.get("COMPANY_NAME"));
        if(StringUtils.isNotEmpty(companyName)){
            return companyName;
        }
        companyName=StringUtil.getString(buscordMap.get("INDIVIDUAL_NAME"));
        if(StringUtils.isNotEmpty(companyName)){
            return companyName;
        }
        return  companyName;
    }
    /**
     * 改变面签状态
     * @param exeId
     * @param flag
     */
    public void changeSignStatus(String exeId,String flag){
        Map<String,Object> execution =new HashMap<String,Object>();
        execution.put("ISFACESIGN",flag);
        dao.saveOrUpdate(execution,"JBPM6_EXECUTION",exeId);
    }

    /**
     * 根据sql语句获取list
     * @param sql
     * @param params
     * @return
     */
    public List<Map<String,Object>> findListBySql(String sql,Object[] params,PagingBean pb){
        return dao.findBySql(sql,params,pb);
    }
    /**
     * 保存上传的面签视频
     * @param file
     * @param token
     * @return
     */
    public AjaxJsonCode handleSignVideo(CommonsMultipartFile file,String token){
        AjaxJsonCode j=new AjaxJsonCode();
        j.setCode("001");
        j.setMsg("上传成功");
        if(StringUtils.isEmpty(token)){
            j.setCode("003");
            j.setMsg("token不能为空");
            return j;
        }
        Map<String,Object> sign=dao.getByJdbc("T_COMMERCIAL_SIGN",
                new String[]{"TOKEN"},new Object[]{token});
        if(sign==null){
            j.setCode("003");
            j.setMsg("token不合法");
            return j;
        }
        String fileName=file.getOriginalFilename();
        String suffixFile="mp4";
        if(StringUtils.isNotEmpty(fileName)){
            String[] fnamearr=fileName.split("\\.");
            if(fnamearr.length>1)
                suffixFile=fnamearr[1];
        }
        FileInfoMsg  fsg=getUploadFilePath("video",suffixFile);
        String absFilePath=fsg.getAbsoultFilePath();
        OutputStream os=null;
        InputStream is=null;
        try {
            os=new FileOutputStream(absFilePath);
            is=file.getInputStream();
            int tmp;
            while((tmp=is.read())!=-1){
                os.write(tmp);
            }
        } catch (Exception e) {
            log.info(e.getMessage());
            j.setCode("005");
            j.setMsg("上传失败");
        }finally {
            try {
                if (os != null) os.close();
                if (is != null) is.close();
            } catch (IOException e) {
                log.info(e.getMessage());
            }
        }
        //上传到文件服务器
        String signName=StringUtil.getString(sign.get("SIGN_NAME"));
        fileName=signName+".mp4";
        File f=new File(absFilePath);
        PtspFile ptspFile=creditService.uploadAndGetPtspfile(sign,fileName,f,"3");
        if("001".equals(j.getCode())){
            sign.put("SIGN_VIDEO",ptspFile.getFileId());
            dao.saveOrUpdate(sign,"T_COMMERCIAL_SIGN",StringUtil.getString(sign.get("SIGN_ID")));
        }
        return j;
    }

    /**
     * 根据办件id获取股东法定公司
     * @param exeId
     * @return
     */
    public List<Map<String,Object>> findLegalCompanyByexeId(String exeId){
        Map<String,Object> busRecord=getBuscordMap(exeId);
        List<Map<String,Object>> peoples=new ArrayList<Map<String, Object>>();
        //商事登记全程网办业务变更、备案、注销需添加企业公章
        Map<String,Object> execution = dao.getByJdbc("JBPM6_EXECUTION",
                new String[]{"EXE_ID"},new Object[]{exeId});
        String busTableName= StringUtil.getString(execution.get("BUS_TABLENAME"));
        Map<String, Object> companyMap = findCompanyByExeId(exeId);
        if(companyMap!=null && companyMap.size()>0){
            peoples.add(companyMap);
        }
        //变更公章列表需要展示变更前、后含有企业的股东的所有公章信息
        if("T_COMMERCIAL_CHANGE_DOMESTIC".equals(busTableName)){
            List<Map<String,Object>> newHolderList = new ArrayList<Map<String,Object>>();
            List<Map<String,Object>> yHolderList = new ArrayList<Map<String,Object>>();
            List<Map<String,Object>> xHolderList = new ArrayList<Map<String,Object>>();
            List<Map<String,Object>> sameHolderList = new ArrayList<Map<String,Object>>();
            String HOLDER_JSON = busRecord.get("HOLDER_JSON") == null ? "" : busRecord.get("HOLDER_JSON").toString();
            if (StringUtils.isNotEmpty(HOLDER_JSON)) {
                yHolderList = JSON.parseObject(HOLDER_JSON, List.class);
            }
            // 股东信息(变更)
            String HOLDER_JSON_CHANGE = busRecord.get("HOLDER_JSON_CHANGE") == null ? ""
                    : busRecord.get("HOLDER_JSON_CHANGE").toString();
            if (StringUtils.isNotEmpty(HOLDER_JSON_CHANGE)) {
                xHolderList = JSON.parseObject(HOLDER_JSON_CHANGE, List.class);
            }
            //剔除新旧股东同名情况
            if(yHolderList.size()>0 && xHolderList.size()>0){
                for (Map<String, Object> xHolder : xHolderList) {
                    for (Map<String, Object> yHolder : yHolderList) {
                        if(StringUtil.getString(xHolder.get("SHAREHOLDER_NAME"))
                                .equals(StringUtil.getString(yHolder.get("SHAREHOLDER_NAME")))){
                            sameHolderList.add(yHolder);
                        } 
                    } 
                }
                if(sameHolderList.size()>0){
                    for (Map<String, Object> same : sameHolderList) {
                        yHolderList.remove(same);
                    }
                }
                newHolderList.addAll(yHolderList);
                newHolderList.addAll(xHolderList);
            }else if(xHolderList.size()<=0){
                newHolderList.addAll(yHolderList);
            }else if(yHolderList.size()<=0){
                newHolderList.addAll(xHolderList);
            }
            if(newHolderList!=null && newHolderList.size()>0){
                for (Map<String, Object> newHolder : newHolderList) {
                    //证件号码是身份证的，才面签
                    if(!"SF".equals(StringUtil.getString(newHolder.get("LICENCE_TYPE")))) {
                        Map<String, Object> people = new HashMap<String, Object>();
                        String legalCompanyname=StringUtil.getString(newHolder.get("SHAREHOLDER_NAME"));
                        String legalName=StringUtil.getString(newHolder.get("LEGAL_PERSON"));
                        people.put("LEGAL_PERSON", legalName);
                        people.put("LEGAL_IDNO_PERSON", StringUtil.getString(newHolder.get("LEGAL_IDNO_PERSON")));
                        people.put("LEGAL_COMPANYNAME",legalCompanyname);
                        people.put("EXE_ID",exeId);
                        peoples.add(people);
                    }
                }
            }
        }else{
            String holderJson=StringUtil.getString(busRecord.get("HOLDER_JSON"));
            if(StringUtils.isEmpty(holderJson)){
                return peoples;
            }
            List<Map<String,Object>> list=( List<Map<String,Object>>) JSON.parse
                    (StringUtil.getString(busRecord.get("HOLDER_JSON")));
            for(Map<String,Object> map:list){
                //证件号码是身份证的，才面签
                if(!"SF".equals(StringUtil.getString(map.get("LICENCE_TYPE")))) {
                    Map<String, Object> people = new HashMap<String, Object>();
                    String legalCompanyname=StringUtil.getString(map.get("SHAREHOLDER_NAME"));
                    String legalName=StringUtil.getString(map.get("LEGAL_PERSON"));
                    people.put("LEGAL_PERSON", legalName);
                    people.put("LEGAL_IDNO_PERSON", StringUtil.getString(map.get("LEGAL_IDNO_PERSON")));
                    people.put("LEGAL_COMPANYNAME",legalCompanyname);
                    people.put("EXE_ID",exeId);
                    peoples.add(people);
                }
            }
        }
        
        return peoples;
    }
    
    /**
     * 根据办件id获取企业信息
     * @param exeId
     * @return
     */
    public Map<String,Object> findCompanyByExeId(String exeId){
        Map<String, Object> companyMap = new HashMap<String, Object>();
        Map<String,Object> execution = dao.getByJdbc("JBPM6_EXECUTION",
                new String[]{"EXE_ID"},new Object[]{exeId});
        Map<String,Object> busRecord=getBuscordMap(exeId);
        String busTableName= StringUtil.getString(execution.get("BUS_TABLENAME"));
        if("T_COMMERCIAL_CHANGE_DOMESTIC".equals(busTableName)){//变更
            String CHANGE_OPTIONS = StringUtil.getString(busRecord.get("CHANGE_OPTIONS"));
            if (StringUtils.isNotEmpty(CHANGE_OPTIONS)) {
                if(CHANGE_OPTIONS.indexOf("01")>=0){//名称变更
                    companyMap.put("LEGAL_COMPANYNAME",StringUtil.getString(busRecord.get("COMPANY_NAME_CHANGE")));    
                }else{
                    companyMap.put("LEGAL_COMPANYNAME",StringUtil.getString(busRecord.get("COMPANY_NAME")));    
                }
                if(CHANGE_OPTIONS.indexOf("03")>=0){//法定代表人变更
                    companyMap.put("LEGAL_PERSON", StringUtil.getString(busRecord.get("LEGAL_NAME_CHANGE")));
                    companyMap.put("LEGAL_IDNO_PERSON", StringUtil.getString(busRecord.get("LEGAL_IDNO_CHANGE")));
                }else{
                    companyMap.put("LEGAL_PERSON", StringUtil.getString(busRecord.get("LEGAL_NAME")));
                    companyMap.put("LEGAL_IDNO_PERSON", StringUtil.getString(busRecord.get("LEGAL_IDNO")));
                }
            }
            companyMap.put("EXE_ID",exeId);
            companyMap.put("UPLOAD_TYPE","2");
            companyMap.put("UPLOAD_INSTRUCTIONS","企业公章");
            companyMap.put("UPLOAD_SIGN","1");
        }else if("T_COMMERCIAL_SSNZQYBA".equals(busTableName) 
                || "T_COMMERCIAL_CANCEL".equals(busTableName)){//备案&注销
            companyMap.put("LEGAL_COMPANYNAME",StringUtil.getString(busRecord.get("COMPANY_NAME")));  
            companyMap.put("LEGAL_PERSON", StringUtil.getString(busRecord.get("LEGAL_NAME")));
            companyMap.put("LEGAL_IDNO_PERSON", StringUtil.getString(busRecord.get("LEGAL_IDNO")));
            companyMap.put("EXE_ID",exeId);
            companyMap.put("UPLOAD_TYPE","2");
            companyMap.put("UPLOAD_INSTRUCTIONS","企业公章");
            companyMap.put("UPLOAD_SIGN","1");
        }
        return companyMap;
    }

    /**
     *回填面签信息，身份正反面，手写签名
     * @param exeId
     * @return
     */
    public Map<String,Object> setSignInfo(String exeId){
        Map<String,Object> signInfo=new HashMap<String,Object>();
        Map<String,Object> execution=dao.getByJdbc("JBPM6_EXECUTION",
                new String[]{"EXE_ID"},new Object[]{exeId});
        String tableName=StringUtil.getString(execution.get("BUS_TABLENAME"));
        //内资
        if("T_COMMERCIAL_DOMESTIC".equals(tableName)){
           signInfo=setCompanySignInfo(exeId);
        }
        return signInfo;
    }
    /**
     * 内资面签信息回填
     * @param buscord
     * @return
     */
    private Map<String,Object> setCompanySignInfo(Map<String,Object> buscord){
        String exeId=StringUtil.getString(buscord.get("EXE_ID"));
        //法定代表人
        String name=StringUtil.getString(buscord.get("LEGAL_NAME"));
        String idNo= StringUtil.getString(buscord.get("LEGAL_IDNO"));
        buscord.putAll(setSignImgByParams(exeId,name,idNo,"LEGAL_BPHOTO"));
        buscord.putAll(setSignImgByParams(exeId,name,idNo,"LEGAL_FPHOTO"));
        buscord.putAll(setSignImgByParams(exeId,name,idNo,"LEGAL_WRITE"));
        //董事
        setDirector(buscord);
        String directorName=StringUtil.getString(buscord.get("DIRECTOR_SIGN_NAME"));
        String directorIdno=StringUtil.getString(buscord.get("DIRECTOR_SIGN_IDNO"));
        buscord.putAll(setSignImgByParams(exeId,directorName,directorIdno,"DIRECTOR_BPHOTO"));
        buscord.putAll(setSignImgByParams(exeId,directorName,directorIdno,"DIRECTOR_FPHOTO"));
        buscord.putAll(setSignImgByParams(exeId,directorName,directorIdno,"DIRECTOR_WRITE"));
        //董事们签名
        getSignedPeopleOfJson(buscord,"DIRECTOR_JSON","DIRECTOR_NAME",
                "DIRECTOR_IDNO","DIRECTOR_IDTYPE","DIRECTORS_WRITE");
        //股东们签名
        getSignedPeopleOfJson(buscord,"HOLDER_JSON","SHAREHOLDER_NAME",
                "LICENCE_NO","LICENCE_TYPE","HOLDERS_WRITE");
        //股东们身份证正面
        getSignedPeopleOfJson(buscord,"HOLDER_JSON","SHAREHOLDER_NAME",
                "LICENCE_NO","LICENCE_TYPE","HOLDERS_FPHOTO");
        //股东们身份证反面
        getSignedPeopleOfJson(buscord,"HOLDER_JSON","SHAREHOLDER_NAME",
                "LICENCE_NO","LICENCE_TYPE","HOLDERS_BPHOTO");
        //监事
        setSupervisor(buscord);
        String supervisorName=StringUtil.getString(buscord.get("SUPERVISOR_SIGN_NAME"));
        String supervisorIdno=StringUtil.getString(buscord.get("SUPERVISOR_SIGN_IDNO"));
        buscord.putAll(setSignImgByParams(exeId,supervisorName,supervisorIdno,"SUPERVISOR_BPHOTO"));
        buscord.putAll(setSignImgByParams(exeId,supervisorName,supervisorIdno,"SUPERVISOR_FPHOTO"));
        buscord.putAll(setSignImgByParams(exeId,supervisorName,supervisorIdno,"SUPERVISOR_WRITE"));
        //经理
        setManager(buscord);
        String managerName=StringUtil.getString(buscord.get("MANAGER_SIGN_NAME"));
        String managerIdno=StringUtil.getString(buscord.get("MANAGER_SIGN_IDNO"));
        buscord.putAll(setSignImgByParams(exeId,managerName,managerIdno,"MANAGER_BPHOTO"));
        buscord.putAll(setSignImgByParams(exeId,managerName,managerIdno,"MANAGER_FPHOTO"));
        buscord.putAll(setSignImgByParams(exeId,managerName,managerIdno,"MANAGER_WRITE"));
        //联络员
        String liaisonName=StringUtil.getString(buscord.get("LIAISON_NAME"));
        String liaisonIdno=StringUtil.getString(buscord.get("LIAISON_IDNO"));
        buscord.putAll(setSignImgByParams(exeId,liaisonName,liaisonIdno,"LIAISON_BPHOTO"));
        buscord.putAll(setSignImgByParams(exeId,liaisonName,liaisonIdno,"LIAISON_FPHOTO"));
        buscord.putAll(setSignImgByParams(exeId,liaisonName,liaisonIdno,"LIAISON_WRITE"));
        //代理人
        String operatorName=StringUtil.getString(buscord.get("OPERATOR_NAME"));
        String operatorIdno=StringUtil.getString(buscord.get("OPERATOR_IDNO"));
        buscord.putAll(setSignImgByParams(exeId,operatorName,operatorIdno,"OPERATOR_BPHOTO"));
        buscord.putAll(setSignImgByParams(exeId,operatorName,operatorIdno,"OPERATOR_FPHOTO"));
        buscord.putAll(setSignImgByParams(exeId,operatorName,operatorIdno,"OPERATOR_WRITE"));
        //财务负责人
        String financeName=StringUtil.getString(buscord.get("FINANCE_NAME"));
        String financeIdno=StringUtil.getString(buscord.get("FINANCE_IDNO"));
        buscord.putAll(setSignImgByParams(exeId,financeName,financeIdno,"FINANCE_BPHOTO"));
        buscord.putAll(setSignImgByParams(exeId,financeName,financeIdno,"FINANCE_FPHOTO"));
        buscord.putAll(setSignImgByParams(exeId,financeName,financeIdno,"FINANCE_WRITE"));
        return buscord;
    }
    /**
     * 内资合伙面签信息回填
     * @param buscord
     * @return
     */
    private Map<String,Object> setCompanySignInfoForHh(Map<String,Object> buscord){
        String exeId=StringUtil.getString(buscord.get("EXE_ID"));
        //股东们签名
        getSignedPeopleOfJson(buscord,"HOLDER_JSON","SHAREHOLDER_NAME",
                "LICENCE_NO","LICENCE_TYPE","HOLDERS_WRITE");
        //股东们身份证正面
        getSignedPeopleOfJson(buscord,"HOLDER_JSON","SHAREHOLDER_NAME",
                "LICENCE_NO","LICENCE_TYPE","HOLDERS_FPHOTO");
        //股东们身份证反面
        getSignedPeopleOfJson(buscord,"HOLDER_JSON","SHAREHOLDER_NAME",
                "LICENCE_NO","LICENCE_TYPE","HOLDERS_BPHOTO");
        //企业营业执照回填
        setLicenseByUploadFile(buscord,"HOLDER_JSON","SHAREHOLDER_NAME",
                "LEGAL_IDNO_PERSON","LICENCE_TYPE","HOLDERS_LICENSE");
        //联络员
        String liaisonName=StringUtil.getString(buscord.get("LIAISON_NAME"));
        String liaisonIdno=StringUtil.getString(buscord.get("LIAISON_IDNO"));
        buscord.putAll(setSignImgByParams(exeId,liaisonName,liaisonIdno,"LIAISON_BPHOTO"));
        buscord.putAll(setSignImgByParams(exeId,liaisonName,liaisonIdno,"LIAISON_FPHOTO"));
        buscord.putAll(setSignImgByParams(exeId,liaisonName,liaisonIdno,"LIAISON_WRITE"));
        //代理人
        String operatorName=StringUtil.getString(buscord.get("OPERATOR_NAME"));
        String operatorIdno=StringUtil.getString(buscord.get("OPERATOR_IDNO"));
        buscord.putAll(setSignImgByParams(exeId,operatorName,operatorIdno,"OPERATOR_BPHOTO"));
        buscord.putAll(setSignImgByParams(exeId,operatorName,operatorIdno,"OPERATOR_FPHOTO"));
        buscord.putAll(setSignImgByParams(exeId,operatorName,operatorIdno,"OPERATOR_WRITE"));
        //财务负责人
        String financeName=StringUtil.getString(buscord.get("FINANCE_NAME"));
        String financeIdno=StringUtil.getString(buscord.get("FINANCE_IDNO"));
        buscord.putAll(setSignImgByParams(exeId,financeName,financeIdno,"FINANCE_BPHOTO"));
        buscord.putAll(setSignImgByParams(exeId,financeName,financeIdno,"FINANCE_FPHOTO"));
        buscord.putAll(setSignImgByParams(exeId,financeName,financeIdno,"FINANCE_WRITE"));
        //委托代表人签字
        setPartTrust(buscord,"HOLDER_JSON");
        return buscord;
    }

    /**
     *监事单人信息
     * @param buscord
     */
    private void  setSupervisor(Map<String,Object> buscord){
        List<Map<String,Object>>  list=getPeopleByJson(buscord,"SUPERVISOR_JSON",
                "SUPERVISOR_NAME","SUPERVISOR_IDNO","SUPERVISOR_IDTYPE");
        if(list!=null&&list.size()>0){
              Map<String,Object> map=list.get(0);
              buscord.put("SUPERVISOR_SIGN_NAME",StringUtil.getString(map.get("NAME")));
              buscord.put("SUPERVISOR_SIGN_IDNO",StringUtil.getString(map.get("IDNO")));
        }
    }
    /**
     *经理单人信息
     * @param buscord
     */
    private void  setManager(Map<String,Object> buscord){
        List<Map<String,Object>>  list=getPeopleByJson(buscord,"MANAGER_JSON",
                "MANAGER_NAME","MANAGER_IDNO","MANAGER_IDTYPE");
        if(list!=null&&list.size()>0){
            Map<String,Object> map=list.get(0);
            buscord.put("MANAGER_SIGN_NAME",StringUtil.getString(map.get("NAME")));
            buscord.put("MANAGER_SIGN_IDNO",StringUtil.getString(map.get("IDNO")));
        }
    }
    /**
     *董事单人信息
     * @param buscord
     */
    private void  setDirector(Map<String,Object> buscord){
        boolean flag=false;
        String companyId=StringUtil.getString(buscord.get("COMPANY_ID"));
        StringBuffer sql=new StringBuffer("SELECT * FROM T_COMMERCIAL_DIRECTOR D  ");
        sql.append(" WHERE D.COMPANY_ID=?  AND D.DIRECTOR_IDTYPE='SF'  order by D.DIRECTOR_JOB ASC");
        List<Map<String,Object>>  directors=dao.findBySql(sql.toString(),new Object[]{companyId},null);
        if(directors==null||directors.size()==0)  return ;
        //如果有执行董事，选择执行董事，不然按照职位排序选择
        for(Map<String,Object> director:directors){
            String directorJob=StringUtil.getString(director.get("DIRECTOR_JOB"));
            if("30".equals(directorJob)){
                flag=true;
                buscord.put("DIRECTOR_SIGN_NAME",StringUtil.getString(director.get("DIRECTOR_NAME")));
                buscord.put("DIRECTOR_SIGN_IDNO",StringUtil.getString(director.get("DIRECTOR_IDNO")));
            }
        }
        if(!flag){
            Map<String,Object> director=directors.get(0);
            buscord.put("DIRECTOR_SIGN_NAME",StringUtil.getString(director.get("DIRECTOR_NAME")));
            buscord.put("DIRECTOR_SIGN_IDNO",StringUtil.getString(director.get("DIRECTOR_IDNO")));
        }
    }
    /**
     *回填面签信息到模板
     * @param buscord
     * @return
     */
    @Override
    public Map<String,Object> setSignInfo(Map<String,Object> buscord){
        String itemCode=StringUtil.getString(buscord.get("ITEM_CODE"));
        //内资
        if("201605170002XK00101".equals(itemCode)){
            buscord=setCompanySignInfo(buscord);
        }
        //内资合伙
        if("201605170002XK00105".equals(itemCode)){
            buscord=setCompanySignInfoForHh(buscord);
        }
        return buscord;
    }
    /**
     * 内资面签信息回填
     * @param exeId
     * @return
     */
    private Map<String,Object> setCompanySignInfo(String exeId){
        Map<String,Object> buscordMap=getBuscordMap(exeId);
        //法定代表人
        Map<String,Object> photo=new HashMap<String,Object>();
        String name=StringUtil.getString(buscordMap.get("LEGAL_NAME"));
        String idNo= StringUtil.getString(buscordMap.get("LEGAL_IDNO"));
        photo.putAll(setSignImgByParams(exeId,name,idNo,"LEGAL_BPHOTO"));
        photo.putAll(setSignImgByParams(exeId,name,idNo,"LEGAL_FPHOTO"));
        photo.putAll(setSignImgByParams(exeId,name,idNo,"LEGAL_WRITE"));
        getPeopleByJson(buscordMap,"DIRECTOR_JSON","DIRECTOR_NAME",
                "DIRECTOR_IDNO","DIRECTOR_IDTYPE");
        //代理人
        String operatorName=StringUtil.getString(buscordMap.get("OPERATOR_NAME"));
        String operatorIdno=StringUtil.getString(buscordMap.get("OPERATOR_IDNO"));
        photo.putAll(setSignImgByParams(exeId,operatorName,operatorIdno,"OPERATOR_BPHOTO"));
        photo.putAll(setSignImgByParams(exeId,operatorName,operatorIdno,"OPERATOR_FPHOTO"));
        photo.putAll(setSignImgByParams(exeId,operatorName,operatorIdno,"OPERATOR_WRITE"));
        return photo;
    }

    /**
     * 根据面签参数获得图片
     * @param exeId 办件id
     * @param idNo  身份证号
     * @param colName  字段名
     * @return
     */
    @Override
    public Map<String,Object> setSignImgByParams(String exeId, String name, String idNo, String colName){
        Map<String,Object> photo=new HashMap<String,Object>();
        Map<String,Object> sign=dao.getByJdbc("T_COMMERCIAL_SIGN",
                new String[]{"EXE_ID","SIGN_NAME","SIGN_IDNO","SIGN_FLAG"}
                ,new Object[]{exeId,name,idNo,"1"});
        if(sign==null){
            photo.put(colName,"");
            return photo;
        }
        String fileId="";
        //根据列名匹配相关的面签附件信息
        if(colName.indexOf("WRITE")>-1){
             fileId=StringUtil.getString(sign.get("SIGN_WRITE"));
        }else if(colName.indexOf("FPHOTO")>-1){
            fileId=StringUtil.getString(sign.get("SIGN_IDPHOTO_FRONT"));
        }else if(colName.indexOf("BPHOTO")>-1){
            fileId=StringUtil.getString(sign.get("SIGN_IDPHOTO_BACK"));
        }
        if(StringUtils.isEmpty(fileId)){
            photo.put(colName,"");
            return photo;
        }
        Map<String,Object> fileAttach=dao.getByJdbc("T_MSJW_SYSTEM_FILEATTACH",
                new String[]{"FILE_ID"},new Object[]{fileId});
        String relPath=StringUtil.getString(fileAttach.get("FILE_PATH"));
        photo=setSignImg(relPath,colName);
        return photo;
    }
    /**
     * 根据面签参数获得图片
     * @param exeId 办件id
     * @param idNo  身份证号
     * @param colName  字段名
     * @return
     */
    public Map<String,Object> setSignLegalImgByParams(String exeId, String name, String idNo, String colName){
        Map<String,Object> photo=new HashMap<String,Object>();
        Map<String,Object> sign=dao.getByJdbc("T_COMMERCIAL_COMPANY_LEGALFILE",
                new String[]{"EXE_ID","LEGAL_COMPANYNAME","LEGAL_IDNO_PERSON","UPLOAD_FLAG"}
                ,new Object[]{exeId,name,idNo,"1"});
        if(sign==null){
            photo.put(colName,"");
            return photo;
        }
        String fileId=StringUtil.getString(sign.get("LEGAL_FILEID"));
        //根据列名匹配相关的面签附件信息
        if(StringUtils.isEmpty(fileId)){
            photo.put(colName,"");
            return photo;
        }
        Map<String,Object> fileAttach=dao.getByJdbc("T_MSJW_SYSTEM_FILEATTACH",
                new String[]{"FILE_ID"},new Object[]{fileId});
        String relPath=StringUtil.getString(fileAttach.get("FILE_PATH"));
        photo=setSignImg(relPath,colName);
        return photo;
    }
    /**
     * 设置面签图像
     * @param relFilePath
     * @param colName  列名
     */
    private Map<String,Object> setSignImg(String relFilePath,String colName){
        Map<String,Object> photo=new HashMap<String,Object>();
        Properties properties = FileUtil.readProperties("project.properties");
        String urlattachFileFolderPath = properties.getProperty("uploadFileUrlIn").replace("\\", "/");
        String attachFileFolderPath = urlattachFileFolderPath + relFilePath;
        Map map = new HashMap();
        map.put("content", image2byte(attachFileFolderPath));
        //根据列名设置相关样式
        if(colName.indexOf("WRITE")>-1){
            map.put("width", "55");
            map.put("height", "80");
        }else if(colName.indexOf("FPHOTO")>-1||colName.indexOf("BPHOTO")>-1){
            map.put("width", "255");
            map.put("height", "210");
        }else if(colName.indexOf("_LICENSE")>-1){
            map.put("width", "300");
            map.put("height", "200");
        }
        String fileType = FileUtil.getFileExtension(relFilePath);
        map.put("type", fileType);
        photo.put(colName, map);
        return photo;
    }
    /**
     *
     * 描述 图片转换成byte[]
     *
     * @author Rider Chen
     * @created 2017年1月13日 上午10:44:58
     * @param path
     * @return
     */
    public  byte[] image2byte(String path) {
        byte[] data = null;
        log.info("attachFileFolderPath============"+path);
        if (path.contains("http://") || path.contains("https://")) {
            data=image2byteByUrlPath(path);
        } else {
            data=image2byteByFilePath(path);
        }
        return data;
    }

    /**
     *
     * 描述 图片转换成byte[]
     *
     * @author Rider Chen
     * @created 2017年1月13日 上午10:44:58
     * @param path
     * @return
     */
    public byte[] image2byteByUrlPath(String path) {
        byte[] data = null;
        InputStream input = null;
        ByteArrayOutputStream output=null;
        try {
            input = DownLoadServlet.getStreamDownloadOutFile(path);
            output = new ByteArrayOutputStream();
            byte[] buf = new byte[1024];
            int numBytesRead = 0;
            while ((numBytesRead = input.read(buf)) != -1) {
                output.write(buf, 0, numBytesRead);
            }
            data = output.toByteArray();
            output.close();
            input.close();
        } catch (Exception e) {
            log.info(e.getMessage());
        }finally {
            if(output!=null) try {
                output.close();
            } catch (IOException e) {
                log.info(e.getMessage());
            }
            if(input!=null) try {
                input.close();
            } catch (IOException e) {
                log.info(e.getMessage());
            }
        }
        return data;
    }
    /**
     *
     * 描述 图片转换成byte[]
     *
     * @author Rider Chen
     * @created 2017年1月13日 上午10:44:58
     * @param path
     * @return
     */
    public byte[] image2byteByFilePath(String path) {
        byte[] data = null;
        FileImageInputStream input = null;
        try {
            input = new FileImageInputStream(new File(path));
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            byte[] buf = new byte[1024];
            int numBytesRead = 0;
            while ((numBytesRead = input.read(buf)) != -1) {
                output.write(buf, 0, numBytesRead);
            }
            data = output.toByteArray();
            output.close();
            input.close();
        } catch (FileNotFoundException ex1) {
            log.error(ex1.getMessage());
        } catch (IOException ex1) {
            log.error(ex1.getMessage());
        }
        return data;
    }

    /**
     * 给出类型和后缀名就可以获取文件保存的文件信息
     * @param type
     * @param suffix
     * @return
     */
    public FileInfoMsg getUploadFilePath(String type, String suffix){
        Properties properties = FileUtil.readProperties("project.properties");
        String attachFileFolderPath = properties.getProperty("AttachFilePath") + "attachFiles/";
        String typePath = type+"/";
        // 定义上传文件的保存的相对目录路径
        SimpleDateFormat dirSdf = new SimpleDateFormat("yyyyMMdd");
        String currentDate = "DATE_" + dirSdf.format(new Date());
        String uploadFullPath = attachFileFolderPath + typePath + currentDate + "/";
        // 建立全路径目录和临时目录
        File uploadFullPathFolder = new File(uploadFullPath);
        if (!uploadFullPathFolder.exists()) {
            uploadFullPathFolder.mkdirs();
        }
        FileInfoMsg f=new FileInfoMsg();
        String uuId = UUIDGenerator.getUUID();
        String fileName = uuId + "."+suffix;
        String absoultFilePath=uploadFullPath+fileName;
        String relFilePath=String.format("attachFiles/%s/%s/%s",type,currentDate,fileName);
        f.setFid(uuId);
        f.setfName(fileName);
        f.setAbsoultFilePath(absoultFilePath);
        f.setRelFilePath(relFilePath);
        f.setSuffix(suffix);
        return f;
    }
    /**
     * 根据exeId获取面签人员信息
     * @param exeId
     * @return
     */
    public AjaxJsonCode getResultOfSign(String exeId,String name,String idNo){
        AjaxJsonCode ajaxJson=new AjaxJsonCode();
        StringBuffer sql=new StringBuffer("SELECT * FROM  JBPM6_EXECUTION E ");
        sql.append(" WHERE E.EXE_ID LIKE '%").append(exeId).append("' ");
        List<Map<String,Object>> executions=dao.findBySql(sql.toString(),null,null);
        //申报号是否合法
        if(executions==null||executions.size()!=1||exeId.length()<8){
            ajaxJson.setCode("003");
            ajaxJson.setMsg("申报号不合法，请输入正确的申报号");
            return  ajaxJson;
        }
        //是否面签环节
        Map<String,Object> execution=executions.get(0);
        String curStepName=StringUtil.getString(execution.get("CUR_STEPNAMES"));
        if(!ExeDataService.ID_IDENTIFY_TASKNAME.equals(curStepName)&&!ExeDataService.ID_IDENTIFY_TASKNAME_MP.equals(curStepName)){
            ajaxJson.setCode("004");
            ajaxJson.setMsg("该办件不是面签环节");
            return ajaxJson;
        }
        //是否已经面签过
        String fullExeId=StringUtil.getString(execution.get("EXE_ID"));//完整的办件id
        boolean flag=isHavedSign(fullExeId,name,idNo);
        if(flag){
            ajaxJson.setCode("005");
            ajaxJson.setMsg("已经面签过，无需面签");
            judgeIsAllSignToChangeStatus(fullExeId);
            return  ajaxJson;
        }
        //是否为面签对象
        flag=isSignPeople(fullExeId,name,idNo);
        if(!flag){
            ajaxJson.setCode("005");
            ajaxJson.setMsg("不是面签人员");
            return  ajaxJson;
        }
        String token=getTokenForSign(fullExeId,name,idNo);
        ajaxJson.setCode("001");
        //String successInfo=String.format("是申报号为:%s的合法面签人员",fullExeId);
        ajaxJson.setMsg(token);
        return ajaxJson;
    }

    /**
     * 根据办件id判断是否全部面签，并把面签状态置为办件已面签状态
     * @param exeId
     */
    public void judgeIsAllSignToChangeStatus(String exeId) {
        int all=getSignPeopleByTableName(exeId).size();
        int signed=findSignedListById(exeId).size();
        int remain=all-signed;
        //全部面签结束，把办件基本表状态置为已面签
        if(remain==0){
            List<Map<String,Object>> legalOfCompany=findLegalCompanyByexeId(exeId);
            //当面签未法定股东时，需要上传公司公章，才算面签通过。
            if(legalOfCompany==null||legalOfCompany.size()==0){
                changeSignStatus(exeId,"1");
            }else{
                dao.remove("T_COMMERCIAL_COMPANY_LEGALFILE",new String[]{"EXE_ID"},new Object[]{exeId});
                for(Map<String,Object> map:legalOfCompany){
                    map.put("UPLOAD_FLAG",0);
                    dao.saveOrUpdate(map,"T_COMMERCIAL_COMPANY_LEGALFILE","");
                }
            }
        }
    }

    /**
     * 获取token
     * @param exeId
     * @param name
     * @param idNo
     */
    private String getTokenForSign(String exeId,String name,String idNo){
        Map<String,Object> sign=new HashMap<String,Object>();
        sign.put("EXE_ID",exeId);
        sign.put("SIGN_NAME",name);
        sign.put("SIGN_IDNO",idNo);
        String token=UUID.randomUUID()+exeId;
        sign.put("TOKEN",token);
        dao.saveOrUpdate(sign,"T_COMMERCIAL_SIGN",null);
        return token;
    }

    /**
     *是否为面签对象
     * @param exeId
     * @param name
     * @param idNo
     * @return
     */
    private boolean isSignPeople(String exeId,String name,String idNo){
        Set<Map<String, Object>> peoples=getSignPeopleByTableName(exeId);
        String peopleJson=JSONObject.toJSONString(peoples);
        String signLog=String.format("身份认证信息exeId=%s , peopleJson=%s",exeId,peopleJson);
        log.info(signLog);
        Map<String,Object> people=new HashMap<String,Object>();
        people.put("NAME",name);
        people.put("IDNO",idNo);
        //身份证字母大小写
        Map<String,Object> people1=new HashMap<String,Object>();
        people1.put("NAME",name);
        people1.put("IDNO",idNo.toLowerCase());
        if(peoples.contains(people)){
            return true;
        }else if(peoples.contains(people1)){
            return true;
        }else{
            return false;
        }
    }
    /**
     * 判断是否已面签
     * @param exeId
     * @param name
     * @param idNo
     * @return
     */
    private boolean isHavedSign(String exeId,String name,String idNo){
        StringBuffer sql=new StringBuffer();
        sql.append("SELECT * FROM T_COMMERCIAL_SIGN S WHERE S.EXE_ID=? ");
        sql.append(" AND S.SIGN_NAME=? AND S.SIGN_IDNO=? AND S.SIGN_FLAG='1'");
        List<Map<String,Object>> wxSign=dao.findBySql(sql.toString(),new Object[]{exeId,name,idNo},null);
        if(wxSign!=null&&wxSign.size()>0){
            return true;
        }else{
            return false;
        }
    }
    /**
     *
     * @param beginTime
     * @return
     */
    @Override
    public List<Map<String, Object>> findExeIdOfCommercial(String beginTime) {
        //List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("SELECT R.EXE_ID FROM JBPM6_FLOW_RESULT  R ");
        sql.append("  LEFT JOIN JBPM6_EXECUTION E  ON R.EXE_ID=E.EXE_ID ");
        sql.append("  WHERE R.SOCIAL_FILEID IS NULL AND E.ITEM_CODE='201605170002XK00101' ");
        sql.append("  AND E.CREATE_TIME>? ");
        sql.append(" ORDER BY E.CREATE_TIME DESC ");
        return dao.findBySql(sql.toString(),new Object[]{beginTime},null);
    }

    /**
     * 根据表名获取需要面签的people
     * @param exeId
     * @return
     */
    @SuppressWarnings("unchecked")
    public Set<Map<String,Object>> getSignPeopleByTableName(String exeId){
        Map<String,Object> execution = dao.getByJdbc("JBPM6_EXECUTION",
                new String[]{"EXE_ID"},new Object[]{exeId});
        String busTableName=String.valueOf(execution.get("BUS_TABLENAME"));
        Set<Map<String,Object>>  peoples=new HashSet<Map<String,Object>>();
        if("T_COMMERCIAL_DOMESTIC".equals(busTableName)||"T_COMMERCIAL_FOREIGN".equals(busTableName)){
            peoples=getPeoplesByCompany(exeId);
        }else if("t_commercial_branch".equals(busTableName.toLowerCase())){

        }else if("t_commercial_individual".equals(busTableName.toLowerCase())){
            /**
             * @author Rider Chen
             * 2020年12月1日 16:36:26  新增个体面签人员信息
             */
            peoples=getPeoplesByIndividual(exeId);
        }else if("t_commercial_solelyinvest".equals(busTableName.toLowerCase())){

        }else if("t_commercial_nz_jointventure".equals(busTableName.toLowerCase())){
            peoples=getPeoplesByHhCompany(exeId);
        }else if("T_COMMERCIAL_CANCEL".equals(busTableName.toUpperCase())){
            //2021年3月30日 下午6:33:52  新增商事注销登记面签人员信息
            peoples=getPeoplesByCancel(exeId);
        }else if("T_COMMERCIAL_SSNZQYBA".equals(busTableName.toUpperCase())){
            //2021年4月9日 上午9:21:18 新增商事内资企业备案登记面签人员信息
            peoples=getPeoplesBySsnzqyba(exeId);
        }else if("T_COMMERCIAL_CHANGE_DOMESTIC".equals(busTableName.toUpperCase())){
            //2021年4月29日 上午9:46:53 新增商事内资企业变更登记面签人员信息
            peoples=getPeoplesByChangeDomestic(exeId);
        }
        return peoples;
    }
    
    /**
     * 
     * 描述   获取商事内资企业变更登记面签人员信息
     * @author Danto Huang
     * @created 2021年4月29日 上午9:47:19
     * @param exeId
     * @return
     */
    private Set<Map<String,Object>> getPeoplesByChangeDomestic(String exeId){
        Set<Map<String, Object>> pepoles = new HashSet<Map<String, Object>>();
        Map<String, Object> buscordMap = getBuscordMap(exeId);
        // 经办人
        Map<String, Object> pepole = new HashMap<String, Object>();
        String OPERATOR_NAME = StringUtil.getString(buscordMap.get("OPERATOR_NAME"));
        String OPERATOR_IDNO = StringUtil.getString(buscordMap.get("OPERATOR_IDNO"));
        pepole.put("NAME", OPERATOR_NAME);
        pepole.put("IDNO", OPERATOR_IDNO);
        pepoles.add(pepole);
        //法定代表人
        pepole = new HashMap<String, Object>();
        if(buscordMap.get("CHANGE_OPTIONS").toString().contains("03")){
            String LEGAL_NAME_CHANGE = StringUtil.getString(buscordMap.get("LEGAL_NAME_CHANGE"));
            String LEGAL_IDNO_CHANGE = StringUtil.getString(buscordMap.get("LEGAL_IDNO_CHANGE"));
            pepole.put("NAME", LEGAL_NAME_CHANGE);
            pepole.put("IDNO", LEGAL_IDNO_CHANGE);
            pepoles.add(pepole);
        }else{
            String LEGAL_NAME = StringUtil.getString(buscordMap.get("LEGAL_NAME"));
            String LEGAL_IDNO = StringUtil.getString(buscordMap.get("LEGAL_IDNO"));
            pepole.put("NAME", LEGAL_NAME);
            pepole.put("IDNO", LEGAL_IDNO);
            pepoles.add(pepole);
        }
        //联络员
        pepole = new HashMap<String, Object>();
        if("1".equals(buscordMap.get("IS_LIAISON_CHANGE"))){
            String LIAISON_NAME = StringUtil.getString(buscordMap.get("LIAISON_NAME"));
            String LIAISON_IDNO = StringUtil.getString(buscordMap.get("LIAISON_IDNO"));
            pepole.put("NAME", LIAISON_NAME);
            pepole.put("IDNO", LIAISON_IDNO);
            pepoles.add(pepole);
        }
        commercialSetService.setHolderCzList(buscordMap);
        //判断是否生成原股东决定/股东会决议材料
        boolean isOldGdFlag = commercialSetService.isOldGdMater(buscordMap);
        if(isOldGdFlag){
            pepoles.addAll(getPeopleByJson(buscordMap, "HOLDER_JSON",
                    "SHAREHOLDER_NAME", "LICENCE_NO", "LICENCE_TYPE"));
        }
        //判断是否生成新股东决定/会议  (依据材料列表生成规则)
        boolean isNewGdFlag = commercialSetService.isNewGdMater(buscordMap);
        if(isNewGdFlag){
            String HOLDER_JSON_CHANGE = buscordMap.get("HOLDER_JSON_CHANGE") == null ? ""
                    : buscordMap.get("HOLDER_JSON_CHANGE").toString();
            if (StringUtils.isNotEmpty(HOLDER_JSON_CHANGE)) {
                pepoles.addAll(getPeopleByJson(buscordMap, "HOLDER_JSON_CHANGE", "SHAREHOLDER_NAME", "LICENCE_NO",
                        "LICENCE_TYPE"));
            }else{//若无新股东信息，则用旧股东信息签字
                pepoles.addAll(getPeopleByJson(buscordMap, "HOLDER_JSON", 
                        "SHAREHOLDER_NAME", "LICENCE_NO", "LICENCE_TYPE"));
            } 
        }
        
        
        //新董事(新公司设董事会，就是新公司的董事会、原公司设董事会，新公司设执行董事，则由原公司董事会成员来签字)
        boolean exDirectorBoard = false;//董事会
        List<Map<String, Object>> directorOldList = (List<Map<String, Object>>)JSON.parse(
                StringUtil.getString(buscordMap.get("DIRECTOR_JSON")));
        for (Map<String, Object> map : directorOldList) {
            if("01".equals(StringUtil.getString(map.get("DIRECTOR_JOB_OLD")))){//董事长
                exDirectorBoard = true;
            }
        }
        //boolean newDirector = false;//执行董事
        boolean newDirectorBoard = false;//董事会
        List<Map<String, Object>> directorChangeList =  (List<Map<String, Object>>) JSON
                .parse(StringUtil.getString(buscordMap.get("DIRECTOR_JSON_CHANGE")));
        if(directorChangeList!=null){
            for (Map<String, Object> map : directorChangeList) {
                /*if("30".equals(StringUtil.getString(map.get("DIRECTOR_JOB")))){//执行董事
                    newDirector = true;
                }*/
                if("01".equals(StringUtil.getString(map.get("DIRECTOR_JOB")))){//董事长
                    newDirectorBoard = true;
                }
            } 
        }
        //判断是否生成董事会决议
        boolean isDshFlag = commercialSetService.isDshMater(buscordMap);
        if(isDshFlag){
            if(exDirectorBoard==true && newDirectorBoard!=true){//原公司设董事会，新公司不设立董事会
                pepoles.addAll(getPeopleByJson(buscordMap, "DIRECTOR_JSON", "DIRECTOR_NAME_OLD", "DIRECTOR_IDNO_OLD",
                        "DIRECTOR_IDTYPE_OLD"));
            }else if(newDirectorBoard==true){
                if (directorChangeList!=null && directorChangeList.size()>0) {
                    pepoles.addAll(getPeopleByJson(buscordMap, "DIRECTOR_JSON_CHANGE", "DIRECTOR_NAME", "DIRECTOR_IDNO",
                            "DIRECTOR_IDTYPE"));
                }
            } 
        }
        
        
        //新监事
        String SUPERVISOR_JSON_CHANGE = buscordMap.get("SUPERVISOR_JSON_CHANGE") == null ? ""
                : buscordMap.get("SUPERVISOR_JSON_CHANGE").toString();
        if (StringUtils.isNotEmpty(SUPERVISOR_JSON_CHANGE)) {
            pepoles.addAll(getPeopleByJson(buscordMap, "SUPERVISOR_JSON_CHANGE", "SUPERVISOR_NAME", "SUPERVISOR_IDNO",
                    "SUPERVISOR_IDTYPE"));
        }
        //新经理
        String MANAGER_JSON_CHANGE = buscordMap.get("MANAGER_JSON_CHANGE") == null ? ""
                : buscordMap.get("MANAGER_JSON_CHANGE").toString();
        if (StringUtils.isNotEmpty(MANAGER_JSON_CHANGE)) {
            pepoles.addAll(getPeopleByJson(buscordMap, "MANAGER_JSON_CHANGE", "MANAGER_NAME", "MANAGER_IDNO",
                    "MANAGER_IDTYPE"));

        }
        return pepoles;
    }

    /**
     * 
     * 描述 获取商事内资企业备案登记面签人员
     * 
     * @author Rider Chen
     * @created 2021年4月9日 上午9:21:18
     * @param exeId
     * @return
     */
    private Set<Map<String, Object>> getPeoplesBySsnzqyba(String exeId) {
        Set<Map<String, Object>> peoples = new HashSet<Map<String, Object>>();
        Map<String, Object> buscordMap = getBuscordMap(exeId);
        // 经办人
        Map<String, Object> people = new HashMap<String, Object>();
        String OPERATOR_NAME = StringUtil.getString(buscordMap.get("OPERATOR_NAME"));
        String OPERATOR_IDNO = StringUtil.getString(buscordMap.get("OPERATOR_IDNO"));
        people.put("NAME", OPERATOR_NAME);
        people.put("IDNO", OPERATOR_IDNO);
        peoples.add(people);
        // 法定代表人
        String LEGAL_NAME = StringUtil.getString(buscordMap.get("LEGAL_NAME"));
        String LEGAL_IDNO = StringUtil.getString(buscordMap.get("LEGAL_IDNO"));
        people = new HashMap<String, Object>();
        people.put("NAME", LEGAL_NAME);
        people.put("IDNO", LEGAL_IDNO);
        peoples.add(people);
        // 所有股东
        peoples.addAll(getPeopleByJson(buscordMap, "HOLDER_JSON", "SHAREHOLDER_NAME", "LICENCE_NO", "LICENCE_TYPE"));
        String DIRECTOR_JSON = buscordMap.get("DIRECTOR_JSON") == null ? ""
                : buscordMap.get("DIRECTOR_JSON").toString();
        if (StringUtils.isNotEmpty(DIRECTOR_JSON)) {
            // 所有董事
            peoples.addAll(
                    getPeopleByJson(buscordMap, "DIRECTOR_JSON", "DIRECTOR_NAME", "DIRECTOR_IDNO", "DIRECTOR_IDTYPE"));
        } 
        String OLD_DIRECTOR_JSON = buscordMap.get("OLD_DIRECTOR_JSON") == null ? ""
                : buscordMap.get("OLD_DIRECTOR_JSON").toString();
        if (StringUtils.isNotEmpty(OLD_DIRECTOR_JSON)) {
            // 所有旧董事
            peoples.addAll(
                    getPeopleByJson(buscordMap, "OLD_DIRECTOR_JSON", "DIRECTOR_NAME", "DIRECTOR_IDNO", "DIRECTOR_IDTYPE"));
        }
        String SUPERVISOR_JSON = buscordMap.get("SUPERVISOR_JSON") == null ? ""
                : buscordMap.get("SUPERVISOR_JSON").toString();
        if (StringUtils.isNotEmpty(SUPERVISOR_JSON)) {
            // 监理
            peoples.addAll(getPeopleByJson(buscordMap, "SUPERVISOR_JSON", "SUPERVISOR_NAME", "SUPERVISOR_IDNO",
                    "SUPERVISOR_IDTYPE"));
        }

        String MANAGER_JSON = buscordMap.get("MANAGER_JSON") == null ? "" : buscordMap.get("MANAGER_JSON").toString();
        if (StringUtils.isNotEmpty(MANAGER_JSON)) {
            // 经理
            peoples.addAll(
                    getPeopleByJson(buscordMap, "MANAGER_JSON", "MANAGER_NAME", "MANAGER_IDNO", "MANAGER_IDTYPE"));

        }
        return peoples;
    }
    /**
     * 
     * 描述 获取商事注销登记面签人员
     * 
     * @author Rider Chen
     * @created 2021年3月30日 下午6:33:52
     * @param exeId
     * @return
     */
    private Set<Map<String, Object>> getPeoplesByCancel(String exeId) {
        Set<Map<String, Object>> peoples = new HashSet<Map<String, Object>>();
        Map<String, Object> buscordMap = getBuscordMap(exeId);     
        //经办人
        Map<String, Object> people = new HashMap<String, Object>();
        String OPERATOR_NAME = StringUtil.getString(buscordMap.get("OPERATOR_NAME"));
        String OPERATOR_IDNO = StringUtil.getString(buscordMap.get("OPERATOR_IDNO"));
        people.put("NAME", OPERATOR_NAME);
        people.put("IDNO", OPERATOR_IDNO);
        peoples.add(people);
        // 法定代表人
        String LEGAL_NAME = StringUtil.getString(buscordMap.get("LEGAL_NAME"));
        String LEGAL_IDNO = StringUtil.getString(buscordMap.get("LEGAL_IDNO"));        
        people = new HashMap<String, Object>();
        people.put("NAME", LEGAL_NAME);
        people.put("IDNO", LEGAL_IDNO);
        peoples.add(people);

        String CANCEL_TYPE = StringUtil.getString(buscordMap.get("CANCEL_TYPE"));
        //一般注销才面签股东和清算组成员
        if(StringUtils.isNotEmpty(CANCEL_TYPE) && CANCEL_TYPE.equals("SSYBZX")){
            //所有股东
            peoples.addAll(getPeopleByJson(buscordMap,"HOLDER_JSON","SHAREHOLDER_NAME",
                    "LICENCE_NO","LICENCE_TYPE"));
            //所有清算组成员
            peoples.addAll(getPeopleByJson(buscordMap,"QSZCY_JSON","QSZ_NAME",
                    "QSZ_IDNO","QSZ_IDTYPE"));
        }
        return peoples;
    }
    /**
     * 
     * 描述： 获取个体商户表单面签人员
     * @author Rider Chen
     * @created 2020年12月1日 下午16:34:26 
     * @param exeId
     * @return
     */
    private Set<Map<String,Object>>  getPeoplesByIndividual(String exeId){
        Set<Map<String,Object>> peoples=new HashSet<Map<String, Object>>();
        Map<String,Object> buscordMap=getBuscordMap(exeId);
        //法定代表人
        Map<String,Object> people=new HashMap<String,Object>();
        people.put("NAME", StringUtil.getString(buscordMap.get("DEALER_NAME")));
        people.put("IDNO", StringUtil.getString(buscordMap.get("DEALER_IDCARD")));
        peoples.add(people);
        return peoples;
    }
    /**
     * 获取表名t_commercial_company的面签人员
     */
    private Set<Map<String,Object>>  getPeoplesByCompany(String exeId){
        Set<Map<String,Object>> peoples=new HashSet<Map<String, Object>>();
        Map<String,Object> buscordMap=getBuscordMap(exeId);
        //法定代表人
        Map<String,Object> people=new HashMap<String,Object>();
        people.put("NAME", StringUtil.getString(buscordMap.get("LEGAL_NAME")));
        people.put("IDNO", StringUtil.getString(buscordMap.get("LEGAL_IDNO")).toUpperCase());
        peoples.add(people);
        //所有股东
        peoples.addAll(getPeopleByJson(buscordMap,"HOLDER_JSON","SHAREHOLDER_NAME",
                "LICENCE_NO","LICENCE_TYPE"));
        //所有董事
        peoples.addAll(getPeopleByJson(buscordMap,"DIRECTOR_JSON","DIRECTOR_NAME",
                "DIRECTOR_IDNO","DIRECTOR_IDTYPE"));
        //监理
        peoples.addAll(getPeopleByJson(buscordMap,"SUPERVISOR_JSON","SUPERVISOR_NAME",
                "SUPERVISOR_IDNO","SUPERVISOR_IDTYPE"));
        //经理
        peoples.addAll(getPeopleByJson(buscordMap,"MANAGER_JSON","MANAGER_NAME",
                "MANAGER_IDNO","MANAGER_IDTYPE"));
        //代理人
        Map<String,Object> operator=new HashMap<String,Object>();
        operator.put("NAME", StringUtil.getString(buscordMap.get("OPERATOR_NAME")));
        operator.put("IDNO", StringUtil.getString(buscordMap.get("OPERATOR_IDNO")).toUpperCase());
        peoples.add(operator);
        //财务负责人
        Map<String,Object> finance=new HashMap<String,Object>();
        finance.put("NAME", StringUtil.getString(buscordMap.get("FINANCE_NAME")));
        finance.put("IDNO", StringUtil.getString(buscordMap.get("FINANCE_IDNO")).toUpperCase());
        peoples.add(finance);
        //联络员
        Map<String,Object> liaison=new HashMap<String,Object>();
        liaison.put("NAME", StringUtil.getString(buscordMap.get("LIAISON_NAME")));
        liaison.put("IDNO", StringUtil.getString(buscordMap.get("LIAISON_IDNO")).toUpperCase());
        peoples.add(liaison);
        return peoples;
    }
    /**
     * 获取表名t_commercial_nz_jointventure的面签人员
     */
    private Set<Map<String,Object>>  getPeoplesByHhCompany(String exeId){
        Set<Map<String,Object>> peoples=new HashSet<Map<String, Object>>();
        Map<String,Object> buscordMap=getBuscordMap(exeId);
        //所有合伙人
        peoples.addAll(getHhPeopleByJson(buscordMap,"HOLDER_JSON","SHAREHOLDER_NAME",
                "LICENCE_NO","LICENCE_TYPE"));
        //代理人
        Map<String,Object> operator=new HashMap<String,Object>();
        operator.put("NAME", StringUtil.getString(buscordMap.get("OPERATOR_NAME")));
        operator.put("IDNO", StringUtil.getString(buscordMap.get("OPERATOR_IDNO")).toUpperCase());
        peoples.add(operator);
        //财务负责人
        Map<String,Object> finance=new HashMap<String,Object>();
        finance.put("NAME", StringUtil.getString(buscordMap.get("FINANCE_NAME")));
        finance.put("IDNO", StringUtil.getString(buscordMap.get("FINANCE_IDNO")).toUpperCase());
        peoples.add(finance);
        //联络员
        Map<String,Object> liaison=new HashMap<String,Object>();
        liaison.put("NAME", StringUtil.getString(buscordMap.get("LIAISON_NAME")));
        liaison.put("IDNO", StringUtil.getString(buscordMap.get("LIAISON_IDNO")).toUpperCase());
        peoples.add(liaison);

        return peoples;
    }
    /**
     * 根据表名获取需要面签的people
     * @param exeId
     * @return
     */
    public Set<Map<String,Object>> findReceiverMobileByTableName(String exeId){
        Map<String,Object> execution = dao.getByJdbc("JBPM6_EXECUTION",
                new String[]{"EXE_ID"},new Object[]{exeId});
        String busTableName=String.valueOf(execution.get("BUS_TABLENAME"));
        Set<Map<String,Object>>  peoples=new HashSet<Map<String,Object>>();
        if("T_COMMERCIAL_DOMESTIC".equals(busTableName)||"T_COMMERCIAL_FOREIGN".equals(busTableName)){
            peoples=findReceiverMobileByCompany(exeId);
            peoples=removeSignedPeople(exeId,peoples);
        }else if("t_commercial_branch".equals(busTableName)){

        }else if("t_commercial_individual".equals(busTableName)){

        }else if("t_commercial_solelyinvest".equals(busTableName)){

        }
        return peoples;
    }

    /**
     *已经面签的人员不需要面签
     * @param exeId
     * @param peoples
     * @return
     */
    private Set<Map<String,Object>> removeSignedPeople(String exeId,Set<Map<String,Object>> peoples){
        Iterator<Map<String,Object>> it=peoples.iterator();
        //获取已经面签的人员
        StringBuffer sql=new StringBuffer("SELECT * ");
        sql.append(" FROM t_commercial_sign S ");
        sql.append("  where s.exe_id=?   ");
        sql.append(" and s.sign_idno=?  and s.sign_flag='1' ");
        for(int i=0;i<peoples.size();i++){
            Map<String,Object> people=it.next();
            String idNo=StringUtil.getString(people.get("IDNO"));
            Map<String,Object> signed=dao.getByJdbc(sql.toString(),new Object[]{exeId,idNo});
            //如果已面签，不需要再面签
            if(!CollectionUtils.isEmpty(signed)){
                it.remove();
                i--;
            }
        }

        return peoples;
    }
    /**
     * 获取表名t_commercial_company的面签人员手机号信息
     */
    private Set<Map<String,Object>>  findReceiverMobileByCompany(String exeId){
        Set<Map<String,Object>> peoples=new HashSet<Map<String, Object>>();
        Map<String,Object> buscordMap=getBuscordMap(exeId);
        //法定代表人
        Map<String,Object> people=new HashMap<String,Object>();
        people.put("NAME", StringUtil.getString(buscordMap.get("LEGAL_NAME")));
        people.put("IDNO", StringUtil.getString(buscordMap.get("LEGAL_IDNO")).toUpperCase());
        people.put("MOBILE", StringUtil.getString(buscordMap.get("LEGAL_MOBILE")));
        peoples.add(people);
        //所有股东
        peoples.addAll(getPeopleMobileByJson(buscordMap,"HOLDER_JSON","SHAREHOLDER_NAME",
                "LICENCE_NO","LICENCE_TYPE","CONTACT_WAY"));
        //所有董事
        peoples.addAll(getPeopleMobileByJson(buscordMap,"DIRECTOR_JSON","DIRECTOR_NAME",
                "DIRECTOR_IDNO","DIRECTOR_IDTYPE","DIRECTOR_PHONE"));
        //监理
        peoples.addAll(getPeopleMobileByJson(buscordMap,"SUPERVISOR_JSON","SUPERVISOR_NAME",
                "SUPERVISOR_IDNO","SUPERVISOR_IDTYPE","SUPERVISOR_PHONE"));
        //经理
        peoples.addAll(getPeopleMobileByJson(buscordMap,"MANAGER_JSON","MANAGER_NAME",
                "MANAGER_IDNO","MANAGER_IDTYPE","MANAGER_PHONE"));
        //代理人
        Map<String,Object> operator=new HashMap<String,Object>();
        operator.put("NAME", StringUtil.getString(buscordMap.get("OPERATOR_NAME")));
        operator.put("IDNO", StringUtil.getString(buscordMap.get("OPERATOR_IDNO")).toUpperCase());
        operator.put("MOBILE", StringUtil.getString(buscordMap.get("OPERATOR_MOBILE")));
        peoples.add(operator);
        //财务负责人
        Map<String,Object> finance=new HashMap<String,Object>();
        finance.put("NAME", StringUtil.getString(buscordMap.get("FINANCE_NAME")));
        finance.put("IDNO", StringUtil.getString(buscordMap.get("FINANCE_IDNO")).toUpperCase());
        finance.put("MOBILE", StringUtil.getString(buscordMap.get("FINANCE_MOBILE")));
        peoples.add(finance);
        //联络员
        Map<String,Object> liaison=new HashMap<String,Object>();
        liaison.put("NAME", StringUtil.getString(buscordMap.get("LIAISON_NAME")));
        liaison.put("IDNO", StringUtil.getString(buscordMap.get("LIAISON_IDNO")).toUpperCase());
        liaison.put("MOBILE", StringUtil.getString(buscordMap.get("LIAISON_MOBILE")));
        peoples.add(liaison);
        return peoples;
    }
    /**
     * json字段获取相关姓名id
     * @param field
     * @param name
     * @param id
     * @return
     */
    private List<Map<String,Object>> getPeopleByJson(Map<String,Object> buscordMap,String field,
                                                     String name,String id,String idType){
        List<Map<String,Object>> peoples=new ArrayList<Map<String, Object>>();
        List<Map<String,Object>> list=( List<Map<String,Object>>) JSON.parse
                (StringUtil.getString(buscordMap.get(field)));
        for(Map<String,Object> map:list){
            //证件号码是身份证的，才面签
            if(StringUtils.isEmpty(idType)||"SF".equals(map.get(idType))) {
                Map<String, Object> people = new HashMap<String, Object>();
                people.put("NAME", StringUtil.getString(map.get(name)));
                people.put("IDNO", StringUtil.getString(map.get(id)).toUpperCase());
                peoples.add(people);
            }else if("HOLDER_JSON".equals(field)&&!"SF".equals(map.get(idType))){ //当股东为公司时，面签法人
                Map<String, Object> people = new HashMap<String, Object>();
                String legalName=StringUtil.getString(map.get("LEGAL_PERSON"));
                people.put("NAME", legalName);
                people.put("IDNO", StringUtil.getString(map.get("LEGAL_IDNO_PERSON")).toUpperCase());
                if(StringUtils.isNotEmpty(legalName) && 
                        StringUtils.isNotEmpty(StringUtil.getString(map.get("LEGAL_IDNO_PERSON")).toUpperCase())){
                    peoples.add(people);
                }
                
            }else if("HOLDER_JSON_CHANGE".equals(field)&&!"SF".equals(map.get(idType))){ //当股东为公司时，面签法人
                Map<String, Object> people = new HashMap<String, Object>();
                String legalName=StringUtil.getString(map.get("LEGAL_PERSON"));
                people.put("NAME", legalName);
                people.put("IDNO", StringUtil.getString(map.get("LEGAL_IDNO_PERSON")).toUpperCase());
                if(StringUtils.isNotEmpty(legalName) && 
                        StringUtils.isNotEmpty(StringUtil.getString(map.get("LEGAL_IDNO_PERSON")).toUpperCase())){
                    peoples.add(people);
                }
                
            }
        }
        return peoples;
    }
    /**
     * json字段获取相关姓名id
     * @param field
     * @param name
     * @param id
     * @return
     */
    private List<Map<String,Object>> getHhPeopleByJson(Map<String,Object> buscordMap,String field,
                                                     String name,String id,String idType){
        List<Map<String,Object>> peoples=new ArrayList<Map<String, Object>>();
        List<Map<String,Object>> list=( List<Map<String,Object>>) JSON.parse
                (StringUtil.getString(buscordMap.get(field)));
        for(Map<String,Object> map:list){
            //证件号码是身份证的，才面签
            if(StringUtils.isEmpty(idType)||"SF".equals(map.get(idType))) {
                Map<String, Object> people = new HashMap<String, Object>();
                people.put("NAME", StringUtil.getString(map.get(name)));
                people.put("IDNO", StringUtil.getString(map.get(id)).toUpperCase());
                peoples.add(people);
            }else if("HOLDER_JSON".equals(field)&&!"SF".equals(map.get(idType))){ //当股东为公司时，面签法人
                String isPartnership=StringUtil.getString(map.get("IS_PARTNERSHIP"));
                if(Objects.equals("1",isPartnership)){
                    //委派
                    Map<String, Object> people = new HashMap<String, Object>();
                    String legalName=StringUtil.getString(map.get("LICENCE_APPOINT_NAME"));
                    people.put("NAME", legalName);
                    people.put("IDNO", StringUtil.getString(map.get("LICENCE_APPOINT_IDNO")).toUpperCase());
                    peoples.add(people);
                }
                //法人
                Map<String, Object> people1 = new HashMap<String, Object>();
                String legalName1=StringUtil.getString(map.get("LEGAL_PERSON"));
                people1.put("NAME", legalName1);
                people1.put("IDNO", StringUtil.getString(map.get("LEGAL_IDNO_PERSON")).toUpperCase());
                peoples.add(people1);
            }
        }
        return peoples;
    }
    /**
     * 合伙执行事务合伙人
     * @param exeId
     */
    @Override
    public Map<String, Object> getHhLegal(String exeId) {
        Map<String, Object> legal = new HashMap<>();
        Map<String, Object> busMap = getExeAndBuscordMap(exeId);
        String busTableName = StringUtil.getString(busMap.get("BUS_TABLENAME"));
        if (Objects.equals(busTableName, TableNameEnum.T_COMMERCIAL_NZ_JOINTVENTURE.getVal())) {
            List<Map<String, Object>> list = (List<Map<String, Object>>) JSON.parse
                    (StringUtil.getString(busMap.get("HOLDER_JSON")));
            boolean legalFlag = false;
            for (int i = 0; i < list.size(); i++) {
                Map<String, Object> map = list.get(i);
                String isPartnership = StringUtil.getString(map.get("IS_PARTNERSHIP"));
                if (Objects.equals("1", isPartnership)) {
                    String idType = StringUtil.getString(map.get("SHAREHOLDER_TYPE"));
                    //证件号码是身份证的，才获得
                    if (Objects.equals(idType,"zrr")) {
                        legal.put("LEGAL_MOBILE", Optional.ofNullable(map.get("CONTACT_WAY")).orElse(""));
                        legal.put("LEGAL_IDNO", Optional.ofNullable(map.get("LICENCE_NO")).orElse(""));
                        legal.put("LEGAL_NAME", Optional.ofNullable(map.get("SHAREHOLDER_NAME")).orElse(""));
                        legal.put("LEGAL_IDTYPE","SF");
                    } /*else {
                        legal.put("LEGAL_MOBILE", Optional.ofNullable(map.get("LEGAL_MOBILE_PERSON")).orElse(""));
                        legal.put("LEGAL_IDNO", Optional.ofNullable(map.get("LEGAL_IDNO_PERSON")).orElse(""));
                        legal.put("LEGAL_NAME", Optional.ofNullable(map.get("LEGAL_PERSON")).orElse(""));
                        legal.put("LEGAL_IDTYPE","SF");
                    }*/else{
                        legal.put("LEGAL_NAME", Optional.ofNullable(map.get("LICENCE_APPOINT_NAME")).orElse(""));
                        legal.put("LEGAL_IDNO", Optional.ofNullable(map.get("LICENCE_APPOINT_IDNO")).orElse(""));
                        legal.put("LEGAL_MOBILE", Optional.ofNullable(map.get("CONTACT_WAY")).orElse(""));
                        legal.put("LEGAL_IDTYPE","SF");
                    }
                    legalFlag = true;
                }
            }
            if(!legalFlag){
                legal.put("LEGAL_NAME", "");
                legal.put("LEGAL_IDNO", "");
                legal.put("LEGAL_MOBILE", "");
            }
        }
        return legal;
    }
    /**
     * json字段获取相关姓名id
     * @param field
     * @param name
     * @param id
     * @return
     */
    private List<Map<String,Object>> getPeopleMobileByJson(Map<String,Object> buscordMap,String field,
                                                     String name,String id,String idType,String mobileField){
        List<Map<String,Object>> peoples=new ArrayList<Map<String, Object>>();
        List<Map<String,Object>> list=( List<Map<String,Object>>) JSON.parse
                (StringUtil.getString(buscordMap.get(field)));
        for(Map<String,Object> map:list){
            //证件号码是身份证的，才面签
            if(StringUtils.isEmpty(idType)||"SF".equals(map.get(idType))) {
                Map<String, Object> people = new HashMap<String, Object>();
                people.put("NAME", StringUtil.getString(map.get(name)));
                people.put("IDNO", StringUtil.getString(map.get(id)).toUpperCase());
                people.put("MOBILE", StringUtil.getString(map.get(mobileField)));
                peoples.add(people);
            }else if("HOLDER_JSON".equals(field)&&!"SF".equals(map.get(idType))){ //当股东为公司时，面签法人
                Map<String, Object> people = new HashMap<String, Object>();
                String legalName=StringUtil.getString(map.get("LEGAL_PERSON"));
                people.put("NAME", legalName);
                people.put("IDNO", StringUtil.getString(map.get("LEGAL_IDNO_PERSON")).toUpperCase());
                peoples.add(people);
            }
        }
        return peoples;
    }
    /**
     * 根据事项
     * @param itemCode
     * @param materFilter
     * @return
     */
    public List<Map<String,Object>>  findMaterFileByItemCode(String itemCode,String materFilter){
        Map<String,Object> item=dao.getByJdbc("T_WSBS_SERVICEITEM",
                new String[]{"ITEM_CODE"},new Object[]{itemCode});
        String itemId = "";
        if (item != null) {
            itemId=StringUtil.getString(item.get("ITEM_ID"));
        }
        StringBuffer sql=new StringBuffer("SELECT SM.MATER_SN,M.MATER_SSYW,SM.MATER_FPARA,SM.MATER_ISNEED,M.*");
        sql.append("  FROM T_WSBS_APPLYMATER M ");
        sql.append(" LEFT JOIN T_WSBS_SERVICEITEM_MATER SM ON M.MATER_ID=SM.MATER_ID");
        sql.append(" LEFT JOIN T_WSBS_SERVICEITEM_BUSCLASS SB ON M.MATER_SSYW = SB.RECORD_ID ");
        sql.append("   WHERE SM.ITEM_ID=? and m.mater_filter=? ");
        sql.append("    ORDER BY SM.MATER_SN ASC  ");
        return dao.findBySql(sql.toString(),new Object[]{itemId,materFilter},null);
    }
    /**
     * json字段获取相关已经面签信息 用来处理list类型的json
     * @param buscordMap
     * @param field  json字段
     * @param name  姓名字段
     * @param id  证件号码字段
     * @param idType 证件类型字段
     * @param colName 进行模板替换的值
     */
    private void getSignedPeopleOfJson(Map<String,Object> buscordMap,String field,
                                                     String name,String id,String idType,String colName){
        String exeId=StringUtil.getString(buscordMap.get("EXE_ID"));
        List<Map<String,Object>> list=( List<Map<String,Object>>) JSON.parse
                (StringUtil.getString(buscordMap.get(field)));
        for(int i=0;i<list.size();i++){
            Map<String,Object> map=list.get(i);
            //证件号码是身份证的，才获得
            if(StringUtils.isEmpty(idType)||"SF".equals(map.get(idType))) {
                String jsonName=StringUtil.getString(map.get(name));
                String jsonIdNo=StringUtil.getString(map.get(id));
                buscordMap.putAll(setSignImgByParams(exeId,jsonName,jsonIdNo,colName+i));
            }else if("HOLDER_JSON".equals(field)&&!"SF".equals(map.get(idType))){ //当股东为公司时，面签法人
                String jsonName=StringUtil.getString(map.get("LEGAL_PERSON"));
                String jsonIdNo=StringUtil.getString(map.get("LEGAL_IDNO_PERSON"));
                buscordMap.putAll(setSignImgByParams(exeId,jsonName,jsonIdNo,colName+i));
            }
        }
        //其余置空
        for(int i=list.size();i<10;i++){
            buscordMap.put(colName+i,"");
        }
    }
    /**
     * json字段获取相关已经面签信息 用来处理list类型的json
     * @param buscordMap
     * @param field  json字段
     * @param name  姓名字段
     * @param id  证件号码字段
     * @param idType 证件类型字段
     * @param colName 进行模板替换的值
     */
    private void setLicenseByUploadFile(Map<String,Object> buscordMap,String field,
                                       String name,String id,String idType,String colName){
        //初始化先全部置为空
        for(int i=0;i<10;i++){
            buscordMap.put(colName+i,"");
        }
        String exeId=StringUtil.getString(buscordMap.get("EXE_ID"));
        List<Map<String,Object>> list=( List<Map<String,Object>>) JSON.parse
                (StringUtil.getString(buscordMap.get(field)));
        for(int i=0;i<list.size();i++){
            Map<String, Object> map = list.get(i);
            if(!"SF".equals(map.get(idType))) {
                String jsonName = StringUtil.getString(map.get(name));
                String jsonIdNo = StringUtil.getString(map.get(id));
                buscordMap.putAll(setSignLegalImgByParams(exeId, jsonName, jsonIdNo, colName + i));
            }
        }
    }
    /**
     * 设置委托人信息,仅执行事务合伙人为企业的时候填写。
     * @param buscordMap
     * @param field  json字段
     */
    private void setPartTrust(Map<String,Object> buscordMap,String field ){
        String exeId=StringUtil.getString(buscordMap.get("EXE_ID"));
        List<Map<String,Object>> list=( List<Map<String,Object>>) JSON.parse
                (StringUtil.getString(buscordMap.get(field)));
        //先初始化为空
        buscordMap.put("TRUST_ENTERNAME","");
        buscordMap.put("TRUST_2ENTERNAME","");
        buscordMap.put("TRUST_ENTERAPPOINTN","");

        buscordMap.put("LEGALPERSON_WRITE","");
        buscordMap.put("LEGALPERSON_FPHOTO","");
        buscordMap.put("LEGALPERSON_BPHOTO","");

        buscordMap.put("TRUST_WRITE","");
        buscordMap.put("TRUST_FPHOTO","");
        buscordMap.put("TRUST_BPHOTO","");
        for(int i=0;i<list.size();i++){
            Map<String,Object> map=list.get(i);
            String isPartnership=StringUtil.getString(map.get("IS_PARTNERSHIP"));
            if (Objects.equals("1", isPartnership)) {
                //当股东为公司时，委托人信息回填
                if (!"zrr".equals(map.get("SHAREHOLDER_TYPE"))) {
                    buscordMap.put("TRUST_ENTERNAME",StringUtil.getString(buscordMap.get("COMPANY_NAME")));
                    buscordMap.put("TRUST_2ENTERNAME",StringUtil.getString(map.get("SHAREHOLDER_NAME")));
                    buscordMap.put("TRUST_ENTERAPPOINTN",StringUtil.getString(map.get("LICENCE_APPOINT_NAME")));
                    String jsonName=StringUtil.getString(map.get("LEGAL_PERSON"));
                    String jsonIdNo=StringUtil.getString(map.get("LEGAL_IDNO_PERSON"));
                    //执行事务合伙人法人
                    buscordMap.putAll(setSignImgByParams(exeId, jsonName, jsonIdNo,"LEGALPERSON_WRITE"));
                    buscordMap.putAll(setSignImgByParams(exeId, jsonName, jsonIdNo,"LEGALPERSON_FPHOTO"));
                    buscordMap.putAll(setSignImgByParams(exeId, jsonName, jsonIdNo,"LEGALPERSON_BPHOTO"));
                    //委托人
                    String trustEnterappointn = StringUtil.getString(map.get("LICENCE_APPOINT_NAME"));
                    String trustEnterappointIdno=StringUtil.getString(map.get("LICENCE_APPOINT_IDNO"));
                    buscordMap.putAll(setSignImgByParams(exeId, trustEnterappointn, trustEnterappointIdno,"TRUST_WRITE"));
                    buscordMap.putAll(setSignImgByParams(exeId, trustEnterappointn, trustEnterappointIdno,"TRUST_FPHOTO"));
                    buscordMap.putAll(setSignImgByParams(exeId, trustEnterappointn, trustEnterappointIdno,"TRUST_BPHOTO"));
                }else{
                    buscordMap.put("TRUST_2ENTERNAME",StringUtil.getString(map.get("SHAREHOLDER_NAME")));
                }
            }
        }
    }
    /**
     * 根据exeId获取业务表信息
     * @param exeId
     * @return
     */
    public Map<String, Object> getBuscordMap(String exeId) {
        Map<String,Object> execution = dao.getByJdbc("JBPM6_EXECUTION",
                new String[]{"EXE_ID"},new Object[]{exeId});
        String busTableName = String.valueOf(execution.get("BUS_TABLENAME"));
        //内资表单，将虚拟主表名替换真实主表名称
        if(busTableName.equals("T_COMMERCIAL_DOMESTIC")){
            busTableName = "T_COMMERCIAL_COMPANY";
        }
        //内资表单，将虚拟主表名替换真实主表名称
        if(busTableName.equals("T_COMMERCIAL_FOREIGN")){
            busTableName = "T_COMMERCIAL_COMPANY";
        }
        //国有转移6个事项虚拟主表替换真实表名称
        if(busTableName.indexOf("T_BDCQLC_GYJSJFWZYDJ")>=0){
            busTableName = "T_BDCQLC_GYJSJFWZYDJ";
        }
        String busRecordId = (String) execution.get("BUS_RECORDID");//获取业务ID
        String busPkName = (String) dao.getPrimaryKeyName(busTableName).get(0);//获取业务表主键名称
        Map<String,Object> busMap = dao.getByJdbc(busTableName,
                new String[]{busPkName}, new Object[]{busRecordId});
        busMap.put("BUS_TABLENAME", busTableName);
        return busMap;
    }
    /**
     * 根据exeId获取办件和业务表信息
     * @param exeId
     * @return
     */
    public Map<String, Object> getExeAndBuscordMap(String exeId) {
        Map<String,Object> execution = dao.getByJdbc("JBPM6_EXECUTION",
                new String[]{"EXE_ID"},new Object[]{exeId});
        String busTableName=String.valueOf(execution.get("BUS_TABLENAME"));
        //内资表单，将虚拟主表名替换真实主表名称
        if(busTableName.equals("T_COMMERCIAL_DOMESTIC")){
            busTableName = "T_COMMERCIAL_COMPANY";
        }
        //内资表单，将虚拟主表名替换真实主表名称
        if(busTableName.equals("T_COMMERCIAL_FOREIGN")){
            busTableName = "T_COMMERCIAL_COMPANY";
        }
        //国有转移7个事项虚拟主表替换真实表名称
        if(busTableName.indexOf("T_BDCQLC_GYJSJFWZYDJ")>=0){
            busTableName = "T_BDCQLC_GYJSJFWZYDJ";
        }
        String busRecordId = (String) execution.get("BUS_RECORDID");//获取业务ID
        String busPkName = (String) dao.getPrimaryKeyName(busTableName).get(0);//获取业务表主键名称
        Map<String,Object> busMap=dao.getByJdbc(busTableName,
                new String[]{busPkName}, new Object[]{busRecordId});
        busMap.putAll(execution);
        return busMap;
    }
    /**
     * 根据exeId获取办件和业务表信息
     * @param exeId
     * @return
     */
    public Map<String, Object> getBuscordAndExeMap(String exeId) {
        Map<String,Object> execution = dao.getByJdbc("JBPM6_EXECUTION",
                new String[]{"EXE_ID"},new Object[]{exeId});
        String busTableName=String.valueOf(execution.get("BUS_TABLENAME"));
        //内资表单，将虚拟主表名替换真实主表名称
        if(busTableName.equals("T_COMMERCIAL_DOMESTIC")){
            busTableName = "T_COMMERCIAL_COMPANY";
        }
        //内资表单，将虚拟主表名替换真实主表名称
        if(busTableName.equals("T_COMMERCIAL_FOREIGN")){
            busTableName = "T_COMMERCIAL_COMPANY";
        }
        String busRecordId = (String) execution.get("BUS_RECORDID");//获取业务ID
        String busPkName = (String) dao.getPrimaryKeyName(busTableName).get(0);//获取业务表主键名称
        Map<String,Object> busMap=dao.getByJdbc(busTableName,
                new String[]{busPkName}, new Object[]{busRecordId});
        busMap.putAll(execution);
        return busMap;
    }
    /**
     * 根据exeId获取面签列表信息
     * @param exeId
     * @return
     */
    public List<Map<String,Object>> findSignListById(String exeId){
        Set<Map<String,Object>> allSignPeople=getSignPeopleByTableName(exeId);
        List<Map<String,Object>> signedPeople=findSignedListById(exeId);
        //去除已面签的
        for(Map<String,Object> map:signedPeople){
            HashMap<String,Object> people=new HashMap<String,Object>();
            people.put("NAME",map.get("SIGN_NAME"));
            people.put("IDNO",map.get("SIGN_IDNO"));
            if(allSignPeople.contains(people)){
                allSignPeople.remove(people);
            }
        }
        for(Map<String,Object> map:allSignPeople){
            String name=String.valueOf(map.get("NAME"));
            String idNo=String.valueOf(map.get("IDNO"));
            map.put("SIGN_NAME",map.get("NAME"));
            map.put("SIGN_IDNO",map.get("IDNO"));
            StringBuffer sql=new StringBuffer("SELECT * FROM T_COMMERCIAL_SIGN S ");
            sql.append(" WHERE S.EXE_ID=? ");
            sql.append("AND S.SIGN_FLAG='-1' AND S.SIGN_NAME=? AND S.SIGN_IDNO=? ");
            sql.append("ORDER BY S.SIGN_TIME DESC ");
            List<Map<String,Object>> sign=dao.findBySql(sql.toString(),
                    new Object[]{exeId,name,idNo},null);
            String opinion="";
            if(sign.size()>0){
                opinion=StringUtil.getString(sign.get(0).get("SIGN_OPINION"));
            }
            map.put("SIGN_OPINION",opinion);
            map.put("SIGNED_FLAG","0");
            signedPeople.add(map);
        }
        return signedPeople;
    }

    /**
     * 查找已经面签的人
     * @param exeId
     * @return
     */
    public List<Map<String,Object>> findSignedListById(String exeId){
        StringBuffer sql=new StringBuffer(" SELECT * FROM T_COMMERCIAL_SIGN S");
        sql.append(" WHERE S.EXE_ID=?  AND S.SIGN_FLAG='1' ORDER BY S.SIGN_TIME DESC ");
        List<Map<String,Object>> signedPeople=dao.findBySql(sql.toString(),new Object[]{exeId},null);
        return  signedPeople;
    }
    /**
     * 根据SQL获取到list中的第一条结果MAP
     *
     * @param sql
     * @param objs
     * @return
     */
    public Map<String, Object> getFirstByJdbc(String sql, Object[] objs) {
        List<Map<String,Object>> list=dao.findBySql(sql,objs,null);
        if(CollectionUtils.isEmpty(list)){
            return null;
        }
        return list.get(0);
    }


    /**
     * 获取信用平台的公司信息
     * @param sfzhm
     * @param entName
     * @return
     */
    public List<Map<String,Object>>  findCreditCompanyInfos(String sfzhm,String entName){
        StringBuffer sql = new StringBuffer("SELECT C.*,  ");
        sql.append(" to_char(C.op_from,'yyyy-mm-dd') as OPFROM,to_char(C.op_to,'yyyy-mm-dd') as OPTO,");
        sql.append(" l.name as LIAISON_NAME,l.TELEPHONE as LIAISON_MOBILE,l.CERT_NO as LIAISON_IDNO ");
        sql.append("FROM ECIP_GSJ_ENT_INFO C ");
        sql.append("LEFT JOIN ECIP_GSJ_ENT_INVESTOR P ");
        sql.append("ON C.UUID=P.ENT_UUID  AND C.LEREP = P.INV_NAME  ");
        sql.append("LEFT JOIN ECIP_GSJ_ENT_CONTACT  L ");
        sql.append("ON C.UUID=L.ENTY_ID ");
        sql.append("LEFT JOIN ECIP_GSJ_ENT_CLEAR  CL ");
        sql.append("ON C.UUID=CL.ENT_UUID WHERE  C.OP_STATE = '1' ");
        if(StringUtils.isNotEmpty(sfzhm)){
            sql.append("and p.B_LIC_NO='").append(sfzhm).append("'");
        }
        if(StringUtils.isNotEmpty(entName)){
            sql.append(" and ENT_NAME='").append(entName).append("'");
        }
        return dao.findBySql(sql.toString(),null,null);
    }
    @Override
    public Set<String> getIsAutoApproval(Map<String, Object> flowVars) {
        // TODO Auto-generated method stub
        //申报类型 0 普通，1 秒批
        String sssblx = flowVars.get("SSSBLX")==null?"":flowVars.get("SSSBLX").toString();
        Set<String> resultNodes = new HashSet<String>();
        if(StringUtils.isNotEmpty(sssblx) && sssblx.equals("1")){
            resultNodes.add(ExeDataService.ID_IDENTIFY_TASKNAME_MP);
        } else{
            resultNodes.add("预审开始");
        }
        return resultNodes;
    }

    /**
     * 秒批发送面签短信
     * @param flowVars
     * @return
     */
    @Override
    public  Map<String,Object> sendMsgOfCompany(Map<String,Object> flowVars){
        String sssblx = flowVars.get("SSSBLX")==null?"":flowVars.get("SSSBLX").toString();
        String eflowIstempsave=StringUtil.getString(flowVars.get("EFLOW_ISTEMPSAVE"));//是否暂存操作
        String busTablename=StringUtil.getString(flowVars.get("EFLOW_BUSTABLENAME"));//是否暂存操作
        //秒批短信通知人员
        String exeId = StringUtil.getString(flowVars.get("EFLOW_EXEID"));
        //秒批并且不是暂存
        if (StringUtils.isNotEmpty(sssblx) && sssblx.equals("1") &&
                !Objects.equals(eflowIstempsave, "1") && StringUtils.isNotEmpty(exeId)) {
            //根据业务表名获取短信发送类
            SignContent signContent=SignMsgFactory.createSignContent(busTablename);
            if(Objects.nonNull(signContent)){
                signContent.sendSignMsg(flowVars);
            }
        }
        return  flowVars;
    }
    /**
     * 推送公司名数据查重到数据库
     * @param map
     * @return
     */
    public AjaxJson pushCompanyName(Map<String,Object> map){
        String id="";
        AjaxJson ajaxJson=new AjaxJson();
        String areaName = StringUtil.getString(map.get("AREA_NAME"));
        String wordNum = StringUtil.getString(map.get("WORD_NUM"));
        String childIndustryName = StringUtil.getString(map.get("CHILD_INDUSTRY_NAME"));
        String orgType = StringUtil.getString(map.get("ORG_TYPE"));
        StringBuffer sql=new StringBuffer();
        sql.append("select * from ");
        sql.append(" t_commercial_companyname c where c.word_num=? ");
        sql.append(" and c.area_name=? and c.child_industry_name=? and  ");
        sql.append("c.org_type=? and c.name_type='1'");

        Map<String,Object> companyName=dao.getByJdbc(sql.toString()
                ,new Object[]{wordNum,areaName,childIndustryName,orgType});
        if(companyName!=null){
            map.put("SEND_STATUS","0");
            map.put("CHECK_RESULT","0");
            id=StringUtil.getString(companyName.get("ID"));
        }
        map.put("name_type","1");
        dao.saveOrUpdate(map,"t_commercial_companyname",id);
        ajaxJson.setSuccess(true);
        return ajaxJson;
    }
    /**
     * 推送公司名数据查重到数据库(个体)
     * @param map
     * @return
     */
    @SuppressWarnings("unchecked")
    public AjaxJson pushCompanyNameToGt(Map<String,Object> map){
        String id="";
        AjaxJson ajaxJson=new AjaxJson();
        String wordNum = StringUtil.getString(map.get("WORD_NUM"));
        String nameType = StringUtil.getString(map.get("NAME_TYPE"));
        StringBuffer sql=new StringBuffer();
        sql.append("select * from ");
        sql.append(" t_commercial_companyname c where c.word_num=? and c.name_type=?");
        Map<String,Object> companyName=dao.getByJdbc(sql.toString()
                ,new Object[]{wordNum,nameType});
        if(companyName!=null){
            map.put("SEND_STATUS","0");
            map.put("CHECK_RESULT","0");
            id=StringUtil.getString(companyName.get("ID"));
        }
        ajaxJson.setSuccess(true);
        dao.saveOrUpdate(map,"t_commercial_companyname",id);
        return ajaxJson;
    }
    /**
     * 公司名查重(个体)
     * @param map
     * @return
     */
    @SuppressWarnings("unchecked")
    public AjaxJsonCode getCompanyNameResultToGt(Map<String,Object> map){
        AjaxJsonCode ajaxJsonCode=new AjaxJsonCode();
        String wordNum = StringUtil.getString(map.get("WORD_NUM"));
        String nameType = StringUtil.getString(map.get("NAME_TYPE"));
        StringBuffer sql=new StringBuffer();
        sql.append("select * from ");
        sql.append(" t_commercial_companyname c where c.word_num=? and c.name_type=?");
        sql.append(" and  CHECK_RESULT!='0' ");
        Map<String,Object> companyName=dao.getByJdbc(sql.toString()
                ,new Object[]{wordNum,nameType});
        if(companyName==null){
            ajaxJsonCode.setMsg(getBeforeWaitPeopleNum(wordNum,nameType));
            ajaxJsonCode.setCode("00");  //未执行开普云名称查询
        }else{
            String checkResult=StringUtil.getString(companyName.get("CHECK_RESULT"));
            String checkMsg=StringUtil.getString(companyName.get("CHECK_MSG"));
            ajaxJsonCode.setCode(checkResult);
            ajaxJsonCode.setMsg(checkMsg);
        }
        return ajaxJsonCode;
    }

    /**
     * 获取前面等待人数
     */
    private String getBeforeWaitPeopleNum(String wordNum,String nameType){
        StringBuffer sql2=new StringBuffer();
        sql2.append("select * from ");
        sql2.append(" t_commercial_companyname c where c.word_num=? and c.name_type=?");
        sql2.append(" and  CHECK_RESULT='0' ");
        Map<String,Object> companyName2=dao.getByJdbc(sql2.toString()
                ,new Object[]{wordNum,nameType});

        String createTime=StringUtil.getString(companyName2.get("CREATE_TIME"));
        StringBuffer sql1=new StringBuffer();
        sql1.append("select * from ");
        sql1.append(" t_commercial_companyname c where c.name_type=? and c.create_time< ?");
        sql1.append(" and  SEND_STATUS='0' ");
        List<Map<String,Object>> companyNames=dao.findBySql(sql1.toString(),
                new Object[]{nameType,createTime},null);
        String waitNum="0";
        if(!CollectionUtils.isEmpty(companyNames)){
            int remain=companyNames.size();
            waitNum=String.valueOf(remain);
        }
        return waitNum;
    }
    /**
     * 公司名查重
     * @param map
     * @return
     */
    public AjaxJsonCode getCompanyNameResult(Map<String,Object> map){
        AjaxJsonCode ajaxJsonCode=new AjaxJsonCode();
        String areaName = StringUtil.getString(map.get("AREA_NAME"));
        String wordNum = StringUtil.getString(map.get("WORD_NUM"));
        String childIndustryName = StringUtil.getString(map.get("CHILD_INDUSTRY_NAME"));
        String orgType = StringUtil.getString(map.get("ORG_TYPE"));
        StringBuffer sql=new StringBuffer();
        sql.append("select * from ");
        sql.append(" t_commercial_companyname c where c.word_num=? ");
        sql.append(" and c.area_name=? and c.child_industry_name=? and  ");
        sql.append("c.org_type=? and  CHECK_RESULT!='0' and c.name_type='1' ");
        Map<String,Object> companyName=dao.getByJdbc(sql.toString()
                ,new Object[]{wordNum,areaName,childIndustryName,orgType});
        if(companyName==null){
            ajaxJsonCode.setCode("00");  //未执行开普云名称查询
        }else{
            String checkResult=StringUtil.getString(companyName.get("CHECK_RESULT"));
            String checkMsg=StringUtil.getString(companyName.get("CHECK_MSG"));
            ajaxJsonCode.setCode(checkResult);
            ajaxJsonCode.setMsg(checkMsg);
        }
        return ajaxJsonCode;
    }

    /**
     * 商事秒批退回短信
     *
     * @param exeId
     */
    @Override
    @SuppressWarnings("unchecked")
    public void sendExeBackMsg(String exeId) {
        ExeContent exeContent = ExeMsgFactory.createExeMsg(exeId);
        if (Objects.nonNull(exeContent)) {
            exeContent.sendBackMsg(exeId);
        }
        setAllSignBack(exeId);
    }

    /**
     * 退回，所有人重新面签
     * @param exeId
     * @return
     */
    @Override
    public boolean  setAllSignBack(String exeId){
        //秒批
        Map<String,Object> busMap=getExeAndBuscordMap(exeId);
        String busTablename=StringUtil.getString(busMap.get("BUS_TABLENAME"));
        String sssblx = StringUtil.getString(busMap.get("SSSBLX"));
        if ("1".equals(sssblx)) {
            if(Objects.equals(busTablename, TableNameEnum.T_COMMERCIAL_DOMESTIC.getVal())||Objects.equals(busTablename, TableNameEnum.T_COMMERCIAL_INDIVIDUAL.getVal())
            ||Objects.equals(busTablename, TableNameEnum.T_COMMERCIAL_NZ_JOINTVENTURE.getVal())) {
                //更改面签状态为未面签
                String sql = "update jbpm6_execution t set t.isfacesign=0 where t.exe_id=?";
                dao.executeSql(sql, new Object[]{exeId});
                String signSql = "update t_commercial_sign s  set SIGN_FLAG='0' where exe_id=?";
                dao.executeSql(signSql, new Object[]{exeId});
                //先清除办件营业执照信息
                dao.remove("T_COMMERCIAL_COMPANY_LEGALFILE",new String[]{"EXE_ID"},new Object[]{exeId});
            }
        }
        return true;
    }
    /**
     * 短信定时发送
     */
    public void sendMsgByTime(){
        StringBuffer sql=new StringBuffer();
        sql.append("select * from T_MESSAGE_INFO m ");
        sql.append(" where m.SEND_STATUS='0' and m.create_time like ? ");
        Date nowDate = new Date();
        String nowDateYMD = DateTimeUtil.dateToStr(nowDate, "yyyy-MM-dd");
        List<Map<String,Object>> msgList=dao.findBySql(sql.toString(), new Object[] {"%" + nowDateYMD + "%"},null);
        for(Map<String,Object> msg:msgList){
            String mobile=StringUtil.getString(msg.get("RECEIVER_MOB"));
            String msgInfo=StringUtil.getString(msg.get("MSG_INFO"));
            String msgId=StringUtil.getString(msg.get("MSG_ID"));
            MsgSendUtils.sendMsg(mobile, msgInfo);
            Map<String,Object> updateStatus=new HashMap<>();
            updateStatus.put("SEND_STATUS",1);
            dao.saveOrUpdate(updateStatus,"T_MESSAGE_INFO",msgId);
        }
    }

    @Override
    public Map<String, Object> sendMsgOfIndividual(Map<String, Object> flowVars) {
        // TODO Auto-generated method stub
        String sssblx = flowVars.get("SSSBLX")==null?"":flowVars.get("SSSBLX").toString();
        String PTT_SQFS = flowVars.get("PTT_SQFS")==null?"":flowVars.get("PTT_SQFS").toString();
        String eflowIstempsave=StringUtil.getString(flowVars.get("EFLOW_ISTEMPSAVE"));//是否暂存操作
        //秒批短信通知人员
        String exeId = StringUtil.getString(flowVars.get("EFLOW_EXEID"));
        if (StringUtils.isNotEmpty(sssblx) && sssblx.equals("1") && !Objects.equals(eflowIstempsave, "1")
                && StringUtils.isNotEmpty(exeId) && !PTT_SQFS.equals("3")) {
            String INDIVIDUAL_NAME=StringUtil.getString(flowVars.get("INDIVIDUAL_NAME"));
            String noticeToSign= StringUtil.getString(dictionaryService.get("msgContent",
                    "gtmpsbtxmqdx").get("DIC_DESC"));
            //经营者
            String mobile = StringUtil.getString(flowVars.get("DEALER_MOBILE"));
            Sm4Utils sm4 = new Sm4Utils();
            if(StringUtils.isNotEmpty(sm4.decryptDataCBC(mobile))){
                mobile = sm4.decryptDataCBC(mobile);
            }
            String signExeId = exeId.substring(8);
            String content = String.format(noticeToSign, INDIVIDUAL_NAME, exeId, signExeId);
            MsgSendUtils.sendMsg(mobile, content);
            //经办人
            String JBR_MOBILE = StringUtil.getString(flowVars.get("JBR_MOBILE"));
            if(StringUtils.isNotEmpty(JBR_MOBILE) && !mobile.equals(JBR_MOBILE)){//与经营者不同时发送短信
                MsgSendUtils.sendMsg(JBR_MOBILE, content);                
            }
        }
        return  flowVars;
    }
}