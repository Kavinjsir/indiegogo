import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

// The file loader to merge multiple files into one.
// For HW2, unused in HW3
public class FileLoader {
  private final List<String> rawDataFilePath;
  private final String targetFile;

  private void loadDataAndWrite(String path, BufferedWriter writer) {
    try {
      // Create file reader to read data from source csv.
      File rawData = new File(path);
      // Create buffer to stream data to the file reader.
      BufferedReader br = new BufferedReader(new FileReader(rawData));
      // Convert the data from buffer to string.
      String st;
      while ((st = br.readLine()) != null) {
        // Data streaming from source to the target.
        writer.write(st+"\n");
      }
      // Ensure file is released when streaming finished.
      br.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public FileLoader(List<String> rawDataFilePath, String targetFile) {
    this.rawDataFilePath = rawDataFilePath;
    this.targetFile = targetFile;
  }

  public void mergeSourceDataIntoOneFile() {
    try {
      // Create file writer to write data to the target file.
      File targetFileWriter = new File(this.targetFile);
      // Create buffer to stream data to the file writer.
      BufferedWriter output = new BufferedWriter(new FileWriter(targetFileWriter));
      // Loop each data file and merge their content into one file.
      this.rawDataFilePath.forEach(path -> this.loadDataAndWrite(path, output));
      // Close the buffer, release the file.
      output.close();
    } catch (Exception e) {
      // FIXME: How to handle error properly?
      throw new Error(e.getMessage());
    }
  }
}
