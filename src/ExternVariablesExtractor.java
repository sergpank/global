import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ExternVariablesExtractor {

    private String cppDir = "C:\\Users\\serg\\Desktop\\gcc_POLYGON\\runs\\";
    private File report = new File("REPORT_WITH_EXTERN_VARIABLES.txt");

    public void extractExternVariables() throws IOException {
        File prevReport = new File("REPORT_WITHOUT_CONSTANTS.txt");

        BufferedReader reader = new BufferedReader(new FileReader(prevReport));

        BufferedWriter reportWriter = new BufferedWriter(new FileWriter(report));

        String line;
        while ((line = reader.readLine()) != null) {
            String[] split = line.split(" ");
            String fileName = split[0];
            System.out.println("Externing: " + fileName);
            reportWriter.write(fileName);

            if (split.length > 1) {
                List<String> variablesList = variablesToList(split);
                List<String> externVariables = findExternVariables(new File(cppDir + fileName));

                for (String externVariable : externVariables) {
                    if(!variablesList.contains(externVariable)){
                        variablesList.add(externVariable);
                    }
                }

                for (int i = 0; i < variablesList.size(); i++) {
                    reportWriter.write(" " + variablesList.get(i));
                }
            }
            reportWriter.newLine();
        }

        reportWriter.close();
        reader.close();
    }

    private List<String> variablesToList(String[] split) {
        List<String> vars = new ArrayList<String>();
        for (int i = 1; i < split.length; i++) {
            vars.add(split[i]);
        }
        return vars;
    }

    public List<String> findExternVariables(File cppFile) throws IOException {
        List<String> externVariables = new ArrayList<String>();

        BufferedReader reader = new BufferedReader(new FileReader(cppFile));

        System.out.println(cppFile.getName());

        String line;
        while ((line = reader.readLine()) != null) {
            if (line.startsWith("extern ")) {
                String externVar = extractVar(line);
                if (externVar != null) {
                    externVariables.add(externVar);
                }
            }
        }
        reader.close();

        return externVariables;
    }

    private String extractVar(String line) {
        String result = null;
        line = removeComment(line);
        line = line.replace('<', ' ');
        line = line.replace('>', ' ');
        line = line.replace('[', ' ');
        line = line.replace(']', ' ');
        line = line.trim();
        if (isNotExternFunction(line)) {
            String[] split = line.split(";")[0].split(" ");
            result = split[split.length - 1];
        }
        return result;
    }

    private boolean isNotExternFunction(String line) {
        return line.charAt(line.length() - 2) != ')';
    }

    private String removeComment(String line) {
        int commentStart = line.indexOf("//");
        if (commentStart > 0) {
            line = line.substring(0, commentStart).trim();
        }
        return line;
    }
}
