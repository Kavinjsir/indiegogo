import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
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

public class HuffmanCompresser {
  public Map<Character, Integer> countCharactersFromFile(String filePath) {
    FileReader fr = null;
    Map<Character, Integer> characterCounter = new HashMap<Character, Integer>();
    try {
      fr = new FileReader(filePath);
      BufferedReader br = new BufferedReader(fr, 1024 * 8192);
      String text;
      while ( (text = br.readLine()) != null ) {
        if (text == null || text.length() == 0) { continue; }
        for (char c: text.toCharArray()) {
          characterCounter.put(c, characterCounter.getOrDefault(c, 0) + 1);
        }
      }
      br.close();
      fr.close();
    } catch (IOException e) {
      System.out.println("[Count characters] Failed to read/write file: " + e);
      e.printStackTrace();
    }

    return characterCounter;
  }

  public Node createHuffmanTree(Map<Character, Integer> characterCounter) {
     PriorityQueue<Node> pq = new PriorityQueue<Node>(Comparator.comparingInt(l -> l.freq));

     // Create a leaf node for each character and add it
     // to the priority queue.
     for (var entry: characterCounter.entrySet()) {
       pq.add(new Node(entry.getKey(), entry.getValue()));
     }

     // Create Huffman Tree By PriorityQueue
     // Do till there is more than one node in the queue
     while (pq.size() != 1) {
       Node left = pq.poll();
       Node right = pq.poll();
       int sum = left.freq + right.freq;
       pq.add(new Node(null, sum, left, right));
     }

     // Return the root of huffman tree
     return pq.peek();
  }

  public Map<Character, String> createHuffmanEncodingTable(Node root) {
    Map<Character, String> table = new HashMap<Character, String>();
    encode(root, "", table);
    return table;
  }

  public void createCompressedFileByHuffmanCodes(String sourceFile, String targetFile, Map<Character, String> huffmanCodes) {
    try {
      File targetFileWriter = new File(targetFile);
      BufferedWriter writer = new BufferedWriter(new FileWriter(targetFileWriter));

      File rawData = new File(sourceFile);
      BufferedReader reader = new BufferedReader(new FileReader(rawData));

      String st;
      while ((st = reader.readLine()) != null) {
        String codes = encodeTextLine(st, huffmanCodes).toString();
        writer.write(codes + "\n");
      }
      reader.close();
      writer.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private StringBuilder encodeTextLine(String text, Map<Character, String> table) {
    StringBuilder sb = new StringBuilder();
    for (char c: text.toCharArray()) {
      sb.append(table.get(c));
    }
    return sb;
  }

  private void encode(Node currentNode, String code, Map<Character, String> table) {
    if (currentNode == null) { return; }

    // Found a leaf node
    if (isLeaf(currentNode)) {
      table.put(currentNode.ch, code.length() > 0 ? code : "1");
    }

    encode(currentNode.left, code + "0", table);
    encode(currentNode.right, code + "1", table);
  }

  private boolean isLeaf(Node node) {
    return node.left == null && node.right == null;
  }

   // Traverse the Huffman Tree and decode the encoded string
   public int decode(Node currentRoot, int index, StringBuilder sb, StringBuilder decodedChs) {
    if (currentRoot == null) { return index; }

    if (isLeaf(currentRoot)) {
      decodedChs.append(currentRoot.ch);
      // System.out.print(currentRoot.ch);
      return index;
    }

    index++;

    currentRoot = (sb.charAt(index) == '0') ? currentRoot.left : currentRoot.right;
    index = decode(currentRoot, index, sb, decodedChs);
    return index;
  }

  public String printDecodedString(StringBuilder sb, Node node) {

    // System.out.print("The decoded string is: ");
    StringBuilder decodedChs = new StringBuilder();

    if (isLeaf(node)) {
      Integer freq = node.freq;
      while (freq-- > 0) {
        // System.out.print(node.ch);
        decodedChs.append(node.ch);
      }
    }
    else {
      // Traverse the Huffman Tree again and this time,
      // decode the encoded string
      int index = -1;
      while (index < sb.length() - 1) {
        index = decode(node, index, sb, decodedChs);
      }
    }

    // convert in string
    return decodedChs.toString();
  }

  public List<String> searchEncodedFile(String encodedkeywords, String filePath, Node root) {
    FileReader fr = null;
    List<String> results = new ArrayList<String>();

    try {
      fr = new FileReader(filePath);
      BufferedReader br = new BufferedReader(fr, 1024 * 8192);
      String text;
      while ( (text = br.readLine()) != null ) {
        if (text == null || text.length() == 0) { continue; }
        if (text.contains(encodedkeywords)) {
          results.add(printDecodedString(new StringBuilder(text), root));
        }
      }
      br.close();
      fr.close();
    } catch (IOException e) {
      System.out.println("[Count characters] Failed to read/write file: " + e);
      e.printStackTrace();
    }

    return results;
  }

  public static void main(String[] args) throws Exception {
    String source = "/Users/jintony/learning/cs622/hw/homework3/sample.json";
    String encode = "/Users/jintony/learning/cs622/hw/homework3/encode.json";
    // String decode = "/Users/jintony/learning/cs622/hw/homework3/decode.json";

    HuffmanCompresser hfc = new HuffmanCompresser();
    Map<Character, Integer> counter = hfc.countCharactersFromFile(source);
    Node root = hfc.createHuffmanTree(counter);
    Map<Character, String> huffmanEncodingTable = hfc.createHuffmanEncodingTable(root);
    hfc.createCompressedFileByHuffmanCodes(source, encode, huffmanEncodingTable);

    String keyword = "wellness";
    String codes = hfc.encodeTextLine(keyword, huffmanEncodingTable).toString();
    List<String> results = hfc.searchEncodedFile(codes, encode, root);
    results.forEach(str -> System.out.println(str));
    results = null;

    keyword = "Boston";
    codes = hfc.encodeTextLine(keyword, huffmanEncodingTable).toString();
    results = hfc.searchEncodedFile(codes, encode, root);
    results.forEach(str -> System.out.println(str));
  }
}
