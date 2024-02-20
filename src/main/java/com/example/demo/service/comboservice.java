package com.example.demo.service;

import org.springframework.stereotype.Service;

import com.example.demo.entity.ComboEntity;
import com.example.demo.repository.ComboRepository;

import java.util.List;

@Service
public class ComboService {

    private final ComboRepository ComboRepository;

    public ComboService(ComboRepository ComboRepository) {
        this.ComboRepository = ComboRepository;
    }

    public List<ComboEntity> findAllMoves() {
        return ComboRepository.findAll();
    }
}
