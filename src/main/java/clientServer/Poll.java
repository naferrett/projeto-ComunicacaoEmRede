package clientServer;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Poll implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private final String question;
    private final List<String> options;

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
