package Inventory.Service.controller;

import Inventory.Service.dto.InventoryResponse;
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

   // @GetMapping("/{sku-code}")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<InventoryResponse> isInStock(@RequestParam List<String> skuCode) {
        return inventoryService.isInStock(skuCode);

        //http:/localhost:8083/api/inventory?sku-code=iphone-13 & sku-code=iphone-13-red;
    }


    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<Inventory> findAll(){
        return inventoryService.getAll();
    }

}

