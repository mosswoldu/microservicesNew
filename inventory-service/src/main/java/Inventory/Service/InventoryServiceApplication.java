package Inventory.Service;

import Inventory.Service.model.Inventory;
import Inventory.Service.repository.InventoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
public class InventoryServiceApplication {

	public static void main(String[] args) {

		SpringApplication.run(InventoryServiceApplication.class, args);


	}
	@Bean
	public CommandLineRunner loadData(InventoryRepository inventoryRepository){
		return args->{
			Inventory inventory=new Inventory();
			inventory.setSkuCode("iphone_13");
			inventory.setQuantity(120);

			Inventory inventory1=new Inventory();
			inventory1.setSkuCode("iphone_15");
			inventory1.setQuantity(110);

			Inventory inventory2=new Inventory();
			inventory2.setSkuCode("iphone_14");
			inventory2.setQuantity(90);
			Inventory inventory3=new Inventory();
			inventory3.setSkuCode("samsung23");
			inventory3.setQuantity(2);;
			inventoryRepository.save(inventory3);

			inventoryRepository.save(inventory);
			inventoryRepository.save(inventory1);
			inventoryRepository.save(inventory2);

		};
	}

}
