package com.dins.kafka_app.service;

import com.dins.kafka_app.model.DataTableOne;
import com.dins.kafka_app.service.kafka.KafkaConsumerService;
import com.dins.kafka_app.service.kafka.KafkaProducerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DataTransportService {

    private static Logger LOG = LoggerFactory
            .getLogger(DataTransportService.class);

    @Autowired
    private DBService dbService;

    @Autowired
    private FileReaderService fileReaderService;

    @Autowired
    private KafkaProducerService producerService;

    @Autowired
    private KafkaConsumerService consumerService;

    private List<DataTableOne> allRecordsFromTableOne;
    private List<DataTableOne> records;

    public void fromFileToTableOne(){
        dbService.saveAllToTableOne(fileReaderService.getNamesFromFile());
    }

    public void fromTableOneToKafka(){
        allRecordsFromTableOne = dbService.getAllRecordsFromTableOne();
        if(!allRecordsFromTableOne.isEmpty()){
            allRecordsFromTableOne.forEach(producerService::sendMessage);
        }
        else {
            LOG.info("Table one is empty");
        }
    }

    public void fromKafkaToTableTwo(){
        records = consumerService.getRecords();
        if(!records.isEmpty()){
            records.forEach(dbService::saveToTableTwo);
        }
        else{
            LOG.info("No messages were received from the kafka topic");
        }
    }

}
