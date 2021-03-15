package com.example.socketinho.demo;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

@Controller
public class GreetingController {
    //Na abordagem spring com stomp messaging,
    // as mensagens stomp podem ser roteadas para o controller
    //ou seja, O greeting controller é mapeado para controlar as mensagems via a tag /hello(só para exemplo)

    @MessageMapping("/hello")//Message mapping garante que a mensagem vai chegar/foi enviada para o /hello
    @SendTo("/topic/greetings")
    public Greeting greeting(Message message) throws Exception{
        Thread.sleep(1000);//delay simulado
        //após esse delay é criado um novo objeto greetings
        // que retorna a mensagem transmitida para todos os inscritos no topico greeting
        Greeting greetingData = new Greeting();
        greetingData.setName(HtmlUtils.htmlEscape(message.getName()));
        return  greetingData;
    }
}
