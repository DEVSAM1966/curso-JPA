package com.debuggeandoideas.gadget_plus.repositories;

import com.debuggeandoideas.gadget_plus.entities.RejectProductEntity;
import com.debuggeandoideas.gadget_plus.entities.RejectProductId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RejectProductRepository extends JpaRepository<RejectProductEntity, RejectProductId> {
}
