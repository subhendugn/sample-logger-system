package util;

import constants.Constants;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileUtil {

    // Method to read the timestamp format from the XML configuration file
    public static String getTimestampFormat(String filePath) {
        try {
            // Create a DocumentBuilderFactory and DocumentBuilder to parse the XML file
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(filePath);

            // Normalize the XML structure
            document.getDocumentElement().normalize();

            // Get the root element and retrieve the timestamp format
            Element root = document.getDocumentElement();
            return root.getElementsByTagName("timestampFormat").item(0).getTextContent();
        } catch (Exception e) {
            e.printStackTrace();
            // Return a default timestamp format in case of an error
            return "yyyy-MM-dd HH:mm:ss";
        }
    }

    // Method to write a message to a file
    public static void writeOutputToFile(String message) {
        File file = new File(Constants.outputFilePath);
        try {
            // Create the file if it does not exist
            if (!file.exists()) {
                file.createNewFile();
            }

            // Write the message to the file
            BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
            writer.write(message);
            writer.newLine();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}