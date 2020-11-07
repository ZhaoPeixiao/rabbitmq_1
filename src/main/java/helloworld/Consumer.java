package helloworld;

import com.rabbitmq.client.*;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author Peixiao Zhao
 * @date 2020/11/7 22:57
 */
public class Consumer {

    @Test
    public void test() throws IOException, TimeoutException {
        // 创建连接mq的连接工厂
        ConnectionFactory connectionFactory = new ConnectionFactory();
        // 设置主机
        connectionFactory.setHost("127.0.0.1");
        // 设置端口号
        connectionFactory.setPort(5672);
        // 设置连接虚拟主机
        connectionFactory.setVirtualHost("/ems");
        // 设置用户名密码
        connectionFactory.setUsername("ems");
        connectionFactory.setPassword("123");

        // 获取连接对象
        Connection connection = connectionFactory.newConnection();

        // 创建通道
        Channel channel = connection.createChannel();

        // 通道绑定队列
        channel.queueDeclare("hello", false, false, false, null);

//        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
        // 消费消息
//        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
//            String message = new String(delivery.getBody(), "UTF-8");
//            System.out.println(" [x] Received '" + message + "'");
//        };
//        channel.basicConsume("hello", true, deliverCallback, consumerTag -> { });
        channel.basicConsume("hello", true, new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("New Message: " + new String(body));
            }
        });
//        channel.close();
//        connection.close();
    }
}
