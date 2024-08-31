package com.emazon.stock_service.adapters.drivend.jpa.mysql.repository;

import com.emazon.stock_service.adapters.drivend.jpa.mysql.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository

public interface ICategoryRepository extends JpaRepository <CategoryEntity, Long> {

    //una consulta en la tabla category
    @Query(value = "SELECT * FROM category WHERE name = :nameCategory", nativeQuery = true)
    Optional <CategoryEntity> findByName(@Param("nameCategory") String name);

/*
    Page<CategoryEntity> findAll(Pageable pageable);
    List<CategoryEntity> findByNameContaining(String name);
    Optional<CategoryEntity> findAllByName(String name);
    Page<CategoryEntity> findAllByNameContaining(String name, Pageable pageable);
*/
}
