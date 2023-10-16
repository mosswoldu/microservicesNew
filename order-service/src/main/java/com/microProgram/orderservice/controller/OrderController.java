package com.microProgram.orderservice.controller;

import com.microProgram.orderservice.dto.OrderRequest;
import com.microProgram.orderservice.service.OrderService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
   @CircuitBreaker(name = "inventory", fallbackMethod = "fallbackMethodResponse")
    @TimeLimiter(name = "inventory")
    @Retry(name ="inventory" )
//    public String createOrder(@RequestBody OrderRequest orderRequest) {
    public CompletableFuture<String> createOrder(@RequestBody OrderRequest orderRequest) {
       return CompletableFuture.supplyAsync(()->  orderService.addOrder(orderRequest));
//        return CompletableFuture.supplyAsync(()-> "order placed successfully");
    }
    public  CompletableFuture<String>  fallbackMethodResponse(OrderRequest orderRequest, RuntimeException runtimeException){
        return CompletableFuture.supplyAsync(()-> "Oops! your oder was not successful, try another time" );
    }
}
