package readability;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.*;
import java.util.Map;


public class ReadabilityScore {



    public static int countVowels (String word) {
        int vCount = 0;

        word = word.toLowerCase();

        if (word.endsWith("e")) {
            word = word.substring(0,word.length() -1);
        }

        Pattern vowelGroup = Pattern.compile("[aeiouy]+");
        Matcher matcher = vowelGroup.matcher(word);

        while (matcher.find()) {
            vCount++;
        }

        return (vCount == 0) ? 1 : vCount;
    }

    public static double calcAverage(int[] sentLength){
        int sum = 0;

        for (int num : sentLength){
            sum+= num;
        }
        return (double) sum / sentLength.length;
    }

    public static int getAge (double score){

        Map<Integer, String> scoreMap = Map.ofEntries(
                Map.entry(1,"6"),
                Map.entry(2, "7"),
                Map.entry(3, "8"),
                Map.entry(4, "9"),
                Map.entry(5, "10"),
                Map.entry(6, "11"),
                Map.entry(7, "12"),
                Map.entry(8, "13"),
                Map.entry(9, "14"),
                Map.entry(10, "15"),
                Map.entry(11, "16"),
                Map.entry(12, "17"),
                Map.entry(13, "18"),
                Map.entry(14, "19"),
                Map.entry(15, "20"),
                Map.entry(16, "21"),
                Map.entry(17,"22")
        );

        int scoreRound = (int) Math.ceil(score);

        return Integer.parseInt(scoreMap.getOrDefault(scoreRound, String.valueOf(0)));

    }

    public static int getARI (int chars, int words, int sentences) {

        double score = 4.71 * ((double) chars / words) + .5 * ((double) words / sentences) - 21.43;

        int age = getAge(score);


        System.out.printf("Automated Readability Index: %f (about %s-year-olds).\n", score, age);

        return age;
    }

    public static int getFK (int words, int sentences, int syllables){
        double score = ((0.39 * ((double) words /sentences)) + (11.8 * ((double) syllables / words))) - 15.59;

        int age = getAge(score + 1);

        System.out.printf("Flesch–Kincaid readability tests: %f (about %s-year-olds).\n", score, age);
        return age;
    }

    public static int getSMOG (int polysyllables, int sentences){
        double score = 1.043 * Math.sqrt(polysyllables * ((double) 30 / sentences)) + 3.1291;

        int age = getAge(score + 1);

        System.out.printf("Simple Measure of Gobbledygook: %f (about %s-year-olds).\n", score, age);
        return age;
    }

    public static int getCL (int chars, int words, int sentences) {
        double score = 0.0588 * (((double) chars / words) * 100) - 0.296 * (((double) sentences / words) * 100) - 15.8;

        int age = getAge(score +2);

        System.out.printf("Coleman–Liau index:%f (about %s-year-olds).\n", score, age);
        return age;
    }

    public static void userMenu (int words, int sentences, int chars, int syllables, int polysyllables) {

        Scanner scanner = new Scanner(System.in);
        String input = "";

        boolean validInput = false;

        while (!validInput) {
            System.out.println("Enter the score you want to calculate (ARI, FK, SMOG, CL, all");

            input = scanner.nextLine().toLowerCase();

            switch (input) {
                case "ari" -> {
                    getARI(chars, words, sentences);
                    validInput = true;
                }
                case "fk" -> {
                    getFK(words, sentences, syllables);
                    validInput = true;
                }
                case "smog" -> {
                    getSMOG(polysyllables, sentences);
                    validInput = true;
                }
                case "cl" -> {
                    getCL(chars, words, sentences);
                    validInput = true;
                }
                case "all" -> {
                    int age = 0;

                    age = age + getARI(chars, words, sentences);
                    age = age + getFK(words, sentences, syllables);
                    age = age + getSMOG(polysyllables, sentences);
                    age = age + getCL(chars, words, sentences);

                    System.out.printf("This text should be understood in average by %f-year-olds.", (double) age/4);
                    validInput = true;
                }
            }
        }
    }


    public static void printOutput(Scanner scanner){
        String userInput = scanner.nextLine();
        String[] inputSplit = userInput.split("[;!.?]");
        String[] wordsList = userInput.split("\\s+");
        int syllables = 0;
        int polysyllables = 0;

        for (String s : wordsList) {
            int sylbCount = countVowels(s);
            syllables = syllables + sylbCount;
            if (sylbCount > 2) {
                polysyllables++;
            }
        }


        int[] sentLength = new int[inputSplit.length];
        int words = userInput.split("\\s+").length;
        int sent = inputSplit.length;
        int chars = userInput.replaceAll("\\s+", "").length();
        double score = 4.71 * ((double) chars / words) + .5 * ((double) words / inputSplit.length) - 21.43;

        System.out.println("The text is:");
        System.out.println(userInput);
        System.out.println();

        System.out.printf("Words: %d\n", words);
        System.out.printf("Sentences: %d\n", sent);
        System.out.printf("Characters: %d\n", chars);
        System.out.printf("Syllables: %d\n", syllables);
        System.out.printf("Polysyllables: %d\n", polysyllables);
        userMenu(words, sent, chars, syllables, polysyllables);


        System.out.printf("The score is: %f\n", score );
        //getGradeLevel((int) Math.ceil(score))


    }

    public static void main(String[] args) {


        File file = new File(args[0]);
        try (Scanner scanner = new Scanner(file)) {
            printOutput(scanner);

        } catch (FileNotFoundException e) {
            System.out.println("Error the file was not found" + args[0]);
        }
    }
}
