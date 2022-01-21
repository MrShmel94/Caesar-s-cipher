import javax.swing.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class CaesarsСipher extends JFrame {

    public static List<Character> alphabet = generateAlphabet();
    public static List<Character> punctuationMarks = generatePunctuationMarks();
    public static final int SIZE_ALPHABET = alphabet.size() - 1;

    public static void main(String[] args) throws IOException {
        info();
    }


    public static void info () throws IOException {

            try(BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))){
                while (true) {

                    System.out.println("Please say Encrypt / Decrypt or Cryptanalysis ?? E/D or C");
                    String result = reader.readLine();

                    if (result.equalsIgnoreCase("E") || result.equalsIgnoreCase("D")
                            || result.equalsIgnoreCase("Encrypt") || result.equalsIgnoreCase("Decrypt")) {
                        System.out.println("Please copy link and how many characters to perform the operation");
                        if (result.equalsIgnoreCase("E")) result = "Encrypt";
                        if (result.equalsIgnoreCase("D")) result = "Decipher";
                        String fileName = reader.readLine();
                        int key = Integer.parseInt(reader.readLine());
                        System.out.println("Please write FileNameSave");
                        String fileNameSave = reader.readLine();
                        encryptDecipher(result, fileName, key, fileNameSave);
                        break;

                    } else if (result.equalsIgnoreCase("Cryptanalysis") || result.equalsIgnoreCase("C")){
                        System.out.println("Wanna hack :) or Do text analysis ? Write Hack or Analysis   H/A");
                        String resultSecond = reader.readLine();
                        if (resultSecond.equalsIgnoreCase("Hack") || resultSecond.equalsIgnoreCase("H")){
                            resultSecond = "BruteForce";
                            System.out.println("Please copy text file");
                            String fileName = reader.readLine();
                            System.out.println("Please write Save name file");
                            String fileNameSave = reader.readLine();
                            breakingInto(resultSecond, fileName, fileNameSave, null);
                            break;
                        } else if (resultSecond.equalsIgnoreCase("analysis") || resultSecond.equalsIgnoreCase("a")){
                            resultSecond = "StaticAnalysis";
                            System.out.println("Please copy file exp");
                            String fileName = reader.readLine();
                            System.out.println("Please copy file to analysis russian lenguage");
                            String fileNameAnalysis = reader.readLine();
                            System.out.println("Please write file name Save");
                            String fileNameSave = reader.readLine();
                            breakingInto(resultSecond,fileName, fileNameSave, fileNameAnalysis);
                            break;
                        }
                        else {
                            continue;
                        }
                    }else {
                        continue;
                    }
                }
            }

    }

    public static void breakingInto (String info, String fileNameHack, String fileNameSave, String fileNameAnalysis) throws IOException{

        if (info.equalsIgnoreCase("BruteForce")){

            boolean test = false;
            int x = 1;
                while (!test) {
                    try (BufferedReader reader = new BufferedReader(new FileReader(fileNameHack))) {
                        File testFile = new File("/Users/mrshmel/Documents/GitHub/Caesar-s-cipher/src", fileNameSave);
                        if (testFile.exists()) {
                            testFile.delete();
                        }
                        Path newFile = Files.createFile(Paths.get("/Users/mrshmel/Documents/GitHub/Caesar-s-cipher/src",fileNameSave));
                        BufferedWriter writer = new BufferedWriter(new FileWriter(newFile.toFile(), true));
                        while (reader.ready()) {
                            for (char ch : reader.readLine().toCharArray()) {
                                if (alphabet.contains(ch)) {
                                    int index = alphabet.indexOf(ch) + x;
                                    if (index > SIZE_ALPHABET) {
                                        while (true) {
                                            index -= alphabet.size();
                                            if (index <= SIZE_ALPHABET) break;
                                        }
                                        writer.write(alphabet.get(index));
                                        continue;
                                    }
                                    writer.write(alphabet.get(index));
                                } else {
                                    writer.write(ch);
                                }
                            }
                            writer.write('\n');
                        }
                        writer.close();
                        try (BufferedReader readerTest = new BufferedReader(new FileReader(newFile.toFile()))) {
                            List<String> result = readerTest.lines().collect(Collectors.toList());
                            for (int i = 0; i < result.size(); i++) {
                                char[] ch = result.get(i).toCharArray();
                                for (int j = 0; j < ch.length - 1; j++) {
                                    if (punctuationMarks.contains(ch[j])) {
                                        if (ch[j + 1] == ' ') {
                                            if(result.stream().map(a -> a.split(" ")).allMatch(a -> Arrays.stream(a).allMatch(b -> b.length() < 25))){
                                                try {
                                                    BufferedReader readerConsole = new BufferedReader(new InputStreamReader(System.in));
                                                    System.out.println("Text this normal? Y/N");
                                                    System.out.println(result.get(0));
                                                    System.out.println(result.get(1));
                                                    if (readerConsole.readLine().equalsIgnoreCase("Y")) {
                                                        System.out.println("This kay " + x);
                                                        test = true;
                                                        readerConsole.close();
                                                        break;
                                                    } else {
                                                        break;
                                                    }
                                                } catch (Exception e) {
                                                }
                                            }
                                        }
                                    }
                                }
                                if (!test || test){
                                    break;
                                }
                            }
                        }
                        x += 1;
                    }
                }
            }
        if (info.equalsIgnoreCase("StaticAnalysis")) {

            Path fileAnalysis = Paths.get(fileNameAnalysis);
            List<String> resultAnalysis = Files.lines(fileAnalysis).collect(Collectors.toList());
            Map<Character, Integer> mapAnalysis = new HashMap<>();
            for (int i = 0; i < resultAnalysis.size(); i++) {
                for (char ch : resultAnalysis.get(i).toCharArray()) {
                    if (alphabet.contains(ch)) {
                        if (mapAnalysis.containsKey(ch)) {
                            int col = mapAnalysis.get(ch);
                            mapAnalysis.put(ch, col + 1);
                        } else {
                            mapAnalysis.put(ch, 1);
                        }
                    }
                }
            }
            Map<Character, Integer> sortedMapAnalysis = mapAnalysis.entrySet().stream().
                    sorted(Map.Entry.<Character, Integer>comparingByValue().reversed()).
                    collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

            Path file = Paths.get(fileNameHack);
            List<String> fileEncrypt = Files.lines(file).collect(Collectors.toList());
            Map<Character, Integer> mapEncrypt = new HashMap<>();
            for (int i = 0; i < fileEncrypt.size(); i++) {
                for (char ch : fileEncrypt.get(i).toCharArray()) {
                    if (alphabet.contains(ch)) {
                        if (mapEncrypt.containsKey(ch)) {
                            int col = mapEncrypt.get(ch);
                            mapEncrypt.put(ch, col + 1);
                        } else {
                            mapEncrypt.put(ch, 1);
                        }
                    }
                }
            }

            Map<Character, Integer> sortedMapEncrypt = mapEncrypt.entrySet().stream().
                    sorted(Map.Entry.<Character, Integer>comparingByValue().reversed()).
                    collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

            List <Character> charactersEncrypt = sortedMapEncrypt.entrySet().stream().map(a -> a.getKey()).collect(Collectors.toList());
            List <Character> charactersAnalysis = sortedMapAnalysis.entrySet().stream().map(a -> a.getKey()).collect(Collectors.toList());

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileNameSave))){
                for (int i = 0; i < fileEncrypt.size(); i++) {
                    char [] time = fileEncrypt.get(i).toCharArray();
                    for (int j = 0; j < time.length; j++) {
                        if (alphabet.contains(time[j])) {
                            int index = charactersEncrypt.indexOf(time[j]);
                            time[j] = charactersAnalysis.get(index);
                        }
                    }
                    writer.write(time);
                    writer.write('\n');
                }
            }
        }
    }


    public static void encryptDecipher(String info, String fileName, int x, String fileNameSave) throws IOException {
        if (info.equalsIgnoreCase("Encrypt")) {
            try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
                File file = new File(fileNameSave);
                if (file.exists()) {
                    file.delete();
                }
                Path newFile = Files.createFile(Paths.get("/Users/mrshmel/Documents/GitHub/Caesar-s-cipher/src",fileNameSave));
                while (reader.ready()) {
                    try (BufferedWriter writer = new BufferedWriter(new FileWriter(newFile.toFile(), true))) {
                        char[] ch = reader.readLine().toCharArray();
                        for (char c : ch) {
                            if (alphabet.contains(c)) {
                                int index = alphabet.indexOf(c) + x;
                                if (index > SIZE_ALPHABET) {
                                    while (true) {
                                        index -= alphabet.size();
                                        if (index <= SIZE_ALPHABET) break;
                                    }
                                    writer.write(alphabet.get(index));
                                    continue;
                                }else if (index < 0){
                                    while (true) {
                                        index += alphabet.size();
                                        if (index >= 0) break;
                                    }
                                    writer.write(alphabet.get(index));
                                    continue;
                                }
                                writer.write(alphabet.get(index));
                            } else {
                                writer.write(c);
                            }
                        }
                        writer.write('\n');
                    }
                }
            }
        } else if (info.equalsIgnoreCase("Decipher")) {
            try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
                File file = new File("/Users/mrshmel/Documents/GitHub/Caesar-s-cipher/src", fileNameSave);
                if (file.exists()) {
                    file.delete();
                }
                Path newFile = Files.createFile(Paths.get("/Users/mrshmel/Documents/GitHub/Caesar-s-cipher/src", fileNameSave));
                while (reader.ready()) {
                    try (BufferedWriter writer = new BufferedWriter(new FileWriter(newFile.toFile(), true))) {
                        char[] ch = reader.readLine().toCharArray();
                        for (char c : ch) {
                            if (alphabet.contains(c)) {
                                int index = alphabet.indexOf(c) - x;
                                if (index < 0) {
                                    while (true) {
                                        index += alphabet.size();
                                        if (index >= 0) break;
                                    }
                                    writer.write(alphabet.get(index));
                                    continue;
                                }else if (index > SIZE_ALPHABET){
                                    while (true){
                                        index -= alphabet.size();
                                        if (index <= SIZE_ALPHABET) break;
                                    }
                                    writer.write(alphabet.get(index));
                                    continue;
                                }
                                writer.write(alphabet.get(index));
                            } else {
                                writer.write(c);
                            }
                        }
                        writer.write('\n');
                    }
                }
            }
        }
    }

    public static List<Character> generateAlphabet(){
        List<Character> alphabet = new ArrayList<>();
        for (char i = 'а'; i <= 'я' ; i++) {
            if (i == 'ж'){
                alphabet.add('ё');
                alphabet.add('Ë');
            }
            alphabet.add(i);
            alphabet.add(Character.toUpperCase(i));
        }
        alphabet.add('.');
        alphabet.add(',');
        alphabet.add('"');
        alphabet.add(':');
        alphabet.add('-');
        alphabet.add('!');
        alphabet.add('?');
        alphabet.add(' ');
        return alphabet;
    }

    public static List<Character> generatePunctuationMarks(){
        List<Character> result = new ArrayList<>();
        result.add('.');
        result.add(',');
        result.add('"');
        result.add(':');
        result.add('-');
        result.add('!');
        result.add('?');

        return result;
    }
}
