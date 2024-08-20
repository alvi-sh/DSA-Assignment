class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode(int val) {
        this.val = val;
        this.left = null;
        this.right = null;
    }
}

class Result {
    int sum;
    int min;
    int max;
    boolean isBST;

    Result(int sum, int min, int max, boolean isBST) {
        this.sum = sum;
        this.min = min;
        this.max = max;
        this.isBST = isBST;
    }
}

public class MagicalGrove {

    private int maxSum = 0;

    public int findLargestMagicalGrove(TreeNode root) {
        postOrder(root);
        return maxSum;
    }

    private Result postOrder(TreeNode node) {
        if (node == null) {
            return new Result(0, Integer.MAX_VALUE, Integer.MIN_VALUE, true);
        }

        Result left = postOrder(node.left);
        Result right = postOrder(node.right);

        if (left.isBST && right.isBST && node.val > left.max && node.val < right.min) {
            int currentSum = node.val + left.sum + right.sum;
            maxSum = Math.max(maxSum, currentSum);
            return new Result(currentSum, Math.min(node.val, left.min), Math.max(node.val, right.max), true);
        } else {
            return new Result(0, 0, 0, false);
        }
    }

    public static void main(String[] args) {
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(4);
        root.right = new TreeNode(3);
        root.left.left = new TreeNode(2);
        root.left.right = new TreeNode(4);
        root.right.left = new TreeNode(2);
        root.right.right = new TreeNode(5);
        root.right.right.left = new TreeNode(4);
        root.right.right.right = new TreeNode(6);

        MagicalGrove mg = new MagicalGrove();
        System.out.println("Largest Magical Grove Sum: " + mg.findLargestMagicalGrove(root));
    }
}
