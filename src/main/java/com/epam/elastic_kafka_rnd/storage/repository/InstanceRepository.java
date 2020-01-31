package com.epam.elastic_kafka_rnd.storage.repository;

import com.epam.elastic_kafka_rnd.storage.domain.Instance;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data MongoDB repository for the Instance entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InstanceRepository extends MongoRepository<Instance, String> {

}
