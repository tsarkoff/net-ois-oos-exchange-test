package ru.netology;

import java.io.Serializable;

public class Message implements Serializable {
    public String name;

    public Message(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Message{" +
                "name='" + name + '\'' +
                '}';
    }
}
