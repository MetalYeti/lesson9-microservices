package com.geekbrains.geekmarketsummer.services;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.stereotype.Service;

@Service
public class SendMessageService {
    private String queueName;
    private String message;
    private static final String HOST = "localhost";
    private ConnectionFactory factory;
    
    public SendMessageService() {
        factory = new ConnectionFactory();
        factory.setHost(HOST);
    }
    
    public void sendMessage(String queueName, String message) {
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()){
             channel.queueDeclare(queueName, false, false, false, null);
             channel.basicPublish("", queueName, null, message.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
