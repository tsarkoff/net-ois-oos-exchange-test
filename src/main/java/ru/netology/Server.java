package ru.netology;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        try (ServerSocket sc = new ServerSocket(9999)) {
            Socket cs = sc.accept();
            var in = new BufferedInputStream(cs.getInputStream());
            ObjectInputStream ois = new ObjectInputStream(in);
            var out = new BufferedOutputStream(cs.getOutputStream());
            ObjectOutputStream oos = new ObjectOutputStream(out);
            while (true) {
                Message msg = (Message) ois.readObject();
                System.out.println(msg.name);
                oos.writeObject(msg);
                oos.flush();
            }
        }
    }
}