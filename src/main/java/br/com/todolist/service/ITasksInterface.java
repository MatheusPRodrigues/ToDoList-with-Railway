package br.com.todolist.service;

import br.com.todolist.dto.TaskRequestDto;
import br.com.todolist.dto.TaskResponseDto;

import java.util.List;

public interface ITasksInterface {

    TaskResponseDto createTask(TaskRequestDto requestDto);
    List<TaskResponseDto> getAllTasks();
    TaskResponseDto getTaskById(Long id);
    TaskResponseDto modifyTask(Long id, TaskRequestDto requestDto);
    TaskResponseDto concludeTask(Long id);
    List<TaskResponseDto> resetAllTasks();
    void deleteTask(Long id);

}
