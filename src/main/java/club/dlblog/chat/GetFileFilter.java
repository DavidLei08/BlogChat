package club.dlblog.chat;

import club.dlblog.chat.util.UeditorUtil;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @author machenike
 */
@Component
@WebFilter("/file")
@Order(1)
public class GetFileFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        if(request.getRequestURI().startsWith("/file")){
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
            return;
        }
        filterChain.doFilter(request, response);
    }
}
