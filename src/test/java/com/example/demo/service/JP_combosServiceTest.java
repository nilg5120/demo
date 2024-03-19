package com.example.demo.service;

import static org.mockito.BDDMockito.*;
import static org.assertj.core.api.Assertions.*;

import com.example.demo.entity.JP_combosEntity;
import com.example.demo.repository.JP_combosRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class JP_combosServiceTest {

    @Autowired
    private JP_combosService jpCombosService;

    @MockBean
    private JP_combosRepository jpCombosRepository;

    @Test
    void findAllCombos_ShouldReturnAllCombos() {
        // Given
        List<JP_combosEntity> expectedCombos = new ArrayList<>();
        expectedCombos.add(new JP_combosEntity());
        given(jpCombosRepository.findAll()).willReturn(expectedCombos);

        // When
        List<JP_combosEntity> result = jpCombosService.findAllCombos();

        // Then
        assertThat(result).isEqualTo(expectedCombos);
    }

    @Test
    void saveJP_combo_ShouldSaveCombo() {
        // Given
        JP_combosEntity newCombo = new JP_combosEntity();
        given(jpCombosRepository.save(any(JP_combosEntity.class))).willReturn(newCombo);

        // When
        JP_combosEntity result = jpCombosService.saveJP_combo(newCombo);

        // Then
        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(newCombo);
    }

    @Test
    void findById_ShouldFindComboById() {
        // Given
        JP_combosEntity foundCombo = new JP_combosEntity();
        given(jpCombosRepository.findById(anyLong())).willReturn(Optional.of(foundCombo));

        // When
        JP_combosEntity result = jpCombosService.findById(1L);

        // Then
        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(foundCombo);
    }

    // その他のメソッドも同様にテストケースを追加...
}
