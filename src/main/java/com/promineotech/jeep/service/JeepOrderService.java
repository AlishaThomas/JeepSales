package com.promineotech.jeep.service;

import com.promineotech.jeep.entity.entities.Order;
import com.promineotech.jeep.entity.entities.OrderRequest;

public interface JeepOrderService {

  Order createOrder(OrderRequest orderRequest);
    
  
}
