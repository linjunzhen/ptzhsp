/*
 * Copyright (c) 2005, 2014, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.hd.dao.impl;

import net.evecom.core.dao.impl.BaseDaoImpl;
import net.evecom.core.util.DateTimeUtil;
import net.evecom.core.util.StringUtil;
import net.evecom.platform.hd.dao.LetterDao;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Map;

/**
 * 描述 写信求诉操作dao
 * 
 * @author Rider Chen
 * @version 1.0
 * @created 2014年9月11日 上午9:07:30
 */
@Repository("letterDao")
public class LetterDaoImpl extends BaseDaoImpl implements LetterDao {

    /**
     * 
     * 描述 获取信件编号
     * 
     * @author Rider Chen
     * @created 2016年5月6日 上午9:46:31
     * @param letterVars
     * @return
     */
    public String getNextLetterCode(Map<String, Object> letterVars) {
        String LETTER_CODE = (String) letterVars.get("LETTER_CODE");
        if (StringUtils.isNotEmpty(LETTER_CODE)) {
            return LETTER_CODE;
        } else {
            String letterType = letterVars.get("LETTER_TYPE") == null ? "" : (String) letterVars.get("LETTER_TYPE");
            if (letterType.equals("咨询")) {
                letterType = "ZX";
            } else if (letterType.equals("建议")) {
                letterType = "JY";
            } else if (letterType.equals("投诉")) {
                letterType = "TS";
            } else if (letterType.equals("举报")) {
                letterType = "JB";
            } else {
                letterType = "GX";
            }
            StringBuffer letterCode = new StringBuffer(letterType);
            letterCode.append(DateTimeUtil.getStrOfDate(new Date(), "yyyyMMdd"));
            int seqValue = this.jdbcTemplate.queryForInt("select S_HD_LETTER.nextval FROM DUAL");
            String nextValue = StringUtil.getFormatNumber(5, String.valueOf(seqValue));
            letterCode.append(nextValue);
            return letterCode.toString();
        }

    }
    /**
     *
     * @param replyFlag
     * @return
     */
    public int getAllSqNum(String replyFlag){
        StringBuffer sql=new StringBuffer("select count(*) from T_HD_LETTER");
        if(StringUtils.isNotEmpty(replyFlag)){
            sql.append(" where reply_flag='").append(replyFlag).append("'");
        }
        return this.jdbcTemplate.queryForInt(sql.toString());
    }
}
