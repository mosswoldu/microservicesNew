package Inventory.Service.repository;

import Inventory.Service.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InventoryRepository  extends JpaRepository<Inventory,Long> {
    Optional<Inventory> findBySkuCode(String skuCode);
//    Optional<Inventory> updateSkuCode(Inventory inventory);
}
