package topic;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import utils.RabbitMQUtils;

import java.io.IOException;

/**
 * @author Peixiao Zhao
 * @date 2020/11/11 22:55
 */
public class Provider {
    private static final String EXCHANGE_NAME = "topic_logs";

    public static void main(String[] args) throws IOException {
        Connection connection = RabbitMQUtils.getConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, "topic");

        String routingKey = "user.save.all";
        String message = "topic type message: [" + routingKey + "]";

        channel.basicPublish(EXCHANGE_NAME, routingKey, null, message.getBytes());

        RabbitMQUtils.closeConnectionAndChannel(channel, connection);
    }
}
