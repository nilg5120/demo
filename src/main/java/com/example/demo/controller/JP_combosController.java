package com.example.demo.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
        model.addAttribute("combos", combos);
        return "use/combo";
    }

    @GetMapping("/combos/{id}/edit")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        JP_combosEntity comboToEdit = jp_combosService.findById(id); // idに基づいてエンティティを取得
        model.addAttribute("combo", comboToEdit);
        return "use/edit_combo";
    }

    @PostMapping("/combos/{id}/edit")
    public String updateCombo(@PathVariable("id") Long id, @ModelAttribute JP_combosEntity updatedCombo) {
        jp_combosService.updateCombo(id, updatedCombo); // コンボを更新する
        return "redirect:/combo"; // 編集後にコンボ一覧ページにリダイレクトする
    }



}
