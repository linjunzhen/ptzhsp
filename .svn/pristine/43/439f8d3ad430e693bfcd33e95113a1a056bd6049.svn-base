/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.project.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import net.evecom.core.dao.impl.BaseDaoImpl;
import net.evecom.platform.project.dao.ProjectXFDao;

/**
 * 
 * 描述：工程建设项目消防事项数据共享定时器
 * @author Scolder Lin
 * @created 2020年1月8日 下午5:30:39
 */
@SuppressWarnings("rawtypes")
@Repository("projectXFDao")
public class ProjectXFDaoImpl extends BaseDaoImpl implements ProjectXFDao {
    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> findXFBaseList(int status) {
        List<Object> param = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("SELECT T.FC_PROJECT_INFO_ID,T.PRJ_CODE AS PrjCode,T.PRJ_NUM AS PrjNum,");
        sql.append(" T.PRJ_NAME AS PrjName, T.ADDRESS AS Address, T.BUILD_CORPNAME AS BuildCorpName, ");
        sql.append(" T.LEGAL_REPRESENT, T.LEGAL_INFORMATION, T.CONTACTOR, T.CONTACT_INFORMATION , ");
        sql.append(" T.FC_PRJ_TYPE AS FCPrjType, T.FC_CHARACTER AS FCCharacter ");
        sql.append(" FROM TB_FC_PROJECT_INFO T LEFT JOIN JBPM6_EXECUTION E ON T.FC_PROJECT_INFO_ID = E.BUS_RECORDID");
        sql.append(" WHERE 1=1 ");
        sql.append(" AND E.RUN_STATUS='2' ");
        if (status >= 0) {
            sql.append(" AND T.BASE_PUSH_STATUS = ? ");
            param.add(status);
        }
        sql.append(" ORDER BY T.CREATE_TIME ASC ");
        List<Map<String, Object>> list = this.findBySql(sql.toString(), param.toArray(), null);
        return list;
    }
    
    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> findXFUnitList(int status) {
        List<Object> param = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("SELECT T.FC_PROJECT_INFO_ID, T.DTXX_JSON ");
        sql.append(" FROM TB_FC_PROJECT_INFO T LEFT JOIN JBPM6_EXECUTION E ON T.FC_PROJECT_INFO_ID = E.BUS_RECORDID");
        sql.append(" WHERE 1=1 ");
        sql.append(" AND E.RUN_STATUS='2' ");
        sql.append(" AND T.DTXX_JSON NOT LIKE '%[]%' ");
        sql.append(" AND T.DTXX_JSON IS NOT NULL ");
        if (status >= 0) {
            sql.append(" AND T.UNIT_PUSH_STATUS = ? ");
            param.add(status);
        }
        sql.append(" ORDER BY T.CREATE_TIME ASC ");
        List<Map<String, Object>> list = this.findBySql(sql.toString(), param.toArray(), null);
        return list;
    }
    
    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> findXFCorpList(int status) {
        List<Object> param = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("SELECT T.FC_PROJECT_INFO_ID, T.PRJ_CODE, T.ZRZTXX_JSON ");
        sql.append(" FROM TB_FC_PROJECT_INFO T LEFT JOIN JBPM6_EXECUTION E ON T.FC_PROJECT_INFO_ID = E.BUS_RECORDID");
        sql.append(" WHERE 1=1 ");
        sql.append(" AND E.RUN_STATUS='2' ");
        sql.append(" AND T.ZRZTXX_JSON NOT LIKE '%[]%' ");
        sql.append(" AND T.ZRZTXX_JSON IS NOT NULL ");
        if (status >= 0) {
            sql.append(" AND T.CORP_PUSH_STATUS = ? ");
            param.add(status);
        }
        sql.append(" ORDER BY T.CREATE_TIME ASC ");
        List<Map<String, Object>> list = this.findBySql(sql.toString(), param.toArray(), null);
        return list;
    }
    
    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> findXFStorageList(int status) {
        List<Object> param = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("SELECT T.FC_PROJECT_INFO_ID,T.PRJ_CODE AS PrjCode, ");
        sql.append(" T.PRJ_NUM AS PrjNum, T.SET_LOCATION AS SetLocation, T.CAPACITY, T.SET_TYPE AS SetType, ");
        sql.append(" T.STORAGE_TYPE AS StorageType, T.CG_STORAGE_NAME AS StorageName ");
        sql.append(" FROM TB_FC_PROJECT_INFO T LEFT JOIN JBPM6_EXECUTION E ON T.FC_PROJECT_INFO_ID = E.BUS_RECORDID");
        sql.append(" WHERE 1=1 ");
        sql.append(" AND E.RUN_STATUS='2' ");
        if (status >= 0) {
            sql.append(" AND T.STORAGE_PUSH_STATUS = ? ");
            param.add(status);
        }
        sql.append(" ORDER BY T.CREATE_TIME ASC ");
        List<Map<String, Object>> list = this.findBySql(sql.toString(), param.toArray(), null);
        return list;
    }
    
    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> findXFYardList(int status) {
        List<Object> param = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("SELECT T.FC_PROJECT_INFO_ID,T.PRJ_CODE AS PrjCode, ");
        sql.append(" T.PRJ_NUM AS PrjNum, T.STORAGE_CAPACITY AS StorageCapacity, T.DC_STORAGE_NAME AS StorageName ");
        sql.append(" FROM TB_FC_PROJECT_INFO T LEFT JOIN JBPM6_EXECUTION E ON T.FC_PROJECT_INFO_ID = E.BUS_RECORDID");
        sql.append(" WHERE 1=1 ");
        sql.append(" AND E.RUN_STATUS='2' ");
        if (status >= 0) {
            sql.append(" AND T.YARD_PUSH_STATUS = ? ");
            param.add(status);
        }
        sql.append(" ORDER BY T.CREATE_TIME ASC ");
        List<Map<String, Object>> list = this.findBySql(sql.toString(), param.toArray(), null);
        return list;
    }
    
    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> findXFInsuList(int status) {
        List<Object> param = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("SELECT T.FC_PROJECT_INFO_ID,T.PRJ_CODE AS PrjCode, ");
        sql.append(" T.PRJ_NUM AS PrjNum, T.MATERIAL_CATEGORY AS MaterialCategory, ");
        sql.append(" T.INSULATION_LAYER AS InsulationLayer, T.BW_USE_CHARACTER AS UseCharacter, ");
        sql.append(" T.BW_ORIGINAL_USE AS OriginalUse ");
        sql.append(" FROM TB_FC_PROJECT_INFO T LEFT JOIN JBPM6_EXECUTION E ON T.FC_PROJECT_INFO_ID = E.BUS_RECORDID");
        sql.append(" WHERE 1=1 ");
        sql.append(" AND E.RUN_STATUS='2' ");
        if (status >= 0) {
            sql.append(" AND T.INSU_PUSH_STATUS = ? ");
            param.add(status);
        }
        sql.append(" ORDER BY T.CREATE_TIME ASC ");
        List<Map<String, Object>> list = this.findBySql(sql.toString(), param.toArray(), null);
        return list;
    }
    
    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> findXFDecorateList(int status) {
        List<Object> param = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("SELECT T.FC_PROJECT_INFO_ID,T.PRJ_CODE AS PrjCode, ");
        sql.append(" T.PRJ_NUM AS PrjNum, T.DECORATION_PART AS DecorationPart, ");
        sql.append(" T.DECORATION_AREA AS DecorationArea, T.DECORATION_LAYER AS DecorationLayer, ");
        sql.append(" T.ZX_USE_CHARACTER AS UseCharacter, T.ZX_ORIGINAL_USE AS OriginalUse ");
        sql.append(" FROM TB_FC_PROJECT_INFO T LEFT JOIN JBPM6_EXECUTION E ON T.FC_PROJECT_INFO_ID = E.BUS_RECORDID");
        sql.append(" WHERE 1=1 ");
        sql.append(" AND E.RUN_STATUS='2' ");
        if (status >= 0) {
            sql.append(" AND T.DECORATE_PUSH_STATUS = ? ");
            param.add(status);
        }
        sql.append(" ORDER BY T.CREATE_TIME ASC ");
        List<Map<String, Object>> list = this.findBySql(sql.toString(), param.toArray(), null);
        return list;
    }
    
    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> findFinishManageList(int status) {
        List<Object> param = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("SELECT T.YW_ID, T.PrjFinishNum,T.PROJECT_NAME AS FinPrjName,");
        sql.append(" T.PROJECTCODE AS PrjCode, T.PRJNUM, T.TenderNum,T.SGXKZH AS BuilderLicenceNum,");
        sql.append(" T.PrjFinishCheckNum, T.INVEST AS FactCost, T.SJMJ AS FactArea,");
        sql.append(" T.MUNILENGTHS AS Length, T.KD AS Span, T.PRJSIZE AS FactSize,T.DataLevel, ");
        sql.append(" T.STRUCTQUALTYPES AS PrjStructureTypeNum, T.SJKGRQ AS BDate, T.SJJGRQ AS EDate, ");
        sql.append(" T.REMARK AS Mark, T.CheckDepartName, T.CheckPersonName, T.DATASOURCE AS DataSource,");
        sql.append(" T.REMARK AS Mark, T.CheckDepartName, T.CheckPersonName, T.DATASOURCE AS DataSource ");
        sql.append(" FROM TB_PROJECT_FINISH_MANAGE T LEFT JOIN JBPM6_EXECUTION E ON T.YW_ID = E.BUS_RECORDID");
        sql.append(" WHERE 1=1 ");
        sql.append(" AND E.RUN_STATUS='2' ");
        if (status >= 0) {
            sql.append(" AND T.PUSH_STATUS = ? ");
            param.add(status);
        }
        List<Map<String, Object>> list = this.findBySql(sql.toString(), param.toArray(), null);
        return list;
    }

