package es.kix2902._201706.java;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.print("You must include input filename as parameter");
            System.exit(1);
        }

        try {
            Scanner input = new Scanner(new File(args[0]));

            String line;
            do {
                line = input.nextLine();
            } while (line.startsWith("#"));

            int coins_total = Integer.valueOf(line);

            int[] coins_values = Arrays.stream(input.nextLine().trim().split("\\s+"))
                    .map(String::trim).mapToInt(Integer::parseInt).toArray();

            int[] coins_quantity = Arrays.stream(input.nextLine().trim().split("\\s+"))
                    .map(String::trim).mapToInt(Integer::parseInt).toArray();

            Integer change = Integer.valueOf(input.nextLine());

            input.close();

            ArrayList<Integer> coins_change = new ArrayList<>();
            while (change > 0) {
                int index;
                int quantity = 0;
                for (index = coins_values.length - 1; index >= 0; index--) {
                    quantity = coins_quantity[index];

                    if (quantity != 0) {
                        int value = coins_values[index];

                        if (value <= change) {
                            break;
                        }
                    }
                }

                int selected_value = coins_values[index];

                coins_change.add(selected_value);
                change -= selected_value;

                if (quantity != -1) {
                    coins_quantity[index] = quantity - 1;
                }
            }

            System.out.println(String.join(" ", coins_change.stream().map(Object::toString)
                    .collect(Collectors.joining(" "))));

        } catch (FileNotFoundException e) {
            System.err.println("File not found");

        } catch (NumberFormatException e) {
            System.err.println("Input it's not well formatted");
        }
    }
}
