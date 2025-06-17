import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class HuffmanTreeVisualizer extends JPanel {
    private HuffmanTree tree;
    private Map<Node, Point> nodePositions;
    private Map<Node, Color> nodeColors;
    private Node highlightedNode;
    private Timer animationTimer;
    private int animationStep = 0;
    private static final int NODE_SIZE = 40;
    private static final int VERTICAL_GAP = 70;
    private static final Color NYT_COLOR = new Color(255, 240, 200);
    private static final Color LEAF_COLOR = new Color(200, 240, 255);
    private static final Color INTERNAL_COLOR = new Color(230, 230, 230);
    private static final Color HIGHLIGHT_COLOR = new Color(255, 200, 200);
    private String currentOperation = "";
    
    public HuffmanTreeVisualizer(HuffmanTree tree) {
        this.tree = tree;
        this.nodePositions = new HashMap<>();
        this.nodeColors = new HashMap<>();
        setPreferredSize(new Dimension(900, 700));
        setBackground(Color.WHITE);
        
        // Create animation timer
        animationTimer = new Timer(500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (highlightedNode != null) {
                    animationStep = (animationStep + 1) % 2;
                    repaint();
                }
            }
        });
        animationTimer.start();
    }
    
    // Method to update the tree visualization
    public void updateTree(HuffmanTree updatedTree) {
        this.tree = updatedTree;
        calculateNodePositions();
        repaint();
    }
    
    // Method to highlight a specific node during operations
    public void highlightNode(Node node) {
        this.highlightedNode = node;
        if (node != null && !animationTimer.isRunning()) {
            animationTimer.start();
            if (node.isNYT) {
                currentOperation = "Added NYT Node";
            } else if (node.isLeaf()) {
                currentOperation = "Updated Node '" + node.symbol + "', Count: " + node.count;
            } else {
                currentOperation = "Updated Internal Node, Count: " + node.count;
            }
        } else if (node == null) {
            animationTimer.stop();
            animationStep = 0;
            currentOperation = "";
        }
        repaint();
    }
    
    // Calculate positions for all nodes in the tree
    private void calculateNodePositions() {
        nodePositions.clear();
        nodeColors.clear();
        
        if (tree.getRoot() != null) {
            // Calculate tree height to determine horizontal spacing
            int treeHeight = calculateTreeHeight(tree.getRoot());
            int initialXOffset = getWidth() / 3;
            
            calculatePositions(tree.getRoot(), getWidth() / 2, 50, initialXOffset, treeHeight);
            assignColors();
        }
    }
    
    // Calculate height of the tree (for positioning)
    private int calculateTreeHeight(Node node) {
        if (node == null) return 0;
        return 1 + Math.max(calculateTreeHeight(node.left), calculateTreeHeight(node.right));
    }
    
    // Recursively calculate positions for all nodes
    private void calculatePositions(Node node, int x, int y, int xOffset, int level) {
        if (node == null) return;
        
        nodePositions.put(node, new Point(x, y));
        
        if (node.left != null) {
            int leftX = x - xOffset / level;
            int leftY = y + VERTICAL_GAP;
            calculatePositions(node.left, leftX, leftY, xOffset, level + 1);
        }
        
        if (node.right != null) {
            int rightX = x + xOffset / level;
            int rightY = y + VERTICAL_GAP;
            calculatePositions(node.right, rightX, rightY, xOffset, level + 1);
        }
    }
    
    // Assign colors to nodes based on their types
    private void assignColors() {
        for (Node node : nodePositions.keySet()) {
            if (node.isNYT) {
                nodeColors.put(node, NYT_COLOR);
            } else if (node.isLeaf()) {
                nodeColors.put(node, LEAF_COLOR);
            } else {
                nodeColors.put(node, INTERNAL_COLOR);
            }
        }
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Draw operation description at the top
        if (!currentOperation.isEmpty()) {
            g2d.setFont(new Font("Arial", Font.BOLD, 14));
            g2d.setColor(Color.BLACK);
            g2d.drawString(currentOperation, 20, 20);
        }
        
        if (tree.getRoot() == null) {
            g2d.setFont(new Font("Arial", Font.BOLD, 18));
            g2d.drawString("Tree is empty", getWidth() / 2 - 60, getHeight() / 2);
            return;
        }
        
        // Recalculate positions if needed
        if (nodePositions.isEmpty()) {
            calculateNodePositions();
        }
        
        // Draw edges first (so they appear behind the nodes)
        drawEdges(g2d);
        
        // Draw nodes
        drawNodes(g2d);
        
        // Draw legend
        drawLegend(g2d);
        
        // Draw codes for each character
        drawCodes(g2d);
    }
    
    private void drawEdges(Graphics2D g2d) {
        g2d.setStroke(new BasicStroke(2.0f));
        
        for (Node node : nodePositions.keySet()) {
            Point nodePoint = nodePositions.get(node);
            
            if (node.left != null && nodePositions.containsKey(node.left)) {
                Point leftPoint = nodePositions.get(node.left);
                g2d.setColor(Color.DARK_GRAY);
                g2d.drawLine(nodePoint.x, nodePoint.y, leftPoint.x, leftPoint.y);
                
                // Draw '0' on left edges
                Point midPoint = new Point((nodePoint.x + leftPoint.x) / 2, (nodePoint.y + leftPoint.y) / 2);
                g2d.setColor(Color.BLUE);
                g2d.drawString("0", midPoint.x - 10, midPoint.y - 5);
            }
            
            if (node.right != null && nodePositions.containsKey(node.right)) {
                Point rightPoint = nodePositions.get(node.right);
                g2d.setColor(Color.DARK_GRAY);
                g2d.drawLine(nodePoint.x, nodePoint.y, rightPoint.x, rightPoint.y);
                
                // Draw '1' on right edges
                Point midPoint = new Point((nodePoint.x + rightPoint.x) / 2, (nodePoint.y + rightPoint.y) / 2);
                g2d.setColor(Color.RED);
                g2d.drawString("1", midPoint.x + 5, midPoint.y - 5);
            }
        }
    }
    
    private void drawNodes(Graphics2D g2d) {
        for (Node node : nodePositions.keySet()) {
            Point point = nodePositions.get(node);
            int x = point.x - NODE_SIZE / 2;
            int y = point.y - NODE_SIZE / 2;
            
            // Set node color
            Color nodeColor = nodeColors.getOrDefault(node, INTERNAL_COLOR);
            
            // Highlight the selected node
            if (node == highlightedNode) {
                if (animationStep == 0) {
                    g2d.setColor(HIGHLIGHT_COLOR);
                } else {
                    g2d.setColor(nodeColor);
                }
            } else {
                g2d.setColor(nodeColor);
            }
            
            // Fill circle
            g2d.fillOval(x, y, NODE_SIZE, NODE_SIZE);
            
            // Draw border
            g2d.setColor(Color.BLACK);
            g2d.setStroke(new BasicStroke(2.0f));
            g2d.drawOval(x, y, NODE_SIZE, NODE_SIZE);
            
            // Draw node information
            String label;
            g2d.setFont(new Font("Arial", Font.PLAIN, 12));
            
            if (node.isNYT) {
                label = "NYT";
                g2d.setFont(new Font("Arial", Font.BOLD, 12));
                g2d.drawString(label, x + NODE_SIZE / 2 - 15, y + NODE_SIZE / 2 + 5);
            } else if (node.isLeaf()) {
                // For leaf nodes, show symbol and count
                char symbol = node.symbol;
                if (Character.isWhitespace(symbol)) {
                    // Handle whitespace characters
                    if (symbol == ' ') label = "SPC:" + node.count;
                    else if (symbol == '\n') label = "LF:" + node.count;
                    else if (symbol == '\t') label = "TAB:" + node.count;
                    else label = "Ch" + (int)symbol + ":" + node.count;
                } else {
                    label = String.valueOf(symbol) + ":" + node.count;
                }
                
                g2d.drawString(label, x + NODE_SIZE / 2 - 15, y + NODE_SIZE / 2 + 5);
            } else {
                // For internal nodes, show count only
                label = String.valueOf(node.count);
                g2d.drawString(label, x + NODE_SIZE / 2 - 10, y + NODE_SIZE / 2 + 5);
            }
            
            // Show node number (order number, if available)
            if (node.number >= 0) {
                g2d.setFont(new Font("Arial", Font.ITALIC, 10));
                g2d.drawString("#" + node.number, x + NODE_SIZE - 15, y + 15);
            }
        }
    }
    
    private void drawLegend(Graphics2D g2d) {
        int legendX = 20;
        int legendY = getHeight() - 100;
        int boxSize = 20;
        int textOffset = 30;
        
        g2d.setFont(new Font("Arial", Font.BOLD, 14));
        g2d.drawString("Legend:", legendX, legendY - 20);
        
        // NYT node
        g2d.setColor(NYT_COLOR);
        g2d.fillRect(legendX, legendY, boxSize, boxSize);
        g2d.setColor(Color.BLACK);
        g2d.drawRect(legendX, legendY, boxSize, boxSize);
        g2d.drawString("NYT Node", legendX + textOffset, legendY + 15);
        
        // Leaf node
        g2d.setColor(LEAF_COLOR);
        g2d.fillRect(legendX + 150, legendY, boxSize, boxSize);
        g2d.setColor(Color.BLACK);
        g2d.drawRect(legendX + 150, legendY, boxSize, boxSize);
        g2d.drawString("Leaf Node", legendX + 150 + textOffset, legendY + 15);
        
        // Internal node
        g2d.setColor(INTERNAL_COLOR);
        g2d.fillRect(legendX + 300, legendY, boxSize, boxSize);
        g2d.setColor(Color.BLACK);
        g2d.drawRect(legendX + 300, legendY, boxSize, boxSize);
        g2d.drawString("Internal Node", legendX + 300 + textOffset, legendY + 15);
        
        // Edge labels
        g2d.setColor(Color.BLUE);
        g2d.drawString("0 = Left Edge", legendX, legendY + 40);
        g2d.setColor(Color.RED);
        g2d.drawString("1 = Right Edge", legendX + 150, legendY + 40);
    }
    
    private void drawCodes(Graphics2D g2d) {
        int codeX = getWidth() - 200;
        int codeY = 50;
        
        g2d.setFont(new Font("Courier New", Font.BOLD, 12));
        g2d.setColor(Color.BLACK);
        g2d.drawString("HUFFMAN CODES:", codeX, codeY);
        
        int lineHeight = 20;
        int currentY = codeY + lineHeight;
        
        g2d.setFont(new Font("Courier New", Font.PLAIN, 12));
        
        // Draw NYT code
        String nytCode = tree.getCode(tree.getNYT());
        g2d.drawString("NYT: " + nytCode, codeX, currentY);
        currentY += lineHeight;
        
        // Draw codes for symbols
        Map<Character, Node> symbolMap = tree.getSymbolMap();
        for (Map.Entry<Character, Node> entry : symbolMap.entrySet()) {
            char symbol = entry.getKey();
            String displaySymbol;
            
            // Handle special characters for display
            if (Character.isWhitespace(symbol)) {
                if (symbol == ' ') displaySymbol = "SPACE";
                else if (symbol == '\n') displaySymbol = "LF";
                else if (symbol == '\t') displaySymbol = "TAB";
                else displaySymbol = "Ch" + (int)symbol;
            } else {
                displaySymbol = String.valueOf(symbol);
            }
            
            String code = tree.getCode(entry.getValue());
            g2d.drawString("'" + displaySymbol + "': " + code, codeX, currentY);
            currentY += lineHeight;
        }
    }
    
    // Add this method to support step-by-step visualization
    public void visualizeStep(Node currentNode, String operationDescription) {
        highlightNode(currentNode);
        this.currentOperation = operationDescription;
        repaint();
    }
}