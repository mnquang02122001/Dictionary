import java.io.*;
import java.util.Scanner;

public class DictionaryManagement {
    public static final String path = "E:\\My documents\\JavaProject\\src\\E_V.html";
    private static final Scanner sc = new Scanner(System.in);

    public static void insertFromCommandline() {
        System.out.print("Input number of words: ");
        int n = sc.nextInt();
        sc.nextLine();
        System.out.println("Insert words: ");
        for (int i = 0; i < n; i++) {
            Dictionary.dic.put(sc.nextLine(), sc.nextLine());
        }
        sc.close();
    }

    public static void insertFromFile() throws IOException {
        FileReader fis = new FileReader(path);
        BufferedReader br = new BufferedReader(fis);
        String line;
        while ((line = br.readLine()) != null) {
            String[] parts = line.split("<html>");
            String word = parts[0];
            String definition = "<html>" + parts[1];
            Dictionary.dic.put(word, definition);
        }
        fis.close();
        br.close();
    }

    public static void dictionaryLookup(String word_target) {
        if (Dictionary.dic.containsKey(word_target)) {
            System.out.println("The meaning of " + word_target + " is: " + Dictionary.dic.get(word_target));
        } else {
            System.out.println("Cant find");
        }
    }

    public static void dictionaryAdd() {
        System.out.print("Type word_target: ");
        String word_target = sc.nextLine();
        if (Dictionary.dic.containsKey(word_target)) {
            System.out.println(word_target + " was existed");
            return;
        }
        System.out.print("Type word_explain: ");
        String word_explain = sc.nextLine();
        word_explain = "<html>" + word_explain + "</html>";
        Dictionary.dic.put(word_target, word_explain);
    }

    public static void dictionaryDelete() {
        System.out.print("Delete word_target: ");
        String word_target = sc.nextLine();
        if (Dictionary.dic.containsKey(word_target)) {
            Dictionary.dic.remove(word_target);
        } else {
            System.out.println("Cant find");
        }
    }

    public static void dictionaryModify() {
        System.out.print("Modify word_target: ");
        String word_target = sc.nextLine();
        System.out.print("With word_explain: ");
        String word_explain = sc.nextLine();
        for (String word : Dictionary.dic.keySet()) {
            if (word.equals(word_target)) {
                Dictionary.dic.put(word_target, "<html>" + word_explain + "</html>");
                return;
            }
        }
        System.out.println("Cant find");
    }

    public static void dictionaryExportToFile() throws IOException {
        FileWriter fw = new FileWriter(path);
        BufferedWriter bw = new BufferedWriter(fw);
        for (String word_target : Dictionary.dic.keySet()) {
            String res = word_target + Dictionary.dic.get(word_target) + "\n";
            bw.write(res);
        }
        bw.flush();
        bw.close();
    }
}

