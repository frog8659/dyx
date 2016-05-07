package sh.ricky.dyx.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 全局页面
 * 
 * @author SHI
 */
@Controller
public class GlobalController {
    /**
     * 框架页
     * 
     * @return
     */
    @RequestMapping("/frame")
    public String frame() {
        return "common/frame";
    }

    /**
     * 首页
     * 
     * @return
     */
    @RequestMapping("/home")
    public String home() {
        return "common/home";
    }
}
