# Adaptive-Huffman-Coding-Compression

## Adaptive Huffman Coding

A Java-based implementation of **Adaptive Huffman Coding**, a real-time, lossless compression algorithm that dynamically updates its encoding tree as new data is processed. This project was developed as part of the **Mini Project** for the **Information Theory (DSAI 325)** course by Mohamed Ehab Yousri.

---

## 📌 Table of Contents

- [Overview](#overview)
- [Key Concepts](#key-concepts)
- [Compression Process](#compression-process)
- [Decompression Process](#decompression-process)
- [GUI Visualization](#gui-visualization)
- [Project Structure](#project-structure)
- [Technologies Used](#technologies-used)
- [How to Run](#how-to-run)
- [Test Case: Tree Evolution](#test-case-tree-evolution)
- [References](#references)
- [Author](#author)
- [License](#license)

---

## 📖 Overview

**Adaptive Huffman Coding** is a **lossless data compression algorithm** that constructs and maintains a **Huffman tree in real-time** as it encodes or decodes input data. Unlike static Huffman coding, which requires a pre-computed frequency table, **adaptive Huffman coding works without prior knowledge of the data distribution**, making it ideal for streaming and online compression scenarios.

This project implements:
- A full adaptive encoder and decoder.
- A GUI-based **visualizer** using Java Swing.
- Binary tree maintenance logic for encoding and decoding paths.

---

## 🧠 Key Concepts

| Concept             | Description |
|---------------------|-------------|
| **NYT Node**         | \"Not Yet Transmitted\" node represents characters that have not appeared yet. |
| **Dynamic Tree Update** | Tree structure changes after every encoded/decoded character to maintain optimal compression. |
| **Prefix Codes**     | No code is a prefix of another; allows unique decoding. |
| **Weight Invariant** | Huffman trees are adjusted so that higher-frequency nodes stay closer to the root. |

---

## 🔐 Compression Process

### Step-by-Step

1. **Initialization**:  
   Start with a single NYT (Not Yet Transmitted) node.

2. **Character Encoding**:
   - If the character is **already in the tree**:
     - Output the binary code corresponding to that character by traversing the Huffman tree.
   - If the character is **new**:
     - Output the code for the NYT node followed by the **8-bit ASCII value** of the character.

3. **Tree Update**:
   - Insert the new character by replacing the NYT node with an **internal node**.
   - The internal node gets two children:
     - One for the new character.
     - One new NYT node.
   - Update weights and **swap nodes if necessary** to preserve the Huffman property.

---

## 🔓 Decompression Process

### Step-by-Step

1. **Start at the Root**:
   - Read the bit stream and follow `0` for left and `1` for right through the tree.

2. **NYT Node Encounter**:
   - If a NYT node is reached, read the next **8 bits** to retrieve the ASCII value of the new character.

3. **Tree Update**:
   - Add the new character to the tree by replacing NYT node with an internal node.
   - Adjust weights and **re-balance the tree** to maintain encoding correctness.

4. **Repeat**:
   - Continue decoding until all input bits are processed.

---

## 🖥 GUI Visualization

The project includes a **Swing-based GUI** that visualizes the evolution of the Huffman tree as characters are encoded.

### Features:
- Displays **node weights**, **characters**, and **NYT position**.
- Shows real-time **tree growth and node swapping**.
- Allows step-by-step input to visualize the impact of each character.

### GUI Files:
```
AdaptiveHuffmanGUI.java
RunVisualizer.java
```

---

## 📁 Project Structure

| File Name                | Description |
|--------------------------|-------------|
| `AdaptiveHuffman.java`   | Main class containing `main()` method |
| `Encoder.java`           | Handles adaptive Huffman encoding logic |
| `Decoder.java`           | Handles adaptive decoding using bit stream |
| `Node.java`              | Defines the tree node structure (weight, symbol, links) |
| `HuffmanTree.java`       | Builds and updates the Huffman tree dynamically |
| `AdaptiveHuffmanTest.java` | Test cases for compression/decompression |
| `AdaptiveHuffmanGUI.java` | Java Swing GUI for real-time tree visualization |
| `RunVisualizer.java`     | Entry point to launch the GUI |

---

## ⚙️ Technologies Used

| Tool/Library     | Purpose |
|------------------|---------|
| **Java SE 8+**   | Primary language and runtime |
| **Java Swing**   | GUI and visualization |
| **Bit Manipulation** | Encoding/decoding binary stream |
| **Tree Structures** | Huffman tree maintenance |
| **ASCII Encoding** | Handling new characters via 8-bit binary |

---

## 🚀 How to Run

### ⏱ Prerequisites

- Java Development Kit (JDK 8 or above)

### 🧪 Run the CLI Encoder/Decoder

```bash
javac *.java
java AdaptiveHuffmanTest
```

### 🖼 Run the GUI Visualizer

```bash
javac *.java
java RunVisualizer
```

Ensure Swing libraries are accessible (default in Java SE).

---

## 🧪 Test Case: Tree Evolution

Given the input stream:  
```
A B A B A B
```

Tree update steps:

| Step | Character | Action                          | Description |
|------|-----------|----------------------------------|-------------|
| 1    | A         | NYT → A                          | NYT node replaced with A node and new NYT |
| 2    | B         | NYT → B                          | Adds B, updates frequencies |
| 3    | A         | Increment A                      | Tree rebalanced |
| 4    | B         | Increment B                      | Tree rebalanced |
| 5    | A         | Increment A                      | A becomes closer to root |
| 6    | B         | Increment B                      | B becomes closer to root |

All tree updates are visualized in the GUI.

---

## 📚 References

- [YouTube – Adaptive Huffman Explained](https://www.youtube.com/watch?v=TFW4OWpw4f8)
- [Ben Tanen’s Guide to Adaptive Huffman](https://ben-tanen.com/adaptive-huffman/)
- [ExperienceStack – Adaptive Huffman Coding](https://experiencestack.co/adaptive-huffman-coding-2f6379bc23fe)
- Sayood, K. – *Introduction to Data Compression*

---


