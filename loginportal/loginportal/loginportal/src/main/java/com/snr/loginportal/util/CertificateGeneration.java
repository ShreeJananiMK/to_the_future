package com.snr.loginportal.util;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfDocument;
import com.snr.loginportal.dto.CertificateDetails;
import com.snr.loginportal.model.StudentRegistration;
import com.snr.loginportal.service.implement.StudentRegistrationServiceImpl;
import jakarta.annotation.PostConstruct;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;
import jakarta.persistence.Column;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class CertificateGeneration {

    private final Logger logger = LoggerFactory.getLogger(CertificateGeneration.class);

    @Autowired
    private MailHandler mailHandler;

    private BufferedImage certificateImage;

    private final Map<String, byte[]> imageCache = new ConcurrentHashMap<>();

    private final String url = "https://raw.githubusercontent.com/ShreeJananiMK/to_the_future/ShreeJananiMK-certificate-template/certificate_of_appreciation_snr_college.jpg";

    //generate and send mail
    public void generateAndSendCertificates(CertificateDetails certificateDetails) throws Exception {
        logger.info("The certificate info  : ---- : {}", certificateDetails);
        if (!imageCache.containsKey("certificate")) {
            loadCertificateImage();
            logger.info("The certificate info  : ---- : Success");
        }
        byte[] certificateBytes = imageCache.get("certificate");
        ByteArrayInputStream bytes = new ByteArrayInputStream(certificateBytes);
        logger.info("The certificate byte : ---- : {}", bytes);
        BufferedImage customizedImage = ImageIO.read(bytes);
        BufferedImage certificate = overlayText(customizedImage, certificateDetails);
        logger.info("The certificate byte : ---- : check 1" );
        byte[] pdfBytes = convertImageToPdf(certificate);
        logger.info("The certificate byte : ---- : check 2" );
        mailHandler.sendEmailWithAttachment(certificateDetails.getEmail(), pdfBytes);
    }


    // Download image only once when the application starts
    @PostConstruct
    public void loadCertificateImage() {
        try {
            byte[] imageBytes = fetchImageFromUrl(url);
            logger.info("The certificate info  : ---- : OK");
            saveImageToFile(imageBytes);
            imageCache.put("certificate", imageBytes);
            System.out.println("Certificate image loaded and cached.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //fetching from url
    public byte[] fetchImageFromUrl(String url) throws IOException {
        logger.info("The url info  : ---- : {}",url);
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             InputStream inputStream = new URL(url).openStream()) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                baos.write(buffer, 0, bytesRead);

            }
            return baos.toByteArray();
        }
    }

    //saving the image
    public void saveImageToFile(byte[] imageBytes) {
        try (FileOutputStream fos = new FileOutputStream("debug_image.png")) {
            fos.write(imageBytes);
            fos.flush();
            System.out.println("Image saved as debug_image.png");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Inserting text dynamically
    public BufferedImage overlayText(BufferedImage image, CertificateDetails certificateDetails) {
        BufferedImage newImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = newImage.createGraphics();
        g.drawImage(image, 0, 0, null);
        g.setColor(Color.BLACK);

        //calculating space for name
        int nameX1 = 650 ;
        int nameX2 = 1400;
        int nameY = 590;
        int nameCenterX = (nameX1 + nameX2) / 2;
        int nameMaxWidth = nameX2 - nameX1;
        drawAutoFittedText(g, certificateDetails.getStudentName(), nameCenterX, nameY, nameMaxWidth, 30, 24);

        //calculating space for college
        int collegeX1 = 150;
        int collegeX2 = 1000;
        int collegeY = 660;
        int collegeCenterX = (collegeX1 + collegeX2) / 2;
        int collegeMaxWidth = collegeX2 - collegeX1;
        drawAutoFittedText(g, certificateDetails.getCollegeName(), collegeCenterX, collegeY, collegeMaxWidth, 30, 22);

        //calculating space for event
        int eventX1 = 250;
        int eventX2 = 1000;
        int eventY = 730;
        int eventCenterX = (eventX1 + eventX2) / 2;
        int eventMaxWidth = eventX2 - eventX1;
        drawAutoFittedText(g, certificateDetails.getEventName(), eventCenterX, eventY, eventMaxWidth, 30 , 22);

        String formattedDate = convertDateFormat(certificateDetails.getDate());
        drawAutoFittedText(g, formattedDate, 1325, 730, 150, 36, 20);

        //calculating space for coordinator
        int coordinatorX1 = 100;
        int coordinatorX2 = 400;
        int coordinatorY = 1070;
        int coordinatorCenterX = (coordinatorX1 + coordinatorX2) / 2;
        int coordinatorMaxWidth = coordinatorX2 - coordinatorX1;
        drawAutoFittedText(g, certificateDetails.getCoordinatorName(), coordinatorCenterX, coordinatorY, coordinatorMaxWidth, 26, 22);
        g.dispose();
        saveImage(newImage, "certificate_with_text.png");
        return newImage;
    }

    public void drawAutoFittedText(Graphics2D g,  String text, int centerX, int y, int maxWidth, int maxFontSize, int minFontSize) {
        Font font = new Font("Open Sans", Font.BOLD, maxFontSize);
        FontMetrics metrics = g.getFontMetrics(font);

        // Shrink the font until it fits within maxWidth or hits minFontSize
        while (metrics.stringWidth(text) > maxWidth && font.getSize() > minFontSize) {
            font = font.deriveFont((float) (font.getSize() - 1));
            metrics = g.getFontMetrics(font);
        }

        int textWidth = metrics.stringWidth(text);
        int x = centerX - (textWidth / 2); // Centering the text based on its width

        g.setFont(font);
        g.drawString(text, x, y);
    }

    // Method to save a BufferedImage to a file in the working directory
    public void saveImage(BufferedImage image, String fileName) {
        try {
            File outputFile = new File(fileName);
            // "png" can be replaced with "jpg" if you prefer that format
            ImageIO.write(image, "png", outputFile);
            System.out.println("Image saved as " + outputFile.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public byte[] convertImageToPdf(BufferedImage image) throws Exception {

        ByteArrayOutputStream pdfBaos = new ByteArrayOutputStream();

        // Convert BufferedImage to ByteArray
        ByteArrayOutputStream imageBaos = new ByteArrayOutputStream();
        ImageIO.write(image, "png", imageBaos);
        byte[] imageBytes = imageBaos.toByteArray();

        // Load the image as iText Image
        Image imagePdf = Image.getInstance(imageBytes);

        // Get image dimensions
        float width = imagePdf.getWidth();
        float height = imagePdf.getHeight();

        // Set page size exactly as the image size, remove all margins
        Document document = new Document(new com.itextpdf.text.Rectangle(width, height), 0, 0, 0, 0);
        PdfWriter.getInstance(document, pdfBaos);
        document.open();

        // Place image exactly on the page without scaling
        imagePdf.setAbsolutePosition(0, 0); // Start from bottom-left corner
        document.add(imagePdf);

        document.close();

        return pdfBaos.toByteArray();

    }

    //Formatting date
    public String convertDateFormat(String inputDate) {
        try {
            SimpleDateFormat fromFormat = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat toFormat = new SimpleDateFormat("dd-MM-yyyy");

            Date date = fromFormat.parse(inputDate);
            return toFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            System.out.println(inputDate);
            return inputDate; // return original if parsing fails
        }
    }
}
