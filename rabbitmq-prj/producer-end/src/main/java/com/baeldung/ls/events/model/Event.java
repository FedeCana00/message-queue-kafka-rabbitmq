package com.baeldung.ls.events.model;

import java.io.Serializable;
import java.time.ZonedDateTime;

public class Event<K, T> implements Serializable {
    public enum Type {CREATE, DELETE, UPDATE}
    private Event.Type eventType;
    private K key;
    private T data;
    private ZonedDateTime eventCreatedAt;

    public Event() {
    }

    public Event(Type eventType, K key, T data) {
        this.eventType = eventType;
        this.key = key;
        this.data = data;
        this.eventCreatedAt = ZonedDateTime.now();
    }
    public Type getEventType() {
        return eventType;
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
        return "Event{" + "eventType=" + eventType + ", key=" + key + ", data=" + data + ", eventCreatedAt=" + eventCreatedAt + '}';
    }
}