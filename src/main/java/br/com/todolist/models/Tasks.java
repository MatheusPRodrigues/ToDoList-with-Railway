package br.com.todolist.models;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "tb_tasks")
public class Tasks {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "taskName", nullable = false)
    private String name;
    private String description;
    @Column(nullable = false)
    private LocalDate createdDate;
    private Boolean wasFulfilled;

    public Tasks(String name, String description) {
        this.name = name;
        this.description = description;
        this.createdDate = LocalDate.now();
        this.wasFulfilled = false;
    }

    public Tasks() {}

    public Boolean getWasFulfilled() {
        return wasFulfilled;
    }

    public void setWasFulfilled() {
        this.wasFulfilled = !getWasFulfilled();
    }

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }
}
