package com.bahlam.brms.training.service;

import java.util.Collection;
import com.bahlam.brms.training.model.Order;


public interface OrderService {

    public Collection<Order> getOrdersByCustomer(Long customerId);
}