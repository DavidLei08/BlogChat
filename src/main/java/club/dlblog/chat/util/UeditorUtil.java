package club.dlblog.chat.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class UeditorUtil {

    public static String saveFilePath;

    public static String saveFile(MultipartFile multipartFile){
        return "";
    }

    public static byte[] getFile(String urlPath){
        if(urlPath.startsWith("/file")){
            urlPath =  urlPath.replaceAll("/file", "");
        }
        File file=new File(saveFilePath+urlPath);
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        byte[] imgBuffer=new byte[(int)file.length()];
        try {
            inputStream.read(imgBuffer);
        } catch (Exception e) {

            e.printStackTrace();
        }finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return imgBuffer;
    }


}
