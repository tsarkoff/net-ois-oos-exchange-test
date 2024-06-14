package ru.netology;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client2 {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        try (Socket cs = new Socket("localhost", 9999)) {
            var out = new BufferedOutputStream(cs.getOutputStream());
            var in = new BufferedInputStream(cs.getInputStream());
            out.flush();
            ObjectOutputStream oos = new ObjectOutputStream(out);
            ObjectInputStream ois = null;
            Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.print("Type name: ");
                String name = scanner.nextLine();
                Message msg = new Message(name);
                oos.writeObject(msg);
                oos.flush();
                if (ois == null)
                    ois = new ObjectInputStream(in);
                msg = (Message) ois.readObject();
                System.out.println(msg);
            }
        }
    }
}
