package br.com.todolist.dto;

import java.time.LocalDate;

public record TaskResponseDto(Long id,
                              String name,
                              String description,
                              LocalDate createdDate,
                              Boolean wasFulfilled) {

    @Override
    public Long id() {
        return id;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public String description() {
        return description;
    }

    @Override
    public LocalDate createdDate() {
        return createdDate;
    }

    @Override
    public Boolean wasFulfilled() {
        return wasFulfilled;
    }

}
