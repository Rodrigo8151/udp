import java.net.*;
import java.io.*;
import java.util.Scanner;

public class Sender2 {
    public static void main(String[] args) {
        String serverIP = "192.168.1.40";
        int port = 2020;

        try (DatagramSocket socket = new DatagramSocket();
             Scanner scanner = new Scanner(System.in)) {

            InetAddress address = InetAddress.getByName(serverIP);
            System.out.print("Ingrese la cadena de texto: ");
            String message = scanner.nextLine();

            byte[] buffer = message.getBytes();
            DatagramPacket request = new DatagramPacket(buffer, buffer.length, address, port);
            socket.send(request);

            byte[] responseBuffer = new byte[1024];
            DatagramPacket response = new DatagramPacket(responseBuffer, responseBuffer.length);

            socket.setSoTimeout(5000);
            socket.receive(response);

            String result = new String(response.getData(), 0, response.getLength());
            System.out.println("Resultado del Receiver: " + result);

        } catch (SocketTimeoutException e) {
            System.out.println("Error: El Clon no respondió (verifica IP y Firewall).");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
