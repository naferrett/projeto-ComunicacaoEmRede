package server.reports;

import clientServer.Poll;
import server.PollServer;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class FinalReport {
    private PollServer pollServer;

    //metodo setter para pegar o PollServer
    public void setPollServer(PollServer pollServer) {
        this.pollServer = pollServer;
    }

    public void generateReport(Poll poll) {
        if (pollServer == null) {
            throw new IllegalStateException("PollServer não foi configurado.");
        }

        try (BufferedWriter bf = new BufferedWriter(new FileWriter("relatorioFinal.txt"))) {
            bf.write("Relatório Final da Eleição\n");

            bf.write("Título da Votação: " + poll.getTitle() + "\n\n");

            bf.write("Resultados:\n");
            Map<String, Integer> voteCounts = pollServer.getVoteCounts();
            for (Map.Entry<String, Integer> entry : voteCounts.entrySet()) {
                bf.write("Opção: " + entry.getKey() + " - Votos: " + entry.getValue() + "\n");
            }

            //determinação do vencedor com for
            String winningOption = null;
            int maxVotes = -1;
            for (Map.Entry<String, Integer> entry : voteCounts.entrySet()) {
                if (entry.getValue() > maxVotes) {
                    maxVotes = entry.getValue();
                    winningOption = entry.getKey();
                }
            }

            if (winningOption != null) {
                bf.write("\nGanhador: " + winningOption + " com " + maxVotes + " votos\n");
            } else {
                bf.write("\nNenhum vencedor. Não houve votos registrados.\n");
            }

            bf.write("\nInformações dos votantes:\n");
            Map<String, String> votes = pollServer.getVotes();
            for (Map.Entry<String, String> entry : votes.entrySet()) {
                bf.write("CPF: " + entry.getKey() + " - Votou em: " + entry.getValue() + "\n");
            }

            System.out.println("Relatório final gerado com sucesso!");
        } catch (IOException | NullPointerException e) {
            throw new RuntimeException("Erro ao gerar o relatório final: " + e.getMessage(), e);
        }
    }
}
