import java.util.Arrays;
// Please note - the implementation does NOT use this import, only used to print tests to screen.

public class DynamicSet {
    public static class Node {
        int data;
        Node left, right;

        public Node(int data) {
            this.data = data;
        }
    }

    private int size;
    private Node head;

    public DynamicSet() {
        this.size = 0;
    }


    /* Dynamic Set, Main Operations */
    // Adds element x to S, if it is not present already
    public void add(int element) {
        head = add(head, element);
    }

    // Helper function to add element x to S, if it is not present already
    private Node add(Node node, int element) {
        if (node == null) {                           // If node empty, return new node
            size++;
            return new Node(element);
        }

        if (element == node.data) {                   // Prevent adding existing elements. Elements must be distinct.
            return node;
        }

        if (element < node.data) {                    // Recursively add new node
            node.left = add(node.left, element);
        } else {
            node.right = add(node.right, element);
        }

        return node;
    }

    // Remove element from node, if it is present
    public void remove(int element) {
        if (!isElement(element)) {
            System.out.println("Element not in Set.");
        } else {
            size--;
            head = remove(head, element);
        }
    }

    // Helper method to remove element from node, if it is present
    private Node remove(Node node, int element) {
        if (node == null) {
            return null;
        }

        if (element < node.data) {
            node.left = remove(node.left, element);
        } else if (element > node.data) {
            node.right = remove(node.right, element);
        } else {
            node = deleteNode(node);
        }
        return node;
    }

    // Checks whether element x is in set S
    public boolean isElement(int element) {
        return isElement(head, element);
    }

    // Helper function to check whether element x is in set S
    private boolean isElement(Node node, int element) {
        if (node == null) {
            return false;
        } else if (element == node.data) {
            return true;
        }
        if (element < node.data) {
            return isElement(node.left, element);
        } else {
            return isElement(node.right, element);
        }
    }

    // Check whether set S has no elements
    public boolean setEmpty() {
        return size == 0;
    }

    // Return the number of elements of set S
    public int setSize() {
        return size;
    }


    /* Dynamic Set, Set Operations */
    // naïve definition of UNION(S(a),T(b)) that takes all elements and inserts one-by-one
    public static DynamicSet union(DynamicSet a, DynamicSet b) {
        return mergeInefficient(a.head, b.head);
    }

    // Efficient definition of UNION(S(a),T(b)), flattens trees into arrays before combining.
    // Only possible because arrays are returned sorted from DynamicSet function 'toArray()'.
    public static DynamicSet unionEfficient(DynamicSet a, DynamicSet b) {
        return mergeEfficient(toArray(a.head), toArray(b.head));
    }

    // return the intersection of sets S(a) and T(b)
    public static DynamicSet intersection(DynamicSet a, DynamicSet b) {
        DynamicSet result = new DynamicSet();

        int[] arrayA = toArray(a.head);
        int[] arrayB = toArray(b.head);
        for (int i : arrayA) {
            for (int j : arrayB) {
                if (i == j) {
                    result.add(i);  // Note, this adds unique elements.
                    break;
                }
            }
        }

        return result;
    }

    // returns the difference of sets S(a) and T(b)
    public static DynamicSet difference(DynamicSet a, DynamicSet b) {
        DynamicSet result = new DynamicSet();

        int[] arrayA = toArray(a.head);
        int[] arrayB = toArray(b.head);
        for (int i : arrayA) {
            result.add(i);
            }
        for (int j : arrayB) {
            if (result.isElement(j)) {
                result.remove(j);
            }
        }

        return result;
    }

    // check whether set S (a) is a subset of set T (b)
    public static boolean subset(DynamicSet a, DynamicSet b) {
        int[] arrayB = toArray(b.head);
        for (int i : arrayB) {
            if (!a.isElement(i)) {
                return false;
            }
        }
        return true;
    }

