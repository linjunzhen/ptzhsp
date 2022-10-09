/*
 * Copyright (c) 2005, 2015, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.core.test.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

import bsh.EvalError;
import bsh.Interpreter;
import net.evecom.core.test.BaseTestCase;
import net.evecom.core.util.StringUtil;

/**
 * 描述
 * @author Flex Hu
 * @version 1.0
 * @created 2015年4月11日 上午9:28:26
 */
public class BshCodeTestCase extends BaseTestCase {
    /**
     * log声明
     */
    private static Log log = LogFactory.getLog(BshCodeTestCase.class);
    
    /**
     * 
     * 描述
     * @author Flex Hu
     * @created 2016年3月7日 下午3:17:05
     * @param args
     */
    public static void main(String[] args){
        Map<String,Object> flowInfo = new HashMap<String,Object>();
        String materParam = "flowInfo.get(\"FLOW_STAGE\")!=null&&flowInfo.get(\"FLOW_STAGE\").toString().equals(\"2\")";
        StringBuffer exeCode =new StringBuffer("if(").append(materParam).append("){ isCollect=\"true\";}");
        String isCollect = "";
        try {
            Interpreter it = new Interpreter();
            it.set("flowInfo", flowInfo);
            it.eval(exeCode.toString());
            isCollect = (String) it.get("isCollect");
            log.info("isCollect:"+isCollect);
        } catch (EvalError e) {
            log.error(e.getMessage());
        }
    }
    /**
     * 
     * 描述
     * @author Flex Hu
     * @throws EvalError 
     * @created 2015年4月11日 上午9:58:45
     */
    @Test
    public void testHello2() throws EvalError{
        StringBuffer exeCode =new StringBuffer("if(a==1){isCollect=\"true\";}");
        Interpreter it = new Interpreter();
        it.set("a",3);
        it.eval(exeCode.toString());
        String tran = (String) it.get("isCollect");
        log.info(tran);
    }

    /**
     * 
     * 描述
     * @author Flex Hu
     * @created 2015年4月11日 上午9:43:09
     * @throws EvalError
     */
    @Test
    public void testHello() throws EvalError{
        Interpreter it = new Interpreter();
        boolean isAllow = false;
        String exeCode = "if(a>0){ isAllow=true;}else{}";
        it.set("a",3);
        /*while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();
            String key = (String) entry.getKey();
            Object val = entry.getValue();
            it.set(key.replace(".", "_"), val);
        }*/
        ///it.set("execution", execution);
        it.eval(exeCode);
        Boolean tran = (Boolean) it.get("isAllow");
        log.info(tran);
    }
}
