package com.altioracorp.altioracorp.controller;

import com.altioracorp.altioracorp.entity.Customer;
import com.altioracorp.altioracorp.service.CustomersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import util.Methods;


@RestController
@RequestMapping("/customers")
@CrossOrigin(origins = "http://localhost:4200")
public class CustomersApi {
    @Autowired
    private CustomersService customersService;

    @GetMapping
    public ResponseEntity<?> getCustomers() {
        return ResponseEntity.ok(customersService.getCustomers());
    }

    @PostMapping("/insertcustomer")
    public ResponseEntity<String> saveCustomers(@RequestBody Customer customer, @RequestHeader("token") String sessionToken) {
        System.out.println(customer);
        String message;
        String[] res;

        if (sessionToken.equals("Security_sesión")) {
            res = customersService.saveCustomers(customer);
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

    @PutMapping("/updatecustomer")
    public ResponseEntity<String> updateCustomers(@RequestBody @Validated Customer customer, @RequestHeader("token") String sessionToken) {
        String message;
        String[] res;
        System.out.println(customer);
        if (sessionToken.equals("Security sesión")) {
            res = customersService.updateCustomers(customer);

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

    @PutMapping("/inactivecustomer")
    public ResponseEntity<String> inactiveCustomer(@RequestBody @Validated Customer customer, @RequestHeader("token") String sessionToken) {
        String message;
        String[] res;

        if (sessionToken.equals("Security sesión")) {
            res = customersService.inactiveCustomers(customer);
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

    @DeleteMapping(value = "{id}")
    public ResponseEntity<Customer> deleteCustomers(@PathVariable("id") Long id) {
        customersService.deleteCustomerbyId(id);
        return ResponseEntity.ok(null);
    }

    @DeleteMapping("/deletecustomer")
    public ResponseEntity<?> deleteCustomers(@RequestBody @Validated Customer customer, @RequestHeader("token") String sessionToken) {
        String[] res;
        String message;

        if (sessionToken.equals("Security sesión")) {
            res = customersService.deleteCustomer(customer);
            return ResponseEntity.ok(res);
        } else {
            message = Methods.getJsonMessage("4", "Sesión Inválida", "[]");
            return new ResponseEntity<>(message, HttpStatus.UNAUTHORIZED);
        }

    }
}
