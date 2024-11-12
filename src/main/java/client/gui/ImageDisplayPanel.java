/* This class is responsible for loading and displaying an image. */

package client.gui;

import clientServer.gui.SystemInfo;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.IOException;
import java.io.Serial;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

class ImageDisplayPanel extends JPanel {
    @Serial
    private static final long serialVersionUID = 1L;

    @Override
    public void paint(Graphics g) {
        super.paintComponent(g);
        Graphics2D auxGraphics = (Graphics2D) g;

        try {
            URL auxURL = this.getClass().getResource(SystemInfo.systemImage);
            Image currentImage = ImageIO.read(auxURL);
            auxGraphics.drawImage(currentImage, 20, 50, 180, 190, 0, 0, currentImage.getWidth(null), currentImage.getHeight(null), null);
        } catch (IOException e) {
            System.out.println("Exceção do tipo IOException capturada: " + e.getMessage());
        }
    }

}