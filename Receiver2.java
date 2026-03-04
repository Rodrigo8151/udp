import java.net.*;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class Receiver2 {
    public static void main(String[] args) {
        int port = 2020;
        try (DatagramSocket socket = new DatagramSocket(port)) {
            System.out.println("Receiver de Frecuencias iniciado en puerto: " + port);
            byte[] buffer = new byte[1024];

            while (true) {
                DatagramPacket request = new DatagramPacket(buffer, buffer.length);
                socket.receive(request);

                String message = new String(request.getData(), 0, request.getLength()).trim();
                System.out.println("Texto recibido: " + message);

                Map<Character, Integer> freqMap = new LinkedHashMap<>();
                for (char c : message.toCharArray()) {
                    freqMap.put(c, freqMap.getOrDefault(c, 0) + 1);
                }

                String responseStr = freqMap.entrySet().stream()
                        .map(entry -> entry.getKey() + ":" + entry.getValue())
                        .collect(Collectors.joining(", "));

                byte[] sendData = responseStr.getBytes();
                DatagramPacket response = new DatagramPacket(
                        sendData, sendData.length, request.getAddress(), request.getPort()
                );
                socket.send(response);
                System.out.println("Respuesta enviada: " + responseStr);
            }
        } catch (Exception e) {
            System.out.println("Error en Receiver: " + e.getMessage());
        }
    }
}
