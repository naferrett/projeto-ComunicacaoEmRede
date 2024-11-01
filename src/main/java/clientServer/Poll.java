package clientServer;

import java.io.Serializable;
import java.util.List;

public class Poll implements Serializable {
    private static final long serialVersionUID = 1L; // Para garantir a compatibilidade de serialização
    private String question;
    private List<String> options;

    public Poll(String question, List<String> options) {
        this.question = question;
        this.options = options;
    }

    public String getQuestion() {
        return question;
    }

    public List<String> getOptions() {
        return options;
    }
}
