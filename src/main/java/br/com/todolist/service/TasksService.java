package br.com.todolist.service;

import br.com.todolist.dto.TaskRequestDto;
import br.com.todolist.dto.TaskResponseDto;
import br.com.todolist.exceptions.TaskNameException;
import br.com.todolist.models.Tasks;
import br.com.todolist.repository.TasksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class TasksService implements ITasksInterface {

    @Autowired
    TasksRepository repository;

    @Override
    public TaskResponseDto createTask(TaskRequestDto requestDto) {
        // Name validation
        if (requestDto.name().isBlank())
            throw new TaskNameException("Insira um nome válido para a tarefa! Ex: 'Realizar atividade fisica'");
        if (checkNameExists(requestDto.name()))
            throw new TaskNameException("Já existe uma tarefa com esse nome!");

        Tasks tasks = new Tasks(
                requestDto.name(),
                requestDto.description()
        );
        Tasks tasksSaved = repository.save(tasks);
        return new TaskResponseDto(
                tasksSaved.getId(),
                tasksSaved.getName(),
                tasksSaved.getDescription(),
                tasksSaved.getCreatedDate(),
                tasksSaved.getWasFulfilled());
    }

    @Override
    public List<TaskResponseDto> getAllTasks() {
        List<Tasks> tasksList = repository.findAllByOrderByWasFulfilledAsc();
        if (tasksList.isEmpty())
            throw new NoSuchElementException("Não há nenhuma tarefa cadastrada!");

        List<TaskResponseDto> responseDtoList = new ArrayList<>();
        for (var t : tasksList) {
            responseDtoList.add(new TaskResponseDto(
                    t.getId(),
                    t.getName(),
                    t.getDescription(),
                    t.getCreatedDate(),
                    t.getWasFulfilled()
            ));
        }
        return responseDtoList;
    }

    @Override
    public TaskResponseDto getTaskById(Long id) {
        Optional<Tasks> tasksSearch = repository.findById(id);
        if (tasksSearch.isEmpty())
            throw new NoSuchElementException("Não existe nenhuma tarefa com esse id!");
        Tasks tasks = tasksSearch.get();
        return new TaskResponseDto(
                tasks.getId(),
                tasks.getName(),
                tasks.getDescription(),
                tasks.getCreatedDate(),
                tasks.getWasFulfilled()
        );
    }

    @Override
    public TaskResponseDto modifyTask(Long id, TaskRequestDto requestDto) {
        Optional<Tasks> tasksSearch = repository.findById(id);
        if (tasksSearch.isEmpty())
            throw new NoSuchElementException("Não existe nenhuma tarefa com esse id!");
        if (requestDto.name().isBlank())
            throw new TaskNameException("Insira um nome válido para a tarefa! Ex: 'Realizar atividade fisica'");

        Tasks tasks = tasksSearch.get();
        tasks.setName(requestDto.name());
        tasks.setDescription(requestDto.description());
        tasks.setCreatedDate(LocalDate.now());

        Tasks taskUpdated = repository.save(tasks);
        return new TaskResponseDto(
                taskUpdated.getId(),
                taskUpdated.getName(),
                taskUpdated.getDescription(),
                taskUpdated.getCreatedDate(),
                taskUpdated.getWasFulfilled()
        );
    }

    @Override
    public TaskResponseDto concludeTask(Long id) {
        Optional<Tasks> tasksSearch = repository.findById(id);
        if (tasksSearch.isEmpty())
            throw new NoSuchElementException("Não existe nenhuma tarefa com esse id!");

        Tasks concluedTask = tasksSearch.get();
        concluedTask.setWasFulfilled();
        Tasks tasks = repository.save(concluedTask);
        return new TaskResponseDto(
                tasks.getId(),
                tasks.getName(),
                tasks.getDescription(),
                tasks.getCreatedDate(),
                tasks.getWasFulfilled()
        );
    }

    @Override
    public List<TaskResponseDto> resetAllTasks() {
        List<Tasks> tasksSearch = repository.findAll();
        if (tasksSearch.isEmpty())
            throw new NoSuchElementException("Não há nenhuma tarefa cadastrada!");
        if (tasksSearch.stream().allMatch(Tasks::getWasFulfilled))
            throw new NoSuchElementException("Não há nenhuma tarefa concluída!");

        List<Tasks> tasksToReset = tasksSearch.stream().toList();
        for (var t : tasksToReset) {
            if (t.getWasFulfilled())
                t.setWasFulfilled();
        }
        List<Tasks> tasksSaved = repository.saveAll(tasksToReset);

        List<TaskResponseDto> responseDtoList = new ArrayList<>();
        for (var t : tasksSaved) {
            responseDtoList.add(new TaskResponseDto(
                    t.getId(),
                    t.getName(),
                    t.getDescription(),
                    t.getCreatedDate(),
                    t.getWasFulfilled()
            ));
        }
        return responseDtoList;
    }

    @Override
    public void deleteTask(Long id) {
        Optional<Tasks> tasksSearch = repository.findById(id);
        if (tasksSearch.isEmpty())
            throw new NoSuchElementException("Não existe nenhuma tarefa com esse id!");
        Tasks tasksToRemove = tasksSearch.get();
        repository.delete(tasksToRemove);
    }

    private boolean checkNameExists(String name) {
        List<Tasks> tasksList = repository.findAll();
        for (var t : tasksList) {
            if (name.equalsIgnoreCase(t.getName()))
                return true;
        }
        return false;
    }

}
