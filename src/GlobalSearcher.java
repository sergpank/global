import javax.swing.*;
import java.io.*;
import java.util.List;

public class GlobalSearcher extends SwingWorker {

    public static final String OBJ_EXT = ".obj";
    private String cppFilesDirectoryPath;
    private String objFilesDirectoryPath;
    private String outputFile;
    private JFrame gui;
    private ProgressDialog progressDialog;

    public GlobalSearcher(String cppFilesDirectoryPath, String outputFile, JFrame gui) {
        this.cppFilesDirectoryPath = cppFilesDirectoryPath;
        objFilesDirectoryPath = "C:\\Users\\serg\\Desktop\\gcc_POLYGON\\VC_NM";
        this.outputFile = outputFile.length() == 0 ? "report.txt" : outputFile;
        this.gui = gui;
    }

    @Override
    protected Object doInBackground() throws Exception {

        progressDialog = new ProgressDialog(gui);

//        generateObjectFiles();
//        analyzeObjectFiles();
        findGlobalVariables();

        progressDialog.dispose();

        return null;
    }

    private void generateObjectFiles() {
        List<String> cppsWithoutObjs = new FileDetective(cppFilesDirectoryPath, cppFilesDirectoryPath).findCppsWithoutObjs();
        progressDialog.setMaxCompilingProgress(cppsWithoutObjs.size());
        for (int i = 0; i < cppsWithoutObjs.size(); i++) {
            try {
                File currentFile = new File(cppFilesDirectoryPath + "\\" + cppsWithoutObjs.get(i));
                progressDialog.setCompileProgress(i + 1);
                progressDialog.setCompileMessage("Compiling file: " + currentFile.getName());
                System.out.print("Compiling file: " + currentFile.getName());
                Process exec = Runtime.getRuntime().exec(createCompileCommand(currentFile));
                exec.wait(7000);
//                int exitValue = exec.exitValue();
//                if(exitValue == 0){
//                    System.out.println(" OK");
//                } else{
//                    System.out.println(" ERROR");
//                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            progressDialog.setCompileMessage("Done.");
        }
    }

    private String createCompileCommand(File cppFile) {
        StringBuilder sb = new StringBuilder("cmd /c g++ -c ");
        try {
            sb.append(cppFile.getCanonicalPath());
//            sb.append(" -o ");
//            sb.append(cppFile.getCanonicalPath().replace(".cpp", ".o"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    private void analyzeObjectFiles() {
        File[] objFiles = new File(objFilesDirectoryPath).listFiles(new FileFilter() {
            @Override
            public boolean accept(File file) {
                return file.getName().contains(OBJ_EXT);
            }
        });
        progressDialog.setMaxAnalysisProgress(objFiles.length);
        for (int i = 0; i < objFiles.length; i++) {
            try {
                File currentFile = objFiles[i];
                progressDialog.setAnalysisProgress(i + 1);
                progressDialog.setAnalysisMessage("Analyzing file: " + currentFile.getName());
                System.out.println("Analyzing file: " + currentFile.getName());
                Process exec = Runtime.getRuntime().exec(createAnalyzeCommand(currentFile));
                exec.waitFor();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            progressDialog.setAnalysisMessage("Done.");
        }
    }

    private String createAnalyzeCommand(File objFile) {
        StringBuilder sb = new StringBuilder("cmd /c nm ");
        try {
            String outputFileName = "C:\\Users\\serg\\Desktop\\gcc_POLYGON\\VC_NM\\" +
                    objFile.getName().replace(OBJ_EXT, ".nm");
            sb.append(objFile.getCanonicalPath());
            sb.append(" > ");
            sb.append(outputFileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    private void findGlobalVariables() throws IOException {
        VCppObjParser parser = new VCppObjParser();
        File[] nmFiles = new File(objFilesDirectoryPath).listFiles();

        File reportFile = new File(outputFile);
        BufferedWriter writer = new BufferedWriter(new FileWriter(reportFile));

        progressDialog.setMaxReportingProgress(nmFiles.length);
        for (int i = 0; i < nmFiles.length; i++) {
            try {
                File currentFile = nmFiles[i];
                progressDialog.setReportingProgress(i + 1);
                progressDialog.setReportingMessage("Looking for global variables: " + currentFile.getName());

                writer.write(currentFile.getName().replace(".nm", ".cpp"));
                System.out.println("Reporting: " + currentFile.getName());
                BufferedReader br = new BufferedReader(new FileReader(currentFile));

                String line;
                while ((line = br.readLine()) != null) {
                    String globalVariable = parser.getGlobalVariableName(line);
                    if (globalVariable != null) {
                        writer.write(" " + globalVariable);
                    }
                }
                br.close();
                writer.newLine();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        writer.flush();
        writer.close();
    }

    private boolean isNotStrange(String globalVariable) {
        return globalVariable.indexOf("_Z") == 0 ? false : true;
    }

    private String extractGlobalVariableFromLine(String line) {
        String[] split = line.split(" ");
        return split[2].substring(1, split[2].length());
    }
}
