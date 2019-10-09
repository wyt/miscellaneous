## Sample 1

This sample demonstrates a simple producer and consumer; the producer sends objects of type `Foo1` and the consumer receives objects of type `Foo2` (the objects have the same field, `foo`).
这是一个简单的demo一个生产者一个消费者; 生产者Foo1对象,消费者接受对象类型为Foo2。对象具有相同的字段foo

The producer uses a `JsonSerializer`; the consumer uses the `StringDeserializer` that is automatically configured by Spring Boot, together with a `StringJsonMessageConverter` which converts to the type of the listener method argument.
生产者使用JsonSerializer,消费者使用Spring Boot自动配置的StringDeserializer,

Run the application and use curl to send some data:

`$ curl -X POST http://localhost:8080/send/foo/bar`

Console:

`2018-11-05 10:03:40.216  INFO 39766 --- [ fooGroup-0-C-1] com.example.Application                  : Received: Foo2 [foo=bar]`

The consumer is configured with a `SeekToCurrentErrorHandler` which replays failed messages up to 3 times and, after retries are exhausted, sends a bad message to a dead-letter topic.

A second `@KafkaListener` consumes the raw JSON from the message.

`2018-11-05 10:12:32.552  INFO 41635 --- [ fooGroup-0-C-1] com.example.Application                  : Received: Foo2 [foo=fail]`
`2018-11-05 10:12:32.561 ERROR 41635 --- [ fooGroup-0-C-1] essageListenerContainer$ListenerConsumer : Error handler threw an exception`
`...`
`2018-11-05 10:12:33.033  INFO 41635 --- [ fooGroup-0-C-1] com.example.Application                  : Received: Foo2 [foo=fail]`
`2018-11-05 10:12:33.033 ERROR 41635 --- [ fooGroup-0-C-1] essageListenerContainer$ListenerConsumer : Error handler threw an exception`
`...`
`2018-11-05 10:12:33.537  INFO 41635 --- [ fooGroup-0-C-1] com.example.Application                  : Received: Foo2 [foo=fail]`
`2018-11-05 10:12:43.359  INFO 41635 --- [ dltGroup-0-C-1] com.example.Application                  : Received from DLT: {"foo":"fail"}`
