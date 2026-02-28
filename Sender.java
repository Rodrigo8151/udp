package simple_udp1;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class Sender {

    public Sender() throws Exception {
        DatagramSocket socket = new DatagramSocket();
        Scanner keyboard = new Scanner(System.in);

        String windowsIP = "192.168.1.33"; 
        int port = 2020;

        System.out.println("Sender running on Fedora 43...");
        System.out.print("Enter your message in lowercase: ");
        String message = keyboard.nextLine();
        byte[] buffer = message.getBytes();

        DatagramPacket packet = new DatagramPacket(
            buffer, 
            buffer.length, 
            InetAddress.getByName(windowsIP), 
            port
        );

        socket.send(packet);
        System.out.println("Sent to Windows: " + message);

        byte[] responseBuffer = new byte[1500];
        DatagramPacket receivePacket = new DatagramPacket(responseBuffer, responseBuffer.length);
        
        socket.receive(receivePacket);

        String result = new String(receivePacket.getData(), 0, receivePacket.getLength()).trim();
        System.out.println("Received from Windows (Modified): " + result);

        socket.close();
        keyboard.close();
    }

    public static void main(String[] args) {
        try {
            new Sender();
        } catch (Exception e) {
            System.err.println("Error en el Sender:");
            e.printStackTrace();
        }
    }
}
