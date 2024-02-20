package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.JP_combosEntity;
import com.example.demo.service.JP_combosService;

import java.util.List;

@RestController
@RequestMapping("/jp-combos") // エンドポイントのURL
public class JP_combosController {

    private final JP_combosService JP_combosService;

    public JP_combosController(JP_combosService JP_combosService) {
        this.JP_combosService = JP_combosService;
    }

    @GetMapping
    public List<JP_combosEntity> getAllJpMoves() {
        return JP_combosService.findAllMoves();
    }
}
