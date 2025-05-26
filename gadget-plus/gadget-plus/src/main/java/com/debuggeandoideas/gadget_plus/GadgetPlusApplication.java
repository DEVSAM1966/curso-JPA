package com.debuggeandoideas.gadget_plus;

import com.debuggeandoideas.gadget_plus.repositories.OrderRepository;
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

	public static void main(String[] args) {
		SpringApplication.run(GadgetPlusApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		this.orderRepository.findAll().forEach(orderEntity -> System.out.println(orderEntity.toString()));

	}
}
