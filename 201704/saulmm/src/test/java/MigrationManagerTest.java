import domain.MigrationManager;
import domain.SyntaxBlockVerifier;
import org.junit.Test;
import rx.observers.TestSubscriber;
import util.FileUtils;

import java.util.EmptyStackException;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class MigrationManagerTest {
    private final static char [] OLD_INDENTATION_MARKS = new char[] {'·', '»'};

    @Test public void thatValidInputIndentsProperly() {
        TestSubscriber<String> testSubscriber = new TestSubscriber<String>();

         MigrationManager.migrate(OLD_INDENTATION_MARKS, new FileUtils()
                .getFileLines("input1.txt"))
                .subscribe(testSubscriber);

        List<String> expectedLines = getExpectedLines("input1_out.txt");
        List<String> actualLines = testSubscriber.getOnNextEvents();
        assertEquals(expectedLines, actualLines);
    }

    @Test public void thatMissingClosingIfThrowsException() {
        TestSubscriber<String> testSubscriber = new TestSubscriber<String>();

        MigrationManager.migrate(OLD_INDENTATION_MARKS, new FileUtils()
                .getFileLines("bonus1.txt"))
                .subscribe(testSubscriber);

        testSubscriber.assertError(SyntaxBlockVerifier.MissingBlockException.class);
    }

    @Test public void thatMissingSeveralClosingPartsThrowsException() {
        TestSubscriber<String> testSubscriber = new TestSubscriber<String>();

        MigrationManager.migrate(OLD_INDENTATION_MARKS, new FileUtils()
                .getFileLines("bonus2.txt"))
                .subscribe(testSubscriber);

        testSubscriber.assertError(SyntaxBlockVerifier.MissingBlockException.class);
    }

    @Test public void thatMissingClosingForThrowsException() {
        TestSubscriber<String> testSubscriber = new TestSubscriber<String>();

        MigrationManager.migrate(OLD_INDENTATION_MARKS, new FileUtils()
                .getFileLines("bonus3.txt"))
                .subscribe(testSubscriber);

        testSubscriber.assertError(SyntaxBlockVerifier.MissingBlockException.class);
    }

    @Test public void thatUselessMissingBlocksThrowsException() {
        TestSubscriber<String> testSubscriber = new TestSubscriber<String>();

        MigrationManager.migrate(OLD_INDENTATION_MARKS, new FileUtils()
                .getFileLines("bonus4.txt"))
                .subscribe(testSubscriber);

        testSubscriber.assertError(EmptyStackException.class);
    }

    private List<String> getExpectedLines(String fileName) {
        TestSubscriber<String> testSubscriber = new TestSubscriber<String>();
        new FileUtils().getFileLines(fileName).subscribe(testSubscriber);
        return testSubscriber.getOnNextEvents();
    }
}
