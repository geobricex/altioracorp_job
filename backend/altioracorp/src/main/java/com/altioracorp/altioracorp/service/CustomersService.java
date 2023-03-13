package com.altioracorp.altioracorp.service;

import com.altioracorp.altioracorp.entity.Customer;
import com.altioracorp.altioracorp.repository.CustomerRepository;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class CustomersService {
    @Autowired
    private CustomerRepository customerRepository;

    public List<Customer> getCustomers() {
        return customerRepository.findByState("A");
    }


    public String[] saveCustomers(Customer customer) {
        String status = "4", message = "Error en los parámetros introducidos", data = "[]";
        JsonObject jsonObject = new JsonObject();
        customer.setState("A");
        customer = customerRepository.save(customer);

        if (customer.getId() != null) {

            jsonObject.addProperty("id", customer.getId());
            data = jsonObject.toString();
            status = "2";
            message = "Cliente ingresado con éxito";

        } else {
            status = "3";
            message = "Error al ingresar cliente";
            data = jsonObject.toString();
        }
        return new String[]{status, message, data};

    }

    public String[] updateCustomers(Customer customer) {
        String status = "4", message = "Error en los parámetros introducidos", data = "[]";
        JsonObject jsonObject = new JsonObject();

        customer = customerRepository.save(customer);

        if (customer.getId() != null) {

            jsonObject.addProperty("id", customer.getId());
            data = jsonObject.toString();
            status = "2";
            message = "Cliente actualizado con éxito";

        } else {
            status = "3";
            message = "Error al actualizar cliente";
            data = jsonObject.toString();
        }
        return new String[]{status, message, data};

    }

    public String[] inactiveCustomers(Customer customer) {
        String status = "4", message = "Error en los parámetros introducidos", data = "[]";
        JsonObject jsonObject = new JsonObject();

        customer.setState("I");
        customer = customerRepository.save(customer);

        if (customer.getId() != null) {

            jsonObject.addProperty("id", customer.getId());
            data = jsonObject.toString();
            status = "2";
            message = "Cliente deshabilitado con éxito";

        } else {
            status = "3";
            message = "Error al deshabilitar cliente";
            data = jsonObject.toString();
        }
        return new String[]{status, message, data};

    }

    public String[] deleteCustomer(Customer customer) {
        String status = "4", message = "Error en los parámetros introducidos", data = "[]";
        JsonObject jsonObject = new JsonObject();

        customerRepository.delete(customer);

        status = "2";
        message = "Cliente eliminado con éxito";

        return new String[]{status, message, data};

    }

    public String[] deleteCustomerbyId(Long id) {
        String status = "4", message = "Error en los parámetros introducidos", data = "[]";

        customerRepository.deleteById(id);

        status = "2";
        message = "Cliente eliminado con éxito";

        return new String[]{status, message, data};

    }
}
