package teste;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.LinkedList;
import java.util.List;


public class ChatServer {
    public static final int PORT = 4000;
    private ServerSocket serverSocket;
    private final List<ClientSocketTeste> clientSocketList = new LinkedList<>();

    public void start() {
        try {
            serverSocket = new ServerSocket(PORT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // aguradando eternamente conexao
    private void clientConnectionLoop() throws IOException {
        while (true) {
            ClientSocketTeste clientSocket = new ClientSocketTeste(serverSocket.accept()); // socket local do cliente que foi conectado
            clientSocketList.add(clientSocket);
            new Thread(() -> clientMessageLoop(clientSocket)).start();
        }
    }

    public void clientMessageLoop(ClientSocketTeste clientSocket) {
        String msg;
        try {
            while((msg = clientSocket.getMessage()) != null) {
                if("sair".equalsIgnoreCase(msg)) {
                    return;
                }

                System.out.printf("Msg recebido do cliente %s: %s\n", clientSocket.getRemoteSocketAdress(), msg);
                sendMsgToAll(clientSocket, msg);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            clientSocket.close();
        }
    }

    private void sendMsgToAll(ClientSocketTeste sender, String msg) {
        for(ClientSocketTeste socket: clientSocketList) {
            if(!sender.equals(socket)) {
                socket.sendMsg(msg);
            }
        }
    }

    public static void main(String[] args) {
        try {
            ChatServer server = new ChatServer();
            server.start();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
