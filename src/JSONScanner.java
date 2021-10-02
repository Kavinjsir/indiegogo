import org.json.JSONObject;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class JSONScanner {
  public class DataFields {
    private String title;
    private Number fundsRaisedPercent;
    private String closeDate;

    public String getTitle() {
      return this.title;
    }

    public void setTitle(String title) {
      this.title = title;
    }

    public Number getFundsRaisedPercent() {
      return this.fundsRaisedPercent;
    }

    public void setFundsRaisedPercent(Number fundsRaisedPercent) {
      this.fundsRaisedPercent = fundsRaisedPercent;
    }

    public String getCloseDate() {
      return this.closeDate;
    }

    public void setCloseDate(String closeDate) {
      this.closeDate = closeDate;
    }
  }

  public void searchKeyword(String keyword, String filePath) {
    try {
      // Create file reader to read data from source csv.
      File rawData = new File(filePath);
      // Create buffer to stream data to the file reader.
      BufferedReader br = new BufferedReader(new FileReader(rawData));
      // Convert the data from buffer to string.
      String st;
      // Scan through file, find lines containing keyword, fetch required fields.
      while ((st = br.readLine()) != null) {
        if (st.contains(keyword)) {
          // Data streaming from source to the target.
          DataFields dataFromFetch = this.fetchDataFromJSON(st);
          if (dataFromFetch != null) {
            // Format Print JSON Array of results
            System.out.println(new JSONObject(dataFromFetch).toString(2));
          }
        }
      }
      // Ensure file is released when streaming finished.
      br.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private DataFields fetchDataFromJSON(String jsonStr) {
    try {
      JSONObject dataObj = new JSONObject(jsonStr).getJSONObject("data");
      DataFields requiredFields = new DataFields();
      requiredFields.setTitle(dataObj.getString("title"));
      requiredFields.setFundsRaisedPercent(dataObj.getNumber("funds_raised_percent"));
      requiredFields.setCloseDate(dataObj.getString("close_date"));
      return requiredFields;
    } catch (JSONException e) {
      // FIXME: Avoid printing too much parsing error stacks.
      // e.printStackTrace();
      return null;
    }
  }
}
