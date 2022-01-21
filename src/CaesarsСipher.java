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
                        encryptDecipher(result, reader.readLine(), Integer.parseInt(reader.readLine()));
                        break;

                    } else if (result.equalsIgnoreCase("Cryptanalysis") || result.equalsIgnoreCase("C")){
                        System.out.println("Wanna hack :) or Do text analysis ? Write Hack or Analysis   H/A");
                        String resultSecond = reader.readLine();
                        if (resultSecond.equalsIgnoreCase("Hack") || resultSecond.equalsIgnoreCase("H")){
                            resultSecond = "BruteForce";
                            System.out.println("Please copy text file");
                            String fileName = reader.readLine();
                            breakingInto(resultSecond, fileName);
                            break;
                        } else if (resultSecond.equalsIgnoreCase("analysis") || resultSecond.equalsIgnoreCase("a")){
                            resultSecond = "StaticAnalysis";
                            breakingInto(resultSecond,"/Users/mrshmel/Documents/Encrypt.txt");
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

    public static void breakingInto (String info, String fileName) throws IOException {

        if (info.equalsIgnoreCase("BruteForce")){

            boolean test = false;
            int x = 1;
            System.out.println("Please write text name Brute");
            BufferedReader readerConsoleFileName = new BufferedReader(new InputStreamReader(System.in));
            String fileNameDep = readerConsoleFileName.readLine();
            readerConsoleFileName.close();
                while (!test) {
                    try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
                        File testFile = new File(fileNameDep);
                        if (testFile.exists()) {
                            testFile.delete();
                        }
                        System.out.println("ja tu");
                        Path newFile = Files.createFile(Paths.get("/Users/mrshmel/Documents/GitHub/Caesar-s-cipher/src",fileNameDep));
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
                                            try{
                                                BufferedReader readerConsole = new BufferedReader(new InputStreamReader(System.in));
                                                System.out.println("Text this normal? Y/N");
                                                System.out.println(result.get(0));
                                                //System.out.println(result.get(1));
                                                if (readerConsole.readLine().equalsIgnoreCase("Y")){
                                                    System.out.println("zaebok");
                                                    test = true;
                                                    readerConsole.close();
                                                    break;
                                                }else {
                                                    break;
                                                }
                                            }catch (Exception e){
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
            System.out.println("Please copy additional file to analysis");
            BufferedReader readerConsole = new BufferedReader(new InputStreamReader(System.in));
            Path fileAnalysis = Paths.get(readerConsole.readLine());
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
            /*int maxAnalysis = 0;
            for (int i : sortedMapAnalysis.values()) {
                maxAnalysis += i;
            }*/
            List<Character> sortAnalysis = sortedMapAnalysis.entrySet().stream().limit(5).map(a -> a.getKey()).collect(Collectors.toList());
            /*for (char a : sortAnalysis){
                System.out.println(alphabet.indexOf(a));
            }*/
            /*for (Map.Entry <Character, Integer> ch : sortedMapAnalysis.entrySet()){
                System.out.println(ch.getKey() + " : " + ch.getValue() + " : " + ch.getValue() * 1.0 / maxAnalysis * 100 + "%");
            }
            System.out.println("---------------------");*/

            Path file = Paths.get(fileName);
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
            /*int maxEncrypt = 0;
            for (int i : sortedMapEncrypt.values()) {
                maxEncrypt += i;
            }
            System.out.println("-----------");*/
            List<Character> sortEncrypt = sortedMapEncrypt.entrySet().stream().limit(5).map(a -> a.getKey()).collect(Collectors.toList());
            /*for (char a : sortEncrypt){
            System.out.println(alphabet.indexOf(a));
        }*/

        /*for (Map.Entry <Character, Integer> ch : sortedMapEncrypt.entrySet()){
            System.out.println(ch.getKey() + " : " + ch.getValue() + " : " + ch.getValue() * 1.0 / maxEncrypt * 100 + "%");
        }*/
            for (int i = 0; i < sortEncrypt.size(); i++) {
               int indexEncrypt = alphabet.indexOf(sortEncrypt.get(i));
               int indexAnalysis = alphabet.indexOf(sortAnalysis.get(i));
               int index = 0;
               if (indexAnalysis > indexEncrypt){
                   index = indexAnalysis - indexEncrypt;
               }else {
                   index = indexEncrypt - indexAnalysis;
               }
               //index++;
                System.out.println(indexEncrypt);
                System.out.println(indexAnalysis);
               encryptDecipher("Decipher", fileName, index);
               BufferedReader reader = new BufferedReader(new FileReader("Decipher.txt"));
                System.out.println("Text normal ?  Y/N");
                System.out.println(reader.readLine());
                String result = readerConsole.readLine();
                if (result.equalsIgnoreCase("Y")){
                    System.out.println("NAISAAAA");
                    reader.close();
                    readerConsole.close();
                    break;
                }
            }
        }
    }


    public static void encryptDecipher(String info, String fileName, int x) throws IOException {
        if (info.equalsIgnoreCase("Encrypt")) {
            try (BufferedReader reader = new BufferedReader(new FileReader(fileName));
            BufferedReader readerConsole = new BufferedReader(new InputStreamReader(System.in))) {
                System.out.println("Please write nameFile");
                String fileNameDecr = readerConsole.readLine();
                File file = new File(fileNameDecr);
                if (file.exists()) {
                    file.delete();
                }
                Path newFile = Files.createFile(Paths.get("/Users/mrshmel/Documents/GitHub/Caesar-s-cipher/src",fileNameDecr));
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
                File file = new File("Decipher.txt");
                if (file.exists()) {
                    file.delete();
                }
                Path newFile = Files.createFile(Paths.get("Decipher.txt"));
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
