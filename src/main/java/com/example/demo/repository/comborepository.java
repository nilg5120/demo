package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.comboentity;

public interface comborepository extends JpaRepository<comboentity, Long> {
    // 特別なクエリメソッドが必要な場合はここに追加しますが、
    // 基本的なCRUD操作はJpaRepositoryによって既に提供されています。
}
