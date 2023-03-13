package com.altioracorp.altioracorp.service;

import com.altioracorp.altioracorp.entity.Customer;
import com.altioracorp.altioracorp.entity.Product;
import com.altioracorp.altioracorp.repository.ProductRepository;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ProductsService {
    @Autowired
    private ProductRepository productRepository;

    public List<Product> getProducts() {
        return productRepository.findByState("A");
    }

    public String[] saveProducts(Product product) {
        String status = "4", message = "Error en los parámetros introducidos", data = "[]";
        JsonObject jsonObject = new JsonObject();
        product.setState("A");
        product = productRepository.save(product);

        if (product.getId() != null) {

            jsonObject.addProperty("id", product.getId());
            data = jsonObject.toString();
            status = "2";
            message = "Producto ingresado con éxito";

        } else {
            status = "3";
            message = "Error al ingresar producto";
            data = jsonObject.toString();
        }
        return new String[]{status, message, data};

    }

    public String[] updateProducts(Product product) {
        String status = "4", message = "Error en los parámetros introducidos", data = "[]";
        JsonObject jsonObject = new JsonObject();

        product = productRepository.save(product);

        if (product.getId() != null) {

            jsonObject.addProperty("id", product.getId());
            data = jsonObject.toString();
            status = "2";
            message = "Producto ingresado con éxito";

        } else {
            status = "3";
            message = "Error al ingresar producto";
            data = jsonObject.toString();
        }
        return new String[]{status, message, data};

    }

    public String[] inactiveProducts(Product product) {
        String status = "4", message = "Error en los parámetros introducidos", data = "[]";
        JsonObject jsonObject = new JsonObject();

        product.setState("I");
        product = productRepository.save(product);

        if (product.getId() != null) {

            jsonObject.addProperty("id", product.getId());
            data = jsonObject.toString();
            status = "2";
            message = "Producto deshabilitado con éxito";

        } else {
            status = "3";
            message = "Error al deshabilitar producto";
            data = jsonObject.toString();
        }
        return new String[]{status, message, data};

    }
}
