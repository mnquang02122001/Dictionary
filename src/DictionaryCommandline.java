import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class DictionaryCommandline {
    public static void showAllWorlds(){
        System.out.println("No\t|English\t\t\t|Vietnamese");
        int i = 1;
        for(String word_target: Dictionary.dic.keySet()){
            System.out.println((i) + "\t|" + word_target + "\t\t\t\t|" + Dictionary.dic.get(word_target));
            i++;
        }
    }
    public static void dictionaryBasic(){
        DictionaryManagement.insertFromCommandline();
        showAllWorlds();
    }
    public static void dictionaryAdvanced(String path, String word_target) throws IOException {
        DictionaryManagement.insertFromFile(path);
        showAllWorlds();
        DictionaryManagement.dictionaryLookup(word_target);
    }
    public static void dictionarySearcher(String word){
        String res = "";
        for(String word_target: Dictionary.dic.keySet()){
            if(word_target.length() >= word.length() &&word_target.substring(0, word.length()).compareTo(word)==0){
                res += word_target + ",";
            }
        }
        if(res.equals("")){
            System.out.println("Cant find");
        }
        else{
            System.out.println(res);
        }
    }
}
