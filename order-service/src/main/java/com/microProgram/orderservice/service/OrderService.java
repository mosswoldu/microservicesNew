package com.microProgram.orderservice.service;

import com.microProgram.orderservice.dto.InventoryResponse;
import com.microProgram.orderservice.dto.OrderLineItemsDto;
import com.microProgram.orderservice.dto.OrderRequest;
import com.microProgram.orderservice.model.Order;
import com.microProgram.orderservice.model.OrderLineItems;
import com.microProgram.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {
    private final OrderRepository orderRepository;
    private  final WebClient webClient;

    public void addOrder(OrderRequest orderRequest){
        Order order= new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        List<OrderLineItems> orderLineItems = new ArrayList<>();
        if (orderRequest.getOrderLineItemsDtOList() != null) {
            orderLineItems = orderRequest.getOrderLineItemsDtOList()
                    .stream()
                    .map(this::mapToDto)
                    .toList();
        }

        order.setOrderLineItemsList(orderLineItems);
      List<String> skuCodes=  order.getOrderLineItemsList().stream()
                // .map(orderLineItem->orderLineItem.getSkuCode())
                .map(OrderLineItems::getSkuCode)
                .toList();
       InventoryResponse[] inventoryResponsesArray= webClient.get()
                .uri("http:/localhost:8083/api/inventory",
                        uriBuilder -> uriBuilder.queryParam("skuCode",skuCodes).build())
                .retrieve()
                        .bodyToMono(InventoryResponse[].class)
                                .block(); //default will be asynchronous call

      boolean allProductsInStock=  Arrays.stream(inventoryResponsesArray).allMatch(InventoryResponse::isInStock);
        if(allProductsInStock){
            orderRepository.save(order);
        }else{

                throw new IllegalArgumentException("product is not in stock, please order after few days");
            }
        //synchronous call-recommended is to use WebClient than RestTemplate as it has more
        //models and Synchronous, asynchronous  and streaming scenarios
        //call the InventoryService and place order if present


    }

    private OrderLineItems mapToDto(OrderLineItemsDto orderLineItemsDto) {
        OrderLineItems orderLineItems=new OrderLineItems();
        orderLineItems.setPrice(orderLineItemsDto.getPrice());
        orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
        orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
        return orderLineItems;
    }


}
