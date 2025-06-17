import javax.swing.*;

public class RunVisualizer {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            HuffmanTree tree = new HuffmanTree();
            JFrame frame = new JFrame("Adaptive Huffman Tree Visualizer");
            HuffmanTreeVisualizer visualizer = new HuffmanTreeVisualizer(tree);
            
            AdaptiveHuffmanGUI gui = new AdaptiveHuffmanGUI(tree, visualizer);
            frame.setContentPane(gui);
            frame.setSize(1200, 800);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}