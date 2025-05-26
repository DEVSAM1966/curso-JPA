package com.debuggeandoideas.gadget_plus.repositories;

import com.debuggeandoideas.gadget_plus.entities.OrderEntity;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<OrderEntity, Long> {

}
