package com.example.demo.service;

import org.springframework.stereotype.Service;

import com.example.demo.entity.situationEntity;
import com.example.demo.repository.situationRepository;

import java.util.List;

@Service
public class situationService {

    private final situationRepository situationRepository;

    public situationService(situationRepository situationRepository) {
        this.situationRepository = situationRepository;
    }

    public List<situationEntity> findAllsituation() {
        return situationRepository.findAll();
    }
}
