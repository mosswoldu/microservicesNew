package com.microProgram.orderservice.dto;

import com.microProgram.orderservice.model.OrderLineItems;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {


    private List<OrderLineItemsDto> orderLineItemsDtoList;
}
