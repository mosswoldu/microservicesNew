package Inventory.Service.controller;

import Inventory.Service.model.Inventory;
import Inventory.Service.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @GetMapping("/{sku-code}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<Inventory> isInStock(@PathVariable("sku-code") String skuCode) {
        return inventoryService.isInStock(skuCode);
    }
    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<Inventory> findAll(){
        return inventoryService.getAll();
    }

}

