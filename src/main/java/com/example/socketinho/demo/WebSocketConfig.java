package com.example.socketinho.demo;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker //permite o websocket controlar as mensagens enviadas pelo message broker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    //é o metodo padrão para configurar o message broker
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        //estamos permitindo um message broker pela memória
        // para carregar a mensagem de boas vindas para o cliente de destino prefixado com /topic
        registry.enableSimpleBroker("/topic");
        //estamos definindo um prefixe para metodos destinados com o /app no messaging mapping
        //esse prefixo é usado para definir todas os mapeamentos de mensagem para o /app
        registry.setApplicationDestinationPrefixes("/app");
    }

    //registrando o endpoint com o mapeamento para o /teste
    //aqui vamos permitir o SockJS fallback, para usar como alternativas para transportar as mensagens
    //caso o websocket não esteja disponível
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        //o sockJS vai tentar conectar e usar o melhor transportador de mensagens
        // sejam eles(websocket, xhr-streaming, xhr-polling, and so on)
        registry.addEndpoint("/teste").withSockJS();
    }
}
