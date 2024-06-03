package com.baeldung.ls.events.model;

import java.io.Serializable;
import java.time.ZonedDateTime;

public class Order<K, T> implements Serializable{
    public enum PaymentMethod {CARD, CASH, COUPON}
    private PaymentMethod paymentMethod;
    private K key;
    private T data;
    private ZonedDateTime eventCreatedAt;

    public Order() {
    }

    public Order(PaymentMethod paymentMethod, K key, T data) {
        this.paymentMethod = paymentMethod;
        this.key = key;
        this.data = data;
        this.eventCreatedAt = ZonedDateTime.now();
    }

    public String getRoutingKey(){
        if(data instanceof Dress)
            return ((Dress) data).getType() + "." + ((Dress) data).getGender() + "." + ((Dress) data).getSize();

        return "unknown.unknown.unknown";
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public K getKey() {
        return key;
    }

    public T getData() {
        return data;
    }

    public ZonedDateTime getEventCreatedAt() {
        return eventCreatedAt;
    }

    @Override
    public String toString() {
        return "Order{" + "paymentMethod=" + paymentMethod + ", key=" + key + ", data=" + data + ", eventCreatedAt=" + eventCreatedAt + '}';
    }
}
