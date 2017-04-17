package domain.util;

public final class StatementUtil {
    public static String cleanLine(String line, char... indentationMarks) {
        for (char mark : indentationMarks)
            line = line.replaceAll(Character.toString(mark), "");

        return line;
    }
}
