package ru.netology;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) throws IOException {
        try (ServerSocket sc = new ServerSocket(9999)) {
            while (true) {
                Socket cs = sc.accept();
                new Thread(() -> {
                    try {
                        var in = new BufferedInputStream(cs.getInputStream());
                        ObjectInputStream ois = new ObjectInputStream(in);
                        var out = new BufferedOutputStream(cs.getOutputStream());
                        ObjectOutputStream oos = new ObjectOutputStream(out);
                        //oos.flush(); - вместо flush() используем отложенное создание ObjectInputStream на Клиенте (см. Client.java)
                        while (true) {
                            Message msg = (Message) ois.readObject();
                            System.out.println(msg);
                            oos.writeObject(msg);
                            oos.flush();
                        }
                    } catch (IOException | ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                }).start();
            }
        }
    }
}