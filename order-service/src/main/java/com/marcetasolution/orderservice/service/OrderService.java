package com.marcetasolution.orderservice.service;

import com.marcetasolution.orderservice.dto.OrderLineItemDto;
import com.marcetasolution.orderservice.dto.OrderRequest;
import com.marcetasolution.orderservice.model.Order;
import com.marcetasolution.orderservice.model.OrderLineItem;
import com.marcetasolution.orderservice.repository.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository){
        this.orderRepository = orderRepository;
    }

    public void placeOrder(OrderRequest orderRequest){
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());

        List<OrderLineItem> orderLineItems =  orderRequest.getOrderLineItemDtos()
                .stream()
                .map(orderLineItemDto -> mapToOrderLineItem(orderLineItemDto)).toList();

        order.setOrderLineItemsList(orderLineItems);

        orderRepository.save(order);
    }

    public OrderLineItem mapToOrderLineItem(OrderLineItemDto orderLineItemDto){
        OrderLineItem item = new OrderLineItem();
        item.setPrice(orderLineItemDto.getPrice());
        item.setQuantity(orderLineItemDto.getQuantity());
        item.setSkuCode(orderLineItemDto.getSkyCode());

        return item;
    }

}
