package domain;

@SuppressWarnings("WeakerAccess")
public class IndentationFormatter {
    private final IndentLevelHandler indentationHandler;
    private final String indentationMark;

    public IndentationFormatter(String indentationMark, IndentLevelHandler indentationHandler) {
        this.indentationHandler = indentationHandler;
        this.indentationMark = indentationMark;
    }

    public String indent(String line) {
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < indentationHandler.indentation(); i++) {
            builder.append(indentationMark);
        }

        builder.append(line);
        return builder.toString();
    }
}
