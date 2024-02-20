package com.example.demo.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Controller;
import com.example.demo.entity.JP_combosEntity;
import com.example.demo.service.JP_combosService;
import java.util.List;


@Controller
public class JP_combosController {

    private final JP_combosService jp_combosService;

    public JP_combosController(JP_combosService jp_combosService) {
        this.jp_combosService = jp_combosService;
    }

    @GetMapping("/combo")
    public String showCombos(Model model) {
        List<JP_combosEntity> combos = jp_combosService.findAllMoves();
        model.addAttribute("combos", combos); // モデルにデータを追加
        return "use/combo";
    }
}
