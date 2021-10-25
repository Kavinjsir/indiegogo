/*
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

class Node {
  Character ch;
  Integer freq;
  Node left = null;
  Node right = null;

  Node(Character ch, Integer freq) {
      this.ch = ch;
      this.freq = freq;
  }

  public Node(Character ch, Integer freq, Node left, Node right) {
      this.ch = ch;
      this.freq = freq;
      this.left = left;
      this.right = right;
  }
}

public class HuffmanEncoder {
  // Count the frequency of appearance of each character
  // and store it in a map.
  private Map<Character, Integer> freq;

  // Create a priority queue to store live nodes of the Huffman tree.
  // Notice that the highest priority item has the lowest frequency
  private PriorityQueue<Node> pq;

  // Pointer to record Root Node
  private Node root;

  // Map to store Huffman code
  private Map<Character, String> huffmanCode;

  // Initailize state to empty
  public void init() {
    freq = new HashMap<>();
    pq = new PriorityQueue<Node>(Comparator.comparingInt(l -> l.freq));
    huffmanCode = new HashMap<Character, String>();
    root = null;
  }

  public HuffmanEncoder() {
    freq = new HashMap<>();
    pq = new PriorityQueue<Node>(Comparator.comparingInt(l -> l.freq));
    huffmanCode = new HashMap<Character, String>();
    root = null;
  }

  // Traverse the Huffman Tree and store Huffman Codes in a map;
  public void encode(Node currentRoot, String str) {
    if (currentRoot == null) { return; }

    // Found a leaf node
    if (isLeaf(currentRoot)) {
        huffmanCode.put(currentRoot.ch, str.length() > 0 ? str : "1");
    }

    encode(currentRoot.left, str + "0");
    encode(currentRoot.right, str + "1");
  }

  // Traverse the Huffman Tree and decode the encoded string
  public int decode(Node currentRoot, int index, StringBuilder sb) {
    if (currentRoot == null) { return index; }

    if (isLeaf(currentRoot)) {
      System.out.print(currentRoot.ch);
      return index;
    }

    index++;

    currentRoot = (sb.charAt(index) == '0') ? currentRoot.left : currentRoot.right;
    index = decode(currentRoot, index, sb);
    return index;
  }

  // Utility funciton to check if Huffman Tree contains only a string node
  private boolean isLeaf(Node node) {
    return node.left == null && node.right == null;
  }

  // Count the frequency of appearance of each character
  // and store it in a map.
  private void countCharacterFrequency(String text) {
    for (char c: text.toCharArray()) {
      freq.put(c, freq.getOrDefault(c, 0) + 1);
    }
  }

  // Create a leaf node for each character and add it
  // to the priority queue.
  private void putCharacterFreqIntoPriorityQueue() {
    for (var entry: freq.entrySet()) {
      pq.add(new Node(entry.getKey(), entry.getValue()));
    }
  }

  private void createHuffmanTreeByPriorityQueue() {
    // Do till there is more than one node in the queue
    while (pq.size() != 1) {
      // Remove the two nodes of the highest priority
      // (the lowest frequency) from the queue
      Node left = pq.poll();
      Node right = pq.poll();

      // Create a new internal node with these two nodes as children
      // and with a frequency equal to the sum of both nodes'
      // frequencies. Add the new node to the priority queue.
      int sum = left.freq + right.freq;
      pq.add(new Node(null, sum, left, right));
    }
  }

  // Traverse the Huffman tree and store the Huffman codes in a map
  public void getHuffmanCodes() {
    encode(root, "");
  }

  public StringBuilder encodeString(String text) {
    // Print encoded string
    StringBuilder sb = new StringBuilder();
    for (char c: text.toCharArray()) {
      sb.append(huffmanCode.get(c));
    }

    // System.out.println("The encoded string is: " + sb);
    return sb;
  }

  public void decodeString(StringBuilder source, StringBuilder result) {

    if (isLeaf(root)) {
      while (root.freq-- > 0) {
        result.append(root.ch);
      }
    }
    else {
      // Traverse the Huffman Tree again and this time,
      // decode the encoded string
      int index = -1;
      while (index < source.length() - 1) {
        index = decode(root, index, source);
      }
    }
  }


  // Builds Huffman Tree and decodes the given input text
  public void buildHuffmanTree() {
    putCharacterFreqIntoPriorityQueue();

    createHuffmanTreeByPriorityQueue();

    // `root` stores pointer to the root of Huffman Tree
    root = pq.peek();
    
    // Traverse the Huffman tree and store the Huffman codes in a map
    encode(root, "");

    // System.out.println("The origin string is: " + text);

    System.out.println("Huffman Codes are: " + huffmanCode);

    // StringBuilder sb =  printEncodedString(text);

    // printDecodedString(sb);
  }

  // Read file and count character
  public void loadFileContents(String sourceFile) throws IOException {
    FileReader  fr = null;
    try{
       fr = new FileReader(sourceFile);
        BufferedReader br = new BufferedReader(fr, 1024 * 8192);
        String text;
        while((text = br.readLine()) != null) {
          // Base case: empty string
          if (text == null || text.length() == 0) { continue; }

          // Count Character
          countCharacterFrequency(text);
        }
        
        br.close();

      } catch (Exception e) {
        System.out.println("Huffman encoding failed: " + e);
      } finally {
        fr.close();
      }
  }

  public void convertFileToHuffmanCodes(String sourcePath, String targetFile) throws IOException {
    try {
      File targetFileWriter = new File(targetFile);
      // Create buffer to stream data to the file writer.
      BufferedWriter output = new BufferedWriter(new FileWriter(targetFileWriter));
      // Create file reader to read data from source csv.
      File rawData = new File(sourcePath);
      // Create buffer to stream data to the file reader.
      BufferedReader br = new BufferedReader(new FileReader(rawData));
      // Convert the data from buffer to string.
      String st;
      while ((st = br.readLine()) != null) {
        String encodedSt = encodeString(st).toString();
        output.write(encodedSt+ "\n");
      }
      // Ensure file is released when streaming finished.
      br.close();
      output.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void decodeHuffmanFile(String sourcePath, String targetFile) throws IOException {
    try {
      File targetFileWriter = new File(targetFile);
      // Create buffer to stream data to the file writer.
      BufferedWriter output = new BufferedWriter(new FileWriter(targetFileWriter));
      // Create file reader to read data from source csv.
      File rawData = new File(sourcePath);
      // Create buffer to stream data to the file reader.
      BufferedReader br = new BufferedReader(new FileReader(rawData));
      // Convert the data from buffer to string.
      String st;
      while ((st = br.readLine()) != null) {
        StringBuilder decodedSb =  new StringBuilder();
        decodeString(new StringBuilder(st), decodedSb);
        output.write(decodedSb.toString()+ "\n");
      }
      // Ensure file is released when streaming finished.
      br.close();
      output.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }


  public static void main(String[] args) throws Exception {
    HuffmanEncoder huffmanEncoder = new HuffmanEncoder();

    String source = "/Users/jintony/learning/cs622/hw/homework3/sample.json";
    String encode = "/Users/jintony/learning/cs622/hw/homework3/encode.json";
    String decode = "/Users/jintony/learning/cs622/hw/homework3/decode.json";

    huffmanEncoder.loadFileContents(source);

    huffmanEncoder.buildHuffmanTree();

    System.out.println(huffmanEncoder.huffmanCode);

    huffmanEncoder.convertFileToHuffmanCodes(source, encode);

    huffmanEncoder.decodeHuffmanFile(encode, decode);

  }
}

*/