package com.debuggeandoideas.gadget_plus.repositories;

import com.debuggeandoideas.gadget_plus.entities.ProductCatalogEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductCatalogRepository extends JpaRepository<ProductCatalogEntity, UUID> {

}
