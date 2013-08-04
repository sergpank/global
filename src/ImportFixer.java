import java.io.*;

public class ImportFixer {

    static String badCppFilesPath = "C:\\Users\\serg\\Desktop\\gcc_POLYGON\\runs";
    static String goodCppFilesPath = "C:\\Users\\serg\\Desktop\\gcc_POLYGON\\good_2";

    public static void main(String[] args) throws IOException {
        BufferedReader cppListReader = new BufferedReader(new FileReader("Cpps without objs.txt"));
        String fileName;
        while((fileName = cppListReader.readLine()) != null){
            File badCppFile = new File(badCppFilesPath + "\\" + fileName);
            File goodCppFile = new File(goodCppFilesPath + "\\" + fileName);

            BufferedReader badReader = new BufferedReader(new FileReader(badCppFile));
            BufferedWriter goodWriter = new BufferedWriter(new FileWriter(goodCppFile));

            goodWriter.write("#include <stdio.h>");
            goodWriter.newLine();

            String line;
            while((line=badReader.readLine()) != null){
                goodWriter.write(line);
                goodWriter.newLine();
            }
            goodWriter.close();
            badReader.close();
        }
        cppListReader.close();
    }
}
