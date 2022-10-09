package net.evecom.platform.wsbs.controller;

import net.evecom.platform.base.controller.BaseController;
import net.evecom.platform.wsbs.service.ZxInterfaceService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 描述:
 *
 * @author Madison You
 * @created 10:54
 */
@Controller
@RequestMapping("zxInterfaceController")
public class ZxInterfaceController extends BaseController {

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2021/3/8 10:55:00
     * @param
     * @return
     */
    @Resource
    private ZxInterfaceService zxInterfaceService;

    /**
     * 描述:
     *
     * @author Madison You
     * @created 2021/3/8 11:08:00
     * @param 
     * @return 
     */
    @RequestMapping("test")
    @ResponseBody
    public String test(){
        zxInterfaceService.sendDataToZx();
        return "aaa";
    }

}
