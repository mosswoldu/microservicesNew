package com.microProgram.orderservice.service;

import com.microProgram.orderservice.dto.OrderLineItemsDto;
import com.microProgram.orderservice.dto.OrderRequest;
import com.microProgram.orderservice.model.Order;
import com.microProgram.orderservice.model.OrderLineItems;
import com.microProgram.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {
    private final OrderRepository orderRepository;

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
        orderRepository.save(order);

    }

    private OrderLineItems mapToDto(OrderLineItemsDto orderLineItemsDto) {
        OrderLineItems orderLineItems=new OrderLineItems();
        orderLineItems.setPrice(orderLineItemsDto.getPrice());
        orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
        orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
        return orderLineItems;
    }


}
