package server.gui;

import client.VotingClient;
import client.gui.EventListener;
import client.gui.MenuHandler;
import client.gui.PollWindow;
import clientServer.Poll;
import clientServer.SystemInfo;
import clientServer.WindowListenerHandler;
import lombok.extern.log4j.Log4j2;
import server.VotingServer;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.Serial;

@Log4j2
public class ServerMainWindow extends JFrame {
    @Serial
    private static final long serialVersionUID = 1L;
    private JLabel labelStatus;
    private VotingServer server;

    public ServerMainWindow(VotingClient votingClient) throws HeadlessException {
        super(SystemInfo.getVersionName());

        windowConfig();
        initAddComponents();

    }

    private void windowConfig() {
        this.setSize((int) (Toolkit.getDefaultToolkit().getScreenSize().getWidth() * 0.3), (int) (Toolkit.getDefaultToolkit().getScreenSize().getHeight() * 0.3));
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.setIcon();
    }

    private void setIcon() {
        try {
            this.setIconImage(ImageIO.read(this.getClass().getResource(SystemInfo.iconImage)));
        } catch (IOException | NullPointerException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar o ícone.", "Erro", JOptionPane.ERROR_MESSAGE);
            log.error(ex);
        }
    }

    public void setStatusMessage(String message) {
        this.labelStatus.setText(message);
    }

    private void initAddComponents() {
        initStatusPanel();
//        initWindowListener();
//        initMenu();
        initBackGround();

        setVisible(true);
    }

    private void initStatusPanel() {
        JPanel statusPanel = new JPanel();
        this.labelStatus = new JLabel();
        statusPanel.add(labelStatus);
        statusPanel.setBackground(Color.gray);
        statusPanel.setBorder(BorderFactory.createEtchedBorder());
        this.add(statusPanel, BorderLayout.SOUTH);
    }

    public void initInterface() {
        this.setStatusMessage(SystemInfo.university);
        this.setVisible(true);
    }

//    private void initWindowListener() {
//        WindowListenerHandler windowEventListener = new WindowListenerHandler(this);
//        this.addWindowListener(windowEventListener);
//    }

//    private void initMenu() {
//        MenuHandler menuHandler = new MenuHandler(this);
//        menuHandler.createAddToMenu();
//
//        initEventListener(menuHandler);
//    }

//    private void initEventListener(MenuHandler menuHandler) {
//        EventListener eventListener = new EventListener(this, menuHandler);
//        this.addMenuListeners(eventListener);
//    }

    public void initBackGround() {
    }

//    void addMenuItemListener(ActionListener listener, JMenu mainMenu) {
//        for(Component target : mainMenu.getMenuComponents()) {
//            if(target instanceof JMenuItem) {
//                ((JMenuItem) target).addActionListener(listener);
//            }
//        }
//    }
//
//    void addMenuListeners(ActionListener listener) {
//        for(Component menu : this.getJMenuBar().getComponents()) {
//            if(menu instanceof JMenu) {
//                addMenuItemListener(listener, (JMenu) menu);
//            }
//        }
//    }

    public void exitInterface() {
        server.closeServer();
        System.exit(NORMAL);
    }
}