package com.dins.kafka_app.service.kafka;

import com.dins.kafka_app.model.DataTableOne;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.*;

@Service
public class KafkaConsumerService {

    private static Logger LOG = LoggerFactory
            .getLogger(KafkaConsumerService.class);
    private List<DataTableOne> dataTableOneList;
    private Properties properties ;
    private Consumer<String, DataTableOne> consumer;
    private ConsumerRecords<String, DataTableOne> consumerRecords;
    private TopicPartition topicPartition;
    private List<TopicPartition> topicPartitionList;
    private Iterator<ConsumerRecord<String, DataTableOne>> recordIterator;
    private ConsumerRecord<String, DataTableOne> consumerRecord;

    @Value("${spring.kafka.consumer.bootstrap-servers}")
    private String server;

    @Value("${spring.kafka.template.default-topic}")
    private String topicName;

    @Value("${spring.kafka.consumer.group-id}")
    private String groupName;

    @Value("${spring.kafka.consumer.max-poll-records}")
    private String maxPollRecords;

    @Value("${spring.kafka.consumer.properties.spring.json.trusted.packages}")
    private String trustedPackage;

    public List<DataTableOne> getRecords() {
        dataTableOneList = new ArrayList<>();
        properties  = new Properties();
        properties.put(ConsumerConfig.GROUP_ID_CONFIG,
                groupName);
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
                server);
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
                StringDeserializer.class.getName());
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
                JsonDeserializer.class.getName());
        properties.put(JsonDeserializer.TRUSTED_PACKAGES, trustedPackage);
        properties.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, maxPollRecords);
        try{
            consumer = new KafkaConsumer<>(properties);
            topicPartition = new TopicPartition(topicName, 0);
            topicPartitionList = Arrays.asList(topicPartition);
            consumer.assign(topicPartitionList);
            consumer.seekToBeginning(topicPartitionList);
            consumerRecords = consumer.poll(Duration.ofMillis(30000));
            if (!consumerRecords.isEmpty()) {
                recordIterator = consumerRecords.iterator();
                while (recordIterator.hasNext()){
                    consumerRecord = recordIterator.next();
                    LOG.info("Message received from topic " + topicName + " " + consumerRecord.value().toString());
                    dataTableOneList.add(consumerRecord.value());
                }

            }
        }
        catch (Exception ex) {
            LOG.error("Exception in Kafka Consumer", ex);
        }
        finally {
            consumer.close();
        }
        return dataTableOneList;
    }
}
