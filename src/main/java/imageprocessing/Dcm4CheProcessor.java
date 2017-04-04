package imageprocessing;

import org.dcm4che2.data.DicomObject;
import org.dcm4che2.io.DicomInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Dcm4che image processor.
 */
public class Dcm4CheProcessor {

  public String extractInformation(InputStream inputStream) {
    DicomObject dicomObject = null;
    DicomInputStream din = null;
    try {
      din = new DicomInputStream(inputStream);
      dicomObject = din.readDicomObject();
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        din.close();
      } catch (IOException ignore) {
      }
    }
    return dicomObject.toString();
  }
}
