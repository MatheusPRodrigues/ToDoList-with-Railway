package br.com.todolist.repository;

import br.com.todolist.models.Tasks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TasksRepository extends JpaRepository<Tasks, Long> {


    @Query("SELECT t FROM Tb_tasks t ORDER BY t.wasFulfilled")
    List<Tasks> findAllOrderByWasFulfilledAsc();


}
