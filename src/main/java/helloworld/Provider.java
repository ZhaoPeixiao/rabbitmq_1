package helloworld;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author Peixiao Zhao
 * @date 2020/11/7 22:40
 */
public class Provider {

    // 生产消息
    @Test
    public void testSendMessage() throws IOException, TimeoutException {
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

        // 通过连接获取连接通道
        Channel channel = connection.createChannel();

        // 通道绑定对应的消息队列
        // 参数1: queue 队列名 队列不存在的情况下自动创建
        // 参数2: durable 是否持久化 true 表示持久化
        // 参数3: exclusive 是否独占队列 true表示独占
        // 参数4: autoDelete 是否在消费完成后自动删除 true自动删除
        // 参数5: arguments 附加额外参数
        channel.queueDeclare("hello", false, false, false, null);

        // 发布消息
        // 参数1: exchange 交换机名称
        // 参数2: routingKey 队列名称
        // 参数3: props 传递消息的额外设置
        // 参数4: 消息具体内容 bytes
        channel.basicPublish("", "hello", null, "hello rabbit".getBytes());

        channel.close();
        connection.close();
    }
}
