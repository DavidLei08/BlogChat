package club.dlblog.chat.config;

import club.dlblog.chat.util.UeditorUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author machenike
 */
@Component
public class UeditorConfig {

    @Value("${club.dlblog.ueditor.savePath}")
   private String savePath;

    @Value("${club.dlblog.ueditor.baseUrl}")
   private String baseUrl;

    public String getSavePath() {
        return savePath;
    }

    public void setSavePath(String savePath) {
        this.savePath = savePath;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    @PostConstruct
    public  void init(){
        UeditorUtil.saveFilePath = this.getSavePath();
    }
}
