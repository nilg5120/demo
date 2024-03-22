package com.example.demo.service;

import org.springframework.stereotype.Service;

import com.example.demo.entity.JP_combosEntity;
import com.example.demo.repository.JP_combosRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class JP_combosService {

    private final JP_combosRepository JP_combosRepository;

    public JP_combosService(JP_combosRepository JP_combosRepository) {
        this.JP_combosRepository = JP_combosRepository;
    }

    public List<JP_combosEntity> findAllCombos() {
        return JP_combosRepository.findAll();
    }

    @Transactional
    public JP_combosEntity saveJP_combo(JP_combosEntity newCombo) {
        if(newCombo == null) {
            throw new IllegalArgumentException("新しいコンボがnullです");
        }else{
            return JP_combosRepository.save(newCombo);
        }
    }

    @Transactional
    public void updateCombo(Long id, JP_combosEntity updatedCombo) {
        if (id == null) {
            throw new IllegalArgumentException("ID must not be null");
        }else{
            JP_combosEntity combo = JP_combosRepository.findById(id).orElseThrow();
            combo.setName(updatedCombo.getName());
            combo.setDamage(updatedCombo.getDamage());
            combo.setInput(updatedCombo.getInput());
            combo.setStartup(updatedCombo.getStartup());
            combo.setUsagedg(updatedCombo.getUsagedg());
            combo.setUsagesa(updatedCombo.getUsagesa());
            combo.setExplain(updatedCombo.getExplain());
            JP_combosRepository.save(combo);
        }
    }

    public JP_combosEntity findById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID must not be null");
        }else{
            return JP_combosRepository.findById(id).orElseThrow();
        }
    }

    public void deleteById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID must not be null");
        }else{
            JP_combosRepository.deleteById(id);
        }
    }
}
