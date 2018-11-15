package io.nuls.eventbus;

import io.nuls.eventbus.model.Greeting;
import io.nuls.eventbus.model.HelloMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by wangkun23 on 2018/11/15.
 */
@Controller
@Slf4j
public class GreetingController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Greeting greeting(HelloMessage message) throws Exception {
        log.info("Received hello: {}", message.getName());
        return new Greeting("Hello, " + message.getName() + "!");
    }

    @GetMapping("/test")
    @ResponseBody
    public Greeting test() {
        Greeting greeting = new Greeting("Hello,test!");
        messagingTemplate.convertAndSend("/topic/greetings", greeting);
        return greeting;
    }
}
