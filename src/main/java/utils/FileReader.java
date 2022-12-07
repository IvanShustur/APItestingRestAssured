package utils;


import com.fasterxml.jackson.databind.ObjectMapper;


import java.io.File;

public class FileReader {

    private FileReader(){}

    public static <T> T readObject(String filePath, Class<T> tClass) {
        T t = null;
    try{
            ObjectMapper om = new ObjectMapper();
            t = om.readValue(new File(filePath), tClass);
        }catch (Exception e){
            e.printStackTrace();
        }
      return t;
    }
}
