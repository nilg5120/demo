package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ComboController {
    @GetMapping("/combo")
    public String showCombo(Model model) {
        return "use/combo";
    }
}
