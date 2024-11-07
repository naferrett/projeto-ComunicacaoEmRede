package server.gui;

import clientServer.gui.BaseWindow;
import server.PollServer;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class ServerMainWindow extends BaseWindow {
    private final PollServer server;

    public ServerMainWindow(PollServer votingServer) {
        super();

        this.server = votingServer;
        new Thread(() -> server.startServer()).start();

        initAddComponents();
    }

    public void initAddComponents() {
        initStatusPanel();
        initWindowListener();
        initMenu();
        initBackGround();
    }

    private final Map<String, JLabel> voteCountsLabels = new HashMap<>();

    // resultados
    public void initBackGround() {
        JPanel backgroundPanel = createBackgroundPanel();
        //criar os outros métodos para adicionar no background e adicionar aqui..
        // ex:
        //JLabel exemplo = createLabelExemplo();
        //addComponentsToBackgroundPanel(backgroundPanel, exemplo);
        addVoteCountLabels(backgroundPanel);
        //o scrollPane é aquela barrinha de rolagem na lateral, é um container externo que envolve o JPanel, por isso ele fica por último
        JScrollPane scrollPane = createScrollPane(backgroundPanel);
        this.add(scrollPane, BorderLayout.CENTER);
        startVoteCountUpdater();
    }

    private void addVoteCountLabels(JPanel panel){
        Map<String, Integer> voteCounts = server.getVoteCounts(); //Pega as opções de voto la da função criada no PollServer

        for(String option : voteCounts.keySet()){
            JLabel voteLabel = new JLabel(option + ": 0 votos");
            voteLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            voteCountsLabels.put(option, voteLabel); // associa a opção com seu respectivo label
            panel.add(voteLabel);
        }
    }

    //vai ficar chamando a função update a cada 1000 milissegundos
    private void startVoteCountUpdater(){
        Timer timer = new Timer(1000, e->updateVoteCounts());
        timer.start();
    }

    //atualização dos votos vai ocorrer aqui
    private void updateVoteCounts(){
        Map<String,Integer> voteCounts = server.getVoteCounts();
        for(Map.Entry<String, Integer> entry : voteCounts.entrySet()){
            String option = entry.getKey();
            int count = entry.getValue();

            JLabel voteLabel = voteCountsLabels.get(option);//pega o Label da opção
            if(voteLabel != null){
                voteLabel.setText(option + ": " + count + " votos");//atualiza o Label com os votos atuais
            }
        }
    }

    private JScrollPane createScrollPane(JPanel backgroundPanel) {
        return new JScrollPane(backgroundPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    }

    private JPanel createBackgroundPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        return panel;
    }

    private JLabel createLabelExemplo() {
        JLabel exemploLabel = new JLabel("Label de exemplo");
        exemploLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        return exemploLabel;
    }

    private void addComponentsToBackgroundPanel(JPanel panel, JLabel exemplo) {
        //panel.add(Box.createVerticalStrut(10)); esse createVerticalStruct dá um espaço entre um elementos pra eles não ficarem colados, fica a seu critério se quiser usar
        //.. adicionar o resto dos componentes do backgroundPanel
        panel.add(exemplo);
    }

    public void initMenu() {
        ServerMenuHandler menuHandler = new ServerMenuHandler(this);
        menuHandler.createAddToMenu();
        initEventListener(menuHandler);
    }

    public void initEventListener(ServerMenuHandler menuHandler) {
        ServerEventListener eventListener = new ServerEventListener(this, menuHandler, server);
        addMenuListeners(eventListener);
    }

    @Override
    public void exitInterface() {
        if(server.isServerRunning()) {
            server.closeServer();
        }
        super.exitInterface();
    }
}