package util;

import rx.Emitter;
import rx.Observable;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public final class FileUtils {
    public Observable<String> getFileLines(final String fileName) {
        return Observable.create((Emitter<String> emitter) -> {
            ClassLoader loader = getClass().getClassLoader();
            File file = new File(loader.getResource(fileName).getFile());

            try {
                Scanner scanner = new Scanner(file);

                while (scanner.hasNextLine())
                    emitter.onNext(scanner.nextLine());

                emitter.onCompleted();
                scanner.close();

            } catch (FileNotFoundException e) {
                emitter.onError(e);
            }

        }, Emitter.BackpressureMode.BUFFER);

    }
}
