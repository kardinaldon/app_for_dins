package com.dins.kafka_app.repository;

import com.dins.kafka_app.model.DataTableOne;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepositoryForTableOne extends JpaRepository<DataTableOne, Integer> {

    @Override
    List<DataTableOne> findAll();

}
