import java.util.Arrays;

public class MinHeap {

    /**
     * A min heap node that stores an element and its priority.
     */
    class Node {
        HeapElement value;
        int priority;

        public Node(HeapElement value, int priority) {
            this.value = value;
            this.priority = priority;
        }
    }

    private Node[] array;
    private int size;

    /**
     * Initialize the min heap.
     */
    public MinHeap() {
        // start with space for ten strings
        this.array = new Node[10];
        this.size = 0;
    }

    // UTILITY METHODS

    /**
     * Double the size of the Node array.
     */
    private void resize() {
        Node[] newArray = new Node[2 * this.array.length];
        for (int i = 0; i < this.array.length; i++) {
            newArray[i] = this.array[i];
        }
        this.array = newArray;
    }

    /**
     * Calculate the index of the parent node.
     *
     * This method assumes the child index is valid,
     * and does not need to perform error checking.
     *
     * @param index The index of the child node.
     */
    private int parent(int index) {
        return (index - 1) / 2;
    }

    /**
     * Calculate the index of the left child.
     *
     * This method assumes the parent index is valid,
     * and does not need to perform error checking.
     *
     * @param index The index of the parent node.
     * @return The index of the left child node.
     */
    private int leftChild(int index) {
        return 2 * index + 1;
    }

    /**
     * Calculate the index of the right child.
     *
     * This method assumes the parent index is valid,
     * and does not need to perform error checking.
     *
     * @param index The index of the parent node.
     * @return The index of the right child node.
     */
    private int rightChild(int index) {
        return 2 * index + 2;
    }

    /**
     * Swap the array contents of the given indices.
     *
     * @param index1 The first index.
     * @param index2 The second index.
     */
    private void swap(int index1, int index2) {
        Node temp = array[index1];
        array[index1] = array[index2];
        array[index2] = temp;
    }

    // ADD

    /**
     * Add an element to the min heap.
     *
     * @param element The element to add.
     * @param priority The priority of the element.
     */
    public void add(HeapElement element, int priority) {
        // Resize if needed
        if (size >= array.length) {
            resize();
        }
        // Create a new node
        Node newNode = new Node(element, priority);
        // Put the new node at the end of the array
        array[size] = newNode;
        size++;
        // Percolate up
        int index = size - 1;
        while (index > 0 && array[index].priority < array[parent(index)].priority) {
            swap(index, parent(index));
            index = parent(index);
        }
    }

    // POLL

    /**
     * Remove and return the element with the lowest priority number.
     *
     * @return The HeapElement with the lowest priority number.
     */
    public HeapElement poll() {
        if (size == 0) {
            return null;
        }
        // Save the root value to return later
        HeapElement rootValue = array[0].value;
        // Replace the root with the last element
        array[0] = array[size - 1];
        size--;
        // Percolate down
        int index = 0;
        while (true) {
            int leftChildIndex = leftChild(index);
            int rightChildIndex = rightChild(index);
            int minIndex = index;
            // Find the minimum child
            if (leftChildIndex < size && array[leftChildIndex].priority < array[minIndex].priority) {
                minIndex = leftChildIndex;
            }
            if (rightChildIndex < size && array[rightChildIndex].priority < array[minIndex].priority) {
                minIndex = rightChildIndex;
            }
            // Break if the current node is smaller than both children
            if (minIndex == index) {
                break;
            }
            // Swap with the minimum child
            swap(index, minIndex);
            index = minIndex;
        }
        return rootValue;
    }

    // OTHER HEAP METHODS

    /**
     * Get the size of the min heap.
     *
     * @return The size of the min heap.
     */
    public int size() {
        return this.size;
    }

    /**
     * Return (but not remove) the element with the lowest priority number.
     *
     * @return The HeapElement with the lowest priority number.
     */
    public HeapElement peek() {
        return this.array[0].value;
    }

    // DEBUG METHODS

    /**
     * Print the array of the min heap, as is.
     */
    public void printArray() {
        if (this.size == 0) {
            System.out.println("{}");
        }
        String result = "{" + this.array[0].value;
        for (int i = 1; i < this.size; i++) {
            result += ", " + this.array[i].value;
        }
        System.out.println(result + "}");

    }

    /**
     * Print the min heap as a binary tree.
     */
    public void printTree() {
        this.printTree(0, "");
    }

    private void printTree(int index, String indent) {
        if (index >= this.size) {
            return;
        }
        this.printTree(this.rightChild(index), indent + "    ");
        System.out.println(indent + this.array[index].value);
        this.printTree(this.leftChild(index), indent + "    ");
    }

    // MAIN

    public static void main(String[] args) {
        // create the heap
        MinHeap heap = new MinHeap();
        // add some numbers
        int[] numbers = {56, 28, 7, 5, 51, 16, 79, 83, 97, 37, 75, 69, 24, 90};
        for (int i = 0; i < numbers.length; i++) {
            String numberString = new Integer(numbers[i]).toString();
            heap.add(new HeapElement(numberString), numbers[i]);
        }
        // print the heap as an array
        heap.printArray();
        // print the heap as a binary tree
        heap.printTree();
        // poll everything out
        for (int i = 0; i < numbers.length; i++) {
            System.out.println(heap.poll());
        }
    }

}
