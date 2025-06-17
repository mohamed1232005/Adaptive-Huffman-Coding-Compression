import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class AdaptiveHuffmanGUI extends JPanel {
    private HuffmanTree tree;
    private HuffmanTreeVisualizer visualizer;
    private JTextField inputField;
    private JButton addButton;
    private JButton resetButton;
    private JTextArea encodedOutputArea;
    private JTextArea decodedOutputArea;
    private StringBuilder currentEncoded;
    private StringBuilder currentDecoded;

    // Constructor
    public AdaptiveHuffmanGUI(HuffmanTree tree, HuffmanTreeVisualizer visualizer) {
        this.tree = tree;
        this.visualizer = visualizer;
        this.currentEncoded = new StringBuilder();
        this.currentDecoded = new StringBuilder();
        // Arranges components into NORTH, CENTER, and SOUTH regions of the window.
        setLayout(new BorderLayout());

        // Top control panel--Input Controls
        JPanel controlPanel = new JPanel();
        inputField = new JTextField(10);
        addButton = new JButton("Add Character");
        resetButton = new JButton("Reset Tree");

        controlPanel.add(new JLabel("Enter character: "));
        controlPanel.add(inputField);
        controlPanel.add(addButton);
        controlPanel.add(resetButton);

        // Center visualization panel
        // Allows you to scroll if the tree grows larger than the window.
        JScrollPane visualizerScroll = new JScrollPane(visualizer);

        // Bottom output panel
        JPanel outputPanel = new JPanel(new GridLayout(2, 1));
        encodedOutputArea = new JTextArea(5, 40);
        encodedOutputArea.setEditable(false);
        decodedOutputArea = new JTextArea(5, 40);
        decodedOutputArea.setEditable(false);

        JPanel encodedPanel = new JPanel(new BorderLayout());
        encodedPanel.add(new JLabel("Encoded Output:"), BorderLayout.NORTH);
        encodedPanel.add(new JScrollPane(encodedOutputArea), BorderLayout.CENTER);

        JPanel decodedPanel = new JPanel(new BorderLayout());
        decodedPanel.add(new JLabel("Characters Added:"), BorderLayout.NORTH);
        decodedPanel.add(new JScrollPane(decodedOutputArea), BorderLayout.CENTER);

        outputPanel.add(encodedPanel);
        outputPanel.add(decodedPanel);

        // Add components to main panel
        add(controlPanel, BorderLayout.NORTH);
        add(visualizerScroll, BorderLayout.CENTER);
        add(outputPanel, BorderLayout.SOUTH);

        // Event handling
        setupEventHandlers();
    }

    private void setupEventHandlers() {
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addCharacter();
            }
        });

        inputField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    addCharacter();
                }
            }
        });

        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetTree();
            }
        });
    }

    private void addCharacter() {
        String input = inputField.getText();
        if (input.length() > 0) {
            char c = input.charAt(0);
            String encoded = tree.encode(c);

            // Update GUI components
            currentEncoded.append(encoded);
            currentDecoded.append(c);

            encodedOutputArea.setText(currentEncoded.toString());
            decodedOutputArea.setText(currentDecoded.toString());

            visualizer.updateTree(tree);
            visualizer.highlightNode(tree.getLastUpdatedNode());

            // Clear input field for next character
            inputField.setText("");
            inputField.requestFocus();
        }
    }

    private void resetTree() {
        tree = new HuffmanTree();
        currentEncoded.setLength(0);
        currentDecoded.setLength(0);

        encodedOutputArea.setText("");
        decodedOutputArea.setText("");

        visualizer.updateTree(tree);
        visualizer.highlightNode(null);

        inputField.setText("");
        inputField.requestFocus();
    }
}