import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class FileLoader {
  public static void main(String[] args) throws Exception {
    // Specify the target file path.
    String targetFile = "output.csv";
    // Specify the source file paths to load data.
    List<String> rawDataFilePath = Arrays.asList("Indiegogo.csv", "Indiegogo001.csv", "Indiegogo002.csv");

    // Create file writer to write data to the target file.
    File targetFileWriter = new File(targetFile);
    // Create buffer to stream data to the file writer.
    BufferedWriter output = new BufferedWriter(new FileWriter(targetFileWriter));

    // Loop each data file and merge their content into one file.
    rawDataFilePath.forEach(path -> {
      // Create file reader to read data from source csv.
      File rawData = new File(path);
      // Create buffer to stream data to the file reader.
      BufferedReader br;

      try {
        // Stream data from source.
        br = new BufferedReader(new FileReader(rawData));

        // Data streaming from source to the target.
        String st;
        while ((st = br.readLine()) != null) {
          System.out.println(st);
          output.write(st+"\n");
        }

        br.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    });
    output.close();
  }
}
