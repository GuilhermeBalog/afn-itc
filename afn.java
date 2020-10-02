/**
 * @author Gabriel de Castro Michelassi - 11208162
 * @author Guilherme Balog Gardino - 11270649
 * 
 * Documentação em: https://guilhermebalog.github.io/afn-itc
 */

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.Scanner;


public class afn {
    /**
     * Método principal
     * @param args
     */
    public static void main(String[] args) {
        Scanner sc = null;
        PrintWriter saida = null;

        try {
            sc = new Scanner(new BufferedReader(new FileReader(args[0])));
            saida = new PrintWriter(args[1]);

            int numeroDeAutomatos = sc.nextInt();
            for (int i = 0; i < numeroDeAutomatos; i++) {
                Automato automato = contruirAutomato(sc);

                int qtdCadeias = sc.nextInt();
                sc.nextLine();
                boolean[] avaliacao = new boolean[qtdCadeias];
                for (int j = 0; j < qtdCadeias; j++)
                    avaliacao[j] = automato.cadeiaEhValida(sc.nextLine());

                geraRelatorio(avaliacao, saida);
            }
        } catch (FileNotFoundException ex) {
            System.out.println("File not found");
        } finally {
            if (sc != null)
                sc.close();
            if (saida != null)
                saida.close();
        }
    }

    /**
     * Constrói um automato a partir de um scanner.
     * @param sc o scanner que fornece os dados.
     * @return o Automato construido.
     */
    private static Automato contruirAutomato(Scanner sc) {
        int numeroDeEstados = sc.nextInt();
        int numeroDeSimbolos = sc.nextInt();
        int numeroDeTransicoes = sc.nextInt();
        int estadoInicial = sc.nextInt();
        int numeroDeEstadosDeAceitacao = sc.nextInt();

        int[] estadosDeAceitacao = new int[numeroDeEstadosDeAceitacao];
        for (int j = 0; j < numeroDeEstadosDeAceitacao; j++)
            estadosDeAceitacao[j] = sc.nextInt();

        sc.nextLine();
        String[] transicoes = new String[numeroDeTransicoes];
        for (int j = 0; j < numeroDeTransicoes; j++)
            transicoes[j] = sc.nextLine();

        Automato automato = new Automato(transicoes, estadoInicial, estadosDeAceitacao);
        return automato;
    }

    /**
     * Gera um arquivo para as cadeias validadas para cada autômato
     * @param resultado
     * @param saida
     */
    private static void geraRelatorio(boolean[] resultado, PrintWriter saida) {
        String resp = "";
        for (boolean i : resultado) {
            if (i)
                resp += "1 ";
            else
                resp += "0 ";
        }
        saida.println(resp.trim());
    }

    /**
     * Classe Aninhada para definir um autômato e realizar a validação das cadeias
     */
    private static class Automato {
        private int qtdTransicoes;
        private int[][] matrizDeTransicoes;
        private int estadoInicial;
        private int[] estadosDeAceitacao;

        /**
         * Método construtor
         * @param transicoes
         * @param estadoInicial
         * @param estadosDeAceitacao
         */
        public Automato(String[] transicoes, int estadoInicial, int[] estadosDeAceitacao) {
            this.estadoInicial = estadoInicial;
            this.estadosDeAceitacao = estadosDeAceitacao;
            this.qtdTransicoes = transicoes.length;

            this.matrizDeTransicoes = new int[transicoes.length][3];
            for (int i = 0; i < transicoes.length; i++) {
                String[] linha = transicoes[i].split(" ");
                this.matrizDeTransicoes[i][0] = Integer.parseInt(linha[0]);
                this.matrizDeTransicoes[i][1] = Integer.parseInt(linha[1]);
                this.matrizDeTransicoes[i][2] = Integer.parseInt(linha[2]);
            }
        }

        /**
         * @param cadeia Cadeia a ser validada
         * @return true se a cadeia é válida, false caso contrário
         */
        public boolean cadeiaEhValida(String cadeia) {
            String[] simbolos = cadeia.split(" ");
            return ehValido(simbolos, this.estadoInicial, 0);
        }

        /**
         * Indica se um valor procurado está presente no vetor especificado
         * @param vetor
         * @param valorProcurado
         * @return true se o vetor contém o valor procurado ou false caso contrário
         */
        private boolean contains(int[] vetor, int valorProcurado) {
            for (int i = 0; i < vetor.length; i++)
                if (vetor[i] == valorProcurado)
                    return true;
            return false;
        }

        /**
         * Função auxiliar recursiva utilizada na validação das cadeias
         * @param simbolos
         * @param estadoAtual
         * @param indiceSimbolo
         * @return true se a cadeia é válida ou false caso contrário
         */
        private boolean ehValido(String[] simbolos, int estadoAtual, int indiceSimbolo) {
            if (indiceSimbolo == simbolos.length) {

                if (contains(this.estadosDeAceitacao, estadoAtual))
                    return true;

                for (int j = 0; j < this.qtdTransicoes; j++) {
                    if (this.matrizDeTransicoes[j][0] == estadoAtual) {
                        if (this.matrizDeTransicoes[j][1] == 0) {
                            int proximoEstado = this.matrizDeTransicoes[j][2];
                            if (contains(this.estadosDeAceitacao, proximoEstado))
                                return true;
                        }
                    }
                }
                return false;
            }

            for (int j = 0; j < this.qtdTransicoes; j++) {
                if (this.matrizDeTransicoes[j][0] == estadoAtual) {
                    int simboloAtual = Integer.parseInt(simbolos[indiceSimbolo]);
                    int proximoEstado = this.matrizDeTransicoes[j][2];

                    if (this.matrizDeTransicoes[j][1] == simboloAtual)
                        if (ehValido(simbolos, proximoEstado, indiceSimbolo + 1))
                            return true;

                    if (this.matrizDeTransicoes[j][1] == 0)
                        if (ehValido(simbolos, proximoEstado, indiceSimbolo))
                            return true;

                }
            }

            return false;
        }
    }
}
