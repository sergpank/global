package parser;

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
                if (prefix.matches("__Z[A-Z][0-9]")) {
                    result = possibleGV.substring(5, possibleGV.length());
                }
            }
        }
        return result;
    }
}
