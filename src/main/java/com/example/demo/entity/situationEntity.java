package com.example.demo.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "situations")
public class situationEntity {

    @Id
    private Long id;
    private String situation;

    // Getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "JpMove{" +
                "id=" + id +
                ", situation='" + situation +
                '}';
    }
}
