class Graph {
    private int V; 
    private int[][] graph; 

    Graph(int V) {
        this.V = V;
        graph = new int[V][V];
    }

    
    void addEdge(int src, int dest, int weight) {
        graph[src][dest] = weight;
        graph[dest][src] = weight; 
    }

    
    int minKey(int[] key, boolean[] mstSet) {
        int min = Integer.MAX_VALUE;
        int minIndex = -1;

        for (int v = 0; v < V; v++) {
            if (!mstSet[v] && key[v] < min) {
                min = key[v];
                minIndex = v;
            }
        }

        return minIndex;
    }

   
    void printMST(int[] parent, int[] key) {
        System.out.println("\nMinimum Spanning Tree using Prim's Algorithm:");
        int totalCost = 0;
        for (int i = 1; i < V; i++) {
            totalCost += key[i];
            System.out.println(parent[i] + " - " + i + " with weight: " + key[i]);
        }
        System.out.println("Total Cost of Minimum Spanning Tree: " + totalCost);
    }

    
    void primsAlgo() {
        
        int[] key = new int[V];
        int[] parent = new int[V];

        
        boolean[] mstvisit = new boolean[V];

       
        for (int i = 0; i < V; i++) {
            key[i] = Integer.MAX_VALUE;
            parent[i] = -1;
        }

        
        int startVertex = 0;
        key[startVertex] = 0;

        for (int count = 0; count < V - 1; count++) {
           
            int u = minKey(key, mstvisit);
            mstvisit[u] = true;

            
            if (parent[u] != -1) {
                System.out.println("Selected Edge: " + parent[u] + " -> " + u + " with weight: " + key[u]);
            }

            
            for (int v = 0; v < V; v++) {
                if (graph[u][v] != 0 && !mstvisit[v] && graph[u][v] < key[v]) {
                    parent[v] = u;
                    key[v] = graph[u][v];
                }
            }
        }

        
        printMST(parent, key);
    }

    public static void main(String[] args) {
        int V = 7; 
        Graph graph = new Graph(V);
        
        
        graph.addEdge(0, 1, 28);
        graph.addEdge(0, 5, 10);
        graph.addEdge(1, 2, 16);
        graph.addEdge(1, 6, 14);
        graph.addEdge(2, 3, 12);
        graph.addEdge(3, 4, 22);
        graph.addEdge(3, 6, 18);
        graph.addEdge(4, 5, 25);
        graph.addEdge(4, 6, 24);

        
        graph.primsAlgo();
    }
}
