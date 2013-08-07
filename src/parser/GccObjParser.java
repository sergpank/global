package parser;

import java.util.regex.Pattern;

public class GccObjParser extends Parser {

    @Override
    public String getGlobalVariableName(String line) {
        String result = null;
        if (line.contains(" B ") || line.contains(" D ")) {
            String[] split = line.split(" ");
            String possibleGV = split[split.length - 1];
            if (!(possibleGV.startsWith("__Z") || possibleGV.startsWith("?"))) {
                result = possibleGV.substring(1, possibleGV.length());
            }
        } else if (line.contains(" b ") || line.contains(" d ")) {
            String[] split = line.split(" ");
            String possibleGV = split[split.length - 1];
            if (!possibleGV.startsWith(".")) {
                String prefix = possibleGV.length() >= 5 ? possibleGV.substring(0, 5) : possibleGV;
                if (prefix.matches("__ZL[0-9]*")) {
                    possibleGV = Pattern.compile("__ZL[0-9]*").matcher(possibleGV).replaceAll("");

                    Pattern pattern = Pattern.compile("L[0-9]*");
                    String[] marker = pattern.split("possibleGV");

                    if(marker.length == 1){
                        result = possibleGV;
                    }
                }
            }
        }
        return result;
    }

    public static void main(String[] args) {
        String symbols = "SYMBOLS";
        System.out.println("const string SYMBOLS[58] = ".contains(symbols + "["));
    }

}
