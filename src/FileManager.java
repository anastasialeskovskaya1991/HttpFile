import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class FileManager {

    private File[] listOfFiles;
    private ArrayList<String> fileType = new ArrayList<>();
    private ArrayList<String>  dateCreated = new ArrayList<>();
    private  ArrayList<Long>  kilobytes = new ArrayList<>();
    private int size;

    public FileManager(File[] files)  {
        try {
            this.listOfFiles = files;
            size = listOfFiles.length;
            generateData();
            System.out.println("Yes!");
         //   fileWriterToHtml();
        }catch (IOException | NullPointerException e){
            System.out.println( "Файл не найден");
        }
    }
    public void generateData() throws IOException {
        for (int i = 0; i < listOfFiles.length; i++) {

            BasicFileAttributes attr = Files.readAttributes(listOfFiles[i].toPath(), BasicFileAttributes.class);
            FileTime date = attr.creationTime();
            SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
            dateCreated.add(df.format(date.toMillis()));

            if (listOfFiles[i].isFile()) {
                long bytes = listOfFiles[i].length();
                long kb = bytes / 1024;
                kilobytes.add(kb);
                fileType.add("FILE");
                // System.out.println("File!");

            } else if (listOfFiles[i].isDirectory()) {
                kilobytes.add( (getDirSize(listOfFiles[i])/1024));
                fileType.add("DIR");

            }
        }
    }
    // Получение размена каталога в байтах
    long getDirSize(File dir) {
        long size = 0;
        if (dir.isFile()) {
            size = dir.length();
        } else {
            File[] subFiles = dir.listFiles();
            for (File file : subFiles) {
                if (file.isFile()) {
                    size += file.length();
                } else {
                    size += getDirSize(file);
                }
            }
        }
        return size;
    }
    public void fileWriterToHtml(String pathForHtmlFile) throws IOException {
        FileWriter writer = new FileWriter(pathForHtmlFile);
        String line1 = "<html>\n" +
                "\t<head>\n" +
                "\t<meta charset=\"utf-8\">\n" +
                "\t<title>  </title>\n" +
                "\t<style type=\"text/css\">\n" +
                "    table {\n" +
                "    border-collapse: collapse; /* Убираем двойные линии между ячейками */\n" +
                "   }\n" +
                "  \n" +
                "  </style>\n" +
                "\t</head>\n" +
                "\t<body bgcolor='#F0FFFF'> \n" +
                "\t\t\n" +
                "\t\t<p><table border=\"1\">\n" +
                "\t\t<tr>\n" +
                "\t\t\t<th>ИМЯ</th>\n" +
                "\t\t\t<th>ТИП</th>\n" +
                "\t\t\t<th>ДАТА СОЗДАНИЯ</th>\n" +
                "\t\t\t<th>РАЗМЕР (В КВ)</th>\n" +
                "\t\t</tr>\n";

        for(int i = 0; i<size; i++){
            String line2 = "\t\t<tr>\n" + "\t\t\t<td>" + listOfFiles[i].getName() + "</td>\n" +
                    "\t\t\t<td>" + fileType.get(i) + "</td>\n" +
                    "\t\t\t<td>"+dateCreated.get(i)+"</td>\n" +
                    "\t\t\t<td>"+kilobytes.get(i)+"</td>\n" +  "\t\t</tr>\n";
            line1 = line1.concat(line2);
        }
        //System.out.println(line1);
        String line3 = "\t\t</table>\n" + "\t\t</p>\n" + "\t</body>\n" + "</html>";
        //System.out.println(line1+line3);
        writer.write(line1+line3);
        writer.flush();
        writer.close();
    }
}















