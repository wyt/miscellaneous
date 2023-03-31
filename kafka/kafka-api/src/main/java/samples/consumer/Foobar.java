package samples.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

/** Kafka消费者API https://www.jianshu.com/p/6c0c012978f8 */
public class Foobar {

  public static void main(String[] args) {

    Properties props = new Properties();
    props.put("bootstrap.servers", "10.20.140.16:9092");
    props.put("group.id", "foobar");
    props.put("enable.auto.commit", "true");
    props.put("auto.commit.interval.ms", "1000");
    props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
    props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

    KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);

    consumer.subscribe(Arrays.asList("ycUsrRdNews"));

    for (; ; ) {
      ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(1000));
      for (ConsumerRecord<String, String> record : records) {
        System.out.printf(
            "partition = %d, offset = %d, timestamp= %d, key = %s, value = %s%n",
            record.partition(), record.offset(), record.timestamp(), record.key(), record.value());
      }
    }
  }
}
