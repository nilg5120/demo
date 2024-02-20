package com.example.demo.service;

import org.springframework.stereotype.Service;

import com.example.demo.entity.JP_combosEntity;
import com.example.demo.repository.JP_combosRepository;

import java.util.List;

@Service
public class JP_combosService {

    private final JP_combosRepository JP_combosRepository;

    public JP_combosService(JP_combosRepository JP_combosRepository) {
        this.JP_combosRepository = JP_combosRepository;
    }

    public List<JP_combosEntity> findAllMoves() {
        return JP_combosRepository.findAll();
    }
}
