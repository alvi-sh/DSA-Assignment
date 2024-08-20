import java.util.*;

public class FriendshipRestrictions {

    static class UnionFind {
        int[] parent;
        int[] rank;

        public UnionFind(int size) {
            parent = new int[size];
            rank = new int[size];
            for (int i = 0; i < size; i++) {
                parent[i] = i;
                rank[i] = 1;
            }
        }

        public int find(int x) {
            if (parent[x] != x) {
                parent[x] = find(parent[x]);
            }
            return parent[x];
        }

        public void union(int x, int y) {
            int rootX = find(x);
            int rootY = find(y);
            if (rootX != rootY) {
                if (rank[rootX] > rank[rootY]) {
                    parent[rootY] = rootX;
                } else if (rank[rootX] < rank[rootY]) {
                    parent[rootX] = rootY;
                } else {
                    parent[rootY] = rootX;
                    rank[rootX]++;
                }
            }
        }

        public boolean connected(int x, int y) {
            return find(x) == find(y);
        }
    }

    public static List<String> processFriendRequests(int n, int[][] restrictions, int[][] requests) {
        UnionFind uf = new UnionFind(n);
        List<String> result = new ArrayList<>();

        for (int[] request : requests) {
            int houseA = request[0];
            int houseB = request[1];
            boolean canBeFriends = true;

            for (int[] restriction : restrictions) {
                int restrictedA = restriction[0];
                int restrictedB = restriction[1];

                if ((uf.connected(houseA, restrictedA) && uf.connected(houseB, restrictedB)) ||
                        (uf.connected(houseA, restrictedB) && uf.connected(houseB, restrictedA))) {
                    canBeFriends = false;
                    break;
                }
            }

            if (canBeFriends) {
                uf.union(houseA, houseB);
                result.add("approved");
            } else {
                result.add("denied");
            }
        }

        return result;
    }

    public static void main(String[] args) {
        int n = 5;
        int[][] restrictions = {{0, 1}, {1, 2}, {2, 3}};
        int[][] requests = {{0, 4}, {1, 2}, {3, 1}, {3, 4}};

        List<String> outcome = processFriendRequests(n, restrictions, requests);
        System.out.println(outcome);
    }
}
