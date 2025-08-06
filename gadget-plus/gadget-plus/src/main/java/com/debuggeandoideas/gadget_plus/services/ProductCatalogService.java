package com.debuggeandoideas.gadget_plus.services;

import com.debuggeandoideas.gadget_plus.dtos.DataEval;
import com.debuggeandoideas.gadget_plus.dtos.ReportProduct;
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
    List<ProductCatalogEntity> findByCategoryId(Long id);
    List<ProductCatalogEntity> findByLaunchingDate(LocalDate date, DataEval key);
    List<ProductCatalogEntity> findByBrandAndRating(String brand, Short rating);
    List<ProductCatalogEntity> findByBrandOrRating(String brand, Short rating);
    List<ReportProduct> makeReport();

    Page<ProductCatalogEntity> findAll(String field, Boolean desc, Integer page);
    Page<ProductCatalogEntity> findAllByBrand(String brand);

    Integer countByBrand(String brand);
}
