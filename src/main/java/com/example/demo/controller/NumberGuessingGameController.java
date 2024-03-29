package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import com.example.demo.History;
import com.example.demo.util.SessionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpSession;

@Controller
public class NumberGuessingGameController {

    @GetMapping("/NumberGuessingGame")
    public String NumberGuessingGame(HttpSession session) {
        // セッションの特定の属性をリセット
        session.removeAttribute("answer");
        session.removeAttribute("histories");
        Random rnd = new Random();
        int answer = rnd.nextInt(100) + 1;
        session.setAttribute("answer", answer);
        return "use/NumberGuessingGame";
    }

    @PostMapping("/challenge")
    public ModelAndView challenge(@RequestParam int number, ModelAndView mv , HttpSession session) {

        // ユーザー入力のバリデーション
        if (number < 1 || number > 100) {
            mv.addObject("error", "入力は1から100の間でなければなりません。");
            mv.setViewName("use/NumberGuessingGame");
            return mv; // ここで処理を終了し、ビューにエラーメッセージを渡す
        }

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
