import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    private static AtomicInteger counterForLength3 = new AtomicInteger(0);
    private static AtomicInteger counterForLength4 = new AtomicInteger(0);
    private static AtomicInteger counterForLength5 = new AtomicInteger(0);

    public static void main(String[] args) {
        Random random = new Random();
        String[] texts = new String[100_000];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }

        // Counting palindromes
        new Thread(() -> {
            for (int i = 0; i < texts.length; i++) {
                String word = texts[i];
                int lenOfWord = word.length();
                boolean flag = true;

                for (int j = 0, k = lenOfWord - 1; j < lenOfWord / 2; j++, k--) {
                    if (word.charAt(j) != word.charAt(k)) {
                        flag = false;
                        break;
                    }
                }
                if (flag && !checkTheSameLetter(word)) {
                    addCounters(word);
                }
            }
        }).start();

        // Counting words from the same letter
        new Thread(() -> {
            for (int i = 0; i < texts.length; i++) {
                if (checkTheSameLetter(texts[i])) {
                    addCounters(texts[i]);
                }
            }
        }).start();

        // Counting letters in a word going in ascending order
        new Thread(() -> {
            for (int i = 0; i < texts.length; i++) {
                String word = texts[i];
                boolean flag = true;

                for (int j = 0; j < word.length() - 1; j++) {
                    if (word.charAt(j) > word.charAt(j + 1)) {
                        flag = false;
                        break;
                    }
                }
                if (flag && !checkTheSameLetter(word)) {
                    addCounters(word);
                }
            }
        }).start();

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        print();
    }

    public static void print() {
        System.out.println("Красивых слов с длиной 3: " + counterForLength3 + " шт");
        System.out.println("Красивых слов с длиной 4: " + counterForLength4 + " шт");
        System.out.println("Красивых слов с длиной 5: " + counterForLength5 + " шт");
    }


    public static boolean checkTheSameLetter(String word) {
        char letter = word.charAt(0);
        boolean flag = true;
        for (int j = 0; j < word.length(); j++) {
            if (word.charAt(j) != letter) {
                flag = false;
                break;
            }
        }
        return flag;
    }

    public static void addCounters(String word) {
        switch (word.length()) {
            case 3:
                counterForLength3.addAndGet(1);
                break;
            case 4:
                counterForLength4.addAndGet(1);
                break;
            case 5:
                counterForLength5.addAndGet(1);
                break;
        }
    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }
}
