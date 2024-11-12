/*
 * Interface that defines the exitInterface method, inherited by the ClientMainWindow and ServerMainWindow classes from the BaseWindow class to implement
 * specific behavior for closing the interface. The interface allows these classes to share a common implementation for window event management
 * through the WindowListenerHandler class.
 */

package clientServer.gui;

public interface ClosableWindow {
    void exitInterface();
}
