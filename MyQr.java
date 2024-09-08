import java.util.Scanner;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.util.Hashtable;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.ByteMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

public class MyQr {

    public static void createQR(String data, String path,String charset, Hashtable <EncodeHintType, Object> hashMap,int height, int width) throws IOException, WriterException
    {

        ByteMatrix matrix = new MultiFormatWriter().encode(data, BarcodeFormat.QR_CODE, width, height, hashMap);

        int widthMatrix = matrix.getWidth();
        int heightMatrix = matrix.getHeight();

        BufferedImage image = new BufferedImage(widthMatrix, heightMatrix, BufferedImage.TYPE_INT_RGB);

        for (int x = 0; x < widthMatrix; x++) {
            for (int y = 0; y < heightMatrix; y++) {
                image.setRGB(x, y, matrix.get(x, y) == 0 ? 0x000000 : 0xFFFFFF);
            }
        }

        ImageIO.write(image, "png", new File(path));

    }

    public static void main(String[] args) throws IOException, WriterException
    
    {

        Scanner input = new Scanner(System.in);
        System.out.println("Enter data to create the QR: ");
        

        String data = input.nextLine(); 
        String path = "QR.png";   
        String charset = "UTF-8";

        Hashtable<EncodeHintType, Object> hashMap = new Hashtable<>();
        hashMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);

        createQR(data, path, charset, hashMap, 200, 200);
        System.out.println("QR Code Generated!!! ");
    }

}