package simple_udp1;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class Receiver {

    public Receiver() throws Exception {
        // Puerto 2020 según la guía
        DatagramSocket socket = new DatagramSocket(2020);
        System.out.println("Receiver is running on Windows (Port: 2020)...");

        byte[] buffer = new byte[1500]; // MTU estándar
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

        // 1. Recibir mensaje del Sender (Fedora)
        socket.receive(packet);
        String received = new String(packet.getData(), 0, packet.getLength()).trim();
        System.out.println("Received from Fedora: " + received);

        // 2. Lógica Pregunta 3: Invertir cadena en minúsculas
        String inverted = new StringBuilder(received).reverse().toString().toLowerCase();

        // 3. Lógica Pregunta 3: Mayúscula al inicio y al final
        String result = "";
        if (inverted.length() > 1) {
            char first = Character.toUpperCase(inverted.charAt(0));
            char last = Character.toUpperCase(inverted.charAt(inverted.length() - 1));
            String middle = inverted.substring(1, inverted.length() - 1);
            result = first + middle + last;
        } else {
            result = inverted.toUpperCase();
        }

        // 4. Enviar de vuelta al Sender en Fedora
        InetAddress senders_address = packet.getAddress();
        int senders_port = packet.getPort();
        byte[] responseBuffer = result.getBytes();
        
        DatagramPacket responsePacket = new DatagramPacket(
            responseBuffer, 
            responseBuffer.length, 
            senders_address, 
            senders_port
        );
        
        socket.send(responsePacket);
        System.out.println("Sent back to Fedora: " + result);
        
        socket.close();
    }

    public static void main(String[] args) {
        try {
            new Receiver();
        } catch (Exception e) {
            System.err.println("Error en el Receiver:");
            e.printStackTrace();
        }
    }
}
