import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();

        String[] stringArray = input.split(",");
        Integer[] hand = new Integer[stringArray.length];
        for (int j = 0; j < stringArray.length; j++) {
            hand[j] = Integer.valueOf(stringArray[j]);
        }

        if (hand.length == 0) {
            throw new IllegalArgumentException("Invalid input: no cards");
        }
        if (hand.length != 5) {
            throw new IllegalArgumentException("Invalid input: illegal number of cards");
        }

        int result = 0;
        Map<Integer, Integer> valueCount = new HashMap<>();
        int aces = 0;

        Arrays.sort(hand);

        // Valid input - 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14
        if ((hand[0] < 0) || (hand[hand.length - 1] > 14)) {
            throw new IllegalArgumentException("Invalid input: card value(s) less than one and/or greater than fourteen");
        }

        // check value occurrence
        for (int i = 0; i < 5; i++) { // hand.length
            if (valueCount.containsKey(hand[i])) {
                valueCount.put(hand[i], valueCount.get(hand[i]) + 1);
            } else {
                valueCount.put(hand[i], 1);
            }
        }

        if (valueCount.containsValue(5)) {
            throw new IllegalArgumentException("Illegal input: more than four cards with the same value");
        }

        // 11 is valid input! (count(1) + count(11) must be 4)
        if (valueCount.containsKey(1) && valueCount.containsKey(11)) {
            aces = valueCount.get(1) + valueCount.get(11);
            if (aces > 4) {
                throw new IllegalArgumentException("Illegal input: more than four cards with the same value");
            }
        }

        // check for Three of a kind - triling
        boolean isTriling = false;
        if (valueCount.containsValue(4) || aces == 4 || valueCount.containsValue(3) || aces == 3) {
            isTriling = true;
        }

        // check for Full house - Full House
        boolean isFullHouse = false;
        if (valueCount.containsValue(3) && valueCount.containsValue(2)) {
            isFullHouse = true;
        }
        if (aces == 2) {
            if (valueCount.containsValue(3)) {
                isFullHouse = true;
            }
        }
        if (aces == 3) {
            if (valueCount.values().stream().filter(v -> v == 2).count() == 2) {
                isFullHouse = true;
            }
        }

        // check for Straight - Kenta
        // Arrays.sort(hand); - hand sorted above
        boolean isKenta = true;
        int i = 0;
        do {
            if ((hand[i + 1] - hand[i]) != 1) {
                isKenta = false;
            }
            i++;
        } while ((i < 4) && isKenta); // hand.length - 1

        // 1 - Three of a kind - triling
        // 2 - Full house - Full House
        // 3 - Straight - Kenta
        if (isFullHouse) {
            result = 2;
        } else if (isTriling) {
            result = 1;
        } else if (isKenta) {
            result = 3;
        }

        System.out.println(result);
    }
}