    /**
     * 描述:查询消防验收（备案）申请列表
     *
     * @author Madison You
     * @created 2020/1/16 10:42:00
     * @param
     * @return
     */
    @Override
    public List<Map<String, Object>> findXFYsbaList(int status) {
        List<Object> param = new ArrayList<>();
        StringBuffer sql = new StringBuffer();
        sql.append(" select T.YW_ID,T.PRJ_CODE AS PrjCode,T.PRJ_NUM AS PrjNum,T.IFSPECIALBUILDING,T.DECLARATIONTYPE,T.DESIGNREVIEWNUM,");
        sql.append(" T.FCACCEPTANCENUM,T.AUDITDATE,T.QUALITYDEPART,T.FINISHCHECKDATE,T.DESIGNRECORDNUM,T.OTHER_INSTRUCTIONS AS");
        sql.append(" OTHERINSTRUCTIONS from T_BSFW_GCJSXFYS T LEFT JOIN JBPM6_EXECUTION E ON T.YW_ID = E.BUS_RECORDID WHERE 1=1 ");
        sql.append(" AND E.RUN_STATUS='2' ");
        if (status >= 0) {
            sql.append(" AND XFYSBA_STATUS = ? ");
            param.add(status);
        }
        List<Map<String, Object>> list = this.findBySql(sql.toString(), param.toArray(), null);
        return list;
    }

