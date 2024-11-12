/* This class implements the WindowListener interface to manage events related
 * to the main window of each application. */

package clientServer.gui;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class WindowListenerHandler implements WindowListener {
    private final ClosableWindow reference;

    public WindowListenerHandler(BaseWindow reference)
    {
        this.reference = reference;
    }

    @Override
    public void windowActivated(WindowEvent arg0) {}

    @Override
    public void windowClosed(WindowEvent arg0) {}

    @Override
    public void windowClosing(WindowEvent arg0) {
        this.reference.exitInterface();
    }

    @Override
    public void windowDeactivated(WindowEvent arg0) {}

    @Override
    public void windowDeiconified(WindowEvent arg0) {}

    @Override
    public void windowIconified(WindowEvent arg0) {}

    @Override
    public void windowOpened(WindowEvent arg0) {}

}