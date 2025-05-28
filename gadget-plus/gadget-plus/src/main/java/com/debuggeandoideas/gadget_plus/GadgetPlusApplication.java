package com.debuggeandoideas.gadget_plus;

import com.debuggeandoideas.gadget_plus.entities.BillEntity;
import com.debuggeandoideas.gadget_plus.entities.OrderEntity;
import com.debuggeandoideas.gadget_plus.repositories.BillRepository;
import com.debuggeandoideas.gadget_plus.repositories.OrderRepository;
import java.math.BigDecimal;
import java.time.LocalDateTime;
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

	public static void main(String[] args) {
		SpringApplication.run(GadgetPlusApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		this.billRepository.findAll().forEach(bill -> System.out.println(bill.getRfc()));

		this.orderRepository.findAll().forEach(orderEntity -> System.out.println(orderEntity.toString()));

		// Creamos un nuevo elemento BillEntity con builder().
		var bill = BillEntity.builder()
				.rfc("AS537GD7D")
				.totalAmount(BigDecimal.TEN)
				.id("b-17")
				.build();

		// Creamos un nuevo elemento OrderEntity con builder().
		var order = OrderEntity.builder()
				.createdAt(LocalDateTime.now())
				.clientName("Alex Pereira")
				.bill(bill)
				.build();

		// Gravamos el elemento orden que hemos creado en la BD.
		this.orderRepository.save(order);
	}
}
