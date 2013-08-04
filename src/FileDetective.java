import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class FileDetective {

    private String cppFilesDirectoryPath;
    private String objFilesDirectoryPath;

    public FileDetective(String cppDir, String objDir) {
        cppFilesDirectoryPath = cppDir;
        objFilesDirectoryPath = objDir;
    }

    public List<String> findCppsWithoutObjs() {
        File[] cppFiles = new File(cppFilesDirectoryPath).listFiles(new FileFilter() {
            @Override
            public boolean accept(File file) {
                return file.getName().contains(".cpp");
            }
        });

        File[] objFiles = new File(objFilesDirectoryPath).listFiles(new FileFilter() {
            @Override
            public boolean accept(File file) {
                return file.getName().contains(".o");
            }
        });

        Set<String> cpps = removeExtensions(cppFiles);
        Set<String> objs = removeExtensions(objFiles);

        cpps.removeAll(objs);
        ArrayList<String> cppList = new ArrayList<String>(cpps);

        for(int i = 0; i < cppList.size(); i++){
            cppList.set(i, cppList.get(i) + ".cpp");
        }

        return cppList;
    }

    private Set<String> removeExtensions(File[] files) {
        Set<String> extensionless = new TreeSet<String>();
        for (File file : files) {
            String fileName = file.getName();
            extensionless.add(fileName.substring(0, fileName.indexOf('.')));
        }
        return extensionless;
    }

    private static void saveResult(Iterable<String> cpps) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("Cpps without objs.txt"));
        for (String cpp : cpps) {
            writer.write(cpp);
            writer.newLine();
        }
        writer.close();
    }

    public static void main(String[] args) throws IOException {
        FileDetective detective = new FileDetective("C:\\Users\\serg\\Desktop\\gcc_POLYGON\\good",
                "C:\\Users\\serg\\Desktop\\gcc_POLYGON\\ccc");
        Iterable<String> cpps = detective.findCppsWithoutObjs();
        saveResult(cpps);
    }
}
