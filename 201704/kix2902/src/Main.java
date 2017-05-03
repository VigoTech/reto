import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.print("You must include input filename as parameter");
            System.exit(1);
        }

        try {
            Scanner input = new Scanner(new File(args[0]));

            int totalLines = Integer.valueOf(input.nextLine());
            String indentText = input.nextLine();

            ArrayList<String> indentBlock = new ArrayList<>();
            for (int i = 0; i < totalLines; i++) {
                String line = input.nextLine().replace("·", "").replace("»", "");

                if (line.startsWith("NEXT")) {
                    if (indentBlock.lastIndexOf("FOR") >= 0) {
                        if (indentBlock.lastIndexOf("FOR") == indentBlock.size() - 1) {
                            indentBlock.remove(indentBlock.size() - 1);

                        } else {
                            System.err.println("Closing FOR block without closing inner IF block");

                            int lastIndex = indentBlock.size() - 1;
                            int blockIndex = indentBlock.lastIndexOf("FOR");
                            for (int j = lastIndex; j >= blockIndex; j--) {
                                indentBlock.remove(j);
                            }
                        }

                    } else {
                        System.err.println("Closing FOR block without opening it before");
                    }
                    
                } else if (line.startsWith("ENDIF")) {
                    if (indentBlock.lastIndexOf("IF") >= 0) {
                        if (indentBlock.lastIndexOf("IF") == indentBlock.size() - 1) {
                            indentBlock.remove(indentBlock.size() - 1);

                        } else {
                            System.err.println("Closing IF block without closing inner FOR block");

                            int lastIndex = indentBlock.size() - 1;
                            int blockIndex = indentBlock.lastIndexOf("IF");
                            for (int j = lastIndex; j >= blockIndex; j--) {
                                indentBlock.remove(j);
                            }
                        }

                    } else {
                        System.err.println("Closing IF block without opening it before");
                    }
                }

                System.out.print(String.join("", Collections.nCopies(indentBlock.size(), indentText))); // Prints the indentation
                System.out.println(line); // Prints the code

                if ((line.startsWith("FOR")) || (line.startsWith("IF"))) {
                    String word = line.substring(0, line.indexOf(" "));
                    indentBlock.add(word);
                }
            }

            input.close();

            if (indentBlock.size() > 0) {
                System.err.print("Program terminated without closing following blocks: ");
                for (int i = 0; i < indentBlock.size(); i++) {
                    String block = indentBlock.get(i);
                    System.err.print(block);

                    if (i < indentBlock.size() - 2) {
                        System.err.print(", ");

                    } else if (i == indentBlock.size() - 2) {
                        System.err.print(" and ");
                    }
                }
            }

        } catch (FileNotFoundException e) {
            System.err.println("File not found, review filename and try again");

        } catch (NumberFormatException e) {
            System.err.println("First line of file doesn't has a number");
        }
    }
}
