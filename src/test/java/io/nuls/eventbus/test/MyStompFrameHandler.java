package io.nuls.eventbus.test;

import io.nuls.eventbus.model.ChatMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;

import java.lang.reflect.Type;

/**
 * Created by wangkun23 on 2018/11/16.
 */
public class MyStompFrameHandler implements StompFrameHandler {
    private final static Logger logger = LoggerFactory.getLogger(SockJsClientTest.class);

    /**
     * Invoked before {@link #handleFrame(StompHeaders, Object)} to determine the
     * type of Object the payload should be converted to.
     *
     * @param headers the headers of a message
     */
    @Override
    public Type getPayloadType(StompHeaders headers) {
        return ChatMessage.class;
    }

    /**
     * Handle a STOMP frame with the payload converted to the target type returned
     * from {@link #getPayloadType(StompHeaders)}.
     *
     * @param headers the headers of the frame
     * @param payload the payload, or {@code null} if there was no payload
     */
    @Override
    public void handleFrame(StompHeaders headers, Object payload) {
        logger.info("handleFrame");
        ChatMessage msg= (ChatMessage)payload;
        logger.info("handleFrame:{}",msg);
    }
}
