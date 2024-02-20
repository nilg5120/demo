package com.example.demo.service;

import org.springframework.stereotype.Service;

import com.example.demo.entity.JP_movesEntity;
import com.example.demo.repository.JP_movesRepository;

import java.util.List;

@Service
public class JP_moveService {

    private final JP_movesRepository ComboRepository;

    public JP_moveService(JP_movesRepository ComboRepository) {
        this.ComboRepository = ComboRepository;
    }

    public List<JP_movesEntity> findAllMoves() {
        return ComboRepository.findAll();
    }
}
