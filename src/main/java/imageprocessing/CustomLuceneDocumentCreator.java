package imageprocessing;

import net.semanticmetadata.lire.builders.GlobalDocumentBuilder;
import net.semanticmetadata.lire.imageanalysis.features.global.AutoColorCorrelogram;
import net.semanticmetadata.lire.imageanalysis.features.global.CEDD;
import net.semanticmetadata.lire.imageanalysis.features.global.FCTH;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.dcm4che2.tool.dcm2jpg.Dcm2Jpg;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Lucene document creator.
 */
public class CustomLuceneDocumentCreator {

  public static Document createLuceneDocument(String imageFilePath) {
    GlobalDocumentBuilder globalDocumentBuilder = new GlobalDocumentBuilder(false, false);

    globalDocumentBuilder.addExtractor(CEDD.class);
    globalDocumentBuilder.addExtractor(FCTH.class);
    globalDocumentBuilder.addExtractor(AutoColorCorrelogram.class);

    BufferedImage img = null;
    File source = new File(imageFilePath);
    File destination = new File("temporary.jpg");
    try {
      Dcm2Jpg dcm2Jpg = new Dcm2Jpg();
      dcm2Jpg.convert(source, destination);
      img = ImageIO.read(new FileInputStream(destination));
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
