package com.example.demo.service;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import com.example.demo.entity.Todo;
import com.example.demo.form.TodoData;
import com.example.demo.form.TodoQuery;
import lombok.AllArgsConstructor;
import com.example.demo.repository.TodoRepository;



@Service
@AllArgsConstructor
public class TodoService {

    private final TodoRepository todoRepository;
    public List<Todo> doQuery(TodoQuery todoQuery){
        List<Todo> todoList = null;
        if(todoQuery.getTitle().length() > 0) {
            todoList = todoRepository.findByTitleLike("%"+todoQuery.getTitle()+"%");
        }else if(todoQuery.getImportance() != null &&  todoQuery.getImportance() != -1) {
            todoList = todoRepository.findByImportance(todoQuery.getImportance());
        }else if(todoQuery.getUrgency() != null && todoQuery.getUrgency() != -1) {
            todoList = todoRepository.findByUrgency(todoQuery.getUrgency());
        }else if(todoQuery.getDeadlineFrom().length() > 0 && todoQuery.getDeadlineTo().length() > 0) {
            todoList = todoRepository.findByDeadlineGreaterThanEqualOrderByDeadlineAsc(
                java.sql.Date.valueOf(todoQuery.getDeadlineFrom())
                );
        }else if(todoQuery.getDeadlineFrom().equals("")&& !todoQuery.getDeadlineTo().equals("")) {
            todoList = todoRepository.findByDeadlineGreaterThanEqualOrderByDeadlineAsc(
                java.sql.Date.valueOf(todoQuery.getDeadlineFrom())
                );
        }else if(!todoQuery.getDeadlineFrom().equals("")&& !todoQuery.getDeadlineTo().equals("")) {
            todoList = todoRepository.findByDeadlineBetweenOrderByDeadlineAsc(
                java.sql.Date.valueOf(todoQuery.getDeadlineFrom()),
                java.sql.Date.valueOf(todoQuery.getDeadlineTo())
                );
        }else if(todoQuery.getDone() != null && todoQuery.getDone().equals("Y")) {
            todoList = todoRepository.findByDone("Y");
        }else {
            todoList = todoRepository.findAll();
        }
        return todoList;
    }




    public boolean isValid(TodoData todoData, BindingResult result) {

        boolean ans = true;

        String title = todoData.getTitle();
        if(title != null && !title.equals("")) {
            boolean isAllDoubleSpace = true;
            for(int i=0;i<title.length();i++) {
                if(title.charAt(i) != ' ') {
                    isAllDoubleSpace = false;
                    break;
                }
            }
            if(isAllDoubleSpace) {
                FieldError fieldError = new FieldError(
                    result.getObjectName(),
                    "title",
                    "件名が全角スペースです。"
                );
                result.addError(fieldError);
                ans = false;
            }
        }

        String deadline = todoData.getDeadline();
        System.out.println("deadline:"+deadline.equals(""));
        if(!deadline.equals("")){
            LocalDate tody = LocalDate.now();
            LocalDate deadlineDate = null;
            try{
                deadlineDate = LocalDate.parse(deadline);
                if(deadlineDate.isBefore(tody)) {
                    FieldError fieldError = new FieldError(
                        result.getObjectName(),
                        "deadline",
                        "締切日は今日以降の日付を入力してください"
                    );
                    result.addError(fieldError);
                    ans = false;
                }
            }catch(DateTimeException e) {
                FieldError fieldError = new FieldError(
                    result.getObjectName(),
                    "deadline",
                    "締切日は「yyyy-MM-dd」形式で入力してください"
                );
                result.addError(fieldError);
                ans = false;
            }
        }
        System.out.println("ans:"+ans);
        return ans;

    }
    public boolean isValid(TodoQuery todoQuery,BindingResult result){
        boolean ans = true;
        String date = todoQuery.getDeadlineFrom();
        if(!date.equals("")){
            try{
                LocalDate.parse(date);
            }catch(DateTimeException e){
                FieldError fieldError = new FieldError(
                    result.getObjectName(),
                    "deadlineForm",
                    "締切日は「yyyy-MM-dd」形式で入力してください"
                );
                result.addError(fieldError);
                ans = false;
            }
        }
        date = todoQuery.getDeadlineTo();
        if(!date.equals("")){
            try{
                LocalDate.parse(date);
            }catch(DateTimeException e){
                FieldError fieldError = new FieldError(
                    result.getObjectName(),
                    "deadlineTo",
                    "締切日は「yyyy-MM-dd」形式で入力してください"
                );
                result.addError(fieldError);
                ans = false;
            }
        }
        return ans;
    }
}
