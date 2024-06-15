package ru.netology;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Server {
    public static final int PORT = 7777;
    private static final Map<Integer, ObjectOutputStream> clients = new HashMap<>();

    public static void main(String[] args) throws IOException {
        try (ServerSocket sc = new ServerSocket(PORT)) {
            while (true) {
                Socket cs = sc.accept();
                new Thread(() -> {
                    try {
                        var oos = new ObjectOutputStream(new BufferedOutputStream(cs.getOutputStream()));
                        oos.flush();
                        clients.put(cs.getPort(), oos);
                        var ois = new ObjectInputStream(new BufferedInputStream(cs.getInputStream()));
                        while (true) {
                            Message msg = (Message) ois.readObject();
                            for (var out : clients.values()) {
                                System.out.println(msg);
                                if (!out.equals(oos)) {
                                    out.writeObject(msg);
                                    out.flush();
                                }
                            }
                        }
                    } catch (IOException | ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                }).start();
            }
        }
    }
}