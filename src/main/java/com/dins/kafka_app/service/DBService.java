package com.dins.kafka_app.service;

import com.dins.kafka_app.service.kafka.KafkaProducerService;
import com.dins.kafka_app.model.DataTableOne;
import com.dins.kafka_app.model.DataTableTwo;
import com.dins.kafka_app.repository.RepositoryForTableOne;
import com.dins.kafka_app.repository.RepositoryForTableTwo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDateTime;
import java.util.List;

@Service
public class DBService {

    private static Logger LOG = LoggerFactory
            .getLogger(DBService.class);

    @Autowired
    private KafkaProducerService kafkaProducerService;

    @Autowired
    private RepositoryForTableOne repositoryForTableOne;

    @Autowired
    private RepositoryForTableTwo repositoryForTableTwo;

    private DataTableOne dataTableOne;

    private List<String> names;

    @Transactional
    public void saveAllToTableOne(List<String> names){
        if(!names.isEmpty()){
            for(String name : names){
                dataTableOne = new DataTableOne();
                dataTableOne.setName(name);
                dataTableOne.setTimeStamp(LocalDateTime.now());
                repositoryForTableOne.save(dataTableOne);
            }
        }
        else {
            LOG.info("the list of random names is empty");
        }
    }

    public List<DataTableOne> getAllRecordsFromTableOne(){
        return repositoryForTableOne.findAll();
    }

    public void saveToTableTwo(DataTableOne dataTableOne){
        try {
            repositoryForTableTwo.save(new DataTableTwo(dataTableOne.getId(),
                    dataTableOne.getName(),
                    dataTableOne.getTimeStamp()));
        }
        catch (Exception ex) {
            LOG.error("Exception when saving data to table 2",ex);
        }
    }
}
