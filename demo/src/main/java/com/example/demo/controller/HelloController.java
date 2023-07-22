package com.example.demo.controller;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class HelloController {

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

}