package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.JP_movesEntity;
import com.example.demo.service.JP_moveService;

import java.util.List;

@RestController
@RequestMapping("/jp-moves") // エンドポイントのURL
public class ComboController {

    private final JP_moveService comboservice;

    public ComboController(JP_moveService comboservice) {
        this.comboservice = comboservice;
    }

    @GetMapping
    public List<JP_movesEntity> getAllJpMoves() {
        return comboservice.findAllMoves();
    }
}
