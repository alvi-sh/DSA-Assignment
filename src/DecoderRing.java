import javax.swing.tree.TreeNode;

public class DecoderRing {

    public static void main(String[] args) {
        String s = "hello";
        int[][] shifts = {{0, 1, 1}, {2, 3, 0}, {0, 2, 1}};

        String decipheredMessage = decipherMessage(s, shifts);
        System.out.println(decipheredMessage);
    }

    private static String decipherMessage(String s, int[][] shifts) {
        char[] message = s.toCharArray();

        for (int[] shift : shifts) {
            int startDisc = shift[0];
            int endDisc = shift[1];
            int direction = shift[2];

            for (int i = startDisc; i <= endDisc; i++) {
                message[i] = rotateChar(message[i], direction);
            }
        }

        return new String(message);
    }

    private static char rotateChar(char c, int direction) {
        if (direction == 1) { // clockwise
            return (char) ((c - 'a' + 1) % 26 + 'a');
        } else { // Counter-clockwise
            return (char) ((c - 'a' - 1 + 26) % 26 + 'a');
        }
    }
}