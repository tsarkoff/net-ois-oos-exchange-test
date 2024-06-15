package ru.netology;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client2 {
    public static void main(String[] args) {
        try (Socket cs = new Socket("localhost", Server.PORT)) {
            var oos = new ObjectOutputStream(new BufferedOutputStream(cs.getOutputStream()));
            oos.flush();
            new Thread(() -> {
                var scanner = new Scanner(System.in);
                while (true) {
                    System.out.print("@client-2 > ");
                    String name = scanner.nextLine();
                    Message msg = new Message(name);
                    try {
                        oos.writeObject(msg);
                        oos.flush();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }).start();

            var ois = new ObjectInputStream(new BufferedInputStream(cs.getInputStream()));
            while (true) {
                try {
                    Message msg = (Message) ois.readObject();
                    System.out.println();
                    System.out.println(msg);
                    System.out.print("@client-2 > ");
                } catch (IOException | ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
