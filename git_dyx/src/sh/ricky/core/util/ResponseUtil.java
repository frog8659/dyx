package sh.ricky.core.util;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

public class ResponseUtil {
    /**
     * 向客户端输出内容
     * 
     * @param response
     * @param content
     * @throws Throwable
     */
    public static void write(HttpServletResponse response, String content) throws Throwable {
        OutputStream os = null;
        InputStream is = null;

        try {
            response.reset();
            response.setContentType("text/html; charset=UTF-8");
            response.setHeader("Cache-Control", "no-cache");
            os = new BufferedOutputStream(response.getOutputStream());
            os.write(((String) content).getBytes());
        } finally {
            if (is != null) {
                is.close();
            }
            if (os != null) {
                os.flush();
                os.close();
            }
        }
    }

    /**
     * 向客户端输出脚本
     * 
     * @param response
     * @param content
     * @throws Throwable
     */
    public static void writeAsScript(HttpServletResponse response, String content) throws Throwable {
        StringBuffer script = new StringBuffer();
        script.append("<script type='text/javascript'>").append(content).append("</script>");
        ResponseUtil.write(response, script.toString());
    }

    /**
     * 向客户端输出文件（下载）
     * 
     * @param response
     * @param content
     * @throws Throwable
     */
    public static void writeAsFile(HttpServletResponse response, File content) throws Throwable {
        OutputStream os = null;
        InputStream is = null;

        try {
            response.reset();

            if (content.exists() && content.isFile()) {
                response.setContentType("application/x-download");
                response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(content.getName(), "UTF-8"));
                response.addHeader("Content-Length", "" + content.length());

                is = new BufferedInputStream(new FileInputStream(content));
                os = new BufferedOutputStream(response.getOutputStream());

                response.setContentType("application/octet-stream;charset=utf-8");
                byte[] buffer = new byte[1024 * 1024 * 4];
                int i = -1;
                while ((i = is.read(buffer)) != -1) {
                    os.write(buffer, 0, i);
                }
            }
        } finally {
            if (is != null) {
                is.close();
            }
            if (os != null) {
                os.flush();
                os.close();
            }
        }
    }

    /**
     * 向客户端输出图片
     * 
     * @param response
     * @param content
     * @throws Throwable
     */
    public static void writeAsImage(HttpServletResponse response, File content) throws Throwable {
        OutputStream os = null;
        InputStream is = null;

        try {
            response.reset();

            if (content.exists() && content.isFile()) {
                response.setContentType("image/jpeg"); // 设置图像生成格式
                response.setHeader("Pragma", "No-cache"); // 设置响应头信息，禁用浏览器缓存
                response.setHeader("Cache-Control", "no-cache"); // 设置响应头信息，禁用浏览器缓存

                is = new BufferedInputStream(new FileInputStream(content));
                os = new BufferedOutputStream(response.getOutputStream());
                BufferedImage bi = ImageIO.read(is);
                ImageIO.write(bi, "jpg", os);
            }
        } finally {
            if (is != null) {
                is.close();
            }
            if (os != null) {
                os.flush();
                os.close();
            }
        }
    }
}
