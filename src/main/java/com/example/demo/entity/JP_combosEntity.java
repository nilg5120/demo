package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "jp_combos")
public class JP_combosEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String name;
    private int damage;
    private String input;
    private Integer startup;
    private Integer usagedg;
    private Integer usagesa;

    @Column(name = "`explain`") // バッククォートで囲むことで予約語をエスケープ
    private String explain;

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

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
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

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }

    // toString method for debugging purposes

    @Override
    public String toString() {
        return "JpCombo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", damage='" + damage + '\'' +
                ", input='" + input + '\'' +
                ", startup=" + startup + '\'' +
                ", explain='" + explain + '\'' +
                ", usagedg=" + usagedg + '\'' +
                ", usagesa=" + usagesa +
                '}';
    }
}
