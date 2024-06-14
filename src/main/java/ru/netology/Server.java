package ru.netology;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {
        try (ServerSocket sc = new ServerSocket(9999)) {
            while (true) {
                Socket cs = sc.accept();
                var in = new BufferedInputStream(cs.getInputStream());
                var out = new BufferedOutputStream(cs.getOutputStream());
                ObjectInputStream ois = new ObjectInputStream(in);
                ObjectOutputStream oos = new ObjectOutputStream(out);
                new Thread(() -> {
                    while (true) {
                        try {
                            Message msg = (Message) ois.readObject();
                            System.out.println(msg);
                            oos.writeObject(msg);
                            oos.flush();
                        } catch (IOException | ClassNotFoundException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }).start();
            }
        } catch (
                IOException e) {
            throw new RuntimeException(e);
        }
    }
}