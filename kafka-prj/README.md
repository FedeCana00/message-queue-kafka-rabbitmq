In this project sample, the Kafka configuration utilizes the `acks` property to **all** to ensure disk persistence. 
This means that the producer will await confirmation from all brokers before considering a message delivered.

Additionally, the consumer is configured with `auto.offset.reset` set to **earliest**, allowing it to read from a previous point in the Kafka message log in case a consumer joins an already running consumer group and needs to recover any missed events or reread from a previous point.

The `errorHandler` method, defined in KafkaConfig.java, returns an instance of DefaultErrorHandler, which is part of the Spring Kafka libraries and is used to handle errors during the processing of Kafka messages by consumers.
<br/>
The **DeadLetterPublishingRecoverer** is a recoverer that sends messages causing errors to a "dead letter queue" (DLQ). This is a typical pattern for handling problematic messages that cannot be processed correctly by consumers.
<br/>
The **FixedBackOff** specifies a retry policy with a fixed delay (1000 milliseconds) and a maximum number of attempts (in our case: 2). This is used to retry processing messages in case of temporary errors.