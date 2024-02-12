package com.example.demo.controller;


import org.springframework.stereotype.Controller;
import lombok.RequiredArgsConstructor;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.example.demo.dao.TodoDaoImpl;

@Controller
@RequiredArgsConstructor
public class HelloController {

    @PersistenceContext
    private EntityManager entityManager;
    @PostConstruct
    public void init() {
        new TodoDaoImpl(entityManager);
    }

    @GetMapping("/")
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
}