package com.debuggeandoideas.gadget_plus;

import com.debuggeandoideas.gadget_plus.entities.BillEntity;
import com.debuggeandoideas.gadget_plus.entities.OrderEntity;
import com.debuggeandoideas.gadget_plus.entities.ProductEntity;
import com.debuggeandoideas.gadget_plus.repositories.BillRepository;
import com.debuggeandoideas.gadget_plus.repositories.OrderRepository;
import com.debuggeandoideas.gadget_plus.repositories.ProductCatalogRepository;
import com.debuggeandoideas.gadget_plus.repositories.ProductRepository;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;
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

	public static void main(String[] args) {
		SpringApplication.run(GadgetPlusApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		// SELECT * FROM products_catalog;
		var productCatalog1 = this.productCatalogRepository.findAll().get(0);
		var productCatalog2 = this.productCatalogRepository.findAll().get(4);
		var productCatalog3 = this.productCatalogRepository.findAll().get(7);

		// SELECT * FROM order WHERE id = '1';
		var order = this.orderRepository.findById(1L).orElseThrow();

		// Creo tres productos - todavia no la asigno a la tabla product
		var product1 = ProductEntity.builder().quantity(BigInteger.ONE).build();
		var product2 = ProductEntity.builder().quantity(BigInteger.TWO).build();
		var product3 = ProductEntity.builder().quantity(BigInteger.TEN).build();

		// Creo una lista con los tres productos
		var products = List.of(product1, product2, product3);

		// Añado aca producto su catalogo que es el detalle.
		product1.setCatalog(productCatalog1);
		product2.setCatalog(productCatalog2);
		product3.setCatalog(productCatalog3);

		// A order le añado la lista de productos
		order.setProduct(products);

		// Desde el lado de productos le tengo que asociar que se relaciona con el registro 1 de la tabla order
		products.forEach(p -> p.setOrder(order));

		// Gravo el registro 1 de la tabla order que lleva una lista de tres productos
		this.orderRepository.save(order);

		//Borramos el product1
		//order.getProduct().remove(0);
	}
}
