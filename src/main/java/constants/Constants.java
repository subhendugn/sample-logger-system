package constants;

import util.ConfigReader;

public class Constants {
    // The output file path is read from the config.xml file in the resources folder
    public static final String outputFilePath = ConfigReader.getOutputFilePath();
}