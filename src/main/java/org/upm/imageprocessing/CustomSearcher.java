package org.upm.imageprocessing;

import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.store.FSDirectory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Custom searcher class.
 */
public class CustomSearcher {

  public static void main(String[] args) throws IOException {
    // Checking if arg[0] is there and if it is an image.
    BufferedImage image = null;
    boolean passed = false;
    if (args.length > 0) {
      File f = new File(args[0]);
      if (f.exists()) {
        try {
          image = ImageIO.read(f);
          passed = true;
        } catch (IOException e) {
          e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
      }
    }
    if (!passed) {
      System.out.println("No image given as first argument.");
      System.out.println("Run \"Searcher <query image>\" to search for <query image>.");
      System.exit(1);
    }

    Path indexPath = Paths.get("index");
    IndexReader indexReader = DirectoryReader.open(FSDirectory.open(indexPath));


  }
}
