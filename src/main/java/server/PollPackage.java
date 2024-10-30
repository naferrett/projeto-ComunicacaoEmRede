package server;

import java.util.List;

public class PollPackage {

    private String question;
    private List<String> options;

    public PollPackage(String question, List<String> options) {
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
