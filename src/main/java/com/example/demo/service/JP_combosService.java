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

    public JP_combosEntity saveJP_combo(JP_combosEntity newMove) {
        return JP_combosRepository.save(newMove);
    }

    public void updateCombo(Long id, JP_combosEntity updatedCombo) {
        JP_combosEntity combo = JP_combosRepository.findById(id).orElseThrow();
        combo.setName(updatedCombo.getName());
        combo.setDamage(updatedCombo.getDamage());
        combo.setInput(updatedCombo.getInput());
        combo.setStartup(updatedCombo.getStartup());
        JP_combosRepository.save(combo);
    }

    public JP_combosEntity findById(Long id) {
        return JP_combosRepository.findById(id).orElseThrow();
    }

    
}
