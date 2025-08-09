package com.debuggeandoideas.gadget_plus.repositories;

import com.debuggeandoideas.gadget_plus.dtos.ReportProduct;
import com.debuggeandoideas.gadget_plus.entities.ProductCatalogEntity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

public interface ProductCatalogRepository extends JpaRepository<ProductCatalogEntity, UUID> {

    Optional<ProductCatalogEntity> findByName(String name);
    List<ProductCatalogEntity> findByNameLike(String key);

    // Construcción por Spring query methods - No lo usamos
    //List<ProductCatalogEntity> findByPriceBetween(BigDecimal min, BigDecimal max);

    // Definido por nosotros usando JPQL - Es el usado
    @Query("from productCatalog p where p.price between :min and :max")
    List<ProductCatalogEntity> findByBetweenTwoPrice(BigDecimal min, BigDecimal max);

    // Implementación de la query:  SELECT * FROM products_catalog pc
    // JOIN product_join_category pjc ON pc.id = pjc.id_product
    // JOIN categories c ON c.id = pjc.id_category WHERE c.id = 2 o 1
    @Query("from productCatalog p left join fetch p.categories c where c.id= :categoryId")
    List<ProductCatalogEntity> getByCategory(Long categoryId);

    // Implementación de la query que filtra por una fecha.
    List<ProductCatalogEntity> findByLaunchingDateBefore(LocalDate date);
    List<ProductCatalogEntity> findByLaunchingDateAfter(LocalDate date);

    // Implementación de query que filtra por brand_name and rating
    List<ProductCatalogEntity> findByBrandAndRatingGreaterThan(String brand, Short rating);

    // Implementación de query que filtra por brand_name or rating
    List<ProductCatalogEntity> findByBrandOrRatingGreaterThan(String brand, Short rating);

    // Implementar el query con group by
    @Query("select new com.debuggeandoideas.gadget_plus.dtos.ReportProduct(" +
            "pc.brand," +
            "avg(pc.price)," +
            "sum(pc.price))" +
            "from productCatalog pc group by pc.brand"
    )
    List<ReportProduct> findAndMakeReport();

    // Implementar un paginación personalizada
    Page<ProductCatalogEntity> findAllByBrand(String brand, Pageable pageable);

    // Implementar el uso de una stored procedure
    @Procedure(procedureName = "count_total_products_by_brand", outputParameterName = "response")
    Integer countTotalProductsByBrandStoreProcedure(@Param(value="brand") String brand);
}
