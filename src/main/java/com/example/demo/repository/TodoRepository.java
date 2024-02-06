package com.example.demo.repository;

import java.sql.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.entity.Todo;

public interface TodoRepository extends JpaRepository<Todo, Integer> {
    List<Todo>findByTitleLike(String title);
    List<Todo>findByImportance(Integer importance);
    List<Todo>findByUrgency(Integer urgency);
    List<Todo>findByDeadlineBetweenOrderByDeadlineAsc(Date from, Date to);
    List<Todo>findByDeadlineGreaterThanEqualOrderByDeadlineAsc(Date from);
    List<Todo>findByDeadlineLessThanEqualOrderByDeadlineAsc(Date to);
    List<Todo>findByDone(String done);
}