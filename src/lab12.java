import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.*;

 class lab12 {
     public static int N;
     public static int M;
     public static int[] Mas1;
     public static int[] Mas2;
     public static int[] Mas3;
     public static int[][] Matrix;
    public static long mst(List<Edge>[] edges, int[] pred) {
        int n = edges.length;
        Arrays.fill(pred, -1);
        boolean[] vis = new boolean[n];
        int[] prio = new int[n];
        Arrays.fill(prio, Integer.MAX_VALUE);
        prio[0] = 1;        Queue<QItem> q = new PriorityQueue<QItem>();
        q.add(new QItem(0, 0));
        long res = 0;
        while (!q.isEmpty()) {
            QItem cur = q.poll();
            int u = cur.u;
            if (vis[u])
                continue;

            vis[u] = true;
            res += cur.prio;
            for (Edge e : edges[u]) {
                int v = e.t;
                if (!vis[v] && prio[v]>e.cost)
                {
                    prio[v] = e.cost;
                    pred[v] = u;
                    q.add(new QItem(prio[v], v));
                }
            }
        }
        return res;
    }

    static class Edge {
        int t, cost;

        public Edge(int t, int cost) {
            this.t = t;
            this.cost = cost;
        }
    }

    static class QItem implements Comparable<QItem> {
        int prio;
        int u;

        public QItem(int prio, int u) {
            this.prio = prio;
            this.u = u;
        }

        public int compareTo(QItem q) {
            return prio < q.prio ? -1 : prio > q.prio ? 1 : 0;
        }
    }
    public static void main(String[] args) throws FileNotFoundException {

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
        Matrix = new int[N][N];
        for (int i = 0; i < M; i++){

            Matrix[Mas1[i]-1][Mas2[i]-1] = Mas3[i];

            Matrix[Mas2[i]-1][Mas1[i]-1] = Mas3[i];

        }
        int n = Matrix.length;

        List<Edge>[] edges = new List[n];
        for (int i = 0; i < n; i++) {
            edges[i] = new ArrayList<Edge>();
            for (int j = 0; j < n; j++) {
                if (Matrix[i][j] != 0) {
                    edges[i].add(new Edge(j, Matrix[i][j]));
                }      }    }
        long res = mst(edges, new int[n]);
        System.out.println("result");
        System.out.println(res);
    }
}