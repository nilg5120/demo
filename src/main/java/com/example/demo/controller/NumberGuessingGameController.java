package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import com.example.demo.History;
import com.example.demo.util.SessionUtils;

import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Controller
public class NumberGuessingGameController {

    private HttpSession session = null;

    public void numberGuessingGameController(HttpSession session) {
        this.session = session;
    }

    @GetMapping("/NumberGuessingGame")
    public String NumberGuessingGame() {
        session.invalidate();
        Random rnd = new Random();
        int answer = rnd.nextInt(100) + 1;
        session.setAttribute("answer", answer);
        return "use/NumberGuessingGame";
    }

    @PostMapping("/challenge")
    public ModelAndView challenge(@RequestParam("number") int number, ModelAndView mv) {
        int answer = (int) session.getAttribute("answer");
        
        List<History> histories = SessionUtils.getHistories(session).orElse(new ArrayList<>());

        if (answer < number) {
            histories.add(new History(histories.size() + 1, number, "大きい"));
        } else if (answer > number) {
            histories.add(new History(histories.size() + 1, number, "小さい"));
        } else {
            histories.add(new History(histories.size() + 1, number, "正解"));
        }

        session.setAttribute("histories", histories);
        mv.setViewName("use/NumberGuessingGame");
        mv.addObject("histories", histories);
        return mv;
    }
}
