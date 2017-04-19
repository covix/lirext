package imageprocessing;

import org.dcm4che2.data.DicomObject;
import org.dcm4che2.io.DicomInputStream;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Dicom feature extractor.
 */
public class DicomFeatureExtractor {

  public Map<String, String> extractFeatures(String imagePath, DicomFeature[] features) {
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

    Map<String, String> extractedFeatures = new HashMap<>();
    for (int i = 0; i < features.length; i++) {
      int featureNumber = features[i].getFeatureNumber();
      String extractedFeatureValue = dicomObject.getString(featureNumber);
      if (extractedFeatureValue != null) {
        extractedFeatures.put(features[i].name(), extractedFeatureValue);
      }
    }
    return extractedFeatures;
  }

}
