import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

/**
 * Created by Lemekh on 10.12.2016.
 */
public class lab11 {
    public static int N;
    public static int M;
    public static int[] Mas1;
    public static int[] Mas2;
    public static int[] Mas3;
    public static int[][] matrix_sum;
    public static int[][] incedence;

    public static void main(String[] args) throws IOException {
        int number, to, from;
        File f = new File("C:/test/TEST.txt");
        Scanner sc = new Scanner(new InputStreamReader(new FileInputStream(f)));
        N = sc.nextInt();
        System.out.println("Кількість вершин " + N);
        M = sc.nextInt();
        System.out.println("Кількість ребер " + M);
        Mas1 = new int[M];
        Mas2 = new int[M];
        Mas3 = new int[M];
        for (int i = 0; i < M; i++){
            Mas1[i] = sc.nextInt();
            Mas2[i] = sc.nextInt();
            Mas3[i] = sc.nextInt();
        }
        matrix_sum = new int[N+1][N+1];
        for (int i = 0; i < M; i++){
            matrix_sum[Mas1[i]][Mas2[i]] = Mas3[i];
            matrix_sum[Mas2[i]][Mas1[i]] = Mas3[i];
        }
        System.out.println("Матриця суміжності:");
        System.out.print("  ");
        for (int i = 1; i <= N; i++)
            System.out.print(i + " ");
        System.out.println();
        for (int i = 1; i <= N; i++){
            System.out.print(i + " ");
            for (int j = 1; j <= N; j++){
                System.out.print(matrix_sum[i][j] + " ");
            }
            System.out.println();
        }

        incedence = new int[N+1][M+1];
        for (int i=0; i<M; i++){
            number = i+1;
            to = Mas1[i];
            from = Mas2[i];
            int value = Mas3[i];
            incedence[to][number] = value;
            incedence[from][number]=value;
        }
        System.out.println("Матриця інцедентності:");
        System.out.println();
        System.out.print("  ");
        for (int i = 1; i <= M; i++)
            System.out.print(i + " ");
        System.out.println();

        for (int i = 1; i <= N; i++)
        {
            System.out.print(i + " ");
            for (int j = 1; j <= M; j++)
                System.out.print(incedence[i][j] + " ");
            System.out.println();
        }

        sc.close();
    }

}
