package io.nuls.eventbus.test;

import io.nuls.eventbus.model.ChatMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.converter.StringMessageConverter;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.socket.*;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.RestTemplateXhrTransport;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

/**
 * Created by wangkun23 on 2018/11/15.
 */
public class SockJsClientTest {

    private final static Logger logger = LoggerFactory.getLogger(SockJsClientTest.class);

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        List transports = new ArrayList<>();
        transports.add(new WebSocketTransport(new StandardWebSocketClient()));
        SockJsClient sockJsClient = new SockJsClient(transports);
//        ListenableFuture<WebSocketSession> listenableFuture = sockJsClient.doHandshake(new TestWebSocketHandler(),
//                "ws://127.0.0.1:8080/ws");

        WebSocketStompClient stompClient = new WebSocketStompClient(sockJsClient);
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());
       stompClient.connect("ws://127.0.0.1:8080/ws", new TestWebSocketHandler());

        new Scanner(System.in).nextLine(); //Don't close immediately.
    }

    private static class TestWebSocketHandler implements StompSessionHandler {

        @Override
        public void afterConnected(StompSession stompSession, StompHeaders stompHeaders) {
            logger.info("client connection afterConnected");
            ChatMessage greeting = new ChatMessage();
            greeting.setSender("tedfdf");
            greeting.setContent("ChatMessage");
            greeting.setType(ChatMessage.MessageType.CHAT);
            stompSession.send("/topic/public", greeting);
            stompSession.subscribe("/topic/public", new MyStompFrameHandler());
            //stompSession.sendMessage(new TextMessage("hello websocket server!"));
        }

        @Override
        public void handleException(StompSession stompSession, StompCommand stompCommand, StompHeaders stompHeaders, byte[] bytes, Throwable throwable) {
            logger.info("[client] error:" + throwable.getMessage());
        }

        @Override
        public void handleTransportError(StompSession stompSession, Throwable throwable) {
            logger.info("[client] error:" + throwable.getMessage());
        }

        @Override
        public Type getPayloadType(StompHeaders stompHeaders) {
            return ChatMessage.class;
        }

        @Override
        public void handleFrame(StompHeaders stompHeaders, Object o) {
            logger.info("[client] message:handleFrame");
            logger.info("[client] message:" + o.toString());
        }
    }
}