    /**
     * 描述:查询消防验收情况列表
     *
     * @author Madison You
     * @created 2020/1/16 11:19:00
     * @param
     * @return
     */
    @Override
    public List<Map<String, Object>> findXFYsqkList(int status) {
        List<Object> param = new ArrayList<>();
        StringBuffer sql = new StringBuffer();
        sql.append(" select T.YW_ID,T.FCAcceptanceNum AS FinishCheckRecNum,T.BuildingCategory, ");
        sql.append(" T.AcceptanceSituation1,T.GeneralLayout, ");
        sql.append(" T.AcceptanceSituation2,T.PlaneLayout,T.AcceptanceSituation3,T.FireSource,T.AcceptanceSituation4, ");
        sql.append(" T.FirePower,T.AcceptanceSituation5,T.DecorationFC,T.AcceptanceSituation6,T.BuildingInsulation, ");
        sql.append(" T.AcceptanceSituation7,T.FireCompartment,T.AcceptanceSituation8,T.OFHydrantSystem,T.AcceptanceSituation9,");
        sql.append(" T.AutoFireSystem,T.AcceptanceSituation10,T.IFHydrantSystem,T.AcceptanceSituation11,T.AutomaticSprinkler, ");
        sql.append(" T.AcceptanceSituation12,T.OtherFacilities,T.AcceptanceSituation13,T.SmokeControlSys, ");
        sql.append(" T.AcceptanceSituation14,T.SafeEvacuation,T.AcceptanceSituation15,T.SmokePreZone,T.AcceptanceSituation16, ");
        sql.append(" T.FireElevator,T.AcceptanceSituation17,T.ExplosionProof,T.AcceptanceSituation18,T.FireExtinguisher, ");
        sql.append(" T.AcceptanceSituation19,T.Others,T.OtherMatter,T.AcceptanceSituation20 from T_BSFW_GCJSXFYS T ");
        sql.append(" LEFT JOIN JBPM6_EXECUTION E ON T.YW_ID = E.BUS_RECORDID");
        sql.append(" where 1 = 1 ");
        sql.append(" AND E.RUN_STATUS='2' ");
        if (status >= 0) {
            sql.append(" AND XFYSQK_STATUS = ? ");
            param.add(status);
        }
        List<Map<String, Object>> list = this.findBySql(sql.toString(), param.toArray(), null);
        return list;
    }
}
