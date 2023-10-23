package com.example.demo.controller;


import java.util.ArrayList;
import java.util.List;
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

import java.util.Random;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PageableDefault;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import com.example.demo.History;
import com.example.demo.dao.TodoDaoImpl;
import com.example.demo.form.TodoQuery;

@Controller
@RequiredArgsConstructor
public class HelloController {

    private final TodoRepository todoRepository;
    private final TodoService todoService;
    private final HttpSession todosession;
    

    @PersistenceContext
    private EntityManager entityManager;
    TodoDaoImpl todoDaoImpl;

    @PostConstruct
    public void init() {
        todoDaoImpl = new TodoDaoImpl(entityManager);
    }

    @Autowired
    HttpSession session;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String showHello(Model model) {
        model.addAttribute("title", "Hello World!");
        model.addAttribute("message", "ようこそ、ばったん技術ブログへ");
        return "hello";
    }

    @RequestMapping("/next")
    public String input(Model model) {
        return "next";
    }

    @RequestMapping("/sf6")
    public String sf6(Model model) {
        return "sf6";
    }

    @GetMapping("/game")
    public String index() {
        session.invalidate();
        Random rnd = new Random();
        int answer = rnd.nextInt(100)+1;
        session.setAttribute("answer", answer);
        System.out.println("answer="+answer);

        return "game";
    }

    @PostMapping("/challenge")
    public ModelAndView challenge(@RequestParam("number") int number,ModelAndView mv){

        //セッションから正解を取得
        int answer = (Integer)session.getAttribute("answer");

        @SuppressWarnings("unchecked")
        List<History> histories = (List<History>)session.getAttribute("histories");
        if(histories == null){
            histories = new ArrayList<>();
            session.setAttribute("histories", histories);
        }

        if(answer < number){
            histories.add(new History(histories.size()+1 , number ,"大きい"));
        }else if(answer > number){
            histories.add(new History(histories.size()+1 , number ,"小さい"));
        }else{
            histories.add(new History(histories.size()+1 , number ,"正解"));
        }

        mv.setViewName("game");
        mv.addObject("histories", histories);
        return mv;
    }

    

    @GetMapping("/todo")
    public ModelAndView showTodoList(ModelAndView mv,@PageableDefault(page = 0, size = 5, sort = "id")Pageable pageable) {
        //一覧画面に遷移
        mv.setViewName("todoList");

        Page<Todo> todoPage = todoRepository.findAll(pageable);
        mv.addObject("todoQuery", new TodoQuery());
        mv.addObject("todoPage", todoPage);
        mv.addObject("todoList", todoPage.getContent());
        session.setAttribute("todoQuery", new TodoQuery());       
        return mv;
    }

    @GetMapping("/todo/create")
    public ModelAndView createTodo(ModelAndView mv) {
        //登録画面に遷移
        mv.setViewName("todoForm");
        mv.addObject("todoData",new TodoData());
        todosession.setAttribute("mode", "create");
        return mv;
    }

    @PostMapping("/todo/create")
    public String createTodo(@ModelAttribute @Validated TodoData todoData, BindingResult result, Model model) {
        
        //登録処理
        boolean isValid = todoService.isValid(todoData,result);
        if(!result.hasErrors() && isValid){
            Todo todo = todoData.toEntity();
            todoRepository.saveAndFlush(todo);
            return "redirect:/todo";
        }else{
            return "todoForm";
        }
    }

    @PostMapping("/todo/cancel")
    public String cancel() {
        //一覧画面に遷移
        System.out.println("cancel");
        return "redirect:/todo";
    }
    
    @GetMapping("/todo/{id}")
    public ModelAndView todoById(@PathVariable(name = "id") int id, ModelAndView mv) {
        //詳細画面に遷移
        mv.setViewName("todoForm");
        Todo todo = todoRepository.findById(id).get();
        mv.addObject("todoData", todo);
        todosession.setAttribute("mode", "update");
        return mv;
    }

    @PostMapping("/todo/update")
    public String updateTodo(@ModelAttribute @Validated TodoData todoData, BindingResult result, Model model) {
        //更新処理
        boolean isValid = todoService.isValid(todoData,result);
        if(!result.hasErrors() && isValid){
            Todo todo = todoData.toEntity();
            todoRepository.saveAndFlush(todo);
            return "redirect:/todo";
        }else{
            return "todoForm";
        }
    }
        
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
            session.setAttribute("todoQuery", todoQuery);
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

        TodoQuery todoQuery = (TodoQuery)session.getAttribute("todoQuery");
        Page<Todo> todoPage = todoDaoImpl.findByCriteria(todoQuery, pageable);
        mv.addObject("todoQuery", todoQuery);
        mv.addObject("todoPage", todoPage);
        mv.addObject("todoList", todoPage.getContent());
        return mv;
    }



}