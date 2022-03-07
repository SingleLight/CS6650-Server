import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.MessageProperties;
import java.nio.charset.StandardCharsets;

public class SendToRabbitMQ {

  private static final String TASK_QUEUE_NAME = "task_queue";

  public static void sendToQueue(String message, Connection connection) throws Exception {
    try (Channel channel = connection.createChannel()) {
      channel.queueDeclare(TASK_QUEUE_NAME, true, false, false, null);
      channel.basicPublish("", TASK_QUEUE_NAME,
          MessageProperties.PERSISTENT_TEXT_PLAIN,
          message.getBytes(StandardCharsets.UTF_8));
    }
  }
}
