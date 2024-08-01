package Game;


import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UDPServer {
    private static List<InetAddress> clientAddresses = new ArrayList<>();
    private static List<Integer> clientPorts = new ArrayList<>();
    private static boolean turnoX = true; // Turno do jogador X

    public static void main(String[] args) {
        DatagramSocket aSocket = null;
        try {
            aSocket = new DatagramSocket(6789);
            byte[] buffer = new byte[1000];
            while (true) {
                DatagramPacket request = new DatagramPacket(buffer, buffer.length);
                aSocket.receive(request);

                InetAddress clientAddress = request.getAddress();
                int clientPort = request.getPort();

                if (!clientAddresses.contains(clientAddress) || !clientPorts.contains(clientPort)) {
                    clientAddresses.add(clientAddress);
                    clientPorts.add(clientPort);
                }

                String receivedMessage = new String(request.getData(), 0, request.getLength());

                if (receivedMessage.equals("NOVO_JOGO") || receivedMessage.equals("ZERAR_PLACAR")) {
                    for (int i = 0; i < clientAddresses.size(); i++) {
                        DatagramPacket reply = new DatagramPacket(request.getData(), request.getLength(), clientAddresses.get(i), clientPorts.get(i));
                        aSocket.send(reply);
                    }
                    turnoX = true; // Reinicia para o jogador X
                    notificarTurno();
                } else if (receivedMessage.startsWith("ATUALIZAR_PLACAR:")) {
                    for (int i = 0; i < clientAddresses.size(); i++) {
                        DatagramPacket reply = new DatagramPacket(request.getData(), request.getLength(), clientAddresses.get(i), clientPorts.get(i));
                        aSocket.send(reply);
                    }
                } else {
                    // Adicionar turno do jogador
                    String[] parts = receivedMessage.split(":");
                    int index = Integer.parseInt(parts[0]);
                    String player = parts[1];

                    String updatedMessage = index + ":" + player;

                    for (int i = 0; i < clientAddresses.size(); i++) {
                        byte[] m = updatedMessage.getBytes();
                        DatagramPacket reply = new DatagramPacket(m, m.length, clientAddresses.get(i), clientPorts.get(i));
                        aSocket.send(reply);
                    }

                    turnoX = !turnoX; // Alternar turno
                    notificarTurno();
                }
            }
        } catch (SocketException e) {
            System.out.println("Socket: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("IO: " + e.getMessage());
        } finally {
            if (aSocket != null) aSocket.close();
        }
    }

    private static void notificarTurno() {
        String message = "SUA_VEZ";
        try {
            byte[] m = message.getBytes();
            if (turnoX) {
                DatagramPacket turnoXPacket = new DatagramPacket(m, m.length, clientAddresses.get(0), clientPorts.get(0));
                DatagramSocket socket = new DatagramSocket();
                socket.send(turnoXPacket);
                socket.close();
            } else {
                DatagramPacket turnoOPacket = new DatagramPacket(m, m.length, clientAddresses.get(1), clientPorts.get(1));
                DatagramSocket socket = new DatagramSocket();
                socket.send(turnoOPacket);
                socket.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
