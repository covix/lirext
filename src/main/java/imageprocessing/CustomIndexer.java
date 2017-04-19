package imageprocessing;

import org.apache.commons.io.IOCase;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.SuffixFileFilter;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.FSDirectory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Custom indexer implementation.
 */
public class CustomIndexer {
  public static void main(String[] args) throws IOException {
    boolean passed = false;
    if (args.length > 0) {
      File f = new File(args[0]);
      System.out.println("Indexing images in " + args[0]);
      if (f.exists() && f.isDirectory()) passed = true;
    }
    if (!passed) {
      System.out.println("No directory given as first argument.");
      System.out.println("Run \"Indexer <directory>\" to index files of a directory.");
      System.exit(1);
    }

    // Getting all images from a directory and its sub directories.
    ArrayList<String> images = getAllImages(new File(args[0]), true);

    // Creating an Lucene IndexWriter
    IndexWriterConfig indexWriterConfig = new IndexWriterConfig(new WhitespaceAnalyzer());
    Path indexPath = Paths.get("index");
    IndexWriter indexWriter = new IndexWriter(FSDirectory.open(indexPath), indexWriterConfig);

    for (Iterator<String> imagesIterator = images.iterator(); imagesIterator.hasNext(); ) {
      String imageFilePath = imagesIterator.next();
      System.out.println("Indexing " + imageFilePath);

      Document luceneDocument = CustomLuceneDocumentCreator.createLuceneDocument(imageFilePath);
      indexWriter.addDocument(luceneDocument);
    }

    indexWriter.close();
    System.out.println("Finished indexing.");
  }

  public static ArrayList<String> getAllImages(File directory, boolean descendIntoSubDirectories) throws IOException {
    ArrayList<String> resultList = new ArrayList(256);
    IOFileFilter includeSubdirectories = TrueFileFilter.INSTANCE;
    if(!descendIntoSubDirectories) {
      includeSubdirectories = null;
    }

    SuffixFileFilter fileFilter =
            new SuffixFileFilter(new String[]{".dcm", ".jpeg", ".png", ".gif"}, IOCase.INSENSITIVE);
    Iterator fileIterator = org.apache.commons.io.FileUtils.iterateFiles(directory, fileFilter, includeSubdirectories);

    while(fileIterator.hasNext()) {
      File next = (File)fileIterator.next();
      resultList.add(next.getCanonicalPath());
    }

    return resultList.size() > 0?resultList:null;
  }
}
