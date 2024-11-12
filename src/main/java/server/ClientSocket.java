/*
 * The ClientSocket class encapsulates a client connection using a Socket object.
 * It provides a method to close the connection, handling any IOExceptions that may occur.
 */

package server;

import java.io.IOException;
import java.net.Socket;

record ClientSocket(Socket socket) {

    public void close() {
        try {
            socket.close();
        } catch (IOException e) {
            System.out.println("Erro ao fechar conexão: " + e.getMessage());
        }
    }
}
