/*
* A classe Poll representa uma votação, contendo um título e uma lista de opções.
* É uma classe imutável (record) que implementa Serializable, permitindo a transmissão e armazenamento dos dados da votação.
* */

package clientServer;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

public record Poll(String title, List<String> options) implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
}
