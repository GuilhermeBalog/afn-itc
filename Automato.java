import java.util.Arrays;

public class Automato {
    private int numeroDeEstados;
    private int numeroDeSimbolos;
    private int qtdTransicoes;
    private int[][] matrizDeTransicoes;
    private int estadoInicial;
    private int[] estadosDeAceitacao;

    public Automato(int numeroDeEstados, int numeroDeSimbolos, String[] transicoes, int estadoInicial,
            int[] estadosDeAceitacao) {
        this.numeroDeEstados = numeroDeEstados;
        this.numeroDeSimbolos = numeroDeSimbolos;
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

    public boolean cadeiaEhValida(String cadeia) {
        String[] simbolos = cadeia.split(" ");
        return ehValido(simbolos, this.estadoInicial, 0);
    }

    private boolean contains(int[] vetor, int valorProcurado){ 
        for(int i = 0; i < vetor.length; i++)
            if(vetor[i] == valorProcurado)
                return true;
        return false;
    }

    private boolean ehValido(String[] simbolos, int estadoAtual, int indiceSimbolo) {
        if (indiceSimbolo == simbolos.length) {
            
            if (contains(this.estadosDeAceitacao, estadoAtual))
                return true;
            
            for(int j = 0; j < this.qtdTransicoes; j++){
                if (this.matrizDeTransicoes[j][0] == estadoAtual) {
                    if(this.matrizDeTransicoes[j][1] == 0){
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

                if(this.matrizDeTransicoes[j][1] == simboloAtual){
        
                    if (ehValido(simbolos, proximoEstado, indiceSimbolo + 1))
                        return true;
                }

                if(this.matrizDeTransicoes[j][1] == 0){
                    if (ehValido(simbolos, proximoEstado, indiceSimbolo))
                        return true;
                }
            }
        }

        return false;
    }
}
