package com.example.demo.Loaders;

import com.example.demo.Entity.Client;
import com.example.demo.Entity.Product;
import com.example.demo.Service.ClientService;
import com.example.demo.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
public class ClientDataLoader implements CommandLineRunner {

    private final ClientService clientService;
    private final ProductService productService;

    @Autowired
    public ClientDataLoader(ClientService clientService, ProductService productService) {
        this.clientService = clientService;
        this.productService = productService;
    }

    @Override
    public void run(String... args) {

        Map<String, Client> clients = new HashMap<>();
        clients.put("client1",
                new Client(UUID.fromString("04b6905f-36b2-4931-b695-2942b14631ea"), "1", "Lionel", "Messi", "leomessi10@gmail.com", "123-456-7890"));
        clients.put("client2",
                new Client(UUID.randomUUID(), "2", "Lautaro", "Martinez", "toromartinez@gmail.com", "234-567-8910"));
        clients.put("client3",
                new Client(UUID.randomUUID(), "3", "James", "Rodriguez", "james_10_co@gmail.com", "345-678-9100"));
        clients.put("client4",
                new Client(UUID.randomUUID(), "4", "Ousmane", "Dembele", "Mosquito@hotmail.com", "456-789-1011"));

        for (Map.Entry<String, Client> entry : clients.entrySet()) {
            clientService.save(entry.getValue());
        }

        Map<String, Product> products = new HashMap<>();
        products.put("product1", new Product("Soccer ball", "A ball to play soccer, made by leather", 50.00, 100));
        products.put("product2", new Product("Ankle boots", "A pair of shoes made for sport in grass court", 100.00, 150));
        products.put("product3", new Product("Soccer shirt", "Light shirt to play soccer or made any sport", 90.00, 77));
        products.put("product4", new Product("Soccer short", "Light short to play soccer or made any sport", 60.00, 75));

        for (Map.Entry<String, Product> entry : products.entrySet()) {
            productService.save(entry.getValue());
        }
    }
}
