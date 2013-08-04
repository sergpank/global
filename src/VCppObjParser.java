import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class VCppObjParser {

    public static void main(String[] args) throws IOException {
        VCppObjParser parser = new VCppObjParser();
        File reportFile = new File("report.txt");
        String[] nmFiles = new File("C:\\Users\\serg\\Desktop\\gcc_POLYGON\\VC_NM").list();
        for (int i = 0; i < nmFiles.length; i++) {
            BufferedReader br = new BufferedReader(new FileReader(nmFiles[i]));
            String line;
            while ((line = br.readLine()) != null) {
                String gvn = parser.getGlobalVariableName(line);
                if (gvn != null) {
                    System.out.println(gvn);
                }
            }
        }
    }

    public String getGlobalVariableName(String line) {
        String result = null;
        if (line.contains(" B ") || line.contains(" D ")) {
            String[] split = line.split(" ");
            String possibleGV = split[split.length - 1];
            result = extractGlobalVariable(possibleGV);
        } else if (line.contains(" b ") || line.contains(" d ")) {
            String[] split = line.split(" ");
            String possibleGV = split[split.length - 1];
            if(possibleGV.charAt(0) == '_'){
                result = possibleGV.substring(1, possibleGV.length());
            }
        }
        return result;
    }

    private String extractGlobalVariable(String possibleGV) {
        String result = null;
        int endOfNamePos = possibleGV.indexOf("@@");
        StringBuffer sb = new StringBuffer();
        for (int i = 1; i < possibleGV.length(); i++) {
            char curChar = possibleGV.charAt(i);
            if (curChar == '@' && i != endOfNamePos) {
                break;
            }
            if (curChar == '@' && i == endOfNamePos) {
                if (!(sb.charAt(0) == '?')) {
                    result = sb.toString();
                }
                break;
            } else {
                sb.append(curChar);
            }
        }
        return result;
    }

}
