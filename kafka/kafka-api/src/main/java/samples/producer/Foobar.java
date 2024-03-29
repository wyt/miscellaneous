package samples.producer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

/** Kafka生产者API https://www.jianshu.com/p/cbf64c1f9fa8 */
public class Foobar {

  public static final String BOOTSTRAP_SERVERS =
      "192.168.101.146:9092,192.168.101.146:9093,192.168.101.146:9094";
  public static final String MY_TEST_TOPIC = "my-replicated-topic-003";

  public static void main(String[] args) {
    Properties props = new Properties();
    props.put("bootstrap.servers", BOOTSTRAP_SERVERS);
    // 等待所有副本节点的应答
    props.put("acks", "all");
    props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
    props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

    Producer<String, String> producer = new KafkaProducer<>(props);

    for (int i = 0; i < 10; i++) {
      ProducerRecord record =
          new ProducerRecord<>(MY_TEST_TOPIC, Integer.toString(i), UUID.randomUUID().toString());
      try {
        producer.send(record).get();
      } catch (InterruptedException e) {
        e.printStackTrace();
      } catch (ExecutionException e) {
        e.printStackTrace();
      }
      System.out.println(i + " send sucessfully.");
    }
    producer.close();
  }
}
