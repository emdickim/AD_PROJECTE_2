package com.ra34.projecte2.DTO;

import java.util.List;

public class AddProductsToOrderRequest {
    private Long orderId;
    private List<Long> productIds;

    public Long getOrderId() { return orderId; }
    public void setOrderId(Long orderId) { this.orderId = orderId; }

    public List<Long> getProductIds() { return productIds; }
    public void setProductIds(List<Long> productIds) { this.productIds = productIds; }
}