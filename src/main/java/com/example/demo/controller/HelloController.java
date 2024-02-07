package com.example.demo.controller;


import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.entity.Todo;
import com.example.demo.form.TodoData;
import com.example.demo.repository.TodoRepository;
import com.example.demo.service.TodoService;
import lombok.RequiredArgsConstructor;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.http.HttpSession;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PageableDefault;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.example.demo.dao.TodoDaoImpl;
import com.example.demo.form.TodoQuery;

@Controller
@RequiredArgsConstructor
public class HelloController {

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

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String showHello(Model model) {
        model.addAttribute("title", "Hello World!");
        model.addAttribute("message", "ようこそ、オオクボのテストページへ");
        return "use/hello";
    }

    @RequestMapping("/next")
    public String input(Model model) {
        return "use/next";
    }

    @RequestMapping("/sf6")
    public String sf6(Model model) {
        return "use/sf6";
    }

    @RequestMapping("/kakuge")
    public String kakuge(Model model) {
        return "use/kakuge";
    }

    @RequestMapping("/resume")
    public String resume(Model model) {
        return "use/resume";
    }

    @RequestMapping("/html")
    public String html(Model model) {
        return "use/html";
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
        mv.setViewName("todoForm");
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
    public ModelAndView todoById(@PathVariable(name = "id") int id, ModelAndView mv) {
        todoRepository.findById(id).ifPresentOrElse(
            todo -> {
                mv.setViewName("todoForm");
                mv.addObject("todoData", todo);
                todoSession.setAttribute("mode", "update");
            },
            () -> {
                mv.setViewName("errorPage");
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
        mv.setViewName("todoList");
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
        mv.setViewName("todoList");

        TodoQuery todoQuery = (TodoQuery)todoSession.getAttribute("todoQuery");
        Page<Todo> todoPage = todoDaoImpl.findByCriteria(todoQuery, pageable);
        mv.addObject("todoQuery", todoQuery);
        mv.addObject("todoPage", todoPage);
        mv.addObject("todoList", todoPage.getContent());
        return mv;
    }



}