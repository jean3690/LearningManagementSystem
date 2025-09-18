package org.lms.config;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpSession;
import jakarta.websocket.HandshakeResponse;
import jakarta.websocket.server.HandshakeRequest;
import jakarta.websocket.server.ServerEndpointConfig;
import lombok.extern.slf4j.Slf4j;
import org.lms.utils.JwtUtil;

import java.util.Map;
import java.util.UUID;
@Slf4j
public class GetHttpSessionConfig extends ServerEndpointConfig.Configurator {
    @Override
    public void modifyHandshake(ServerEndpointConfig sec, HandshakeRequest request, HandshakeResponse response) {
        HttpSession httpSession = (HttpSession) request.getHttpSession();
        String token = (String) httpSession.getAttribute("token");
        if(token==null || token.trim().isEmpty()){
            throw new RuntimeException("WebSocket握手失败,缺少JWT TOKEN");
        }
        Claims claims;
        try {
            claims = JwtUtil.parseJWT(token);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        Map<String, Object> properties = sec.getUserProperties();
        properties.put("userId",claims.getSubject());
        String sessionKey = UUID.randomUUID().toString().replaceAll("-", "");
        properties.put("Connected",sessionKey);
        log.info("websocket用户已认证:"+claims.getSubject());
    }
}
