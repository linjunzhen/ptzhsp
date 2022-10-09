package net.evecom.platform.wsbs.dao;

import net.evecom.core.dao.BaseDao;

import java.sql.Connection;

/**
 * 描述:
 *
 * @author Madison You
 * @created 10:26
 */
public interface ZxInterfaceDao extends BaseDao {

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2021/3/9 9:12:00
     * @param
     * @return
     */
    void deleteAttrNoInJbpm6Execution();

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2021/3/9 9:13:00
     * @param
     * @return
     */
    void deleteInfoNoInJbpm6Execution();

}
