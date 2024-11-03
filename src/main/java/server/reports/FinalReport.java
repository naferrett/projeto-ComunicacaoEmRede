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
        try (BufferedWriter bf = new BufferedWriter(new FileWriter("relatorioFinal.txt"))) {
            bf.write("Relatório Final da Eleição\n");
            bf.write("Título da Votação: " + poll.getQuestion() + "\n\n");

            bf.write("Resultados:\n");

            bf.write("\nInformações dos votantes:\n");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
