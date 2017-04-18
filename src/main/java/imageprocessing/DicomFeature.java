package imageprocessing;

import net.semanticmetadata.lire.imageanalysis.features.GlobalFeature;
import net.semanticmetadata.lire.imageanalysis.features.LireFeature;
import org.dcm4che2.data.DicomElement;
import org.dcm4che2.data.DicomObject;
import org.dcm4che2.io.DicomInputStream;
import org.dcm4che2.util.TagUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

/**
 * Created by tomassostak on 04/04/2017.
 */
public class DicomFeature implements GlobalFeature {

    private int[] selectedFeatures;

    public DicomFeature() {
        this.selectedFeatures = new int[]{
                524322,
                524338,
                528432
        };
    }

    @Override
    public void extract(BufferedImage bufferedImage) {
        DicomObject dicomObject = null;
        DicomInputStream din = null;

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ImageIO.write(bufferedImage, "dicom", baos);
        } catch (IOException e) {
            e.printStackTrace();
        }
        InputStream is = new ByteArrayInputStream(baos.toByteArray());

        try {
            din = new DicomInputStream(is);
            dicomObject = din.readDicomObject();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                din.close();
            } catch (IOException ignore) {
            }
        }
        Iterator<DicomElement> iter = dicomObject.datasetIterator();
        while(iter.hasNext()) {
            DicomElement element = iter.next();
            int tag = element.tag();
            try {
                String tagName = dicomObject.nameOf(tag);
                String tagAddr = TagUtils.toString(tag);
                String tagVR = dicomObject.vrOf(tag).toString();
                if (tagVR.equals("SQ")) {
                    if (element.hasItems()) {
                        System.out.println(tagAddr +" ["+  tagVR +"] "+ tagName);
                        //listHeader(element.getDicomObject());
                        continue;
                    }
                }
                String tagValue = dicomObject.getString(tag);
                System.out.println(tag + " @ " +  tagAddr +" ["+ tagVR +"] "+ tagName +" ["+ tagValue+"]");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String getFeatureName() {
        return null;
    }

    @Override
    public String getFieldName() {
        return null;
    }

    @Override
    public byte[] getByteArrayRepresentation() {
        return new byte[0];
    }

    @Override
    public void setByteArrayRepresentation(byte[] bytes) {

    }

    @Override
    public void setByteArrayRepresentation(byte[] bytes, int i, int i1) {

    }

    @Override
    public double getDistance(LireFeature lireFeature) {
        return 0;
    }

    @Override
    public double[] getFeatureVector() {
        return new double[0];
    }
}
