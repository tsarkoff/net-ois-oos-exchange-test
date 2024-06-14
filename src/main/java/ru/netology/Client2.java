package ru.netology;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client2 {
    private static ObjectOutputStream oos;
    private static ObjectInputStream ois;
    private static Scanner scanner;

    public static void main(String[] args) {
        try (Socket cs = new Socket("localhost", 9999)) {
            oos = new ObjectOutputStream(new BufferedOutputStream(cs.getOutputStream()));
            //oos.flush(); - вместо flush() используем отложенное создание блокирующего ObjectInputStream см. ниже if (ois == null)
            new Thread(() -> {
                scanner = new Scanner(System.in);
                while (true) {
                    System.out.print("Type name: ");
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

            while (true) {
                try {
                    if (ois == null)
                        ois = new ObjectInputStream(new BufferedInputStream(cs.getInputStream()));
                    Message msg = (Message) ois.readObject();
                    System.out.println();
                    System.out.println(msg);
                } catch (IOException | ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
