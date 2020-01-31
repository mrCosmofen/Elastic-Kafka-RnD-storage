package com.epam.elastic_kafka_rnd.storage.repository;

import com.epam.elastic_kafka_rnd.storage.domain.Agent;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data MongoDB repository for the Agent entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AgentRepository extends MongoRepository<Agent, String> {

}
