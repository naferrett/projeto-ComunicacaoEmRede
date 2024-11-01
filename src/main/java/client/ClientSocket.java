package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientSocket {
    private final Socket socket;
    private final BufferedReader reader;
    private final PrintWriter writer;

    public ClientSocket(Socket socket) throws IOException {
        this.socket = socket;
        this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.writer = new PrintWriter(socket.getOutputStream(), true);
        System.out.println("Cliente conectado: " + socket.getRemoteSocketAddress());
    }

    public String getMessage() {
        try {
            return reader.readLine();
        } catch (IOException e) {
            System.out.println("Erro ao ler mensagem do servidor: " + e.getMessage());
            return null;
        }
    }

    public void sendMsg(String message) {
        writer.println(message);
    }

    public Socket getSocket() {
        return socket;
    }

    public void close() {
        try {
            reader.close();
            writer.close();
            socket.close();
        } catch (IOException e) {
            System.out.println("Erro ao fechar conexão: " + e.getMessage());
        }
    }
}
