import java.util.*;

public class HuffmanTree {
    private Node root;
    private Node NYT;
    private Map<Character, Node> symbolMap;
    private Map<Integer, Node> numberMap;
    // Node number begins at 512 and counts down.
    private int maxNodeNumber = 512;
    private Node lastUpdatedNode;

    public HuffmanTree() {
        // Starts with a single NYT node (root = NYT).
        NYT = new Node('\0', 0, maxNodeNumber--, true);
        root = NYT;
        // Maps initialized to track symbols and node numbers.
        symbolMap = new HashMap<>();
        numberMap = new HashMap<>();
        numberMap.put(NYT.number, NYT);
        lastUpdatedNode = NYT;
    }

    public String encode(char symbol) {
        StringBuilder encoded = new StringBuilder();
        // Checks whether the symbol has been encountered before
        if (!symbolMap.containsKey(symbol)) {
            // The encoder first emits the code for the NYT node
            encoded.append(getCode(NYT));
            // transmit its ASCII binary value (8 bits).
            encoded.append(toBinary(symbol));
            // Inserts the new symbol into the tree
            insert(symbol);
            // GUI visualization: highlights the most recently added/updated node.
            lastUpdatedNode = symbolMap.get(symbol);

            // if symbol already exists in the tree.
        } else {
            // Retrieves the existing node for the symbol.
            Node node = symbolMap.get(symbol);
            // Gets the binary path
            encoded.append(getCode(node));
            // Rebalances the tree:
            // Increments frequency of the symbol.
            // Swaps it with higher-numbered nodes if needed
            update(node);
            lastUpdatedNode = node;
        }

        return encoded.toString();
    }

    // Creates a new NYT node, a leaf for the symbol, and a new internal parent
    // node.
    public void insert(char symbol) {
        Node newNYT = new Node('\0', 0, maxNodeNumber--, true);
        Node symbolNode = new Node(symbol, 1, maxNodeNumber--, false);
        Node internal = new Node('\0', 1, maxNodeNumber--, false);

        // Links the nodes
        internal.left = newNYT;
        internal.right = symbolNode;
        newNYT.parent = internal;
        symbolNode.parent = internal;

        // Insert into Main Tree
        // the first time a symbol is inserted
        // Checks whether the current NYT node is the root of the tree
        if (NYT.parent == null) {
            // The internal node becomes the new root of the tree.
            root = internal;

            // Runs when the NYT node is not the root
        } else {
            // Finds which side (left/right) the old NYT node was on in its parent.

            if (NYT.parent.left == NYT)
                // Replaces the NYT node with the new internal node
                NYT.parent.left = internal;
            else
                NYT.parent.right = internal;
            // assign the parent pointer to the new internal node.
            internal.parent = NYT.parent;
        }

        NYT = newNYT;
        symbolMap.put(symbol, symbolNode);
        numberMap.put(symbolNode.number, symbolNode);
        numberMap.put(newNYT.number, newNYT);
        numberMap.put(internal.number, internal);

        update(internal);
    }

    public void update(Node node) {
        while (node != null) {
            // Finds the node with the highest number:
            // Has the same count as the current node.
            // Comes after it numerically (more recent in the tree structure).
            Node highest = getHighestNodeWithSameCount(node);
            if (highest != null && highest != node && highest != node.parent) {
                swap(node, highest);
            }
            // After swapping (if needed), increment this node’s frequency count by 1.
            node.count++;
            // Move up the tree.
            node = node.parent;
        }
    }

    // Finds the deepest node (higher number) with same count — used in swaps.
    private Node getHighestNodeWithSameCount(Node node) {
        for (int i = 512; i > node.number; i--) {
            Node candidate = numberMap.get(i);
            if (candidate != null && candidate.count == node.count) {
                return candidate;
            }
        }
        return null;
    }

    private void swap(Node a, Node b) {
        // These nodes must not be parent-child
        if (a == b || a == b.parent || b == a.parent)
            return;
        // store the original parents of a and b for reconnection later
        Node aParent = a.parent;
        Node bParent = b.parent;
        // Rewire a Parent to point to b instead of a.
        if (aParent.left == a)
            aParent.left = b;
        else
            aParent.right = b;
        // Rewire bParent to point to a instead of b.
        if (bParent.left == b)
            bParent.left = a;
        else
            bParent.right = a;
        // Update the parent pointers inside the nodes
        a.parent = bParent;
        b.parent = aParent;
        // Swap their node numbers.
        int temp = a.number;
        a.number = b.number;
        b.number = temp;
        // Update the numberMap
        numberMap.put(a.number, a);
        numberMap.put(b.number, b);
    }

    // Builds the code by inserting bits in reverse traversal order.
    public String getCode(Node node) {
        StringBuilder code = new StringBuilder();
        Node current = node;
        // Traverse Until Root
        while (current.parent != null) {
            if (current.parent.left == current)
                code.insert(0, '0');
            else
                code.insert(0, '1');
            current = current.parent;
        }
        return code.toString();
    }

    // Converts a char to an 8-bit binary string (used when emitting new symbols).
    public String toBinary(char c) {
        return String.format("%8s", Integer.toBinaryString(c)).replace(' ', '0');
    }

    public void printTreeState(String currentStream) {
        System.out.println("Compressed stream so far: " + currentStream);
        System.out.println("Tree structure:");
        for (char c : symbolMap.keySet()) {
            Node node = symbolMap.get(c);
            System.out.println("Symbol '" + c + "' -> Code: " + getCode(node) + ", Count: " + node.count);
        }
        System.out.println("NYT node -> Code: " + getCode(NYT) + ", Count: " + NYT.count);
        System.out.println("--------------------------------------------------");
    }

    public Node getRoot() {
        return this.root;
    }

    public Node getNYT() {
        return this.NYT;
    }

    public Node getLastUpdatedNode() {
        return lastUpdatedNode;
    }

    public Map<Character, Node> getSymbolMap() {
        return symbolMap;
    }
}