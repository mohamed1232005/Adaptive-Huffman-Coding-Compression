public class Encoder {
    private HuffmanTree tree;
    private StringBuilder encodedStream;

    // While HuffmanTree.java contains the core logic of how characters are
    // encoded,the Encoder.java :
    // Iterates through the input string, and for each character, calls
    // tree.encode(c). It accumulates the encoded bits.
    public Encoder() {
        tree = new HuffmanTree();
        encodedStream = new StringBuilder();
    }

    // Uses tree.encode(c) to: --> Get the binary code for character c.
    public String encode(String input) {
        for (char c : input.toCharArray()) {
            String encoded = tree.encode(c);
            // Appends the binary result to encodedStream.
            encodedStream.append(encoded);
        }
        // Returns the full encoded binary string at the end.
        return encodedStream.toString();
    }
    // This is what’s used in AdaptiveHuffmanTest.java to encode test inputs like
    // "ABABAB".

    // Provides access to the final binary output if needed from another class.
    public String getEncodedStream() {
        return encodedStream.toString();
    }

    // Returns the current internal state of the Huffman tree,Useful for Visualizing
    // the tree
    public HuffmanTree getTree() {
        return tree;
    }
}