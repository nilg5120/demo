package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.entity.JP_combosEntity;

public interface JP_combosRepository extends JpaRepository<JP_combosEntity, Long> {
    // 特別なクエリメソッドが必要な場合はここに追加しますが、
    // 基本的なCRUD操作はJpaRepositoryによって既に提供されています。
}
