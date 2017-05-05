import domain.MigrationManager;
import rx.Observable;
import util.FileUtils;


public class VigoJugChallenge {
    private static final String SAMPLE_FILE = "input1.txt";
    private static final char[] SIRENO_EXPRESS = new char[] {'·', '»'};

    public static void main(String [] args) {
        Observable<String> fileLines = new FileUtils().getFileLines(SAMPLE_FILE);

        MigrationManager.migrate(SIRENO_EXPRESS, fileLines)
            .subscribe(System.out::println);
    }
}
