package server.reports;

import clientServer.Poll;
import server.PollServer;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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

            bf.write("Título da Votação: " + poll.title() + "\n\n");

            bf.write("Resultados:\n");
            Map<String, Integer> voteCounts = pollServer.getVoteCounts();

            List<String> pollOptions = poll.options(); // Supondo que `poll.getOptions()` retorna todas as opções disponíveis na votação
            for (String option : pollOptions) {
                int votes = voteCounts.getOrDefault(option, 0);
                bf.write("Opção: " + option + " - Votos: " + votes + "\n");
            }

            //determinação do vencedor com for
            List<String> winningOption = new ArrayList<>();
            int maxVotes = -1;
            for (Map.Entry<String, Integer> entry : voteCounts.entrySet()) {
                if (entry.getValue() > maxVotes) {
                    winningOption.clear();
                    maxVotes = entry.getValue();
                    winningOption.add(entry.getKey());
                } else if (entry.getValue() == maxVotes) {
                    winningOption.add(entry.getKey());
                }
            }

            if (!winningOption.isEmpty()) {
                if(winningOption.size() > 1) {
                    bf.write("\nEmpate entre as opções mais votadas: ");
                    for (String option : winningOption) {
                        bf.write("\n" + option + " empatou com " + maxVotes + " votos");
                    }
                    bf.write("\n");
                } else {
                    bf.write("\nGanhador: " + winningOption.getFirst() + " com " + maxVotes + " votos\n");
                }
            } else {
                bf.write("\nNenhum vencedor. Não houve votos registrados.\n");
            }

            bf.write("\nInformações dos votantes:\n");
            Map<String, String> votes = pollServer.getVotes();
            for (Map.Entry<String, String> entry : votes.entrySet()) {
                bf.write("CPF: " + entry.getKey() + " - Votou em: " + entry.getValue() + "\n");
            }
        } catch (IOException | NullPointerException e) {
            throw new RuntimeException("Erro ao gerar o relatório final: " + e.getMessage(), e);
        }
    }
}
