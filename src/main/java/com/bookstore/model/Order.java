package com.bookstore.model;

import java.util.Date;
import java.util.List;

public class Order {
    private Long id;
    private Long customerId;
    private List<CartItem> items;
    private double totalAmount;
    private Date orderDate;

    public Order(Long id, Long customerId, List<CartItem> items, double totalAmount, Date orderDate) {
        this.id = id;
        this.customerId = customerId;
        this.items = items;
        this.totalAmount = totalAmount;
        this.orderDate = orderDate;
    }

    public Long getId() {
        return id;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public List<CartItem> getItems() {
        return items;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public void setItems(List<CartItem> items) {
        this.items = items;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }
}
