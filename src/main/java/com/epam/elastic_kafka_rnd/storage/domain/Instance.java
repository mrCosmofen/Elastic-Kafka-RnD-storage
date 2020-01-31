package com.epam.elastic_kafka_rnd.storage.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.io.Serializable;
import java.time.Instant;

/**
 * A Instance.
 */
@Document(collection = "instance")
public class Instance implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("type")
    private String type;

    @Field("title")
    private String title;

    @Field("language")
    private String language;

    @Field("publication_date")
    private Instant publicationDate;

    @DBRef
    @Field("work")
    @JsonIgnoreProperties("instances")
    private Work work;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public Instance type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public Instance title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLanguage() {
        return language;
    }

    public Instance language(String language) {
        this.language = language;
        return this;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Instant getPublicationDate() {
        return publicationDate;
    }

    public Instance publicationDate(Instant publicationDate) {
        this.publicationDate = publicationDate;
        return this;
    }

    public void setPublicationDate(Instant publicationDate) {
        this.publicationDate = publicationDate;
    }

    public Work getWork() {
        return work;
    }

    public Instance work(Work work) {
        this.work = work;
        return this;
    }

    public void setWork(Work work) {
        this.work = work;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Instance)) {
            return false;
        }
        return id != null && id.equals(((Instance) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Instance{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", title='" + getTitle() + "'" +
            ", language='" + getLanguage() + "'" +
            ", publicationDate='" + getPublicationDate() + "'" +
            "}";
    }
}
