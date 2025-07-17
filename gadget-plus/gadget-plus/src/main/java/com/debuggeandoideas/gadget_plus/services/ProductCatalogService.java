package com.debuggeandoideas.gadget_plus.services;


import com.debuggeandoideas.gadget_plus.entities.ProductCatalogEntity;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface ProductCatalogService {

    ProductCatalogEntity findById(UUID id);
    ProductCatalogEntity findByName(String name);
    List<ProductCatalogEntity> findNameLike(String key);
    List<ProductCatalogEntity> findByBetweenTwoPrice(BigDecimal min, BigDecimal max);
    List<ProductCatalogEntity> findByCategoryName(BigInteger id);
    //List<ProductCatalogEntity> findByLaunchingDate(LocalDate date, DataEval key);
    List<ProductCatalogEntity> findByBrandAndRating(String brand, Short rating);
    //List<StatisticProduct> findStatistics();

    Page<ProductCatalogEntity> findAll(String field,Boolean desc);
    Page<ProductCatalogEntity> findAllByBrand(String brand);

    Integer countByBrand(String brand);
}
