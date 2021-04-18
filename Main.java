
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        menu();
    }

    public static void menu(){
        int x=0;
        while (x != 4) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Вход в личный кабинет");
            System.out.println("1. Авторизация");
            System.out.println("2. Регистрация");
            System.out.println("3. Регистрация администратора");
            System.out.println("4. Выход");
            System.out.println();
            System.out.print("Выберите опцию: ");
            x = scanner.nextInt();
            createPerson(x);
        }
    }
    public static void createPerson(int x) {
        switch (x){
            case 1:
                galaxyVoid.authentication.authorization();
                break;
            case 2:
                galaxyVoid.regestration user = new galaxyVoid.regestration();
                break;
            case 3:
                galaxyVoid.admin.regAdmin();
            default:
                for (int i = 0; i<20; i++){
                    System.out.println();
                }
                System.out.println("Выбран неверный пункт");
                System.out.println();
                menu();
        }

    }
}

class galaxyVoid {
    private static String strDatabase;
    private static String strPersonInfo;
    private static String password;
    private static String codeword;
    private static String login;
    private static String adminCode;
    private static char[] salt = new char[20];
    private static char[] saltAdmin = new char[20];
    private static char[] saltCodeWord = new char[20];
    private static int count;
    static Random random = new Random();
    static class authentication{



        public static void authorization(){
            Scanner scanner = new Scanner(System.in);
            System.out.println("Введите логин");
            login = scanner.next();
            System.out.println("Введите пароль");
            password = scanner.next();
            System.out.println("Введите кодовое слово");
            codeword = scanner.next();
            int hashCodeword = codeword.hashCode();
            int hashPassword = password.hashCode();
            String presalt = login;
            for (int i = 0; i < presalt.length(); i++) {
                salt[i] = presalt.charAt(random.nextInt(presalt.length()));
            }
            for (int i = 0; i < codeword.length(); i++) {
                saltCodeWord[i] = codeword.charAt(random.nextInt(codeword.length()));
            }
            String hexstrPassword = Integer.toString(hashPassword, 16) + salt;
            String hexstrCodeword = Integer.toString(hashCodeword, 16) + saltCodeWord;
            password = hexstrPassword;
            codeword = hexstrCodeword;
            strPersonInfo = login + password + codeword;
            readDataBase(strPersonInfo);
        }
    }


    static class regestration {


        public regestration() {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Введите логин");
            login = scanner.next();
            System.out.println("Введите пароль");
            password = scanner.next();
            System.out.println("Введите кодовое слово");
            codeword = scanner.next();
            int hashCodeword = codeword.hashCode();
            int hashPassword = password.hashCode();
            String presalt = login;
            for (int i = 0; i < presalt.length(); i++) {
                salt[i] = presalt.charAt(random.nextInt(presalt.length()));
            }
            for (int i = 0; i < codeword.length(); i++) {
                saltCodeWord[i] = codeword.charAt(random.nextInt(codeword.length()));
            }
            String hexstrPassword = Integer.toString(hashPassword, 16) + salt;
            String hexstrCodeword = Integer.toString(hashCodeword, 16) + saltCodeWord;
            password = hexstrPassword;
            codeword = hexstrCodeword;
            recordingData(login, password, codeword);
        }
    }
    static class admin{


        public static void regAdmin(){
            {
                Scanner scanner = new Scanner(System.in);
                System.out.println("Введите логин");
                login = scanner.next();
                System.out.println("Введите пароль");
                password = scanner.next();
                System.out.println("Введите кодовое слово");
                codeword = scanner.next();
                int hashCodeword = codeword.hashCode();
                int hashPassword = password.hashCode();
                int hashAdmin = "admin".hashCode();
                String presalt = login;
                for (int i = 0; i < presalt.length(); i++) {
                    salt[i] = presalt.charAt(random.nextInt(presalt.length()));
                }
                for (int i = 0; i < codeword.length(); i++) {
                    saltCodeWord[i] = codeword.charAt(random.nextInt(codeword.length()));
                }
                for (int i = 0; i < codeword.length(); i++) {
                    saltAdmin[i] = "admin".charAt(random.nextInt("admin".length()));
                }
                String hexstrPassword = Integer.toString(hashPassword, 16) + salt;
                String hexstrCodeword = Integer.toString(hashCodeword, 16) + saltCodeWord;
                String hexstrAdmin = Integer.toString(hashAdmin, 16) + saltAdmin;
                password = hexstrPassword;
                codeword = hexstrCodeword;
                login = login + hexstrAdmin;

                recordingData(login, password, codeword);
            }
        }
    }
    private static void recordingData(String login, String password, String codeword) {
        File file = new File("/Users/Admin/IdeaProjects/untitled/src/com/company/database.txt");
        FileWriter fr = null;
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        byte[] byteArray = new byte[(int)file.length()];
        try {
            fis.read(byteArray);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String data = new String(byteArray);
        String[] stringArray = data.split("\r\n");
        count = stringArray.length;
        try {
            fr = new FileWriter(file);
            count++;
            fr.write(login  + password + codeword);
            fr.write("\r\n");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                assert fr != null;
                fr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    static void readDataBase(String strPersonInfo){
        try (BufferedReader reader = Files.newBufferedReader(Paths.get("database.txt"))) {
            String line;
            boolean access = false;
            while ((line = reader.readLine()) != null) {
                strDatabase = line.replace(" ","");
                if (strDatabase.equals(strPersonInfo)){
                    System.out.println("Доступ получен");
                    access = true;
                    break;
                }
            }
            if (!access){
                System.out.println("Ошибка авторизации");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println();
    }
}


