package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.comboentity;
import com.example.demo.service.comboservice;

import java.util.List;

@RestController
@RequestMapping("/jp-moves") // エンドポイントのURL
public class ComboController {

    private final comboservice comboservice;

    public ComboController(comboservice comboservice) {
        this.comboservice = comboservice;
    }

    @GetMapping
    public List<comboentity> getAllJpMoves() {
        return comboservice.findAllMoves();
    }
}
