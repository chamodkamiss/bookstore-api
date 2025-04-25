package com.bookstore.model;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private Long customerId;
    private List<CartItem> items = new ArrayList<>();

    public Long getCustomerId() {
        return customerId;
    }
    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }
    public List<CartItem> getItems() {
        if (items == null) {
            items = new ArrayList<>();
        }
        return items;
    }
    public void setItems(List<CartItem> items) {
        this.items = items;
    }

}
