package server;

import lombok.Getter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

@Getter
class ClientSocket {
    private final Socket socket;

    public ClientSocket(Socket socket) {
        this.socket = socket;
        System.out.println("Cliente conectado: " + socket.getRemoteSocketAddress());
    }

    public void close() {
        try {
            socket.close();
        } catch (IOException e) {
            System.out.println("Erro ao fechar conexão: " + e.getMessage());
        }
    }
}
