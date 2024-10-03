import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.UnsupportedEncodingException;
import java.util.Hashtable;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.ByteMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

public class GUIQRGenerator extends JFrame {
   
    private JTextField inputField;
    private JButton generateButton;
    private JLabel qrCodeLabel;

    public GUIQRGenerator() {
        
        setTitle("QR Code Generator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel();
        
        inputField = new JTextField(20);
        
        generateButton = new JButton("Generate QR");
        
        inputPanel.add(inputField);
        inputPanel.add(generateButton);
        add(inputPanel, BorderLayout.NORTH);

        qrCodeLabel = new JLabel();
        qrCodeLabel.setHorizontalAlignment(JLabel.CENTER);
        add(qrCodeLabel, BorderLayout.CENTER);

        generateButton.addActionListener(new ActionListener() {
        
            public void actionPerformed(ActionEvent e) {
                generateQRCode();
            }
        });

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void generateQRCode() {
        
        String data = inputField.getText();
        if (data.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter some data to generate QR code.");
            return;
        }

        try {
            BufferedImage qrImage = createQR(data);
            ImageIcon icon = new ImageIcon(qrImage);
            qrCodeLabel.setIcon(icon);
            pack();
        } catch (WriterException | UnsupportedEncodingException ex) {
            JOptionPane.showMessageDialog(this, "Error generating QR code: " + ex.getMessage());
        }
    }

    private BufferedImage createQR(String data) throws WriterException, UnsupportedEncodingException {

        String charset = "UTF-8";

        Hashtable<EncodeHintType, ErrorCorrectionLevel> hintMap = new Hashtable<>();
        hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);

        ByteMatrix matrix = new MultiFormatWriter().encode(
            new String(data.getBytes(charset), charset),
            BarcodeFormat.QR_CODE, 200, 200, hintMap);

        int width = matrix.getWidth();
        int height = matrix.getHeight();

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, matrix.get(x, y) == 0 ? 0x000000 : 0xFFFFFF);
            }
        }

        return image;
    }

    public static void main(String[] args) {

    GUIQRGenerator guiRun = new GUIQRGenerator();

    }
}