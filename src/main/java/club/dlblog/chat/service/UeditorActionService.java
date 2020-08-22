package club.dlblog.chat.service;

import club.dlblog.chat.config.UeditorConfig;
import club.dlblog.chat.controller.UeditorController;
import club.dlblog.chat.util.UeditorUtil;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Service
public class UeditorActionService {


    Logger logger  = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UeditorConfig ueditorConfig;

    public String getConfig(){
        String result = null;
        // 配置文件路径， 相对于classpath
        String configPath = "/config.json";
        InputStream inStream = UeditorController.class.getResourceAsStream(configPath);
        StringBuilder builder = new StringBuilder();
        try {
            InputStreamReader reader = new InputStreamReader(inStream, "UTF-8");
            BufferedReader bfReader = new BufferedReader(reader);
            String tmpContent;
            while ((tmpContent = bfReader.readLine()) != null) {
                builder.append(tmpContent);
            }
            bfReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 过滤输入字符串, 剔除多行注释以及替换掉反斜杠
        result = builder.toString().replaceAll("/\\*[\\s\\S]*?\\*/", "").replaceAll(" ", "");
        logger.debug("result:" + result);
        return result;
    }

    public String uploadFile(MultipartFile upfile){
        String result = null;
        // 上传成功后返回的json数据
        /*
         * {"state": "SUCCESS","original": "Hydrangeas.jpg","size": "595284","title":
         * "1551927256870045443.jpg","type": ".jpg","url":
         * "/upload/image/20190307/1551927256870045443.jpg"}
         */
        String originalFilename = upfile.getOriginalFilename();
        String type = originalFilename.substring(originalFilename.indexOf("."),originalFilename.length());
        long size = upfile.getSize();


        String middlePath = LocalDate.now() + "/";

        String fileFullName = ueditorConfig.getSavePath() + middlePath + originalFilename;
        // 图片访问地址（tomcat服务器）
        String url = ueditorConfig.getBaseUrl() +middlePath+ originalFilename;
        try {

            File file = new File(fileFullName);
            if(!file.getParentFile().exists()) {
                file.getParentFile().mkdirs() ;
            }

            if(file.exists()){
                file.createNewFile();
            }
            upfile.transferTo(file);

            Map<String, Object> map = new HashMap<String, Object>();
            map.put("state", "SUCCESS");
            map.put("original", originalFilename);
            map.put("size", size);
            map.put("title", originalFilename);
            map.put("type", type);
            map.put("url", url);

            result = JSON.toJSONString(map);
            logger.debug("result : " + result);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

}
