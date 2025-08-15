package com.debuggeandoideas.gadget_plus.services;

import com.debuggeandoideas.gadget_plus.dtos.OrderDTO;

public interface OrdersCrudService {

    String create(OrderDTO order);
    OrderDTO read(Long id);
    OrderDTO update(OrderDTO order, Long id);
    void delete(Long id);
    void delete(String clientName);
}
