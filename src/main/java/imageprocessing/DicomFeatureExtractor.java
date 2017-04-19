package imageprocessing;

import org.dcm4che2.data.DicomObject;
import org.dcm4che2.io.DicomInputStream;

import java.io.*;

/**
 * Created by tomassostak on 04/04/2017.
 */
public class DicomFeatureExtractor {

  public String extractFeature(String imagePath, DicomFeature feature) {
    DicomObject dicomObject = null;
    DicomInputStream din = null;

    try {
      FileInputStream fileInputStream = new FileInputStream(imagePath);
      din = new DicomInputStream(fileInputStream);
      dicomObject = din.readDicomObject();
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        din.close();
      } catch (IOException ignore) {
      }
    }
    int featureNumber = feature.getFeatureNumber();
    return dicomObject.getString(featureNumber);
  }

}
