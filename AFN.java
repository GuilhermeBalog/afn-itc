import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class AFN {
    public static void main(String[] args) {
        Scanner sc = null;

        try{
            sc = new Scanner(new BufferedReader(new FileReader(args[0])));

            while(sc.hasNext()){
                int numeroDeAutomatos = sc.nextInt();
            }

        }catch (FileNotFoundException ex){
            System.out.println("File not found");
        }finally{
            if(sc != null)
                sc.close();
        }
    }
}