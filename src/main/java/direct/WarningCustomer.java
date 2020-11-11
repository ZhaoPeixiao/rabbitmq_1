package direct;

import com.rabbitmq.client.*;
import utils.RabbitMQUtils;

import java.io.IOException;

/**
 * @author Peixiao Zhao
 * @date 2020/11/11 22:27
 */
public class WarningCustomer {
    private static final String EXCHANGE_NAME = "direct_logs";

    public static void main(String[] args) throws IOException {
        Connection connection = RabbitMQUtils.getConnection();
        Channel channel = connection.createChannel();

        // 绑定交换机
        channel.exchangeDeclare(EXCHANGE_NAME, "direct");

        // 临时队列
        String queueName  = channel.queueDeclare().getQueue();
        // 绑定交换机和队列
        channel.queueBind(queueName, EXCHANGE_NAME, "warning");

        channel.basicConsume(queueName, true, new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("Warning get message: " + new String(body));
            }
        });

    }
}
