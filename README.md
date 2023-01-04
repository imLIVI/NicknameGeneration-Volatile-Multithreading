# Volatile-Atomic
## NicknameGeneration-Volatile-Multithreading
### Description
Suppose that in order to implement the nickname selection service, you have developed the following random text generator:
```java
    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }
```
To generate 100'000 short words, you used:
```java
        Random random = new Random();
        String[] texts = new String[100_000];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }
```

The user has the opportunity to choose from them only those that meet the criteria of a "beautiful" nickname, namely:
* the generated word is a palindrome, i.e. it reads the same from left to right and from right to left, for example, **abba**;
* the generated word consists of the same letter, for example, **aaa**;
* the letters in the word are ascending: first all **a** (if available), then all **b** (if available), then all **c** and etc . For example, **aaccc**.

You want to count how many "beautiful" words are found among the generated ones with a length of 3, 4, 5, for which you start three counters in static fields.
The verification of each criterion should be carried out in a separate thread.

After all three threads are completed, output a message like:
```text
Красивых слов с длиной 3: 100 шт
Красивых слов с длиной 4: 104 шт
Красивых слов с длиной 5: 90 шт
```
Do not use `synchronized` at the same time, look in the direction of `AtomicInteger'.

### Realization
1. Create a text generator and generate a set of 100,000 texts using the code from the task description.
2. Set up three counters in the static fields - one for the lengths: 3, 4 and 5.
3. Create three streams - one for each criterion of the "beauty" of the word. Each thread checks all texts for "beauty" and increments the counter of the desired
length if the text meets the criteria.
5. After all threads are finished, display the results on the screen.

<a href="https://github.com/netology-code/jd-homeworks/blob/video/volatile/task1/README.md">(RUS version of description)</a>
### Result
![image](https://user-images.githubusercontent.com/63547457/210635659-61978906-8a98-4276-86c9-b25f050b0b01.png)

