package guiApp;

import lombok.extern.log4j.Log4j2;

import java.awt.*;

@Log4j2
public class MainTeste {

    public static void main(String[] args) {
        try {
            (new MainWindow()).initInterface();
        } catch (HeadlessException ex) {
            log.error("Exceção do tipo HeadLessException capturada: " + ex);
        } catch (Exception ex) {
            log.error("Exceção genérica capturada: " + ex);
        }
    }
}

