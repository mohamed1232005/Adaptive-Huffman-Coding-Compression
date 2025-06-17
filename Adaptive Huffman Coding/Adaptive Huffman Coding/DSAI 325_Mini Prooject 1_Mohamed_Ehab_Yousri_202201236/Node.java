// Defines a single node in the Huffman Tree:
// Can be a leaf (with a character), internal node, or NYT node.

public class Node implements Comparable<Node> {
    public char symbol;
    public int count;
    public int number;
    public Node left, right, parent;
    public boolean isNYT;

    // Constructor
    // Builds a new node
    public Node(char symbol, int count, int number, boolean isNYT) {
        this.symbol = symbol;
        this.count = count;
        this.number = number;
        this.isNYT = isNYT;
        this.left = null;
        this.right = null;
        this.parent = null;
    }

    // This helps the decoder and visualizer decide whether to decode, insert, or
    // display a symbol.
    public boolean isLeaf() {
        return left == null && right == null;
    }

    // Compares this node to another by frequency count.
    @Override
    public int compareTo(Node other) {
        return this.count - other.count;
    }

    // Debug-Friendly Output:
    @Override
    public String toString() {
        if (isNYT)
            return "[NYT] Count: " + count + ", Number: " + number;
        return "[Symbol: " + symbol + "] Count: " + count + ", Number: " + number;
    }
}