package com.altioracorp.altioracorp.service;

import com.altioracorp.altioracorp.dto.OrderDto;
import com.altioracorp.altioracorp.entity.Customer;
import com.altioracorp.altioracorp.entity.Order;
import com.altioracorp.altioracorp.entity.OrdersDetail;
import com.altioracorp.altioracorp.entity.Product;
import com.altioracorp.altioracorp.repository.OrderRepository;
import com.altioracorp.altioracorp.repository.OrdersDetailRepository;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.hibernate.tool.schema.internal.exec.ScriptTargetOutputToFile;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import util.Methods;

import javax.transaction.Transactional;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class OrdersService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrdersDetailRepository ordersDetailRepository;

    public List<OrderDto> getOrders() {

        List<OrderDto> orderDtos = orderRepository.selectOrders("A").stream()
                .map(param -> Methods.convertEntityToDto(param, OrderDto.class))
                .collect(Collectors.toList());
        return orderDtos;

    }

    public String[] saveOrders(String orderFinal) {
        String status = "4", message = "Error en los parámetros introducidos", data = "[]";
        Order order = new Order();
        OrdersDetail ordersDetail = new OrdersDetail();

        JsonObject jso = Methods.stringToJSON(orderFinal);

        JsonObject Orderdata = jso.getAsJsonObject("Order");
        int id_o = Methods.JsonToInteger(Orderdata, "id", 0);
        String state = Methods.JsonToString(Orderdata, "state", "");

        JsonObject Customerdata = Orderdata.getAsJsonObject("customer");
        int id_c = Methods.JsonToInteger(Customerdata, "id", 0);

        order.setId(id_o);
        order.setDate(Instant.now());
        order.setState(state);
        order.setCustomer(new Customer((long) (id_c)));

        order = orderRepository.save(order);

        if (order.getId() != null) {

            JsonArray OrdersDetailsdata = jso.getAsJsonArray("OrdersDetails");

            for (int i = 0; i < OrdersDetailsdata.size(); i++) {
                ordersDetail = new OrdersDetail();

                JsonObject jsonObject = OrdersDetailsdata.get(i).getAsJsonObject();
                int id_od = Methods.JsonToInteger(jsonObject.getAsJsonObject("products"), "id", 0);

                ordersDetail.setProducts(new Product(id_od));
                ordersDetail.setOrders(order);

                ordersDetail = ordersDetailRepository.save(ordersDetail);
            }

            data = order.getId().toString();
            status = "2";
            message = "Orden ingresado con éxito";

        } else {
            status = "3";
            message = "Error al ingresar orden";
            data = order.toString();
        }
        return new String[]{status, message, data};

    }

    public String[] updateOrders(Order order) {
        String status = "4", message = "Error en los parámetros introducidos", data = "[]";
        JsonObject jsonObject = new JsonObject();

        order.setState("A");
        order = orderRepository.save(order);

        if (order.getId() != null) {

            jsonObject.addProperty("id", order.getId());
            data = jsonObject.toString();
            status = "2";
            message = "Orden ingresado con éxito";

        } else {
            status = "3";
            message = "Error al ingresar orden";
            data = jsonObject.toString();
        }
        return new String[]{status, message, data};

    }

    public String[] inactiveOrders(String dataorder) {
        String status = "4", message = "Error en los parámetros introducidos", data = "[]";
        JsonObject jso = Methods.stringToJSON(dataorder);
        int orderid = Methods.JsonToInteger(jso, "orderid", 0);
        int customerid = Methods.JsonToInteger(jso, "customerid", 0);
        String orderdate = Methods.JsonToString(jso, "orderdate", "");
        DateTimeFormatter formatter = DateTimeFormatter.ISO_INSTANT;
        Instant instant = Instant.from(formatter.parse(orderdate));

        Order order = new Order();
        order.setState("I");
        order.setId(orderid);
        order.setDate(instant);
        order.setCustomer(new Customer((long) customerid));

        System.out.println(order);

        order = orderRepository.save(order);

        if (order.getId() != null) {

            data = jso.toString();
            status = "2";
            message = "Orden deshabilitado con éxito";

        } else {
            status = "3";
            message = "Error al deshabilitar orden";
            data = jso.toString();
        }
        return new String[]{status, message, data};

    }
}
