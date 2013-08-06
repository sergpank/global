package parser;

public class VcppObjParser extends Parser {

    @Override
    public String getGlobalVariableName(String line) {
        String result = null;
        if (line.contains(" B ") || line.contains(" D ")) {
            String[] split = line.split(" ");
            String possibleGV = split[split.length - 1];
            result = extractGlobalVariable(possibleGV);
        } else if (line.contains(" b ") || line.contains(" d ")) {
            String[] split = line.split(" ");
            String possibleGV = split[split.length - 1];
            if (possibleGV.charAt(0) == '_') {
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
