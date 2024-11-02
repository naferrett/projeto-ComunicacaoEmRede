package server.reports;

import clientServer.Poll;
import server.VotingServer;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class FinalReport {

    // metodo pra calcular contagem de votos pra cada opção
    // método pra
    public void generateReport(Poll poll) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("relatorioFinal.txt"))) {
            writer.write("Relatório Final da Eleição\n");
            writer.write("Título da Votação: " + poll.getQuestion() + "\n\n");

            // Calcula a contagem de votos para cada opção

            // Escreve os resultados da votação
            writer.write("Resultados:\n");

            writer.write("\nInformações dos votantes:\n");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
