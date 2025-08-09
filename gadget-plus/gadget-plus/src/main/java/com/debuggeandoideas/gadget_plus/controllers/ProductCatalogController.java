package com.debuggeandoideas.gadget_plus.controllers;

import com.debuggeandoideas.gadget_plus.dtos.DataEval;
import com.debuggeandoideas.gadget_plus.dtos.ReportProduct;
import com.debuggeandoideas.gadget_plus.entities.ProductCatalogEntity;
import com.debuggeandoideas.gadget_plus.enums.LikeKey;
import com.debuggeandoideas.gadget_plus.services.ProductCatalogService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@RestController
@RequestMapping(path = "product-catalog")
@RequiredArgsConstructor
public class ProductCatalogController {

    private final ProductCatalogService productCatalogService;

    @GetMapping(path="{id}")
    public ResponseEntity<ProductCatalogEntity> getById(@PathVariable String id) {
        return ResponseEntity.ok(this.productCatalogService.findById(UUID.fromString(id)));
    }

    @GetMapping(path="name/{name}")
    public ResponseEntity<ProductCatalogEntity> getByName(@PathVariable String name) {
        return ResponseEntity.ok(this.productCatalogService.findByName(name));
    }

    @GetMapping(path="like/{key}")
    public ResponseEntity<List<ProductCatalogEntity>> getByNameLike(@PathVariable LikeKey key, @RequestParam String word) {
        final var placeholder = "%";
        System.out.println("Entre en CONTROLLER \n");

        if(key.equals(LikeKey.AFTER)){
            return ResponseEntity.ok(this.productCatalogService.findNameLike(placeholder + word));
        }

        if(key.equals(LikeKey.BETWEEN)){
            return ResponseEntity.ok(this.productCatalogService.findNameLike(placeholder + word + placeholder));
        }

        if(key.equals(LikeKey.BEFORE)){
            System.out.println("Entre en el IF de BEFORE \n");
            return ResponseEntity.ok(this.productCatalogService.findNameLike(word + placeholder));
        }

        System.out.println("No pase por los IF \n");
        return ResponseEntity.badRequest().build();
    }

    @GetMapping(path="between")
    public ResponseEntity<List<ProductCatalogEntity>> getByBetweenTwoPrice(@RequestParam BigDecimal min,
                                                                     @RequestParam BigDecimal max) {
        return ResponseEntity.ok(this.productCatalogService.findByBetweenTwoPrice(min, max));
    }

    @GetMapping(path="category")
    public ResponseEntity<List<ProductCatalogEntity>> getByCategory(@RequestParam Long id) {
        return ResponseEntity.ok(this.productCatalogService.findByCategoryId(id));
    }

    @GetMapping(path="date-launch/{key}")
    public ResponseEntity<List<ProductCatalogEntity>> getByDate(
            @PathVariable DataEval key,
            @RequestParam LocalDate date) {
        return ResponseEntity.ok(this.productCatalogService.findByLaunchingDate(date, key));
    }

    @GetMapping(path="brand-rating")
    public ResponseEntity<List<ProductCatalogEntity>> getByBrandAndRating(
            @RequestParam String brand,
            @RequestParam Short rating) {
        return ResponseEntity.ok(this.productCatalogService.findByBrandAndRating(brand, rating));
    }

    @GetMapping(path="brand-or-rating")
    public ResponseEntity<List<ProductCatalogEntity>> getByBrandOrRating(
            @RequestParam String brand,
            @RequestParam Short rating) {
        return ResponseEntity.ok(this.productCatalogService.findByBrandOrRating(brand, rating));
    }

    @GetMapping(path="report")
    public ResponseEntity<List<ReportProduct>> getReport() {
        return ResponseEntity.ok(this.productCatalogService.makeReport());
    }

    @GetMapping(path="all")
    public ResponseEntity<Page<ProductCatalogEntity>> getAll(
            @RequestParam(required = false) String field,
            @RequestParam(required = false) Boolean desc,
            @RequestParam(required = true) Integer page
    ) {
        return ResponseEntity.ok(this.productCatalogService.findAll(field, desc, page));
    }

    @GetMapping(path="allsort")
    public ResponseEntity<Page<ProductCatalogEntity>> getAllSort(
            @RequestParam(required = true) String field,
            @RequestParam(required = true) Boolean desc,
            @RequestParam(required = true) Integer page
    ) {
        return ResponseEntity.ok(this.productCatalogService.findAllSort(field, desc, page));
    }

    @GetMapping(path="all-by-brand")
    public ResponseEntity<Page<ProductCatalogEntity>> getAllByBrand(
            @RequestParam String brand,
            @RequestParam Integer page
    ) {
        return ResponseEntity.ok(this.productCatalogService.findAllByBrand(brand, page));
    }

    @GetMapping(path="brand-count/{brand}")
    public ResponseEntity<Integer> getCountByBrand(@PathVariable String brand) {
        return ResponseEntity.ok(this.productCatalogService.countByBrand(brand));
    }
}
