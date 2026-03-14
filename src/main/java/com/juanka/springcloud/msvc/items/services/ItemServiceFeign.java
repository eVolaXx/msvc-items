package com.juanka.springcloud.msvc.items.services;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.juanka.springcloud.msvc.items.clients.ProductFeignClient;
import com.juanka.springcloud.msvc.items.models.Item;
import com.juanka.springcloud.msvc.items.models.Product;

@Service
public class ItemServiceFeign implements ItemService {

    @Autowired
    private ProductFeignClient client;

    public ItemServiceFeign(ProductFeignClient client) {
        this.client = client;
    }

    @Override
    public List<Item> findAll() {
        return client.findAll()
                .stream()
                .map(p -> new Item(p, new Random().nextInt( 10) + 1))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Item> findById(Long id) {
        Product p = client.details(id);
        if (p == null) {
            return Optional.empty();
        }
        return Optional.of(new Item(p, new Random().nextInt( 10) + 1));
    }

}
