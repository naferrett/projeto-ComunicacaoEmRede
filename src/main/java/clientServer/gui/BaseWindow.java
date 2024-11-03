package clientServer.gui;

import client.gui.ClientMenuHandler;
import client.gui.ClientEventListener;
import lombok.extern.log4j.Log4j2;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.Serial;

public class BaseWindow extends JFrame implements ClosableWindow {
    @Serial
    private static final long serialVersionUID = 1L;
    protected JLabel labelStatus;

    public BaseWindow() throws HeadlessException {
        super(SystemInfo.getVersionName());
        windowConfig();
    }

    public void windowConfig() {
        this.setSize((int) (Toolkit.getDefaultToolkit().getScreenSize().getWidth() * 0.3), (int) (Toolkit.getDefaultToolkit().getScreenSize().getHeight() * 0.3));
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setIcon();
    }

    public void setIcon() {
        try {
            this.setIconImage(ImageIO.read(this.getClass().getResource(SystemInfo.iconImage)));
        } catch (IOException | NullPointerException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar o ícone.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void initInterface() {
        this.setStatusMessage(SystemInfo.university);
        setVisible(true);
    }

    public void setStatusMessage(String message) {
        this.labelStatus.setText(message);
    }

    public void initStatusPanel() {
        JPanel statusPanel = new JPanel();
        this.labelStatus = new JLabel();
        statusPanel.add(labelStatus);
        statusPanel.setBackground(Color.gray);
        statusPanel.setBorder(BorderFactory.createEtchedBorder());
        this.add(statusPanel, BorderLayout.SOUTH);
    }

    public void initWindowListener() {
        WindowListenerHandler windowEventListener = new WindowListenerHandler(this);
        this.addWindowListener(windowEventListener);
    }

    public void addMenuItemListener(ActionListener listener, JMenu mainMenu) {
        for(Component target : mainMenu.getMenuComponents()) {
            if (target instanceof JMenuItem) {
                ((JMenuItem) target).addActionListener(listener);
            }
        }
    }

    public void addMenuListeners(ActionListener listener) {
        for (Component menu : this.getJMenuBar().getComponents()) {
            if (menu instanceof JMenu) {
                addMenuItemListener(listener, (JMenu) menu);
            }
        }
    }

    @Override
    public void exitInterface() {
        System.exit(NORMAL);
    }
}
