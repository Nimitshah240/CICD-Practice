package com.example.demo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "medicines")
public class Medicine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String batchId;
    private String name;
    private Integer count;

    public Long getId() {
        return id;
    }

    public Medicine setId(Long id) {
        this.id = id;
        return this;
    }

    public String getBatchId() {
        return batchId;
    }

    public Medicine setBatchId(String batchId) {
        this.batchId = batchId;
        return this;
    }

    public String getName() {
        return name;
    }

    public Medicine setName(String name) {
        this.name = name;
        return this;
    }

    public Integer getCount() {
        return count;
    }

    public Medicine setCount(Integer count) {
        this.count = count;
        return this;
    }
}