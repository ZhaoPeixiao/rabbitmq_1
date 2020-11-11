package fanout;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import utils.RabbitMQUtils;

import java.io.IOException;

/**
 * @author Peixiao Zhao
 * @date 2020/11/11 21:57
 */
public class Provider {

    public static void main(String[] args) throws IOException {
        Connection connection = RabbitMQUtils.getConnection();
        Channel channel = connection.createChannel();

        // 通道声明指定交换机
        // 参数1 交换机名称
        // 参数2 交换机类型 fanout表示广播
        channel.exchangeDeclare("logs", "fanout");

        // 发送消息
        String messgae = "fanout type message";
        channel.basicPublish("logs", "", null, messgae.getBytes());

        // 释放资源
        RabbitMQUtils.closeConnectionAndChannel(channel, connection);
    }
}
