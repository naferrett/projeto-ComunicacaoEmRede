<div align="center">
    <h1>Votação Distribuída em Java</h1>
</div>

<div align="center">
    <img src="https://github.com/user-attachments/assets/1cf3a00e-6391-4b17-a4b0-0ee057536f7a" alt="image" style="height: 270px;">
    <img src="https://github.com/user-attachments/assets/ca9ce4ca-fd83-4a7b-9f87-ba22404d994c" alt="image" style="height: 270px;">
</div>

## 📋 Sobre o Projeto
Este é um sistema de votação distribuído desenvolvido em Java, com funcionalidades de cliente e servidor. Ele permite que os usuários votem em diferentes opções e visualizem resultados em tempo real da votação, além de gerar relatórios finais com informações detalhadas sobre a votação. O sistema permite a criação de novas votações, assim como seu gerenciamento, através de menus interativos.

## 🎯 Objetivos de Aprendizagem
- Compreender e implementar protocolos de comunicação de rede, utilizando TCP/IP para garantir a comunicação confiável entre os componentes de sistemas distribuídos.
- Desenvolver servidores multithread, adquirindo experiência em gerenciar conexões simultâneas de clientes de forma eficiente no servidor.
- Projetar e implementar interfaces gráficas de usuário (GUIs), praticando a criação de interfaces intuitivas e funcionais em Java.
- Aplicar os princípios de sistemas distribuídos, introduzindo a arquitetura distribuída, como o modelo cliente-servidor e a troca de dados pela rede.
- Gerenciar a entrada de dados de forma segura, validando o CPF para simular garantias de autenticidade e utilizando estruturas de dados que previnam a duplicação de informações.
- Lidar com o processamento e a apresentação de dados em tempo real, desenvolvendo sistemas que atualizam e exibem informações e geram relatórios.

## 🛠️ Funcionalidades
### Cliente
- Conexão com o servidor de votação.
- Receber o pacote de votação.
- Interface gráfica para digitar o CPF e escolher uma opção de voto.
- Envoar o CPF e voto ao servidor.
- Desconectar-se do servidor após o término da interação.
- Menus interativos com opções de Ajuda e Créditos.

### Servidor
- Gerenciamento de votações:
  - Criação de novas votações.
  - Encerramento de votações existentes.
- Aceitar a conexão de clientes e enviar o pacote de votação.
- Receber e validar os CPFs e votos enviados.
- Exibição de resultados em tempo real na interface.
- Geração de relatórios finais detalhados contendo:
  - Opções e a quantidade de votos recebidas.
  - Informações dos votantes.
  - Ganhador da votação ou opções empatadas.
- Suporte a múltiplos clientes simultaneamente.

## 💻 Tecnologias Utilizadas
- **Linguagem:** Java 17+
- **Frameworks/Libs:** 
  - Swing (Interface Gráfica)
  - `javax.swing.Timer` (Atualização de Resultados em Tempo Real)
  - `lombok` 
- **Arquitetura:** Cliente-Servidor

## 🚀 Como Usar
### Requisitos
- Java 17 ou superior instalado no sistema.
- IDE de sua escolha ou terminal para compilar e executar os arquivos.

### Passos
1. Clone este repositório:
   ```bash
   git clone https://github.com/seu-usuario/seu-repositorio.git

2. Execute o arquivo <i>ServerMain</i> para iniciar o servidor de votação e crie uma nova votação.

3. Configure o IP do servidor no cliente, alterando a String serverAddress em VotingClient.java para o IP corrente do servidor.

4. Execute o arquivo <i>ClientMain</i> para iniciar o cliente de votação, insira o CPF e escolha uma das opções.

## 📊 Relatórios de Votação
Os relatórios são gerados automaticamente após o encerramento da votação e salvos no arquivo relatorioFinal.txt. O relatório inclui:
- Ganhador da votação;
- Resultado individual de cada opção;
- CPFs dos votantes;
