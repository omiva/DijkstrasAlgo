package com.company;

import java.util.*;

class Main
{

    int source, dest, weight;

    public static void main(String[] args)
    {
        List<Main> edges = Arrays.asList(

                new Main(0, 1, 10), new Main(0, 4, 3),

                new Main(1, 2, 2), new Main(1, 4, 4),

                new Main(2, 3, 9), new Main(3, 2, 7),

                new Main(4, 1, 1), new Main(4, 2, 8),

                new Main(4, 3, 2)

        );

        final int N = 5;
        Graph graph = new Graph(edges, N);
        int source = 0;
        shortestPath(graph, source, N);
    }
    public static void shortestPath(Graph graph, int source, int N)
    {
        PriorityQueue<Node> minHeap = new PriorityQueue<>(Comparator.comparingInt(node -> node.weight));
        minHeap.add(new Node(source, 0));
        List<Integer> dist = new ArrayList<>(Collections.nCopies(N, Integer.MAX_VALUE));
        dist.set(source, 0);

        boolean[] done = new boolean[N];
        done[source] = true;

        int[] prev = new int[N];
        prev[source] = -1;
        List<Integer> route = new ArrayList<>();

        while (!minHeap.isEmpty())
        {
            Node node = minHeap.poll();
            int u = node.vertex;

            for (Main edge: graph.adjList.get(u))
            {
                int v = edge.dest;
                int weight = edge.weight;

                if (!done[v] && (dist.get(u) + weight) < dist.get(v))
                {
                    dist.set(v, dist.get(u) + weight);
                    prev[v] = u;
                    minHeap.add(new Node(v, dist.get(v)));
                }
            }
            done[u] = true;
        }
        for (int i = 1; i < N; ++i)
        {
            if (i != source && dist.get(i) != Integer.MAX_VALUE)
            {
                getRoute(prev, i, route);
                System.out.printf("Path (%d -> %d): Minimum Cost = %d and Route is %s\n", source, i, dist.get(i), route);
                route.clear();
            }
        }
    }
    public Main(int source, int dest, int weight)
    {
        this.source = source;
        this.dest = dest;
        this.weight = weight;
    }
    private static void getRoute(int[] prev, int i, List<Integer> route)
    {
        if (i >= 0)
        {
            getRoute(prev, prev[i], route);
            route.add(i);
        }
    }
}

class Node
{
    int vertex, weight;
    public Node(int vertex, int weight)
    {
        this.vertex = vertex;
        this.weight = weight;
    }
}
class Graph
{
    List<List<Main>> adjList;
    Graph(List<Main> edges, int N)
    {
        adjList = new ArrayList<>(N);
        for (int i = 0; i < N; i++)
        {
            adjList.add(i, new ArrayList<>());
        }
        for (Main edge: edges)
        {
            adjList.get(edge.source).add(edge);
        }
    }
}