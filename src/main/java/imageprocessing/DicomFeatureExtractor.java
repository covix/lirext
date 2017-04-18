package imageprocessing;

import org.dcm4che2.data.DicomObject;
import org.dcm4che2.io.DicomInputStream;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by tomassostak on 04/04/2017.
 */
public class DicomFeatureExtractor {

    public String extractFeature(InputStream imageStream, int featureName) {
        DicomObject dicomObject = null;
        DicomInputStream din = null;

        try {
            din = new DicomInputStream(imageStream);
            dicomObject = din.readDicomObject();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                din.close();
            } catch (IOException ignore) {
            }
        }
        return dicomObject.getString(featureName);
    }

}
