package domain;

@SuppressWarnings("WeakerAccess")
public class IndentLevelHandler {
    private int indentation;

    public IndentLevelHandler(int indentation) {
        this.indentation = indentation;
    }

    private  void increment() {
        this.indentation++;
    }

    private void decrement() {
        if (indentation > 0) this.indentation--;
    }

    public int indentation() {
        return indentation;
    }

    public void sync(String line) {
        if (line.startsWith("FOR") || line.startsWith("IF"))
            increment();

        if (line.contains("NEXT") || line.contains("ENDIF"))
            decrement();
    }

    public void sync(String currentLine, String nextLine) {
        if (currentLine.startsWith("FOR") || currentLine.startsWith("IF"))
            increment();

        if (nextLine != null) {
            if (nextLine.contains("NEXT") || nextLine.contains("ENDIF"))
                decrement();
        }
    }
}