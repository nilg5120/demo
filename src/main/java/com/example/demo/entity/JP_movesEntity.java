package com.example.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "jp_moves")
public class JP_movesEntity {

    @Id
    private Long id;
    private String name;
    private String input;
    private Integer startup;
    private Integer usagedg;
    private Integer usagesa;

    // Getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public Integer getStartup() {
        return startup;
    }

    public void setStartup(Integer startup) {
        this.startup = startup;
    }

    public Integer getUsagedg() {
        return usagedg;
    }

    public void setUsagedg(Integer usagedg) {
        this.usagedg = usagedg;
    }

    public Integer getUsagesa() {
        return usagesa;
    }

    public void setUsagesa(Integer usagesa) {
        this.usagesa = usagesa;
    }

    // toString method for debugging purposes

    @Override
    public String toString() {
        return "JpMove{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", input='" + input + '\'' +
                ", startup=" + startup +
                '}';
    }
}
