package com.dins.kafka_app.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileReaderService {

    private static Logger LOG = LoggerFactory
            .getLogger(FileReaderService.class);

    private List<String> names;

    @Value("${application.random_names}")
    String randomNames;

    public List<String> getNamesFromFile(){
        names = new ArrayList<>();
        try(BufferedReader reader =
                    new BufferedReader
                            (new InputStreamReader
                                    (new ClassPathResource(randomNames).getInputStream()))) {
            reader.lines().forEach(names::add);
        }
        catch (FileNotFoundException exception){
            LOG.error("FileNotFoundException", exception);
        }
        catch (IOException exception) {
            LOG.error("IOException", exception);
        }
        return names;
    }
}
