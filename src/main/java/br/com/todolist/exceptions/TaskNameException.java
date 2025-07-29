package br.com.todolist.exceptions;

public class TaskNameException extends RuntimeException {
    public TaskNameException(String message) {
        super(message);
    }
}
