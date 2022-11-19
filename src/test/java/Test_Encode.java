import org.junit.Test;

import java.io.*;
import java.nio.file.Files;
import java.util.Arrays;

public class Test_Encode {


    @Test
    public void fileRead() throws IOException {
        String filepath = "C:\\Users\\JungHyungJin\\Downloads\\newtour\\workspace\\tourbest.erp\\src\\test\\java\\Server.java";

        File file = new File(filepath);
        byte[] fileContent = Files.readAllBytes(file.toPath());
        System.out.println(Arrays.toString(fileContent));
        String data = new String(fileContent);
        System.out.println(data.length());
        System.out.println(data);

//        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filepath),"EUC-KR"));
//
//        String line = reader.readLine();
//        while (line != null) {
//            System.out.println(line);
//            line = reader.readLine();
//        }
//
//        reader.close();
    }
}
