package com.kob.backend.consumer;

import com.alibaba.fastjson2.JSONObject;
import com.kob.backend.consumer.utils.Game;
import com.kob.backend.consumer.utils.JWTAuthentication;
import com.kob.backend.mapper.UserMapper;
import com.kob.backend.pojo.User;
import jakarta.websocket.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jackson.JsonObjectDeserializer;
import org.springframework.stereotype.Component;

import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;

import java.io.IOException;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Component
@ServerEndpoint("/websocket/{token}")  // 注意不要以'/'结尾
public class WebSocketServer {
    //不是单例的，所以不能用@Autowired注入
    // 单例：每一个类在同一时间只能有一个实例
    private static UserMapper userMapper;
    @Autowired
    public void setUserMapper(UserMapper userMapper){
        WebSocketServer.userMapper = userMapper;
    }
    final private static ConcurrentHashMap<Integer, WebSocketServer> users = new ConcurrentHashMap<>();
    final private static Set<User> matchPool = Collections.newSetFromMap(new ConcurrentHashMap<>());
    private User user;
    private Session session;

    private Game game = null;
    @OnOpen
    public void onOpen(Session session, @PathParam("token") String token) throws IOException {
        // 建立连接
        this.session = session;
        System.out.println("connected");
        Integer userId = JWTAuthentication.getUserID(token);
        //获取用户
        this.user = userMapper.selectById(userId);
        if(this.user != null){
            users.put(this.user.getId(), this);
        }else{
            this.session.close();
        }
        System.out.println(users);
    }

    @OnClose
    public void onClose() {
        // 关闭链接
        System.out.println("closed");
        //删去用户
        if(this.user != null){
            users.remove(this.user.getId());
            matchPool.remove(this.user);
        }
    }

    private void startMatching(){
        //开始匹配
        System.out.println("start matching");
        matchPool.add(this.user);
        System.out.println(matchPool);
        while(matchPool.size() >= 2){
            Iterator<User> it = matchPool.iterator();
            User a = it.next(), b = it.next();
            matchPool.remove(a);
            matchPool.remove(b);

            Game game = new Game(13,14,20);
            game.createMap();

            JSONObject respA = new JSONObject();
            respA.put("event", "matching-success");
            respA.put("username", b.getUsername());
            respA.put("photo", b.getPhoto());
            respA.put("gamemap", game.getG());
            users.get(a.getId()).sendMessage(respA.toJSONString());

            JSONObject respB = new JSONObject();
            respB.put("event", "matching-success");
            respB.put("username", a.getUsername());
            respB.put("photo", a.getPhoto());
            respB.put("gamemap", game.getG());
            users.get(b.getId()).sendMessage(respB.toJSONString());
        }
    }

    private void stopmatching(){
        // 取消匹配
        System.out.println("stop matching");
        matchPool.remove(this.user);
    }

    @OnMessage
    public void onMessage(String message, Session session) { //当做路由
        // 从Client接收消息
        System.out.println("received message: " + message);
        JSONObject data = JSONObject.parseObject(message);
        String event = data.getString("event");
        if("start-matching".equals(event)) {
            //开始匹配
            startMatching();
        }else if("stop-matching".equals(event)){
            //停止匹配
            stopmatching();
        }
    }

    @OnError
    public void onError(Session session, Throwable error) {
        error.printStackTrace();
    }

    public void sendMessage(String message) {
        // 向Client发送消息
       synchronized (this.session) {
           try{
                this.session.getBasicRemote().sendText(message);
           } catch(IOException e){
               e.printStackTrace();
           }
        }
    }
}
