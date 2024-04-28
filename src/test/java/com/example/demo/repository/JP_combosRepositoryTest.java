package com.example.demo.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import com.example.demo.entity.JP_combosEntity;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class JP_combosRepositoryTest {

    @Autowired
    private JP_combosRepository jp_combosRepository;

    @BeforeEach
    public void setup() {
        JP_combosEntity combo = new JP_combosEntity();
        combo.setName("コンボ");
        combo.setDamage(0);
        combo.setInput("入力");
        combo.setStartup(0);
        combo.setUsagedg(0);
        combo.setUsagesa(0);
        combo.setExplain("説明");
        combo.setHittype(0);
        // 必要であれば他のフィールドも設定
        jp_combosRepository.save(combo);
    }

    @Test
    public void testFindById() {
        // 保存したエンティティを検索
        JP_combosEntity foundEntity = jp_combosRepository.findById(1L).orElse(null);
        assertThat(foundEntity).isNotNull();
        // 他のアサーションを追加して、検索したエンティティの状態を確認します
    }

    // 他のテストケースを追加...
}
