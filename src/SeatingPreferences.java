public class SeatingPreferences {

    public static boolean canFindSeating(int[] nums, int indexDiff, int valueDiff) {
        int n = nums.length;

        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j <= i + indexDiff && j < n; j++) {
                if (Math.abs(nums[i] - nums[j]) <= valueDiff) {
                    return true;
                }
            }
        }

        return false;
    }

    public static void main(String[] args) {
        int[] nums = {2, 3, 5, 4, 9};
        int indexDiff = 2;
        int valueDiff = 1;

        boolean result = canFindSeating(nums, indexDiff, valueDiff);
        System.out.println(result);
    }
}
