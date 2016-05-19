package sh.ricky.core.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sh.ricky.core.constant.ConfigConstants;
import sh.ricky.core.constant.DicConstants;

public class InitServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    public void init() throws ServletException {
        try {
            // 设置系统根路径
            System.setProperty("webapp.root", getServletContext().getRealPath("\\").replaceAll("\\\\", "/"));
            // 初始化配置信息并读入内存
            ConfigConstants.getInstance().init();
            // 初始化字典信息并读入内存
            DicConstants.getInstance().init();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 重新加载字典及配置信息
        init();
    }
}
