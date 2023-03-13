package com.altioracorp.altioracorp.controller;

import com.altioracorp.altioracorp.entity.Order;
import com.altioracorp.altioracorp.entity.OrdersDetail;
import com.altioracorp.altioracorp.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.spring.web.json.Json;
import util.Methods;


@RestController
@RequestMapping("/orders")
@CrossOrigin(origins = "http://localhost:4200")
public class OrdersApi {
    @Autowired
    private OrdersService ordensService;

    @GetMapping
    public ResponseEntity<?> getOrdens() {
        return ResponseEntity.ok(ordensService.getOrders());
    }

    @PostMapping("/insertorders")
    public ResponseEntity<String> saveOrders(@RequestBody @Validated String dataOrders, @RequestHeader("token") String sessionToken) {
        String message = "";
        String[] res;
        if (sessionToken.equals("Security sesión")) {
            res = ordensService.saveOrders(dataOrders);
            message = Methods.getJsonMessage(res[0], res[1], res[2]);
            if (res[0].equals("2") || res[0].equals("3")) {
                return new ResponseEntity<>(message, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(message, HttpStatus.BAD_GATEWAY);
            }
        } else {
            message = Methods.getJsonMessage("4", "Sesión Inválida", "[]");
            return new ResponseEntity<>(message, HttpStatus.UNAUTHORIZED);
        }
    }

    @PutMapping("/updateorders")
    public ResponseEntity<String> updateOrders(@RequestBody @Validated Order orders, @RequestHeader("token") String sessionToken) {
        String message = "";
        String[] res;
        System.out.println(orders);

        if (sessionToken.equals("Security sesión")) {
            res = ordensService.updateOrders(orders);
            message = Methods.getJsonMessage(res[0], res[1], res[2]);
            if (res[0].equals("2") || res[0].equals("3")) {
                return new ResponseEntity<>(message, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(message, HttpStatus.BAD_GATEWAY);
            }
        } else {
            message = Methods.getJsonMessage("4", "Sesión Inválida", "[]");
            return new ResponseEntity<>(message, HttpStatus.UNAUTHORIZED);
        }
    }

    @PutMapping("/inactiveorders")
    public ResponseEntity<String> inactiveOrders(@RequestBody @Validated String dataorders, @RequestHeader("token") String sessionToken) {
        String message;
        String[] res;

        if (sessionToken.equals("Security sesión")) {
            res = ordensService.inactiveOrders(dataorders);
            message = Methods.getJsonMessage(res[0], res[1], res[2]);
            if (res[0].equals("2") || res[0].equals("3")) {
                return new ResponseEntity<>(message, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(message, HttpStatus.BAD_GATEWAY);
            }
        } else {
            message = Methods.getJsonMessage("4", "Sesión Inválida", "[]");
            return new ResponseEntity<>(message, HttpStatus.UNAUTHORIZED);
        }
    }
}
