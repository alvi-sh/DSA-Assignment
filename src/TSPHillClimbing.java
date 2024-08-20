import java.util.Arrays;
import java.util.Random;

public class TSPHillClimbing {

    // Function to calculate the total distance of the path
    public static int calculateTotalDistance(int[] path, int[][] distanceMatrix) {
        int totalDistance = 0;
        for (int i = 0; i < path.length - 1; i++) {
            totalDistance += distanceMatrix[path[i]][path[i + 1]];
        }
        // Add the distance from the last city back to the first
        totalDistance += distanceMatrix[path[path.length - 1]][path[0]];
        return totalDistance;
    }

    // Function to generate a random path
    public static int[] generateRandomPath(int numCities) {
        int[] path = new int[numCities];
        for (int i = 0; i < numCities; i++) {
            path[i] = i;
        }
        // Shuffle the array to create a random path
        Random rand = new Random();
        for (int i = 0; i < numCities; i++) {
            int randomIndexToSwap = rand.nextInt(numCities);
            int temp = path[randomIndexToSwap];
            path[randomIndexToSwap] = path[i];
            path[i] = temp;
        }
        return path;
    }

    // Hill Climbing algorithm
    public static int[] hillClimbing(int[][] distanceMatrix) {
        int numCities = distanceMatrix.length;
        int[] currentSolution = generateRandomPath(numCities);
        int currentDistance = calculateTotalDistance(currentSolution, distanceMatrix);

        boolean improvement = true;

        while (improvement) {
            improvement = false;
            for (int i = 0; i < numCities - 1; i++) {
                for (int j = i + 1; j < numCities; j++) {
                    // Swap cities i and j
                    int[] newSolution = Arrays.copyOf(currentSolution, currentSolution.length);
                    int temp = newSolution[i];
                    newSolution[i] = newSolution[j];
                    newSolution[j] = temp;

                    int newDistance = calculateTotalDistance(newSolution, distanceMatrix);

                    // Check if the new solution is better
                    if (newDistance < currentDistance) {
                        currentSolution = newSolution;
                        currentDistance = newDistance;
                        improvement = true;
                    }
                }
            }
        }
        return currentSolution;
    }

    public static void main(String[] args) {
        int[][] distanceMatrix = {
                {0, 29, 20, 21},
                {29, 0, 15, 17},
                {20, 15, 0, 28},
                {21, 17, 28, 0}
        };

        int[] solution = hillClimbing(distanceMatrix);
        int distance = calculateTotalDistance(solution, distanceMatrix);

        System.out.println("Best path found: " + Arrays.toString(solution));
        System.out.println("Total distance: " + distance);
    }
}
