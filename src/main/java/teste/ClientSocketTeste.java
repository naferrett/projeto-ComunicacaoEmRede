package teste;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketAddress;

public class ClientSocketTeste {
    private final Socket socket;
    private final BufferedReader in;
    private final PrintWriter out;

    public ClientSocketTeste(Socket socket) throws IOException {
        this.socket = socket;
        System.out.println("Cliente " + socket.getRemoteSocketAddress() + " conectou.");
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.out =  new PrintWriter(socket.getOutputStream());
    }

    public SocketAddress getRemoteSocketAdress() {
        return socket.getRemoteSocketAddress();
    }

    public void close() {
        try {
            in.close();
            out.close();
            socket.close();
        } catch(Exception e ) {

        }
    }

    public String getMessage() {
        try {
            return in.readLine();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public boolean sendMsg(String msg) {
        out.println(msg);
        return !out.checkError();
    }
}