/* Essa classe define as variáveis globais do programa, assim como também possui
 * métodos para exibir créditos sobre o sistema e instruções de ajuda. */

package clientServer.gui;

public class SystemInfo {
    public static final String author = "Grupo G - S400B - Manhã";
    public static final String faculty = "FT - Faculdade de Tecnologia";
    public static final String university = "Unicamp - Universidade Estadual de Campinas";
    public static final String name = "Projeto II - Comunicação em Rede";
    public static final String version = "Ver 1.1.2";
    public static final String systemImage = "/system.png";
    public static final String iconImage = "/groupIcon.png";

    public static String getCredits() {
        final StringBuffer text = new StringBuffer();

        text.append(university);
        text.append("\n\n");
        text.append(faculty);
        text.append("\n\n");
        text.append(author);
        text.append("\n\n");
        text.append(name);
        text.append("\n\n");
        text.append(version);

        return (text.toString());
    }

    public static String getHelp() {
        StringBuilder text = new StringBuilder();

        text.append("Este projeto envolve desenvolver uma aplicação cliente-servidor em Java, utilizando TCP/IP, multithreading e interfaces gráficas de usuário para atender múltiplos clientes simultaneamente. \n");
        text.append("\n");
        text.append("Para votar:\n");
        text.append("  Insira o CPF.\n");
        text.append("    O CPF deve conter apenas números.\n");
        text.append("    O CPF deve conter onze digitos numéricos.\n");
        text.append("  Você será redirecionado para uma janela de votação.\n");
        text.append("  Selecione a opção desejada e aperte no botão 'Confirmar Voto' para enviar seu voto ao servidor.\n");
        text.append("\n");
        text.append("Para exibir as informações da aplicação:\n");
        text.append("  Selecione a opção 'Ajuda' na barra de menu.\n");
        text.append("  No menu, selecione a opção 'Ajuda' para ler sobre como usar a aplicação.\n");
        text.append("  No menu, selecione a opção 'Créditos' para ler os créditos da aplicação.\n");
        text.append("\n");
        text.append("Para sair da aplicação:\n");
        text.append("  Selecione o botão 'X' no canto superior direito da tela.\n");
        text.append("\n");

        return (text.toString());
    }

    public static String getInstructionsToAddPool() {
        StringBuilder text = new StringBuilder();

        text.append("Adicione o título da votação e as opções de voto.\n");
        text.append("Para enviar uma opção, digite a opção e aperte o botão 'Enviar Opção'.\n");
        text.append("Ao finalizar, aperte 'Confirmar'. \n");
        text.append("Para cancelar a votação, aperte 'Cancelar'.");

        return (text.toString());
    }

    public static String getVersionName()
    {
        return (name + " - " + version);
    }
}