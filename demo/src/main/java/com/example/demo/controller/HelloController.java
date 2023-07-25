package com.example.demo.controller;


import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import com.example.demo.entity.Todo;
import com.example.demo.form.TodoData;
import com.example.demo.repository.TodoRepository;
import com.example.demo.service.TodoService;
import lombok.AllArgsConstructor;
import java.util.Random;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import com.example.demo.History;

@Controller
@AllArgsConstructor
public class HelloController {

    private final TodoRepository todoRepository;
    private final TodoService todoService;

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
    public ModelAndView showTodoList(ModelAndView mv) {
        //一覧画面に遷移
        System.out.println("showTodoList");
        mv.setViewName("todoList");
        List<Todo>todoList = todoRepository.findAll();
        mv.addObject("todoList", todoList);
        return mv;
    }

    @GetMapping("/todo/create")
    public ModelAndView createTodo(ModelAndView mv) {
        //登録画面に遷移
        System.out.println("showCreate");
        mv.setViewName("todoForm");
        mv.addObject("todoData",new TodoData());
        return mv;
    }

    @PostMapping("/todo/create")
    public ModelAndView createTodo(@ModelAttribute @Validated TodoData todoData, BindingResult result, ModelAndView mv) {
        
        //登録処理
        boolean isValid = todoService.isValid(todoData,result);

        if(!result.hasErrors() && isValid){
            Todo todo = todoData.toEntity();
            todoRepository.saveAndFlush(todo);
            return showTodoList(mv);
        }else{
            mv.setViewName("todoForm");
            return mv;
        }
    }

    @PostMapping("/todo/cancel")
    public String cancel() {
        //一覧画面に遷移
        System.out.println("cancel");
        return "redirect:/todo";
    }
    



}