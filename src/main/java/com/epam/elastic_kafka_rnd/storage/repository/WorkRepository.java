package com.epam.elastic_kafka_rnd.storage.repository;

import com.epam.elastic_kafka_rnd.storage.domain.Work;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data MongoDB repository for the Work entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WorkRepository extends MongoRepository<Work, String> {

}
