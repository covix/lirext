package imageprocessing;

import javax.swing.*;
import java.applet.Applet;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * Created by tomassostak on 29/03/2017.
 */
public class Main extends Applet {

  public void init() {

    setSize(370, 100);

    JButton openButton = new JButton("Open");
    final JLabel statusbar = new JLabel("Select a library and DICOM image:");
    final JLabel instructions = new JLabel("Check console for the output results.");

    String[] algorithms = { "Dcm4che", "ImageJ" };
    final JComboBox select = new JComboBox(algorithms);

    openButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent ae) {
        InputStream inputStream = null;
        DicomFeatureExtractor imageProcessor;
        try {
          JFileChooser chooser = new JFileChooser();
          int option = chooser.showOpenDialog(imageprocessing.Main.this);
          if (option == JFileChooser.APPROVE_OPTION) {
            File sf = chooser.getSelectedFile();
            String fileList = sf.getName();
            File file = chooser.getCurrentDirectory();
            String fullpath = file.getCanonicalPath() + "/" + fileList;

            /* Algorithm layer */
            inputStream = new FileInputStream(new File(fullpath));
            imageProcessor = new DicomFeatureExtractor();
            System.out.println(imageProcessor.extractFeature(inputStream, 1048608) + "\n");
            /* Algorithm layer */
          }
        } catch (Exception exception) {
          exception.printStackTrace();
        }
      }
    });

    this.add(statusbar);
    this.add(select);
    this.add(openButton);
    this.add(instructions);
  }
}

