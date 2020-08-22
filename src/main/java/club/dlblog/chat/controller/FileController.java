package club.dlblog.chat.controller;

import club.dlblog.chat.util.UeditorUtil;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @author machenike
 */
public class FileController {



    public void getFile(HttpServletRequest request, HttpServletResponse response){
        OutputStream outputStream = null;
        try {
            outputStream = response.getOutputStream();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        response.setContentType("image/*");
        try {
            outputStream.write(UeditorUtil.getFile(request.getRequestURI()));
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
