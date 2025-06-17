 class AdaptiveHuffmanTest {
    public static void main(String[] args) {
        StringBuilder result = new StringBuilder();
        result.append("===== Test Cases =====\n\n");
        result.append(test(1, "ABABAB"));
        result.append(test(2, "HELLO"));
        result.append(test(3, "AAAAAAA"));
        result.append("\n======================");
        // Print all results at once
        System.out.println(result.toString());
    }
    
    public static String test(int testCaseNumber, String input) {
        Encoder encoder = new Encoder();
        Decoder decoder = new Decoder();
        String compressed = encoder.encode(input);
        String decompressed = decoder.decode(compressed);
        
        StringBuilder testResult = new StringBuilder();
        testResult.append("Test Case " + testCaseNumber + ": " + input + "\n");
        testResult.append("Original:  " + input + "\n");
        testResult.append("Encoded:   " + compressed + "\n");
        testResult.append("Decoded:   " + decompressed + "\n");
        testResult.append("Matched:   " + input.equals(decompressed) + "\n");
        testResult.append("**********\n\n");
        
        System.out.println("****************************************************************************************************************************\n");
        return testResult.toString();
    }
}