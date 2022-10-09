/*
 * Copyright (c) 2005, 2017, EVECOM Technology Co.,Ltd. All rights reserved.
 * EVECOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 */
package net.evecom.platform.commercial.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import net.evecom.core.service.BaseService;
import net.evecom.core.util.SqlFilter;

/**
 * 描述
 * @author Danto Huang
 * @created 2017年12月4日 上午10:19:20
 */
public interface BankService extends BaseService {

    /**
     * 
     * 描述 工商列表数据
     * @author Danto Huang
     * @created 2017年12月4日 上午10:22:54
     * @return
     */
    public List<Map<String,Object>> findForUpload(SqlFilter filter);

    /**
     * 
     * 描述 工商列表数据
     * @author Danto Huang
     * @created 2017年12月4日 上午10:22:54
     * @return
     */
    public List<Map<String,Object>> findForDownload(SqlFilter filter);
    /**
     * 
     * 描述  上传材料保存
     * @author Danto Huang
     * @created 2017年12月6日 下午5:07:08
     * @param variables
     */
    public void saveUploadInfo(Map<String, Object> variables);
    /**
     * 
     * 描述  打包下载
     * @author Danto Huang
     * @created 2017年12月6日 上午9:22:57
     * @param exeId
     */
    public void genApplyRar(String exeId,HttpServletResponse response);
}
