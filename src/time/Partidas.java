package time;
import teclado.Teclado;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.io.Serializable;

public class Partidas implements Serializable {
    private Time[] times;
    private int quantTimes;
    private List<Partida> partidas;
    private static final String ARQUIVO_DADOS = "dados.txt";

    public Partidas(int quantidade) {
        times = new Time[quantidade];
        quantTimes = 0;
        partidas = new ArrayList<>();
    }
    
    public void salvarDados() {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(ARQUIVO_DADOS));
            outputStream.writeObject(times);
            outputStream.writeObject(partidas);
            outputStream.close();
            System.out.println("Dados salvos com sucesso.");
        } catch (IOException e) {
            System.out.println("Erro ao salvar os dados: " + e.getMessage());
        }
    }

    // M�todo para carregar as informa��es de um arquivo
    public void carregarDados() {
        try {
            ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(ARQUIVO_DADOS));
            times = (Time[]) inputStream.readObject();
            partidas = (List<Partida>) inputStream.readObject();
            inputStream.close();
            System.out.println("Dados carregados com sucesso.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Nenhum dado encontrado. Ser� criado um novo arquivo de dados.");
        }
    }


    public void quantidadeTimes() {
        quantTimes = Teclado.leInt("\nQuantos times você deseja inserir? ");
        times = new Time[quantTimes];
    }

    public void insereTime() {
        for (int i = 0; i < times.length; i++) {
            times[i] = new Time(Teclado.leString("\nPor favor, digite o nome do " + (i + 1) + "° time a ser registrado: "), 0, 0, 0, 0, 0, 0);
            quantTimes++;
        }
    }

    public void registraPartida() {
        imprimeTimesPosicao();
        int t = 1;
        int numPosicao1 = Teclado.leInt("\nQual o número da posição do "+t+"° time que você deseja registrar a partida?");
        int numPosicao2 = Teclado.leInt("\nQual o número da posição do "+(t+1)+"° time que você deseja registrar esta partida contra?");
        int numGols1;
        int numGols2;

        while (true) {
            numGols1 = Teclado.leInt("\nQual o número de gols feito pelo " + times[numPosicao1].getNomeTime() + "?");
            numGols2 = Teclado.leInt("\nQual o número de gols feito pelo " + times[numPosicao2].getNomeTime() + "?");
            if (numGols1 >= 0 && numGols2 >= 0) {
                break;
            } else {
                System.out.println("\nO número de gols não pode ser negativo! Vamos tentar novamente.");
            }
        }

        partidas.add(new Partida(times[numPosicao1], times[numPosicao2], numGols1, numGols2));
        times[numPosicao1].registrarPartida(numGols1, numGols2);
        times[numPosicao2].registrarPartida(numGols2, numGols1);
        System.out.println("Partida registrada!");
    }

    public void imprimeTimesPosicao() {
        for (int i = 0; i < times.length; i++) {
            System.out.println((i) + "°: " + times[i].toString());
        }
    }

    public void organizaTabela() {
        Arrays.sort(times, (t1, t2) -> {
            // Critério 1: Maior saldo de gols
            int saldoGolsCompare = Integer.compare(t2.getSaldoGols(), t1.getSaldoGols());
            if (saldoGolsCompare != 0) {
                return saldoGolsCompare;
            }

            // Critério 2: Maior número de gols pró
            int golProCompare = Integer.compare(t2.getGolPro(), t1.getGolPro());
            if (golProCompare != 0) {
                return golProCompare;
            }

            // Critério 3: Partidas entre os dois times empatados na posição
            // O time vencedor será posicionado acima
            int partidaCompare = Integer.compare(getVitoriasDiretas(t2, t1), getVitoriasDiretas(t1, t2));
            if (partidaCompare != 0) {
                return partidaCompare;
            }

            // Critério 4: Sorteio
            return (Math.random() < 0.5) ? -1 : 1;
        });
    }

    private int getVitoriasDiretas(Time time1, Time time2) {
        int numVitorias = 0;
        for (Partida partida : partidas) {
            if (partida.getTime1() == time1 && partida.getTime2() == time2 && partida.getVencedor() == time1) {
                numVitorias++;
            }
        }
        return numVitorias;
    }

    public void imprimeTabela() {
    	organizaTabela();
        System.out.println("--------------------------------------------------------------------------------------------------------------------------");
        System.out.println("|   Time    |  Pontos  |  Jogos  | Vitórias | Empates | Derrotas | Saldo Gols | Gols Pró | Gols Contra | % Aproveitamento|");
        System.out.println("--------------------------------------------------------------------------------------------------------------------------");

        for (int i = 0; i < times.length; i++) {
            Time time = times[i];
            String nome = time.getNomeTime();
            int pontos = time.getPontos();
            int jogos = time.getNumJogos();
            int vitorias = time.getNumVitorias();
            int empates = time.getNumEmpates();
            int derrotas = time.getNumDerrotas();
            int saldoGol = time.getSaldoGols();
            int golPro = time.getGolPro();
            int golContra = time.getGolContra();
            double percentual = time.getPercentual();

            System.out.printf("| %-9s | %-8d | %-7d | %-8d | %-7d | %-9d | %-11d | %-8d | %-11d | %-10.2f%% |\n",
                    nome, pontos, jogos, vitorias, empates, derrotas, saldoGol, golPro, golContra, percentual);

            System.out.println("-----------------------------------------------------------------------------------------------------------------------");
        }
    }

    public void imprimeInfo() {
        Time timeGanhando = times[0];

        for (int i = 1; i < times.length; i++) {
            Time timeAgora = times[i];

            if (timeAgora.getPontos() > timeGanhando.getPontos()) {
                timeGanhando = timeAgora;
            }
        }
        System.out.println("\nO time que está atualmente com mais pontos é o: " + timeGanhando.getNomeTime());
        System.out.println("Com um total de " + timeGanhando.getPontos() + " pontos!\n");
    }

    public void imprimeMenu() {
        System.out.println("========== MENU ==========");
        System.out.println("1- Inserir times");
        System.out.println("2- Registrar partida entre times");
        System.out.println("3- Mostrar tabela de classificação");
        System.out.println("4- Mostrar informação atual do time ganhador");
        System.out.println("5- Sair");
        System.out.println("===========================");
    }
}