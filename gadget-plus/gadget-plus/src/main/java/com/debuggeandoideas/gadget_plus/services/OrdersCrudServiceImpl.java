package com.debuggeandoideas.gadget_plus.services;

import com.debuggeandoideas.gadget_plus.dtos.BillDTO;
import com.debuggeandoideas.gadget_plus.dtos.OrderDTO;
import com.debuggeandoideas.gadget_plus.dtos.ProductsDTO;
import com.debuggeandoideas.gadget_plus.entities.BillEntity;
import com.debuggeandoideas.gadget_plus.entities.OrderEntity;
import com.debuggeandoideas.gadget_plus.entities.ProductCatalogEntity;
import com.debuggeandoideas.gadget_plus.entities.ProductEntity;
import com.debuggeandoideas.gadget_plus.repositories.OrderRepository;
import com.debuggeandoideas.gadget_plus.repositories.ProductCatalogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class OrdersCrudServiceImpl implements OrdersCrudService{

    private final OrderRepository orderRepository;
    private final ProductCatalogRepository productCatalogRepository;

    @Override
    public String create(OrderDTO order) {
        final var toInsert = this.mapOrderFromDto(order);
        toInsert.setCreatedAt(LocalDateTime.now());
        //return "17";
        return this.orderRepository.save(toInsert).getId().toString();
    }

    @Override
    public OrderDTO read(Long id) {
        return this.mapOrderFromEntity(this.orderRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found")));
    }

    @Override
    public OrderDTO update(OrderDTO order, Long id) {
        // Busco el registro a modificar y se almacena en toUpdate
        final var toUpdate = this.orderRepository.findById(id).orElseThrow();

        // aplico setter al objeto toUpdate
        toUpdate.setClientName(order.getClientName());
        toUpdate.getBill().setClientRfc(order.getBill().getClientRfc());

        // Guardo toUpdate en BD y devuelvo el resultado en un objeto DTO.
        return this.mapOrderFromEntity(this.orderRepository.save(toUpdate));
    }

    @Override
    public void delete(Long id) {

        if (orderRepository.existsById(id)) {
            orderRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Client not exist");
        }

        // Forma de borrar no tan eficiente
        /* var toDelete = orderRepository.findById(id).orElseThrow();

        this.orderRepository.delete(toDelete); */
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public void delete(String clientName) {

        if (orderRepository.existsByClientName(clientName)) {
            orderRepository.deleteByClientName(clientName);
        } else {
            throw new IllegalArgumentException("Client not exist");
        }
    }

    private OrderDTO mapOrderFromEntity(OrderEntity orderEntity) {
        final var modelMapper = new ModelMapper();

        modelMapper
                .typeMap(ProductEntity.class, ProductsDTO.class)
                .addMappings(mapper -> mapper.map(
                        entity -> entity.getCatalog().getName(), ProductsDTO::setName
                ));

        return modelMapper.map(orderEntity, OrderDTO.class);
    }

    private OrderEntity mapOrderFromDto(OrderDTO orderDTO) {

        final var orderResponse = new OrderEntity();
        final var modelMapper = new ModelMapper();

        //Mapeo personalizado de idBill (bill dto) a id (bill entity)
        modelMapper
                .typeMap(BillDTO.class, BillEntity.class)
                        .addMappings(mapper -> mapper.map(
                                BillDTO::getIdBill, BillEntity::setId
                                ));
        System.out.println("ANTES: id -> " + orderResponse.getId());
        System.out.println("clientName -> " + orderResponse.getClientName());
        System.out.println("createdAt -> " + orderResponse.getCreatedAt());
        System.out.println("-------------------");

        //log.info("Before {}", orderResponse);
        modelMapper.map(orderDTO, orderResponse);

        System.out.println("DESPUES: id -> " + orderResponse.getId());
        System.out.println("clientName -> " + orderResponse.getClientName());
        System.out.println("createdAt -> " + orderResponse.getCreatedAt());
        System.out.println("Bill - id -> " + orderResponse.getBill().getId());
        System.out.println("Bill - clientRfc -> " + orderResponse.getBill().getClientRfc());
        System.out.println("Bill - totalAmount -> " + orderResponse.getBill().getTotalAmount());
        System.out.println("-------------------");

        //log.info("After {}", orderResponse);

        //this.getAndSetProducts(orderDTO.getProducts(), orderResponse);
        final var total = this.getAndSetProductsAndTotal(orderDTO.getProducts(), orderResponse);

        System.out.println("DESPUES con productos: id -> " + orderResponse.getId());
        System.out.println("clientName -> " + orderResponse.getClientName());
        System.out.println("createdAt -> " + orderResponse.getCreatedAt());
        System.out.println("Bill - id -> " + orderResponse.getBill().getId());
        System.out.println("Bill - clientRfc -> " + orderResponse.getBill().getClientRfc());
        System.out.println("Bill - totalAmount -> " + orderResponse.getBill().getTotalAmount());
        System.out.println("-------------------");
        //log.info("After with products {}", orderResponse);

        orderResponse.getBill().setTotalAmount(total);

        return orderResponse;
    }

    private void getAndSetProducts(List<ProductsDTO> productsDto, OrderEntity orderEntity) {

        productsDto.forEach(product -> {
            final var productFromCatalog = this.productCatalogRepository.findByName(product.getName()).orElseThrow();

            final var productEntity = ProductEntity
                    .builder()
                    .quantity(product.getQuantity())
                    .catalog(productFromCatalog)
                    .build();

            orderEntity.addProduct(productEntity);
            productEntity.setOrder(orderEntity);
        });

    }

    private BigDecimal getAndSetProductsAndTotal(List<ProductsDTO> productsDto, OrderEntity orderEntity) {
        // Creamos una referencia atomica de un bigdecimal que sera 0
        var total = new AtomicReference<>(BigDecimal.ZERO);

        // Creamos una lambda
        productsDto.forEach(product -> {
            final var productFromCatalog = this.productCatalogRepository.findByName(product.getName()).orElseThrow();

            total.updateAndGet(bigDecimal -> bigDecimal.add(productFromCatalog.getPrice()));

            final var productEntity = ProductEntity
                    .builder()
                    .quantity(product.getQuantity())
                    .catalog(productFromCatalog)
                    .build();

            orderEntity.addProduct(productEntity);
            productEntity.setOrder(orderEntity);
        });

        return total.get();
    }
}
