import java.util.ArrayList;

public class MinPriorityQueue {
    private final ArrayList<Integer> heap;

    public MinPriorityQueue() {
        heap = new ArrayList<>();
    }

    // Returns size of the heap
    public int size() {
        return heap.size();
    }

    // Insert the element a into the queue
    public void insert(int a) {
        heap.add(a);
        heapifyUp(heap.size() - 1);
    }

    // Returns the element of the queue with the smallest value/key. Equivalent to 'peek'.
    // By design, this will always be the first element.
    public int min() {
        if (heap.isEmpty()) {
            throw new IllegalStateException("Queue is empty.");
        }
        return heap.getFirst();
    }

    // Removes and returns the element of the queue with the smallest value/key
    public int extractMin() {
        if (heap.isEmpty()) {
            throw new IllegalStateException("Queue is empty.");
        }

        int min = heap.getFirst();
        int lastLength = heap.removeLast();
        if (!heap.isEmpty()) {
            heap.set(0, lastLength);
            heapifyDown(0);
        }

        return min;
    }

    // Swap two pairs in the heap
    private void swap(int i, int j) {
        int temp = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, temp);
    }

    // Construct Min-Heap from an unsorted array
    public static MinPriorityQueue buildHeap(int[] array) {
        MinPriorityQueue new_pq = new MinPriorityQueue();

        for (int j : array) {
            new_pq.heap.add(j);
            new_pq.heapifyUp(new_pq.heap.size() - 1);
        }
        return new_pq;
    }

    // 'Bubbles up' an element to its correct position in the heap
    private void heapifyUp(int i) {
        while (i> 0) {
            int parent = (i - 1) / 2;
            if (heap.get(parent) > heap.get(i)) {
                swap(i, parent);
                i = parent;
            } else {
                break;
            }
        }
    }

    // 'Sinks down' an element to its correct position
    private void heapifyDown(int i) {
        int left, right, smallest;
        while (true) {
            left = 2 * i + 1;
            right = 2 * i + 2;
            smallest = i;

            if (left < heap.size() && heap.get(left) < heap.get(smallest)) {
                smallest = left;
            }

            if (right < heap.size() && heap.get(right) < heap.get(smallest)) {
                smallest = right;
            }

            if (smallest != i) {
                swap(i, smallest);
                i = smallest;
            } else {
                break;
            }
        }
    }

    @Override
    public String toString() {
        return heap.toString();
    }
}


