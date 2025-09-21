package org.lms.channel;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpSession;
import jakarta.websocket.*;
import jakarta.websocket.server.ServerEndpoint;
import lombok.extern.slf4j.Slf4j;
import org.lms.config.GetHttpSessionConfig;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint(value = "/channel/echo",configurator = GetHttpSessionConfig.class)
@Slf4j
@CrossOrigin(origins = "*")
public class EchoChannel {
    private static final Map<String, Session> group = new ConcurrentHashMap<>();
    private Session session;
    private String sessionKey;
    @OnMessage
    public void onMessage(String message){
        this.session.getAsyncRemote().sendText(message);
    }

    @OnOpen
    public void onOpen(Session session, EndpointConfig endpointConfig){
        this.session = session;
        this.sessionKey = (String) endpointConfig.getUserProperties().get("Connected");
        group.put(sessionKey,session);
        log.info("websocket建立成功");
    }
    @OnClose
    public void onClose(CloseReason closeReason){
         String key = this.sessionKey;
         if(key!=null){
             group.remove(key);
         }
         log.info("websocket关闭连接");
    }
    @OnError
    public void onError(Throwable throwable) throws IOException {
        this.session.close(new CloseReason(CloseReason.CloseCodes.UNEXPECTED_CONDITION,throwable.getMessage()));
    }
    public void broadcast(String message){
        Set<Map.Entry<String, Session>> entries =
                group.entrySet();
        for (Map.Entry<String, Session> entry : entries) {
            Session userSession = entry.getValue();
            userSession.getAsyncRemote().sendText(message);
        }
    }
}
