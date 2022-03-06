package com.promineotech.jeep.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import com.promineotech.jeep.entity.JeepModel;
import com.promineotech.jeep.entity.entities.Order;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Sql(
      scripts = {"classpath:flyway/migrations/V1.0__Jeep_Schema.sql",
    "classpath:flyway/migrations/V1.1__Jeep_Data.sql"}, 
    config = @SqlConfig(encoding = "utf-8"))
class CreateOrderTest {

  @LocalServerPort
  private int serverPort;
  
  @Autowired
  private TestRestTemplate restTemplate;
  
  @Test
  void testCreateOrderReturnsSuccess201() {
   String body = createOrderBody();
   String uri = String.format(
       "http://localhost:%d/orders", serverPort);
   HttpHeaders headers = new HttpHeaders();
   headers.setContentType(MediaType.APPLICATION_JSON);
   
   HttpEntity<String> bodyEntity = new HttpEntity<>(body, headers);

   ResponseEntity<Order> response = restTemplate.exchange(uri,
       HttpMethod.POST, bodyEntity, Order.class);

   assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
   assertThat(response.getBody()).isNotNull();

   Order order = response.getBody();
   assertThat(order.getCustomer().getCustomerId()).isEqualTo("MORISON_LINA");
   assertThat(order.getModel().getModelId()).isEqualTo(JeepModel.RENEGADE);
   assertThat(order.getModel().getTrimLevel()).isEqualTo("Jeepster");
   assertThat(order.getModel().getNumDoors()).isEqualTo(4);
   assertThat(order.getColor().getColorId()).isEqualTo("EXT_SNAZZBERRY");
   assertThat(order.getEngine().getEngineId()).isEqualTo("3_6_GAS");
   assertThat(order.getTire().getTireId()).isEqualTo("265_MICHELIN");
   assertThat(order.getOptions()).hasSize(5);

   
  }

    protected String createOrderBody() {
      //@formatter:off
      return "{\n"
          +" \"customer\":\"MORISON_LINA\",\n"
          +" \"model\":\"RENEGADE\",\n"
          +" \"trim\":\"Jeepster\",\n"
          +" \"doors\":\"4\",\n"
          +" \"color\":\"EXT_SNAZZBERRY\",\n"
          +" \"engine\":\"3_6_GAS\",\n"
          +" \"tire\":\"265_MICHELIN\",\n"
          +" \"options\":[\n"
          +" \"DOR_QUAD_4\",\n"
          +" \"EXT_AEV_LIFT\",\n"
          +" \"EXT_WARN_WINCH\",\n"
          +" \"EXT_WARN_BUMPER_FRONT\",\n"
          +" \"EXT_WARN_BUMPER_REAR\",\n"
          +" \"EXT_ARB_COMPRESSOR\"\n"
          +"  ]\n"
          +"}";
  }
  
}
