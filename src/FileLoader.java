import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class FileLoader {
  private List<String> rawDataFilePath;
  private String targetFile;

  private void loadDataAndWrite(String path, BufferedWriter writer) {
    try {
      // Create file reader to read data from source csv.
      File rawData = new File(path);
      // Create buffer to stream data to the file reader.
      BufferedReader br = new BufferedReader(new FileReader(rawData));

      // Data streaming from source to the target.
      String st;
      while ((st = br.readLine()) != null) {
        writer.write(st+"\n");
      }

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

      output.close();
    } catch (Exception e) {
      throw new Error(e.getMessage());
    }
  }
}
