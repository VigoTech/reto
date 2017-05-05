package domain;

import java.util.HashMap;
import java.util.Stack;

@SuppressWarnings("WeakerAccess")
public class SyntaxBlockVerifier {
    private final static HashMap<String, String> BLOCKS_MAP;
    private final static Stack<String> statementStack = new Stack<>();

    static {
        BLOCKS_MAP = new HashMap<>(2);
        BLOCKS_MAP.put("IF", "ENDIF");
        BLOCKS_MAP.put("FOR", "NEXT");
    }

    public static class MissingBlockException extends RuntimeException {
        private final String expected;
        private final String actual;

        MissingBlockException(String expected, String actual) {
            this.expected = expected;
            this.actual = actual;
        }

        @Override
        public String getMessage() {
            return String.format("Found %s when expecting %s", actual, expected);
        }
    }

    void verify(String line) {
        String currentBlock = getBlock(line);

        if (isStartBlock(currentBlock)) {
            statementStack.push(currentBlock);
        }

        if (isEndBlock(line)) {
            String previousControlBlock = statementStack.pop();
            String expectedBlock = BLOCKS_MAP.get(previousControlBlock);

            if (!currentBlock.equals(expectedBlock))
                throw new MissingBlockException(expectedBlock, currentBlock);

        }
    }

    void onComplete() {
        if (!statementStack.isEmpty()) {
            String previousControlBlock = statementStack.pop();
            String expectedBlock = BLOCKS_MAP.get(previousControlBlock);
            throw new MissingBlockException(expectedBlock, previousControlBlock);
        }
    }

    private static boolean isStartBlock(String line) {
        return BLOCKS_MAP.get(line) != null;
    }

    private static boolean isEndBlock(String line) {
        for (String endBlock : BLOCKS_MAP.values())
            if (line.startsWith(endBlock)) return true;

        return false;
    }

    private static String getBlock(String line) {
        return line.split(" ")[0];
    }
}