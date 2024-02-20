package com.example.demo.service;

import org.springframework.stereotype.Service;

import com.example.demo.entity.JP_movesEntity;
import com.example.demo.repository.JP_movesRepository;

import java.util.List;

@Service
public class JP_moveService {

    private final JP_movesRepository JP_movesRepository;

    public JP_moveService(JP_movesRepository JP_movesRepository) {
        this.JP_movesRepository = JP_movesRepository;
    }

    public List<JP_movesEntity> findAllMoves() {
        return JP_movesRepository.findAll();
    }
}
