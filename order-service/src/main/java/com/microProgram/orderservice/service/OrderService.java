package com.microProgram.orderservice.service;

import com.microProgram.orderservice.dto.InventoryResponse;
import com.microProgram.orderservice.dto.OrderLineItemsDto;
import com.microProgram.orderservice.dto.OrderRequest;
import com.microProgram.orderservice.event.OrderCreatedEvent;
import com.microProgram.orderservice.model.Order;
import com.microProgram.orderservice.model.OrderLineItems;
import com.microProgram.orderservice.repository.OrderRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;
    //    private final WebClient webClient;
    private final WebClient.Builder webClientBuilder;
    private final KafkaTemplate<String,OrderCreatedEvent> kafkaTemplate;

//      public void addOrder(OrderRequest orderRequest) {
    public String addOrder(OrderRequest orderRequest) {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());

        List<OrderLineItems> orderLineItems = orderRequest.getOrderLineItemsDtoList()
                .stream()
                .map(this::mapToDto)
                .toList();

//        List<OrderLineItems> orderLineItems = new ArrayList<>();
//        if (orderRequest.getOrderLineItemsDtoList() != null) {
//            orderLineItems = orderRequest.getOrderLineItemsDtoList()
//                    .stream()
//                    .map(this::mapToDto)
//                    .toList();
//        }
//
        order.setOrderLineItemsList(orderLineItems);

              //collect all the sku-codes as a list
        List<String> skuCodes = order.getOrderLineItemsList()
                .stream()
                // .map(orderLineItem->orderLineItem.getSkuCode())
                .map(OrderLineItems::getSkuCode)
                .toList();

        // Call Inventory Service, and place order if product is in
        // stock
        InventoryResponse[] inventoryResponsArray = webClientBuilder.build().get()
                // .uri("http://localhost:8083/api/inventory",
                .uri("http://inventory-service/api/inventory",
                        uriBuilder -> uriBuilder.queryParam("skuCode", skuCodes).build())
                .retrieve()
                .bodyToMono(InventoryResponse[].class)
                .block(); //default will be asynchronous call

       //log.info("Checking inventory");

        boolean allProductsInStock = Arrays.stream(inventoryResponsArray)
                .allMatch(InventoryResponse::isInStock);

        if(allProductsInStock){
            orderRepository.save(order);
            kafkaTemplate.send("notificationTopic",new OrderCreatedEvent(order.getOrderNumber()));
            return "order placed successfully";
        } else {
            throw new IllegalArgumentException("product is not in stock, please order after few days");
        }
    }


//        //synchronous call-recommended is to use WebClient than RestTemplate as it has more
//        //models and Synchronous, asynchronous  and streaming scenarios
//        //call the InventoryService and place order if present

    private OrderLineItems mapToDto(OrderLineItemsDto orderLineItemsDto) {
        OrderLineItems orderLineItems = new OrderLineItems();
        orderLineItems.setPrice(orderLineItemsDto.getPrice());
        orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
        orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
        return orderLineItems;
    }
}





