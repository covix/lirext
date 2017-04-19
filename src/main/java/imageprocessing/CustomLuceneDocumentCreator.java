package imageprocessing;

import net.semanticmetadata.lire.builders.GlobalDocumentBuilder;
import net.semanticmetadata.lire.imageanalysis.features.global.AutoColorCorrelogram;
import net.semanticmetadata.lire.imageanalysis.features.global.CEDD;
import net.semanticmetadata.lire.imageanalysis.features.global.FCTH;
import org.apache.commons.io.IOCase;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.SuffixFileFilter;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Lucene document creator.
 */
public class CustomLuceneDocumentCreator {

  public static Document createLuceneDocument(String imageFilePath) {
    GlobalDocumentBuilder globalDocumentBuilder = new GlobalDocumentBuilder(false, false);

//    globalDocumentBuilder.addExtractor(CEDD.class);
//    globalDocumentBuilder.addExtractor(FCTH.class);
//    globalDocumentBuilder.addExtractor(AutoColorCorrelogram.class);

    BufferedImage img = null;
    FileInputStream fileInputStream = null;
    try {
      fileInputStream = new FileInputStream(new File(imageFilePath));
      img = ImageIO.read(fileInputStream);
    } catch (IOException e) {
      e.printStackTrace();
    }
    Document document = globalDocumentBuilder.createDocument(img, imageFilePath);

    DicomFeatureExtractor dicomFeatureExtractor = new DicomFeatureExtractor();
    String extractedFeature =
            dicomFeatureExtractor.extractFeature(imageFilePath, DicomFeature.PATIENT_BIRTH_DATE);

    document.add(new StringField(DicomFeature.PATIENT_BIRTH_DATE.name(), extractedFeature, Field.Store.YES));
    return document;
  }

}
