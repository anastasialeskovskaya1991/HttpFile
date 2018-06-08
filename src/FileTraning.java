import java.io.File;
import java.io.IOException;


public class FileTraning {
    public static void main(String[] args) throws IOException {
      //  String getPath1 = args[0];
//        String getPath2 = args[1];
        String getPath1 = "d://download//";
        String getPath2 =  "d://EPAM//MyFile1.html";


        //File folder = new File("d://download//");
        // File folder = new File("d://EPAM//file/");

        File folder = new File(getPath1);
        File[] listOfFiles = folder.listFiles();
        FileManager fileManager = new FileManager(listOfFiles);
        fileManager.fileWriterToHtml(getPath2);
    }
}

