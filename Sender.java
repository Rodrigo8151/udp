import java.net.*;
import java.io.*;
import java.util.Scanner;

public class Sender {
    public static void main(String[] args) {
        // CAMBIA ESTA IP por la de tu Windows (ipconfig)
        String serverIP = "192.168.1.33"; 
        int port = 2020;

        try (DatagramSocket socket = new DatagramSocket();
             Scanner scanner = new Scanner(System.in)) {
            
            InetAddress address = InetAddress.getByName(serverIP);

            System.out.print("Enter your message: ");
            String message = scanner.nextLine();

            byte[] buffer = message.getBytes();
            DatagramPacket request = new DatagramPacket(buffer, buffer.length, address, port);
            socket.send(request);
            System.out.println("Sent to Windows: " + message);

            byte[] responseBuffer = new byte[1024];
            DatagramPacket response = new DatagramPacket(responseBuffer, responseBuffer.length);
            
            socket.setSoTimeout(5000); // Espera 5 segundos máximo
            socket.receive(response);

            String modifiedMessage = new String(response.getData(), 0, response.getLength());
            System.out.println("Received from Windows (Modified): " + modifiedMessage);

        } catch (SocketTimeoutException e) {
            System.out.println("Error: No response from Windows. Check IP and Firewall.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
