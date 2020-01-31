package com.epam.elastic_kafka_rnd.storage.service.dto;
import java.time.Instant;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.epam.elastic_kafka_rnd.storage.domain.Instance} entity.
 */
public class InstanceDTO implements Serializable {

    private String id;

    private String type;

    private String title;

    private String language;

    private Instant publicationDate;


    private String workId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Instant getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(Instant publicationDate) {
        this.publicationDate = publicationDate;
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

        InstanceDTO instanceDTO = (InstanceDTO) o;
        if (instanceDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), instanceDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "InstanceDTO{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", title='" + getTitle() + "'" +
            ", language='" + getLanguage() + "'" +
            ", publicationDate='" + getPublicationDate() + "'" +
            ", workId=" + getWorkId() +
            "}";
    }
}
