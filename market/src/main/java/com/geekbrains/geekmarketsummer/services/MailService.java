package com.geekbrains.geekmarketsummer.services;

import contract.entities.Order;
import contract.entities.OrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;


@Service
public class MailService {

    private JavaMailSender javaMailSender;

    @Autowired
    public void setJavaMailSender(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendOrderNotification(Order order) throws MessagingException {
        String userEmail = order.getUser().getEmail();
        StringBuilder sb = new StringBuilder();
        sb.append("Уважаемый ")
                .append(order.getUser().getFirstName())
                .append(" ")
                .append(order.getUser().getLastName())
                .append(", благодарим Вас за заказ сделанный в нашем магазине!\n\n")
                .append("Статус Вашего заказа: ")
                .append(order.getStatus().getTitle())
                .append("\n\n")
                .append("Содержимое заказа:\n");

        for (OrderItem item : order.getOrderItems()) {
            sb.append(item.getProduct().getTitle())
                    .append(" - ")
                    .append(item.getQuantity())
                    .append(" x ")
                    .append(item.getItemPrice())
                    .append(" = ")
                    .append(item.getTotalPrice())
                    .append("\n");
        }
        sb.append("\nСумма заказа: ")
                .append(order.getPrice());

        sb.append("\nАдрес доставки: ")
                .append(order.getDeliveryAddress().getAddress())
                .append("\n");

        sendMessage(userEmail, sb.toString());
    }

    public void sendMessage(String to, String text) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom("andruxa.ic@gmail.com");
        helper.setTo(to);
        helper.setSubject("Информация о заказе в GeekSummerMarket");
        helper.setText(text);

        javaMailSender.send(message);
    }
}
