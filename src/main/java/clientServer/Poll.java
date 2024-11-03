package clientServer;

import lombok.Getter;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Getter
public class Poll implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private final String title;
    private final List<String> options;

    public Poll(String title, List<String> options) {
        this.title = title;
        this.options = options;
    }
}
