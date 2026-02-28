import java.net.*;
import java.io.*;

public class Receiver {
    public static void main(String[] args) {
        int port = 2020;
        try (DatagramSocket socket = new DatagramSocket(port)) {
            System.out.println("Receiver is running on Windows (Port: " + port + ")...");

            byte[] buffer = new byte[1024];

            while (true) {
                DatagramPacket request = new DatagramPacket(buffer, buffer.length);
                socket.receive(request);

                String message = new String(request.getData(), 0, request.getLength());
                System.out.println("Received from Fedora: " + message);

                String reversed = new StringBuilder(message).reverse().toString();
                if (reversed.length() > 1) {
                    reversed = reversed.substring(0, 1).toUpperCase() + 
                               reversed.substring(1, reversed.length() - 1) + 
                               reversed.substring(reversed.length() - 1).toUpperCase();
                } else {
                    reversed = reversed.toUpperCase();
                }

                byte[] sendData = reversed.getBytes();
                DatagramPacket response = new DatagramPacket(
                    sendData, sendData.length, request.getAddress(), request.getPort()
                );
                socket.send(response);
                System.out.println("Sent back to Fedora: " + reversed);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
