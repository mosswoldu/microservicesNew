package Inventory.Service.service;

import Inventory.Service.dto.InventoryResponse;
import Inventory.Service.model.Inventory;
import Inventory.Service.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    @Transactional(readOnly = true)
//    public Optional<Inventory> isInStock(String skuCode) {
    public List<InventoryResponse> isInStock(List<String> skuCode) {
        return inventoryRepository.findBySkuCodeIn(skuCode)
                .stream()
                .map(inventory ->
                    InventoryResponse.builder().skuCode(inventory.getSkuCode())
                            .isInStock(inventory.getQuantity()>0)
                            .build()
                )
                .toList();
    }
    public List<Inventory> getAll(){

        return inventoryRepository.findAll();
    }

//    public Optional<Inventory> updateSkuCode(Inventory inventory){
//
//        inventory.setSkuCode(inventory.getSkuCode());
//        inventory.setQuantity(inventory.getQuantity());
//       return Optional.of(inventoryRepository.save(inventory));
//    }
}
