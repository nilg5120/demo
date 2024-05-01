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

    private Long savedEntityId;

    //JP_combosEntityクラスをインスタンス化して初期データを保存します
    //@BeforeEach：各テストメソッドの前に実行されるメソッド
    // 保存されたエンティティのIDを保持するフィールド

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
        JP_combosEntity savedEntity = jp_combosRepository.save(combo);
        savedEntityId = savedEntity.getId(); // 保存されたエンティティのIDを保存
    }
    
    @Test
    public void testFindById() {
        // 保存したエンティティを検索
        JP_combosEntity foundEntity = jp_combosRepository.findById(savedEntityId).orElse(null);
        assertThat(foundEntity).isNotNull();
        assertThat(foundEntity.getName()).isEqualTo("コンボ");
        assertThat(foundEntity.getDamage()).isEqualTo(0);
        assertThat(foundEntity.getInput()).isEqualTo("入力");
        assertThat(foundEntity.getStartup()).isEqualTo(0);
        assertThat(foundEntity.getUsagedg()).isEqualTo(0);
        assertThat(foundEntity.getUsagesa()).isEqualTo(0);
        assertThat(foundEntity.getExplain()).isEqualTo("説明");
        assertThat(foundEntity.getHittype()).isEqualTo(0);

    }
}
