package com.example.demo.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import lombok.RequiredArgsConstructor;

import com.example.demo.entity.Todo;
import com.example.demo.form.TodoData;
import com.example.demo.form.TodoQuery;
import com.example.demo.repository.TodoRepository;
import com.example.demo.service.TodoService;

import jakarta.persistence.PersistenceContext;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.servlet.http.HttpSession;

import com.example.demo.dao.TodoDaoImpl;

@Controller
@RequiredArgsConstructor
public class TodoController {

    private final TodoRepository todoRepository;
    private final TodoService todoService;
    private final HttpSession todoSession;

    @PersistenceContext
    private EntityManager entityManager;
    private TodoDaoImpl todoDaoImpl;

    @PostConstruct
    public void init() {
        todoDaoImpl = new TodoDaoImpl(entityManager);
    }


        @GetMapping("/todo")
    public ModelAndView showTodoList(ModelAndView mv,@PageableDefault(page = 0, size = 5, sort = "id")Pageable pageable) {
        //一覧画面に遷移
        mv.setViewName("use/todoList");

        @SuppressWarnings("null")
        Page<Todo> todoPage = todoRepository.findAll(pageable);
        mv.addObject("todoQuery", new TodoQuery());
        mv.addObject("todoPage", todoPage);
        mv.addObject("todoList", todoPage.getContent());
        todoSession.setAttribute("todoQuery", new TodoQuery());       
        return mv;
    }

    @GetMapping("/todo/create")
    public ModelAndView createTodo(ModelAndView mv) {
        //登録画面に遷移
        mv.setViewName("use/todoForm");
        mv.addObject("todoData",new TodoData());
        todoSession.setAttribute("mode", "create");
        return mv;
    }

    @SuppressWarnings("null")
    @PostMapping("/todo/create")
    public String createTodo(@ModelAttribute @Validated TodoData todoData, BindingResult result, Model model) {
        
        //登録処理
        boolean isValid = todoService.isValid(todoData,result);
        if(!result.hasErrors() && isValid){
            Todo todo = todoData.toEntity();
            todoRepository.saveAndFlush(todo);
            return "redirect:/todo";
        }else{
            return "use/todoForm";
        }
    }

    @PostMapping("/todo/cancel")
    public String cancel() {
        //一覧画面に遷移
        return "redirect:/todo";
    }
    
    @GetMapping("/todo/{id}")
    public ModelAndView todoById(@PathVariable int id, ModelAndView mv) {
        todoRepository.findById(id).ifPresentOrElse(
            todo -> {
                mv.setViewName("use/todoForm");
                mv.addObject("todoData", todo);
                todoSession.setAttribute("mode", "update");
            },
            () -> {
                mv.setViewName("use/errorPage");
                mv.addObject("errorMessage", "Requested Todo not found");
            }
        );
        return mv;
    }
    

    @SuppressWarnings("null")
    @PostMapping("/todo/update")
    public String updateTodo(@ModelAttribute @Validated TodoData todoData, BindingResult result, Model model) {
        //更新処理
        boolean isValid = todoService.isValid(todoData,result);
        if(!result.hasErrors() && isValid){
            Todo todo = todoData.toEntity();
            todoRepository.saveAndFlush(todo);
            return "redirect:/todo";
        }else{
            return "use/todoForm";
        }
    }
        
    @SuppressWarnings("null")
    @PostMapping("/todo/delete")
    public String deleteTodo(@ModelAttribute TodoData todoData) {
        //削除処理
        todoRepository.deleteById(todoData.getId());
        return "redirect:/todo";
    }
    
    @PostMapping("/todo/query")
    public ModelAndView queryTodo(@ModelAttribute TodoQuery todoQuery, BindingResult result, @PageableDefault(page = 0, size= 5)Pageable pageable,ModelAndView mv) {
        //一覧画面に遷移
        mv.setViewName("use/todoList");
        Page<Todo>todoPage = null;
        if(todoService.isValid(todoQuery,result)){
            //todoList = todoService.doQuery(todoQuery);
            todoPage = todoDaoImpl.findByCriteria(todoQuery , pageable);
            todoSession.setAttribute("todoQuery", todoQuery);
            mv.addObject("todoPage", todoPage);
            mv.addObject("todoList", todoPage.getContent());
        }
        mv.addObject("todoList", null);
        mv.addObject("todoPage", null);
        return mv;
    }

    @GetMapping("/todo/query")
    public ModelAndView queryTodo(@PageableDefault(page = 0 , size = 5) Pageable pageable , ModelAndView mv){
        mv.setViewName("use/todoList");

        TodoQuery todoQuery = (TodoQuery)todoSession.getAttribute("todoQuery");
        Page<Todo> todoPage = todoDaoImpl.findByCriteria(todoQuery, pageable);
        mv.addObject("todoQuery", todoQuery);
        mv.addObject("todoPage", todoPage);
        mv.addObject("todoList", todoPage.getContent());
        return mv;
    }

}
