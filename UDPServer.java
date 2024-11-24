package Game; 


import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UDPServer {
    private static List<InetAddress> clientAddresses = new ArrayList<InetAddress>();
    private static List<Integer> clientPorts = new ArrayList<Integer>();
    private static boolean turnoX = true; // Turno do jogador X

    public static void main(String[] args) {
        DatagramSocket aSocket = null;
        
        try {
            aSocket = new DatagramSocket(6789);
            byte[] buffer = new byte[1000];
            
            // Loop do servidor que ira ficar recebendo os pacotes
            while (true) {
            	// Receber um pacote UDP e armazenar os dados no buffer
                DatagramPacket request = new DatagramPacket(buffer, buffer.length);
                aSocket.receive(request);
                
                // Extrair o endereço IP e a porta do cliente a partir do pacote recebido
                InetAddress clientAddress = request.getAddress();
                int clientPort = request.getPort();
                
                // Se o cliente ainda não estiver na lista de endereços e portas ele é adicionado
                if (!clientAddresses.contains(clientAddress) || !clientPorts.contains(clientPort)) {
                    clientAddresses.add(clientAddress);
                    clientPorts.add(clientPort);
                    System.out.println("Novo cliente registrado: " + clientAddress + ":" + clientPort); // Imprime o novo cliente
                    
                }
                
                // A mensagem recebida é transformada em String
                String receivedMessage = new String(request.getData(), 0, request.getLength());
                System.out.println("Mensagem recebida: " + receivedMessage);

                
                if (receivedMessage.equals("NOVO_JOGO") || receivedMessage.equals("ZERAR_PLACAR")) {
                	System.out.println("Comando recebido: " + receivedMessage);
                    for (int i = 0; i < clientAddresses.size(); i++) {
                        DatagramPacket reply = new DatagramPacket(request.getData(), request.getLength(), clientAddresses.get(i), clientPorts.get(i));
                        aSocket.send(reply);
                    }
                    turnoX = true; // Reinicia para o jogador X
                    notificarTurno();
                    
                
                } else if (receivedMessage.startsWith("ATUALIZAR_PLACAR:")) {
                	System.out.println("Comando de atualização de placar recebido: " + receivedMessage);
                    for (int i = 0; i < clientAddresses.size(); i++) {
                        DatagramPacket reply = new DatagramPacket(request.getData(), request.getLength(), clientAddresses.get(i), clientPorts.get(i));
                        aSocket.send(reply);
                    }
                
                }else if (receivedMessage.startsWith("VITORIA:")) {
                	System.out.println("Comando de vitória recebido: " + receivedMessage); 
                    for (int i = 0; i < clientAddresses.size(); i++) {
                        DatagramPacket reply = new DatagramPacket(receivedMessage.getBytes(), receivedMessage.length(), clientAddresses.get(i), clientPorts.get(i));
                        aSocket.send(reply);
                    }
                } 

                else {
                    // Adicionar turno do jogador
                    String[] parts = receivedMessage.split(":");
                    int index = Integer.parseInt(parts[0]);
                    String player = parts[1];
                    System.out.println("Jogador " + player + " fez um movimento na posição " + index);
                    
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
            	System.out.println("É a vez do jogador X.");
                DatagramPacket turnoXPacket = new DatagramPacket(m, m.length, clientAddresses.get(0), clientPorts.get(0));
                DatagramSocket socket = new DatagramSocket();
                socket.send(turnoXPacket);
                socket.close();
            } else {
            	System.out.println("É a vez do jogador O.");
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
