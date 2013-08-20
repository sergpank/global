import java.io.*;

public class ConstFilter {

    private File report = new File("REPORT_WITHOUT_CONSTANTS.txt");
    private String cppDir = "C:\\Users\\serg\\Desktop\\gcc_POLYGON\\runs\\";


    public void filterConstants()
            throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("report.txt"));

        BufferedWriter reportWriter = new BufferedWriter(new FileWriter(report));

        String line;
        while ((line = reader.readLine()) != null) {
            String[] split = line.split(" ");
            String fileName = split[0];
            System.out.println("Filtering: " + fileName);
            reportWriter.write(fileName);

            for (int i = 1; i < split.length; i++) {
                String varName = split[i];
                if (!isConstant(fileName, varName)) {
                    reportWriter.write(" " + varName);
                }
            }
            reportWriter.newLine();
        }

        reportWriter.close();
        reader.close();
    }


    private boolean isConstant(String fileName, String varName)
            throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(cppDir + fileName));

        boolean result = false;
        String line;
        while ((line = reader.readLine()) != null) {
            if (containsVar(line, varName)) {
                if (line.contains("const ")) {
                    if ( ! isPointer(line, varName)) {
                        result = true;
                    }
                    break;
                } else {
                    break;
                }
            }
        }

        reader.close();

        return result;
    }

    private boolean isPointer(String line, String varName) {
        boolean result = false;
        if(line.contains("*")){
            result = line.indexOf("*") < line.indexOf(varName);
        }
        return result;
    }


    private boolean containsVar(String line, String varName) {
        int varStartPos = line.indexOf(varName);

        if (varStartPos <= 0 || line.length() <= varStartPos + varName.length()) {
            return false;
        }

        boolean beforeOk = isBeforeOk(line, varStartPos);
        boolean afterOk = isAfterOkForVar(line, varStartPos + varName.length());

        return beforeOk && afterOk;
    }


    private boolean isBeforeOk(String line, int varStartPos) {
        char c = line.charAt(varStartPos - 1);
        return c == ' ' || c == '>' || c == ',';
    }


    private boolean isAfterOkForVar(String line, int varEndPos) {
        char c = line.charAt(varEndPos);
        return c == '=' || c == ' ' || c == '[' || c == ';' || c == ',' || c == '(';
    }


    private boolean isAfterOkForFunction(String line, int varEndPos) {
        boolean result = false;

        while (varEndPos < line.length()) {
            final char c = line.charAt(varEndPos);
            if (c == '(') {
                result = true;
                break;
            } else if (c == ' ') {
                varEndPos++;
            } else {
                break;
            }
        }

        return result;
    }

}
