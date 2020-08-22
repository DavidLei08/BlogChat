package club.dlblog.chat.controller;

import club.dlblog.chat.service.UeditorActionService;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @author machenike
 */

@Controller
public class UeditorController {

    Logger logger  = LoggerFactory.getLogger(this.getClass());

    private final static String ACTION_CONFIG  = "config";

    private final static String ACTION_UPLOAD_IMG  = "uploadimage";

    @Autowired
    UeditorActionService ueditorActionService;

    /**
     * 初始化UEditor上传文件、图片等配置
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "/umeditor/jsp/controller.jsp")
    public void initConfig(MultipartFile upfile, HttpServletRequest request, HttpServletResponse response) {
        try {
            request.setCharacterEncoding("UTF-8");
            response.setHeader("Content-Type", "text/html");

            logger.debug("url : " + request.getRequestURL().toString());
            String value = request.getParameter("action");
            logger.debug("action:" + value);

            String result = null;
            // 读取配置文件，将配置文件数据以json格式返回
            if (ACTION_CONFIG.equals(value)) {
                result = ueditorActionService.getConfig();
            } else if (ACTION_UPLOAD_IMG.equals(value)) {
                // 上传文件
                result = ueditorActionService.uploadFile(upfile);
            }
            PrintWriter writer = response.getWriter();
            writer.write(result);
            writer.flush();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
