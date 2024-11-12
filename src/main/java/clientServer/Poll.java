/*
 * The Poll class represents a vote, containing a title and a list of options.
 * It is an immutable class (record) that implements Serializable, allowing the transmission and storage of voting data.
 * */

package clientServer;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

public record Poll(String title, List<String> options) implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
}
