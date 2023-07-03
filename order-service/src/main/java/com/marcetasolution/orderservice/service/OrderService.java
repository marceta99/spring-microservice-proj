package com.marcetasolution.orderservice.service;

import com.marcetasolution.orderservice.dto.InventoryResponse;
import com.marcetasolution.orderservice.dto.OrderLineItemDto;
import com.marcetasolution.orderservice.dto.OrderRequest;
import com.marcetasolution.orderservice.model.Order;
import com.marcetasolution.orderservice.model.OrderLineItem;
import com.marcetasolution.orderservice.repository.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;
    private final WebClient webClient;
    public OrderService(OrderRepository orderRepository, WebClient webClient){
        this.orderRepository = orderRepository;
        this.webClient = webClient;
    }

    public void placeOrder(OrderRequest orderRequest){
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());

        List<OrderLineItem> orderLineItems =  orderRequest.getOrderLineItemDtos()
                .stream()
                .map(orderLineItemDto -> mapToOrderLineItem(orderLineItemDto)).toList();

        order.setOrderLineItemsList(orderLineItems);

        List<String> skuCodes =
                order.getOrderLineItemsList().stream().map(orderLineItem -> orderLineItem.getSkuCode()).toList();

        //call inventory service and save order if product is in stock in inventory
        InventoryResponse[] inventoryResponseArray  = webClient.get()
                 .uri("http://localhost:8080/api/inventory",
                         uriBuilder -> uriBuilder.queryParam("skuCodes", skuCodes).build())
                 .retrieve()
                 .bodyToMono(InventoryResponse[].class)
                 .block(); //we need this block for this to be sychronious call, because by default is asynchronious


        boolean allProductsIsInStock =
                Arrays.stream(inventoryResponseArray).allMatch(inventoryResponse -> inventoryResponse.getIsInStock());

        if(allProductsIsInStock){
            orderRepository.save(order);
        }else{
            throw new IllegalArgumentException("Product is not in stock, please try again later");
        }
    }

    public OrderLineItem mapToOrderLineItem(OrderLineItemDto orderLineItemDto){
        OrderLineItem item = new OrderLineItem();
        item.setPrice(orderLineItemDto.getPrice());
        item.setQuantity(orderLineItemDto.getQuantity());
        item.setSkuCode(orderLineItemDto.getSkyCode());

        return item;
    }

}
