package com.emazon.stock_service.adapters.drivend.jpa.mysql.repository;


import com.emazon.stock_service.adapters.drivend.jpa.mysql.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IProductRepository extends JpaRepository<ProductEntity, Long> {

}

/**
 * Repositorio para manejar operaciones CRUD de la entidad {@link ProductEntity}.
 *
 * Extiende {@link JpaRepository}, proporcionando métodos predefinidos para realizar
 * operaciones comunes en la base de datos como guardar, encontrar, actualizar y eliminar
 * instancias de {@link ProductEntity}.
 *
 * La clave primaria de {@link ProductEntity} es de tipo {@link Long}.
 *
 * Métodos disponibles incluyen:
 * - `save(S entity)`: Guarda una entidad en la base de datos.
 * - `findById(ID id)`: Busca una entidad por su identificador.
 * - `findAll()`: Recupera todas las entidades de la base de datos.
 * - `deleteById(ID id)`: Elimina una entidad por su identificador.
 * - `count()`: Cuenta el número total de entidades.
 *
 * Puedes agregar métodos personalizados para consultas específicas según sea necesario.
 */