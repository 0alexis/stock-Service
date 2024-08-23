package com.emazon.stock_service.adapters.drivend.jpa.mysql.repository;

import com.emazon.stock_service.adapters.drivend.jpa.mysql.entity.CategoryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository

public interface ICategoryRepository extends JpaRepository <CategoryEntity, Long> {

    List<CategoryEntity> findByNameContaining(String name);

    Optional<CategoryEntity> findAllByName(String name);

    List<CategoryEntity> findByName(String name);

    Page<CategoryEntity> findAll(Pageable pageable);

    Page<CategoryEntity> findAllByNameContaining(String name, Pageable pageable);
}
