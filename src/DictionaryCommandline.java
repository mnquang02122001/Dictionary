import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DictionaryCommandline {

    public static void showAllWorlds() {
        System.out.println("No\t|English\t\t\t|Vietnamese");
        int i = 1;
        for (String word_target : Dictionary.dic.keySet()) {
            System.out.println((i) + "\t|" + word_target + "\t\t\t\t|" + Dictionary.dic.get(word_target));
            i++;
        }
    }

    public static void dictionaryBasic() {
        DictionaryManagement.insertFromCommandline();
        showAllWorlds();
    }

    public static void dictionaryAdvanced(String word_target) throws IOException {
        DictionaryManagement.insertFromFile();
        showAllWorlds();
        DictionaryManagement.dictionaryLookup(word_target);
    }

    public static List<String> dictionarySearcher(String word) {
        List<String> searcherResult = new ArrayList<>();
        if (word.equals("")) {
            return searcherResult;
        }
        for (String word_target : Dictionary.dic.keySet()) {
            if (word_target.length() >= word.length() && word_target.substring(0, word.length()).compareTo(word) == 0) {
                searcherResult.add(word_target);
            }
        }
        return searcherResult;
    }
}
