import java.io.IOException;
<<<<<<< HEAD
import java.util.Map;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
=======
import java.util.ArrayList;
import java.util.List;
>>>>>>> 638008393e2e6457c8807a03b2b9bf040404616a

public class DictionaryCommandline {
    public static List<String> searcherResult = new ArrayList<>();

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

    public static void dictionarySearcher(String word) {
        String res = "";
        for (String word_target : Dictionary.dic.keySet()) {
            if (word_target.length() >= word.length() && word_target.substring(0, word.length()).compareTo(word) == 0) {
                res += word_target + ",";
                searcherResult.add(word_target);
            }
        }
        if (res.equals("")) {
            System.out.println("Cant find");
        } else {
            System.out.println(res);
        }
    }
    public static List<String> _dictionarySearcher(String word){
        List<String> suggest=new ArrayList<>();
        for(String word_target: Dictionary.dic.keySet()){
            if(word_target.substring(0, word.length()).compareTo(word)==1){
                suggest.add(word_target);
            }
        }
        return suggest;
    }
    public static List<String> _swapdictionarySearcher(String word){
        List<String> suggest=new ArrayList<>();
        for(Map.Entry<String, String> word_target: Dictionary.dic.entrySet()){
            if(word_target.getValue().substring(0, word.length()).compareTo(word)==1){
                suggest.add(word_target.getValue());
            }
        }
        return suggest;
    }
}
