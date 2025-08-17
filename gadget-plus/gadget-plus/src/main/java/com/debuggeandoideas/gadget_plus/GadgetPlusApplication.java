package com.debuggeandoideas.gadget_plus;

import com.debuggeandoideas.gadget_plus.entities.BillEntity;
import com.debuggeandoideas.gadget_plus.entities.OrderEntity;
import com.debuggeandoideas.gadget_plus.entities.ProductEntity;
import com.debuggeandoideas.gadget_plus.repositories.*;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

import com.debuggeandoideas.gadget_plus.services.CatalogBatch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.debuggeandoideas.gadget_plus.repositories")
public class GadgetPlusApplication implements CommandLineRunner {

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private BillRepository billRepository;

	@Autowired
	private ProductCatalogRepository productCatalogRepository;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private RejectProductRepository rejectProductRepository;

    @Autowired
    private CatalogBatch catalogBatch;

	public static void main(String[] args) {
		SpringApplication.run(GadgetPlusApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

        // PROCEDIMIENTO DE INSERCIÓN Y BORRAR DE TIPO BATCH
        /*this.catalogBatch.insertBatch();
        Thread.sleep(10000);
        this.catalogBatch.deleteBatch();*/

        // PROCEDIMIENTO PARA RECREAR LOS DATOS EN TODA LAS TABLAS
		/* this.rejectProductRepository.findAll().forEach(System.out::println);

		final var HOME = this.categoryRepository.findById(1L).orElseThrow();
		final var OFFICE = this.categoryRepository.findById(2L).orElseThrow();

		this.productCatalogRepository.findAll().forEach(product -> {

			if (product.getDescription().contains("home")){
				product.addCategory(HOME);
			}

			if (product.getDescription().contains("office")){
				product.addCategory(OFFICE);
			}

			this.productCatalogRepository.save(product);
		});

		var random = new Random();

		var productsCatalog = new LinkedList<>(this.productCatalogRepository.findAll());

		IntStream.range(0, productsCatalog.size()).forEach(i-> {
			var idOrderRandom = random.nextLong(16) + 1;
			var orderRandom = this.orderRepository.findById(idOrderRandom).orElseThrow();
			var product = ProductEntity.builder()
					.quantity(BigInteger.valueOf(random.nextInt(5) + 1))
					.catalog(productsCatalog.poll())
					.build();

			orderRandom.addProduct(product);
			product.setOrder(orderRandom);
			this.orderRepository.save(orderRandom);
		}); */
	}
}
