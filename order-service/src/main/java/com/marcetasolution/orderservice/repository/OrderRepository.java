package com.marcetasolution.orderservice.repository;

import com.marcetasolution.orderservice.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
