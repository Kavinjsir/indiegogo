import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileLoader {
  public static void main(String[] args) throws Exception {
    String targetFile = "output.csv";

    // Create file writer to write data to the target file.
    File targetFileWriter = new File(targetFile);
    // Create buffer to stream data to the file writer.
    BufferedWriter output = new BufferedWriter(new FileWriter(targetFileWriter));

    // Create file reader to read data from source csv.
    File rawData = new File("sample.csv");
    // Create buffer to stream data from source.
    BufferedReader br = new BufferedReader(new FileReader(rawData));

    try {
      // Load data from to buffer to string.
      String st;
      while ((st = br.readLine()) != null) {
        System.out.println(st);
        output.write(st+"\n");
      }
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      br.close();
      output.close();
    }
  }
}
