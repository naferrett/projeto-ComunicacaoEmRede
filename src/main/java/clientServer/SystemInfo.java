/* Essa classe define as variáveis globais do programa, assim como também possui
 * métodos para exibir informações sobre o sistema e instruções de ajuda. */

package clientServer;

public class SystemInfo {
    public static final String author = "Grupo G - S400B - Manhã";
    public static final String faculty = "FT - Faculdade de Tecnologia";
    public static final String university = "Unicamp - Universidade Estadual de Campinas";
    public static final String name = "Projeto II - Comunicação em Rede";
    public static final String version = "Ver 1.0.0";
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

        text.append("Este programa tem como objetivo demonstrar o uso de interface gráfica em Java enquanto explora a criação de interfaces, integração com arquivos, uso de threads e personalização de componentes. \n");
        text.append("\n");
        text.append("A aplicação demonstra a mecânica de ouvintes, manipulação de arquivos, criação de diálogos e animações dinâmicas.\n");
        text.append("\n");
        text.append("Para selecionar um arquivo:\n");
        text.append("  Selecione a opção 'Arquivo' na barra de menu.\n");
        text.append("  Selecione a opção 'Abrir Arquivo' no menu.\n");
        text.append("  Para fechar o arquivo, elecione a opção 'Fechar Arquivo' na barra de menu.\n");
        text.append("\n");
        text.append("Para sair da aplicação:\n");
        text.append("  Primeira opção:\n");
        text.append("    Selecione a opção 'Arquivo' na barra de menu.\n");
        text.append("    Selecione a opção 'Sair' no menu.\n");
        text.append("\n");
        text.append("  Segunda opção:\n");
        text.append("    Selecione o botão 'X' no canto superior direito da tela.\n");
        text.append("\n");
        text.append("Para alterar o padrão do comportamento dinâmico do fundo:\n");
        text.append("  Selecione a opção 'Configuração' na barra de menu.\n");
        text.append("  Selecione a opção 'Alterar Padrão' no menu.\n");
        text.append("  Selecione o padrão desejado.\n");
        text.append("\n");
        text.append("Para alterar a cor do fundo:\n");
        text.append("  Selecione a opção 'Configuração' na barra de menu.\n");
        text.append("  Selecione a opção 'Alterar Cor' no menu.\n");
        text.append("  Selecione a cor desejada.\n");
        text.append("\n");
        text.append("Para alterar a velocidade das animações do fundo:\n");
        text.append("  Selecione a opção 'Configuração' na barra de menu.\n");
        text.append("  Selecione a opção 'Alterar Velocidade' no menu.\n");
        text.append("  Selecione a velocidade desejada.\n");
        text.append("\n");
        text.append("Para exibir as informações da aplicação:\n");
        text.append("  Selecione a opção 'Ajuda' na barra de menu.\n");
        text.append("  Selecione a opção 'Sobre' no menu.\n");

        return (text.toString());
    }

    public static String getInstructionsToAddPool() {
        StringBuilder text = new StringBuilder();

        text.append("Adicione as opções de voto.\n");
        text.append("Para enviar uma opção, digite a opção e aperte o botão 'Adicionar Opção'.\n");
        text.append("Ao finalizar, aperte 'Confirmar'. \n");
        text.append("Para cancelar a votação, aperte 'Cancelar'.");

        return (text.toString());
    }

    public static String getVersionName()
    {
        return (name + " - " + version);
    }
}