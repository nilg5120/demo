package com.example.demo.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.JP_combosEntity;
import com.example.demo.service.JP_combosService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Controller
public class JP_combosController {

    private final JP_combosService jp_combosService;

    public JP_combosController(JP_combosService jp_combosService) {
        this.jp_combosService = jp_combosService;
    }
    
    @GetMapping("/combo")
    public String showCombos(Model model) {
        List<JP_combosEntity> combos = jp_combosService.findAllCombos();
        model.addAttribute("combos", combos);
        return "use/combo";
    }
    /*
    //テスト用
    @GetMapping("/combo")
    public String showCombos(Model model) {
        List<JP_combosEntity> combos = jp_combosService.findAllCombos();
        model.addAttribute("combos", combos);
        return "use/combo copy";
    }
    */

    // ...

    @PostMapping("/combos/{id}/edit")
    public ResponseEntity<?> updateCombo(@PathVariable("id") Long id, @RequestBody JP_combosEntity updatedCombo) {
        try {
            jp_combosService.updateCombo(id, updatedCombo); // コンボを更新する
            return ResponseEntity.ok().build(); // 成功時は200 OKを返す
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("エラーメッセージ");
        }
    }
    

    @GetMapping("/combos/add")
    public String showAddForm(Model model) {
        model.addAttribute("combo", new JP_combosEntity());
        return "use/combo_add";
    }

    @PostMapping("/combos/add")
    public String addCombo(@ModelAttribute JP_combosEntity newCombo) {
        jp_combosService.saveJP_combo(newCombo); // 新しいコンボを保存する
        return "redirect:/combo"; // 保存後にコンボ一覧ページにリダイレクトする
    }

    @DeleteMapping("/combos/{id}/delete")
    @ResponseBody
    public ResponseEntity<Void> deleteCombo(@PathVariable Long id) {
        jp_combosService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
