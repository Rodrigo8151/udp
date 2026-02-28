import java.net.*;
import java.io.*;
import java.util.Scanner;

public class Sender {
    public static void main(String[] args) {
        // !!! IMPORTANTE: Verifica esta IP con 'ipconfig' en Windows !!!
        String serverIP = "192.168.1.33"; 
        int port = 2020;

        try (DatagramSocket socket = new DatagramSocket();
             Scanner scanner = new Scanner(System.in)) {
            
            InetAddress address = InetAddress.getByName(serverIP);

            System.out.print("Enter your message for Windows: ");
            String message = scanner.nextLine();

            // Enviar mensaje
            byte[] buffer = message.getBytes();
            DatagramPacket request = new DatagramPacket(buffer, buffer.length, address, port);
            socket.send(request);
            System.out.println("Sent: " + message);

            // Recibir respuesta
            byte[] responseBuffer = new byte[1024];
            DatagramPacket response = new DatagramPacket(responseBuffer, responseBuffer.length);
            
            socket.setSoTimeout(5000); // 5 segundos de espera
            socket.receive(response);

            String modifiedMessage = new String(response.getData(), 0, response.getLength());
            System.out.println("Received from Windows (Modified): " + modifiedMessage);

        } catch (SocketTimeoutException e) {
            System.out.println("Error: No hubo respuesta. Revisa IP y Firewall en Windows.");
        } catch (Exception e) {
            System.out.println("Error en Fedora: " + e.getMessage());
        }
    }
}
