package br.com.todolist.dto;

public record TaskRequestDto(String name, String description) {

    @Override
    public String name() {
        return name;
    }

    @Override
    public String description() {
        return description;
    }

}
