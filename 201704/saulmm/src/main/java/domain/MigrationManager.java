package domain;

import rx.Observable;
import domain.util.StatementUtil;


public class MigrationManager {
    private final IndentLevelHandler indentationHandler;
    private final IndentationFormatter indentationFormatter;
    private final SyntaxBlockVerifier syntaxVerifier;
    private final char[] oldIndentationMarks;

    private MigrationManager(char[] oldIndentationMarks, String indentationMark) {
        this(oldIndentationMarks, indentationMark, 0);
    }

    private MigrationManager(char[] oldIndentationMarks, String indentationMark, int indentationStartLevel) {
        this.oldIndentationMarks = oldIndentationMarks;
        syntaxVerifier = new SyntaxBlockVerifier();
        indentationHandler = new IndentLevelHandler(indentationStartLevel);
        indentationFormatter = new IndentationFormatter(indentationMark, indentationHandler);
    }

    private void sync(String current, String next) {
          indentationHandler.sync(current, next);
      }

    private String indent(String line) {
      syntaxVerifier.verify(line);
      return indentationFormatter.indent(line);
    }

    private void verify() {
        syntaxVerifier.onComplete();
    }

    private String clean(String line) {
        return StatementUtil.cleanLine(line, oldIndentationMarks);
    }

    public static Observable<String> migrate(char[] indentationMarks, Observable<String> fileLines) {
        return fileLines.toList()
            .flatMap(lines -> {
                MigrationManager manager = new MigrationManager(indentationMarks, lines.get(1));

                return Observable.from(lines) // Get the lines of the file as an observable
                    .skip(2) // Skip the number of lines and the indentation mark
                    .map(manager::clean) // Clean each line
                    .scan((current, next) -> { // Keep up to date the indentation level manager with the current and next line
                        manager.sync(current, next);
                        return next;
                    })
                    .doOnCompleted(manager::verify)
                    .map(manager::indent); // Indent each line
                }
            );
    }



}