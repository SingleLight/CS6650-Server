import com.rabbitmq.client.Channel;
import java.io.IOException;
import java.util.Queue;

public class SendToRabbitMQ {

  private final String queueName;

  public SendToRabbitMQ(String queueName) {
    this.queueName = queueName;
  }

  public void sendToQueue(String message, Queue<Channel> queue) throws Exception {
    try {
      Channel channel = queue.poll();
      assert channel != null;
      channel.basicPublish("direct_exchange", queueName, null, message.getBytes());
      queue.offer(channel);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
