import java.util.LinkedList;
import java.util.List;

public class BusService {

    public static List<Integer> optimizeBoardingProcess(List<Integer> head, int k) {
        List<Integer> result = new LinkedList<>();

        for (int i = 0; i < head.size(); i += k) {
            int end = Math.min(i + k, head.size());
            for (int j = end - 1; j >= i; j--) {
                result.add(head.get(j));
            }
        }

        return result;
    }

    public static void main(String[] args) {
        List<Integer> head1 = List.of(1, 2, 3, 4, 5);
        int k1 = 2;
        System.out.println(optimizeBoardingProcess(head1, k1));

        List<Integer> head2 = List.of(1, 2, 3, 4, 5);
        int k2 = 3;
        System.out.println(optimizeBoardingProcess(head2, k2));
    }
}
