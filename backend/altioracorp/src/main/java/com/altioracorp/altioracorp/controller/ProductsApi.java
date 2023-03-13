package com.altioracorp.altioracorp.controller;

import com.altioracorp.altioracorp.entity.Product;
import com.altioracorp.altioracorp.service.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import util.Methods;


@RestController
@RequestMapping("/products")
@CrossOrigin(origins = "http://localhost:4200")
public class ProductsApi {
    @Autowired
    private ProductsService productsService;

    @GetMapping
    public ResponseEntity<?> getProducts() {
        return ResponseEntity.ok(productsService.getProducts());
    }

    @PostMapping("/insertproducts")
    public ResponseEntity<String> saveProducts(@RequestBody @Validated Product products, @RequestHeader("token") String sessionToken) {
        String message;
        String[] res;

        if (sessionToken.equals("Security_sesión")) {
            res = productsService.saveProducts(products);
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

    @PutMapping("/updateproducts")
    public ResponseEntity<String> updateProducts(@RequestBody @Validated Product products, @RequestHeader("token") String sessionToken) {
        String message;
        String[] res;

        if (sessionToken.equals("Security sesión")) {
            res = productsService.updateProducts(products);
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

    @PutMapping("/inactiveproducts")
    public ResponseEntity<String> inactiveProducts(@RequestBody @Validated Product products, @RequestHeader("token") String sessionToken) {
        String message;
        String[] res;

        if (sessionToken.equals("Security sesión")) {
            res = productsService.inactiveProducts(products);
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
