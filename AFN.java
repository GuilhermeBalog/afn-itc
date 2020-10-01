import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;
import java.util.Scanner;

public class AFN {
    public static void main(String[] args) {
        Scanner sc = null;

        try{
            sc = new Scanner(new BufferedReader(new FileReader("ArqTeste.txt")));

            int numeroDeAutomatos = sc.nextInt();
            for(int i = 0; i < numeroDeAutomatos; i++){
                Automato afn = contruirAutomato(sc);
                    
                int qtdCadeias = sc.nextInt();
                sc.nextLine();
                boolean[] avaliacao = new boolean[qtdCadeias];
                for(int j = 0; j < qtdCadeias; j++)
                    avaliacao[j] = afn.cadeiaEhValida(sc.nextLine());
                System.out.println(Arrays.toString(avaliacao));
            }
        }catch (FileNotFoundException ex){
            System.out.println("File not found");
        }finally{
            if(sc != null)
                sc.close();
        }
    }

    private static Automato contruirAutomato(Scanner sc) {
        int numeroDeEstados = sc.nextInt();
        int numeroDeSimbolos = sc.nextInt();
        int numeroDeTransicoes = sc.nextInt();
        int estadoInicial = sc.nextInt();
        int numeroDeEstadosDeAceitacao = sc.nextInt();

        int[] estadosDeAceitacao = new int[numeroDeEstadosDeAceitacao];
        for(int j = 0; j < numeroDeEstadosDeAceitacao; j++)
            estadosDeAceitacao[j] = sc.nextInt();
        
        sc.nextLine();
        String[] transicoes = new String[numeroDeTransicoes];
        for(int j = 0; j < numeroDeTransicoes; j++)
            transicoes[j] = sc.nextLine();

        Automato afn = new Automato(numeroDeEstados, numeroDeSimbolos, transicoes, estadoInicial, estadosDeAceitacao);
        return afn;
    }
}