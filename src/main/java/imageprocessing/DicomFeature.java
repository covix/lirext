package imageprocessing;

/**
 * Created by Aleksandar on 19.04.2017.
 */
public enum DicomFeature {

  INSTITUTION_NAME(524416),

  BODY_PART_EXMINED(1572885),

  PATIENT_SEX(1048640),

  PATIENT_BIRTH_DATE(1048624),

  PATIENT_NAME(1048592);

  private int featureNumber;

  DicomFeature(int featureNumber) {
    this.featureNumber = featureNumber;
  }

  public int getFeatureNumber() {
    return featureNumber;
  }
}
