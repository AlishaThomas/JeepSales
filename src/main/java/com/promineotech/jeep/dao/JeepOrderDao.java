package com.promineotech.jeep.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import com.promineotech.jeep.entity.entities.Color;
import com.promineotech.jeep.entity.entities.Customer;
import com.promineotech.jeep.entity.entities.Engine;
import com.promineotech.jeep.entity.Jeep;
import com.promineotech.jeep.entity.JeepModel;
import com.promineotech.jeep.entity.entities.Option;
import com.promineotech.jeep.entity.entities.Order;
import com.promineotech.jeep.entity.entities.Tire;

public interface JeepOrderDao {
  List<Option> fetchOptions(List<String> optionIds);
  Optional<Customer> fetchCustomer(String customerId);
  Optional<Jeep> fetchModel(JeepModel model, String trim, int doors);
  Optional<Color> fetchColor(String colorId);
  Optional<Engine> fetchEngine(String engineId);
  Optional<Tire> fetchTire(String tireId);
  
  
  Order saveOrder(Customer customer, Jeep jeep, Color color, Engine engine, 
      Tire tire, BigDecimal price, List<Option> options);
  
}
