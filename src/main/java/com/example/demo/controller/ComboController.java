package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.ComboEntity;
import com.example.demo.service.ComboService;

import java.util.List;

@RestController
@RequestMapping("/jp-moves") // エンドポイントのURL
public class ComboController {

    private final ComboService comboservice;

    public ComboController(ComboService comboservice) {
        this.comboservice = comboservice;
    }

    @GetMapping
    public List<ComboEntity> getAllJpMoves() {
        return comboservice.findAllMoves();
    }
}
