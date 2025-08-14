package com.debuggeandoideas.gadget_plus.controllers;

import com.debuggeandoideas.gadget_plus.dtos.OrderDTO;
import com.debuggeandoideas.gadget_plus.services.OrdersCrudService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping(path = "order")
@RequiredArgsConstructor
public class OrderController {

    private final OrdersCrudService ordersCrudService;

    @GetMapping(path="{id}")
    public ResponseEntity<OrderDTO> get(@PathVariable Long id) {
        return ResponseEntity.ok(ordersCrudService.read(id));
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody OrderDTO orderDTO) {
        var path = "/" + this.ordersCrudService.create(orderDTO);
        return ResponseEntity.created(URI.create(path)).build();
    }
}
