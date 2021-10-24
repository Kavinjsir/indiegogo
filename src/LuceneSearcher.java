import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
// import org.apache.lucene.search.TopDocs;

import java.io.*;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;

public class LuceneSearcher {
  private static StandardAnalyzer analyzer = new StandardAnalyzer();

  private IndexWriter writer;
  private ArrayList<File> queue = new ArrayList<File>();

  /**
   * Constructor
   * @param indexDir the name of the folder in which the index should be created
   * @throws java.io.IOException when exception creating index.
   */
  public LuceneSearcher(String indexDir) throws IOException {
    // the boolean true parameter means to create a new index everytime, 
    // potentially overwriting any existing files there.
    FSDirectory dir = FSDirectory.open(Paths.get(indexDir));
    IndexWriterConfig config = new IndexWriterConfig(analyzer);
    writer = new IndexWriter(dir, config);
  }

  /**
   * Indexes a file or directory
   * @param fileName the name of a text file or a folder we wish to add to the index
   * @throws java.io.IOException when exception
   */
  public void indexFileOrDirectory(String fileName) throws IOException {
    //===================================================
    //gets the list of files in a folder (if user has submitted
    //the name of a folder) or gets a single file name (is user
    //has submitted only the file name) 
    //===================================================
    addFiles(new File(fileName));

    JSONScanner jScanner = new JSONScanner();
    
    int originalNumDocs = writer.getDocStats().numDocs;
    for (File f : queue) {
      FileReader fr = null;
      try {

        //===================================================
        // add contents of file
        //===================================================
        fr = new FileReader(f);
        // doc.add(new TextField("contents", fr));

        // Create buffer to stream data to the file reader.
        BufferedReader br = new BufferedReader(fr, 1024 * 8192);
        String st;
        while((st = br.readLine()) != null) {
          Document doc = new Document();
          String jsonTitle = jScanner.getTitle(st);
          doc.add(new TextField("contents", jsonTitle, Field.Store.YES));
          doc.add(new StringField("path", f.getPath(), Field.Store.YES));
          doc.add(new StringField("filename", f.getName(), Field.Store.YES));
          writer.addDocument(doc);
        }
        
        br.close();

        System.out.println("Added: " + f);
      } catch (Exception e) {
        System.out.println("Could not add: " + f);
      } finally {
        fr.close();
      }
    }
    
    int newNumDocs = writer.getDocStats().numDocs;
    System.out.println("");
    System.out.println("************************");
    System.out.println((newNumDocs - originalNumDocs) + " documents added.");
    System.out.println("************************");

    queue.clear();
  }

  /**
   * Close the index.
   * @throws java.io.IOException when exception closing
   */
  public void closeIndex() throws IOException {
    writer.close();
  }

  private void addFiles(File file) {

    if (!file.exists()) {
      System.out.println(file + " does not exist.");
    }
    if (file.isDirectory()) {
      for (File f : file.listFiles()) {
        addFiles(f);
      }
    } else {
      String filename = file.getName().toLowerCase();
      //===================================================
      // Only index text files
      //===================================================
      if (filename.endsWith(".json")) {
        queue.add(file);
      } else {
        System.out.println("Skipped " + filename);
      }
    }
  }

  public void initIndex(String docsFile) {
    //===================================================
    //read input from user until he enters q for quit
    //===================================================
    try {
      //try to add file into the index
      indexFileOrDirectory(docsFile);
    } catch (Exception e) {
      System.out.println("Error indexing " + docsFile + " : " + e.getMessage());
    }

    //===================================================
    //after adding, we always have to call the
    //closeIndex, otherwise the index is not created    
    //===================================================
    try {
      closeIndex();
    } catch (Exception e) {
      //TODO: handle exception
      System.out.println("Failed to close indexer" + e);
    }


  }

  public void search(String keyword, String indexPath) {
    try {
      Instant startV = Instant.now();
      DirectoryReader reader = DirectoryReader.open(FSDirectory.open(Paths.get(indexPath)));
      IndexSearcher searcher = new IndexSearcher(reader);
      TopScoreDocCollector collector = TopScoreDocCollector.create(5, 5);

      Query q = new QueryParser("contents", analyzer).parse(keyword);
      searcher.search(q, collector);
      ScoreDoc[] hits = collector.topDocs().scoreDocs;

      Instant stopV = Instant.now();
      System.out.println("--Lucene Search--->" + Duration.between(startV, stopV).getNano());
      // 4. display results
      System.out.println("--Lucene Search--->" + "Found " + hits.length + " hits.");
      for(int i = 0; i < hits.length; ++i) {
        int docId = hits[i].doc;
        Document d = searcher.doc(docId);
        System.out.println("--Lucene Search--->" + (i + 1) + ". " + d.get("path") + " score=" + hits[i].score);
      }
    } catch (Exception e) {
      System.out.println("Error searching " + keyword + " : " + e.getMessage());
    }
  }

}
