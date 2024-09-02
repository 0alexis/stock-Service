package com.emazon.stock_service.adapters.drivend.jpa.mysql.repository;

import com.emazon.stock_service.adapters.drivend.jpa.mysql.entity.BrandEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository

public interface IBrandRepository extends JpaRepository<BrandEntity, Long> {
    //una consulta en la tabla category
    @Query(value = "SELECT * FROM brand WHERE name = :nameBrand", nativeQuery = true)
    Optional<BrandEntity> findByName(@Param("nameBrand") String name);

}
