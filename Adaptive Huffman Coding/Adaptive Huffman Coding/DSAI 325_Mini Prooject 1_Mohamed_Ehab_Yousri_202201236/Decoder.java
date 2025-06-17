public class Decoder {
    // tree: the adaptive Huffman tree, initialized fresh for decoding.
    // decodedOutput: holds the reconstructed message.
    private HuffmanTree tree;
    private StringBuilder decodedOutput;

    public Decoder() {
        // Starts with an empty Huffman Tree containing only an NYT node.
        tree = new HuffmanTree();
        decodedOutput = new StringBuilder();
    }

    public String decode(String bitstream) {
        // i is a pointer to track your current bit position in the input binary string.
        int i = 0;
        while (i < bitstream.length()) {
            // Start traversal from the root
            Node current = tree.getRoot();
            // While current is not a leaf node
            while (!current.isLeaf()) {
                if (i >= bitstream.length())
                    // Stop when you reach a leaf node
                    break;
                // Traverse the tree using bits:'0' → go left,'1' → go right
                current = (bitstream.charAt(i++) == '0') ? current.left : current.right;
            }

            // Case 1: If Node is NYT
            char symbol;
            if (current.isNYT) {
                // Read the next 8 bits to get the ASCII of the new character.
                if (i + 8 > bitstream.length())
                    break;
                // Convert from binary string to a character.
                String asciiBits = bitstream.substring(i, i + 8);
                i += 8;
                symbol = (char) Integer.parseInt(asciiBits, 2);
                // Insert that character into the tree.
                tree.insert(symbol);

                // 🧾 Case 2: If Node is a Regular Symbol
                // If it’s not NYT, the node contains a character seen before.
                // We extract the character and update the tree to reflect its new frequency and
                // rebalancing.
            } else {
                symbol = current.symbol;
                tree.update(current);
            }
            // Append the decoded character to the final result.
            decodedOutput.append(symbol);
        }
        // return the full decoded message.
        return decodedOutput.toString();
    }

    // Used to access the final tree after decoding — helpful for visualization
    public HuffmanTree getTree() {
        return tree;
    }
}