/*
 * A classe é responsável por gerar um relatório final em arquivo de texto
 * da eleição com base nas informações de uma votação contendo:
 * 1. O título da votação.
 * 2. Os resultados de cada opção de votação (quantidade de votos).
 * 3. O vencedor da votação ou empate.
 * 4. Informações sobre os votantes e suas escolhas.
 */


package server.reports;

import clientServer.Poll;
import lombok.Setter;
import server.PollServer;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Setter
public class FinalReport {

    private PollServer pollServer;

    public void generateReport(Poll poll) {
        if (pollServer == null) {
            throw new IllegalStateException("PollServer não foi configurado.");
        }

        try (BufferedWriter bf = new BufferedWriter(new FileWriter("relatorioFinal.txt"))) {
            bf.write("Relatório Final da Eleição\n");
            bf.write("Título da Votação: " + poll.title() + "\n\n");
            bf.write("Resultados:\n");

            writeResults(bf, poll);
            writeWinner(bf);
            writeVotersInfo(bf);
        } catch (IOException | NullPointerException e) {
            throw new RuntimeException("Erro ao gerar o relatório final: " + e.getMessage(), e);
        }
    }

    private void writeResults(BufferedWriter bf, Poll poll) throws IOException {
        Map<String, Integer> voteCounts = pollServer.getVoteCounts();
        List<String> pollOptions = poll.options();

        for (String option : pollOptions) {
            int votes = voteCounts.getOrDefault(option, 0);
            bf.write("Opção: " + option + " - Votos: " + votes + "\n");
        }
    }

    private void writeWinner(BufferedWriter bf) throws IOException {
        Map<String, Integer> voteCounts = pollServer.getVoteCounts();
        List<String> winningOption = determineWinner(voteCounts);

        if (!winningOption.isEmpty()) {
            if (winningOption.size() > 1) {
                bf.write("\nEmpate entre as opções mais votadas: ");
                for (String option : winningOption) {
                    bf.write("\n" + option + " empatou com " + voteCounts.get(option) + " votos.");
                }
                bf.write("\n");
            } else {
                bf.write("\nGanhador: " + winningOption.getFirst() + " com " + voteCounts.get(winningOption.getFirst()) + " votos\n");
            }
        } else {
            bf.write("\nNenhum vencedor. Não houve votos registrados.\n");
        }
    }

    private List<String> determineWinner(Map<String, Integer> voteCounts) {
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

        return winningOption;
    }

    private void writeVotersInfo(BufferedWriter bf) throws IOException {
        bf.write("\nInformações dos votantes:\n");
        Map<String, String> votes = pollServer.getVotes();

        for (Map.Entry<String, String> entry : votes.entrySet()) {
            bf.write("CPF: " + entry.getKey() + " - Votou em: " + entry.getValue() + "\n");
        }
    }
}
