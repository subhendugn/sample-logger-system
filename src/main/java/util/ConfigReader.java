package util;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import java.io.InputStream;

public class ConfigReader {

    // Method to read the output file path from the XML configuration file
    public static String getOutputFilePath() {
        try {
            // Create a DocumentBuilderFactory and DocumentBuilder to parse the XML file
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            // Load the XML file from the classpath
            InputStream inputStream = ConfigReader.class.getClassLoader().getResourceAsStream("config.xml");
            Document document = builder.parse(inputStream);

            // Normalize the XML structure
            document.getDocumentElement().normalize();

            // Get the root element and retrieve the output file path
            Element root = document.getDocumentElement();
            return root.getElementsByTagName("outputFilePath").item(0).getTextContent();
        } catch (Exception e) {
            e.printStackTrace();
            // Return a default output file path in case of an error
            return "output.txt";
        }
    }
}