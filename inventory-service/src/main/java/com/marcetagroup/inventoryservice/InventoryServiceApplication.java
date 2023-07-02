package com.marcetagroup.inventoryservice;

import com.marcetagroup.inventoryservice.model.Inventory;
import com.marcetagroup.inventoryservice.repository.InventoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class InventoryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventoryServiceApplication.class, args);
	}

	@Bean
	public CommandLineRunner loadData(InventoryRepository inventoryRepository){
		return args -> {
			Inventory inventory = new Inventory();
			inventory.setSkuCode("iphone 12");
			inventory.setQuantity(200);

			Inventory inventory1 = new Inventory();
			inventory1.setSkuCode("samsung galaxy 1");
			inventory1.setQuantity(202);

			inventoryRepository.save(inventory);
			inventoryRepository.save(inventory1);

		};
	}

}
