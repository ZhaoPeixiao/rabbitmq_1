package direct;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import utils.RabbitMQUtils;

import java.io.IOException;

/**
 * @author Peixiao Zhao
 * @date 2020/11/11 22:22
 */
public class Provider {
    private static final String EXCHANGE_NAME = "direct_logs";

    public static void main(String[] args) throws IOException {
        Connection connection = RabbitMQUtils.getConnection();
        Channel channel = connection.createChannel();

        // 通道声明交换机
        // 参数2 direct -> Route
        channel.exchangeDeclare(EXCHANGE_NAME, "direct");

        // 发送消息
        String routeKey = "error";
        String message = "direct type message: [" + routeKey + "]";
        channel.basicPublish(EXCHANGE_NAME, routeKey, null, message.getBytes());

        // 关闭资源
        RabbitMQUtils.closeConnectionAndChannel(channel, connection);

    }
}
