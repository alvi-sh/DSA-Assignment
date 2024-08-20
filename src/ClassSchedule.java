import java.util.*;

class ClassSchedule {
    public static int mostUsedClassroom(int n, int[][] classes) {
        Arrays.sort(classes, (a, b) -> {
            if (a[0] == b[0]) {
                return b[2] - a[2];
            }
            return a[0] - b[0];
        });

        PriorityQueue<int[]> roomHeap = new PriorityQueue<>((a, b) -> a[0] - b[0]);

        int[] roomCount = new int[n];

        for (int[] classInfo : classes) {
            int startTime = classInfo[0];
            int endTime = classInfo[1];

            while (!roomHeap.isEmpty() && roomHeap.peek()[0] <= startTime) {
                roomHeap.poll();
            }

            int assignedRoom;
            if (roomHeap.size() < n) {
                assignedRoom = roomHeap.size();
                roomHeap.offer(new int[]{endTime, assignedRoom});
            } else {
                int[] earliestRoom = roomHeap.poll();
                int delayedStart = earliestRoom[0];
                int delayedEnd = delayedStart + (endTime - startTime);
                assignedRoom = earliestRoom[1];
                roomHeap.offer(new int[]{delayedEnd, assignedRoom});
            }
            roomCount[assignedRoom]++;
        }

        int mostUsedRoom = 0;
        for (int i = 1; i < n; i++) {
            if (roomCount[i] > roomCount[mostUsedRoom] ||
                    (roomCount[i] == roomCount[mostUsedRoom] && i < mostUsedRoom)) {
                mostUsedRoom = i;
            }
        }

        return mostUsedRoom;
    }

    public static void main(String[] args) {
        int n1 = 2;
        int[][] classes1 = {{0, 10, 30}, {1, 5, 20}, {2, 7, 25}, {3, 4, 15}};
        System.out.println(mostUsedClassroom(n1, classes1));

        int n2 = 3;
        int[][] classes2 = {{1, 20, 50}, {2, 10, 45}, {3, 5, 30}, {4, 9, 40}, {6, 8, 25}};
        System.out.println(mostUsedClassroom(n2, classes2));
    }
}
