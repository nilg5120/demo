package com.example.demo.controller;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.entity.situationEntity;
import com.example.demo.service.situationService;


@Controller
public class HelloController {

    @PersistenceContext
    private EntityManager entityManager;
    private final situationService situationService;

    public HelloController(situationService situationService) {
        this.situationService = situationService;
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

    @RequestMapping("/buttontest")
    public String buttontest(Model model) {
        return "use/buttontest";
    }

    @GetMapping("/tmptest")
    public String showsituation(Model model) {
        List<situationEntity> situations = situationService.findAllsituation();
        model.addAttribute("situations", situations);
        return "use/tmptest";
    }
}