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

	public static void main(String[] args) {
		SpringApplication.run(GadgetPlusApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		this.rejectProductRepository.findAll().forEach(System.out::println);

		//final var HOME = this.categoryRepository.findById(1L).orElseThrow();
		//final var OFFICE = this.categoryRepository.findById(2L).orElseThrow();

		//this.productCatalogRepository.findAll().forEach(product -> {

			//if (product.getDescription().contains("home")){
			//	product.addCategory(HOME);
			//}

			//if (product.getDescription().contains("office")){
			//	product.addCategory(OFFICE);
			//}

			//this.productCatalogRepository.save(product);
		//});

		// Genero una variable de valor aleatorio
		//var random = new Random();

		// Creo una cola con LinkedList y contiene la colección de datos de todo
		// el contenido de la tabla products_catalog. su tamaño es de 42 registros
		//var productsCatalog = new LinkedList<>(this.productCatalogRepository.findAll());

		// Con el lambda IntStream(0, 42) puedo usar un forEach()
		// La alternativa seria: for(int i = 0; i<productsCatalog.size;i++)
		//IntStream.range(0, productsCatalog.size()).forEach(i-> {
			// Limito los valores aleatorio entre el 1 al 16
			// La funcion genera del 0 al 15 pero al sumar 1 se arregla el rango
			//var idOrderRandom = random.nextLong(16) + 1;

			// Me traigo un registro orden usando como indice idOrderRandom
			//var orderRandom = this.orderRepository.findById(idOrderRandom).orElseThrow();

			// Ahora creo un objeto product.
			// quantity entre 1 a 6
			// catalog le aplico el contenido de productsCatalog
			/*var product = ProductEntity.builder()
					.quantity(BigInteger.valueOf(random.nextInt(5) + 1))
					.catalog(productsCatalog.poll())
					.build();*/

			// Añado a la lista product (definida en OrderEntity) el objeto product
			//orderRandom.addProduct(product);

			// al objeto product le añado mediante setOrder, la orden encontrada.
			//product.setOrder(orderRandom);

			// Salvamos el objeto order.
			//this.orderRepository.save(orderRandom);
		//});
	}
}
