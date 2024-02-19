package com.example.demo.service;

import org.springframework.stereotype.Service;

import com.example.demo.entity.comboentity;
import com.example.demo.repository.comborepository;

import java.util.List;

@Service
public class comboservice {

    private final comborepository ComboRepository;

    public comboservice(comborepository ComboRepository) {
        this.ComboRepository = ComboRepository;
    }

    public List<comboentity> findAllMoves() {
        return ComboRepository.findAll();
    }
}
