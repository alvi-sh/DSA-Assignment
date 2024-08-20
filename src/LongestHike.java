public class LongestHike {
    public static int longestHike(int[] nums, int k) {
        int maxLen = 0;
        int start = 0;

        for (int end = 1; end < nums.length; end++) {
            if (nums[end] - nums[end - 1] > k) {
                // If the elevation gain exceeds the limit, reset the start point
                start = end;
            }
            // Update the maximum length of the hike
            maxLen = Math.max(maxLen, end - start + 1);
        }

        return maxLen;
    }

    public static void main(String[] args) {
        int[] trail = {4, 2, 1, 4, 3, 4, 5, 8, 15};
        int k = 3;

        int result = longestHike(trail, k);
        System.out.println("Longest hike length: " + result); // Output: 5
    }
}
