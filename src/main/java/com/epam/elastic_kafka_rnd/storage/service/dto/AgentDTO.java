package com.epam.elastic_kafka_rnd.storage.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.epam.elastic_kafka_rnd.storage.domain.Agent} entity.
 */
public class AgentDTO implements Serializable {

    private String id;

    private String firstName;

    private String lastName;


    private String workId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getWorkId() {
        return workId;
    }

    public void setWorkId(String workId) {
        this.workId = workId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AgentDTO agentDTO = (AgentDTO) o;
        if (agentDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), agentDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AgentDTO{" +
            "id=" + getId() +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", workId=" + getWorkId() +
            "}";
    }
}
