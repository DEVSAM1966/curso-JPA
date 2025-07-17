package com.debuggeandoideas.gadget_plus.repositories;

import com.debuggeandoideas.gadget_plus.entities.ProductCatalogEntity;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductCatalogRepository extends JpaRepository<ProductCatalogEntity, UUID> {

    Optional<ProductCatalogEntity> findByName(String name);
    List<ProductCatalogEntity> findByNameLike(String key);

    // Construcción por Spring query methods - No lo usamos
    //List<ProductCatalogEntity> findByPriceBetween(BigDecimal min, BigDecimal max);

    // Definido por nosotros usando JPQL - Es el usado
    @Query("from productCatalog p where p.price between :min and :max")
    List<ProductCatalogEntity> findByBetweenTwoPrice(BigDecimal min, BigDecimal max);
}