    /* Additional functions to support Dynamic Set below here */
    // Helper function to delete nodes for 'remove'
    private Node deleteNode(Node node) {
        // If no children or only one child
        if (node.left == null) {
            return node.right;
        } else if (node.right == null) {
            return node.left;
        }

        // Two children, find the smallest element in the right subtree to succeed/replace deleted node
        Node successor = node.right;
        while (successor.left != null) {
            successor = successor.left;
        }
        node.data = successor.data;                       // Replace the node's data with the successor's data


        node.right = remove(node.right, successor.data);  // Delete the smallest element, as found

        return node;
    }

    // Converts a Node to an Array
    public static int[] toArray(Node head) {
        int treeSize = countNodes(head);
        int[] result = new int[treeSize];
        int[] index = new int[1];      // Use array as reference, rather than integer, as all recursive calls can see it
        toArray(head, result, index);
        return result;
    }

    // Helper function for toArray()
    private static void toArray(Node node, int[] array, int[] index) {
        if (node != null) {
            toArray(node.left, array, index);
            array[index[0]++] = node.data;
            toArray(node.right, array,index);
        }
    }

    private static int countNodes(Node node) {
        if (node == null) return 0;
        return 1 + countNodes(node.left) + countNodes(node.right);
    }

    public static DynamicSet mergeEfficient(int[] array1, int[] array2) {
        int length1 = array1.length;
        int length2 = array2.length;
        int[] temp = new int[length1 + length2];

        int index1 = 0, index2 = 0, resultIndex = 0;

        // Merge until end of one of arrays
        while (index1 < length1 && index2 < length2) {
            if (array1[index1] < array2[index2]) {
                if (resultIndex == 0 || array1[index1] != temp[resultIndex-1]) {  // This additional code prevents duplicates
                    temp[resultIndex++] = array1[index1];
                }
                index1++;
            } else if (array1[index1] > array2[index2]) {
                if (resultIndex == 0 || array2[index2] != temp[resultIndex-1]) {
                    temp[resultIndex++] = array2[index2];
                }
                index2++;
            } else {
                // Equal elements, add to array only once
                if (resultIndex == 0 || array1[index1] != temp[resultIndex-1]) {
                    temp[resultIndex++] = array1[index1];
                }
                index1++;
                index2++;
            }
        }

        // If one array completes, copy all leftover elements from the other
        while (index1 < length1) {
            if (resultIndex == 0 || array1[index1] != temp[resultIndex-1]) {
                temp[resultIndex++] = array1[index1];
            }
            index1++;
        }

        while (index2 < length2) {
            if (resultIndex == 0 || array2[index2] != temp[resultIndex-1]) {
                temp[resultIndex++] = array2[index2];
            }
            index2++;
        }

        DynamicSet dynamicSet = new DynamicSet();
        for (int i: temp) {
            dynamicSet.add(i);
        }

        return dynamicSet;
    }

    public static DynamicSet mergeInefficient(Node a, Node b) {
        DynamicSet result = new DynamicSet();

        int[] arrayA = toArray(a);
        int[] arrayB = toArray(b);
        for (int i : arrayA) {
            result.add(i);
        }
        for (int j : arrayB) {
            result.add(j);
        }

        return result;
    }

    @Override
    public String toString() {
        return Arrays.toString(toArray(this.head));
    }

    public static void main(String[] args) {
        DynamicSet set1 = new DynamicSet();
        DynamicSet set2 = new DynamicSet();
        int[] arrayA = {1, 2, 3, 4, 4, 5, 5, 6, 7};
        int[] arrayB = {6, 7, 7, 8, 8, 9};
        for (int j : arrayA) {
            set1.add(j);
        }
        for (int k : arrayB) {
            set2.add(k);
        }

        System.out.println(set1);
        System.out.println(set2);

        DynamicSet union = union(set1, set2);
        DynamicSet intersection = intersection(set1, set2);
        DynamicSet difference = difference(set2, set1);
        System.out.println(union);
        System.out.println(intersection);
        System.out.println(difference);
        System.out.println(subset(union, intersection));
    }
}
