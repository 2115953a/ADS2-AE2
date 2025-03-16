public class RopeConnector {
    public static int calculateMinimumCost(int[] ropeLengths) {
        int cost = 0;
        int length1, length2;
        int join;
        MinPriorityQueue ropes = MinPriorityQueue.buildHeap(ropeLengths);
        System.out.println(ropes);

        while (ropes.size() > 1) {
            length1 = ropes.extractMin();
            length2 = ropes.extractMin();

            join = length1 + length2;
            cost += join;
            ropes.insert(join);

            System.out.println(ropes);
        }

        return cost;
    }

    public static void main(String[] args) {
        int[] ropeLengths = {4, 8, 3, 1, 6, 9, 12, 7, 2};
        int totalCost = calculateMinimumCost(ropeLengths);

        System.out.println("\nTotal cost: " + totalCost);
    }
}
