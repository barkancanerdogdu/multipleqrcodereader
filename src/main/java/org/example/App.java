package org.example;


import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.multi.MultipleBarcodeReader;
import com.google.zxing.multi.qrcode.QRCodeMultiReader;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Hello world!
 *
 */
public class App
{
    public static void main( String[] args )
    {
        int count = 0;
        String imagePath = "src/qr.png";
        try {
            BufferedImage image = ImageIO.read(new File(imagePath));
            LuminanceSource source = new BufferedImageLuminanceSource(image);
            BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

            MultipleBarcodeReader reader = new QRCodeMultiReader();

            Map<DecodeHintType, Object> hints = new HashMap<>();
            hints.put(DecodeHintType.TRY_HARDER, Boolean.TRUE);
            hints.put(DecodeHintType.PURE_BARCODE, Boolean.FALSE);
            hints.put(DecodeHintType.POSSIBLE_FORMATS, BarcodeFormat.QR_CODE);

            Result[] results = reader.decodeMultiple(bitmap, hints);

            if (results.length == 0) {
                System.out.println("QR code not found.");
            } else {
                for (Result result : results) {
                    System.out.println(result.getText());
                    count ++;
                }
            }
            System.out.println(count + "QR messages have been found.");

        } catch (IOException e) {
            System.out.println("Image not readable: " + e.getMessage());
        } catch (NotFoundException e) {
            System.out.println("QR code not found.");
        }
    }


}
