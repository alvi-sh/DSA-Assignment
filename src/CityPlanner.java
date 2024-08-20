import java.util.*;

class CityPlanner {
    class Edge {
        int node, weight;
        Edge(int node, int weight) {
            this.node = node;
            this.weight = weight;
        }
    }

    public int[][] modifyRoads(int n, int[][] roads, int source, int destination, int targetTime) {
        List<Edge>[] graph = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            graph[i] = new ArrayList<>();
        }

        List<int[]> underConstruction = new ArrayList<>();
        for (int[] road : roads) {
            if (road[2] == -1) {
                underConstruction.add(road);
            } else {
                graph[road[0]].add(new Edge(road[1], road[2]));
                graph[road[1]].add(new Edge(road[0], road[2]));
            }
        }

        int currentShortest = dijkstra(graph, n, source, destination);
        if (currentShortest == targetTime) {
            return roads;
        }

        int low = 1, high = 2 * (int)1e9;
        for (int[] road : underConstruction) {
            int minWeight = low;
            int maxWeight = high;
            road[2] = binarySearchForWeight(graph, n, road, source, destination, targetTime, minWeight, maxWeight);
        }

        return roads;
    }

    private int binarySearchForWeight(List<Edge>[] graph, int n, int[] road, int source, int destination, int targetTime, int low, int high) {
        int res = -1;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            addEdge(graph, road, mid);
            int shortestTime = dijkstra(graph, n, source, destination);
            if (shortestTime == targetTime) {
                return mid;
            } else if (shortestTime < targetTime) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
            removeEdge(graph, road);
        }
        return res;
    }

    private void addEdge(List<Edge>[] graph, int[] road, int weight) {
        graph[road[0]].add(new Edge(road[1], weight));
        graph[road[1]].add(new Edge(road[0], weight));
    }

    private void removeEdge(List<Edge>[] graph, int[] road) {
        graph[road[0]].removeIf(edge -> edge.node == road[1]);
        graph[road[1]].removeIf(edge -> edge.node == road[0]);
    }

    private int dijkstra(List<Edge>[] graph, int n, int source, int destination) {
        int[] dist = new int[n];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[source] = 0;
        PriorityQueue<Edge> pq = new PriorityQueue<>(Comparator.comparingInt(e -> e.weight));
        pq.offer(new Edge(source, 0));

        while (!pq.isEmpty()) {
            Edge curr = pq.poll();
            if (curr.node == destination) {
                return curr.weight;
            }
            for (Edge edge : graph[curr.node]) {
                int newDist = curr.weight + edge.weight;
                if (newDist < dist[edge.node]) {
                    dist[edge.node] = newDist;
                    pq.offer(new Edge(edge.node, newDist));
                }
            }
        }
        return dist[destination];
    }

    public static void main(String[] args) {
        CityPlanner planner = new CityPlanner();
        int n = 5;
        int[][] roads = {{4, 1, -1}, {2, 0, -1}, {0, 3, -1}, {4, 3, -1}};
        int source = 0;
        int destination = 1;
        int targetTime = 5;

        int[][] modifiedRoads = planner.modifyRoads(n, roads, source, destination, targetTime);

        System.out.println("Modified Roads:");
        for (int[] road : modifiedRoads) {
            System.out.println(Arrays.toString(road));
        }
    }
}
