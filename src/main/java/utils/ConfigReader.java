package utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
    private static Properties properties;
    private static FileInputStream fileIn;
    private static FileOutputStream fileOut;

// Static block to load the properties file once at runtime -> "properties" variable always has value -> not null
    static {
        //Tạo đường dẫn đến file configs.properties mặc định
        String filePath = "src/test/resources/config.properties";
        try {
            //Khởi tạo giá trị cho đối tượng của class FileInputStream
            fileIn = new FileInputStream(filePath);
            properties = new Properties();
            //Load properties file
            properties.load(fileIn);
        } catch (IOException e) {
            System.err.println("Failed to load config.properties file.");
            e.printStackTrace();
        }
    }

    //Xây dựng hàm Get Value từ Key của file properties đã setup bên trên
    public static String getPropValue(String key) {
        String value = properties.getProperty(key);
        if (value == null) {
            throw new RuntimeException("Key '" + key + "' not found in config.properties");
        }
        return value;
    }
}

