package club.dlblog.chat.socketio.util;

import club.dlblog.chat.bean.SocketMessageBean;
import club.dlblog.chat.socketio.handler.NettySocketHandler;
import club.dlblog.chat.util.JsonTransferUtil;
import com.corundumstudio.socketio.SocketIOClient;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * socket发送消息用工具类
 * @author machenike
 */
public class NettySocketUtil {

    /**
     * 保存client资源Map
     */
    static Map<String, SocketIOClient> socketMap;

    static{
        //client资源Map赋值
        socketMap =  NettySocketHandler.clientMap;
    }

    /**
     * 发送消息 指定客户端 指定event
     * @param clientId
     * @param event
     * @param message
     */
    public static void sendMessage(String clientId,String event,Object message){
        socketMap.get(clientId).sendEvent(event,message);
    }

    public static void sendMessageByGroup(List<String> clientIds,String event,Object message){
        for(String clientId:clientIds) {
            socketMap.get(clientId).sendEvent(event, message);
        }
    }

    /**
     * 发送消息 指定客户端
     * @param clientId
     * @param message
     */
    public static void sendMessage(String clientId,Object message){
        socketMap.get(clientId).sendEvent("message",message);
    }

    /**
     * 发送消息 全部客户端
     * @param message
     */
    public static void sendNotice(Object message){
        Set<String> clientIdSet = socketMap.keySet();
        for(String clientId:clientIdSet){
            socketMap.get(clientId).sendEvent("message",message);
        }
    }
    public static void sendAll(SocketMessageBean bean){
        Set<String> clientIdSet = socketMap.keySet();
        SocketMessageBean  messageBean = new SocketMessageBean();
        bean.setDate(LocalDateTime.now());
        BeanUtils.copyProperties(bean, messageBean);
        for(String clientId:clientIdSet){
            if (messageBean.getClientId().equals(clientId)) {
                messageBean.setSelf(true);
            }
            socketMap.get(clientId).sendEvent("message", JsonTransferUtil.getJSONStr(messageBean));
        }
    }


    /**
     * 发送消息 指定event 全部客户端
     * @param message
     */
    public static void sendNotice(Object message,String event){
        Set<String> clientIdSet = socketMap.keySet();
        for(String clientId:clientIdSet){
            socketMap.get(clientId).sendEvent(event,message);
        }
    }
}
