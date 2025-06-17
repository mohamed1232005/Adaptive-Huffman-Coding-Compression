import java.util.Scanner;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class AdaptiveHuffman {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Scanner scanner = new Scanner(System.in);
            Encoder encoder = new Encoder();
            Decoder decoder = new Decoder();
            HuffmanTree tree = new HuffmanTree();

            // Visualization
            // GUI Setup
            JFrame frame = new JFrame("Adaptive Huffman Tree Visualization");
            HuffmanTreeVisualizer visualizer = new HuffmanTreeVisualizer(tree);
            frame.add(visualizer);
            frame.setSize(1200, 800);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);

            System.out.println("===== Adaptive Huffman Coding Project =====");
            System.out.print("Enter a message to encode: ");
            String input = scanner.nextLine();

            System.out.println("\nEncoding process begins...\n");

            StringBuilder currentEncoded = new StringBuilder();
            // Process each character individually to update the visualization

            // Character-by-Character Encoding + Visualization
            for (char c : input.toCharArray()) {
                String encoded = tree.encode(c);
                currentEncoded.append(encoded);
                tree.printTreeState(currentEncoded.toString());
                visualizer.updateTree(tree);
                visualizer.visualizeStep(tree.getLastUpdatedNode(), "Added character: '" + c + "'");

                // Add a small delay to see the visualization update
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            // Final Output
            String compressed = currentEncoded.toString();

            System.out.println("\nFinal Compressed Bit Stream:");
            System.out.println(compressed);

            // Decoding Phase
            System.out.println("\nDecoding process begins...\n");
            String decompressed = decoder.decode(compressed);
            visualizer.updateTree(decoder.getTree());

            System.out.println("\nDecoded Original Message:");
            System.out.println(decompressed);
            // Compares the original with the decoded to ensure correctness.
            if (input.equals(decompressed)) {
                System.out.println("\nSUCCESS: Decoded message matches the original input.");
            } else {
                System.out.println("\nERROR: Decoded message does NOT match the original input.");
            }

            System.out.println("\n===========================================\n");
            scanner.close();
        });
    }
}