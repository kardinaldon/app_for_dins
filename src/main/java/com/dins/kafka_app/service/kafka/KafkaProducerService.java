package com.dins.kafka_app.service.kafka;

import com.dins.kafka_app.KafkaApplication;
import com.dins.kafka_app.model.DataTableOne;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {

    private static Logger LOG = LoggerFactory
            .getLogger(KafkaApplication.class);

    @Value("${spring.kafka.template.default-topic}")
    private String topic;

    @Autowired
    private KafkaTemplate<String, DataTableOne> kafkaTemplate;

    public void sendMessage(DataTableOne dataTableOne) {
        try{
            kafkaTemplate.send(topic, dataTableOne);
            LOG.info("Message sent to topic " + topic + " " + dataTableOne.toString());
        }
        catch (Exception ex){
            LOG.error("Exception in Kafka Producer", ex);
        }
    }

}
