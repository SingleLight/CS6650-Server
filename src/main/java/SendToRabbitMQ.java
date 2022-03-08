import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.MessageProperties;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Queue;

public class SendToRabbitMQ {

  private static final String TASK_QUEUE_NAME = "task_queue";

  public void sendToQueue(String message, Queue<Channel> queue) throws Exception {
    try {
      Channel channel = queue.poll();
      assert channel != null;
      channel.queueDeclare(TASK_QUEUE_NAME, true, false, false, null);
      channel.basicPublish("", TASK_QUEUE_NAME,
          MessageProperties.BASIC,
          message.getBytes(StandardCharsets.UTF_8));
      queue.offer(channel);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
