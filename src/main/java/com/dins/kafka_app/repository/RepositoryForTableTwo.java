package com.dins.kafka_app.repository;

import com.dins.kafka_app.model.DataTableTwo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositoryForTableTwo extends JpaRepository<DataTableTwo, Integer> {
}
