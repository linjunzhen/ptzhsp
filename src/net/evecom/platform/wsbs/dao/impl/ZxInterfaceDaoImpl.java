package net.evecom.platform.wsbs.dao.impl;

import net.evecom.core.dao.impl.BaseDaoImpl;
import net.evecom.platform.wsbs.dao.ZxInterfaceDao;
import org.springframework.stereotype.Repository;

/**
 * 描述:
 *
 * @author Madison You
 * @created 10:27
 */
@Repository("zxInterfaceDao")
public class ZxInterfaceDaoImpl extends BaseDaoImpl implements ZxInterfaceDao {

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2021/3/9 9:12:00
     * @param
     * @return
     */
    @Override
    public void deleteAttrNoInJbpm6Execution() {
        StringBuffer sql = new StringBuffer();
        sql.append(" delete from t_bsfw_swbdata_attr r where not exists ");
        sql.append(" (select 1 from jbpm6_execution e, t_bsfw_swbdata_res res ");
        sql.append(" where e.exe_id = res.exe_id and res.res_id = r.res_id) and r.oper_status in(0,2) ");
        this.jdbcTemplate.execute(sql.toString());
    }

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2021/3/9 9:13:00
     * @param
     * @return
     */
    @Override
    public void deleteInfoNoInJbpm6Execution() {
        StringBuffer sql = new StringBuffer();
        sql.append(" delete from t_bsfw_swbdata_res r where not exists (select 1 from jbpm6_execution e ");
        sql.append(" where e.exe_id = r.exe_id) and r.oper_status in (0,2) ");
        this.jdbcTemplate.execute(sql.toString());
    }
}
