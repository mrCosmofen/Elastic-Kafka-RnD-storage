package com.epam.elastic_kafka_rnd.storage.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.epam.elastic_kafka_rnd.storage.domain.Work} entity.
 */
public class WorkDTO implements Serializable {

    private String id;

    private String type;

    private String genre;

    private String title;


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

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        WorkDTO workDTO = (WorkDTO) o;
        if (workDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), workDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "WorkDTO{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", genre='" + getGenre() + "'" +
            ", title='" + getTitle() + "'" +
            "}";
    }
}
