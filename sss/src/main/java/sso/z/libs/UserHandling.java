package sso.z.libs;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import java.util.Scanner;
import java.util.Random;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import sso.z.sso_menu;

public class UserHandling {
    private static final String USEDATA_JS = "data\\usrdat.json";
    private static final String BUYDATA_JS = "data\\slrdat.json";

    // create a new user and store it in the JSON file.
    private static void SIGN_UP(String SU_email, String SU_username, String SU_password,
            String SU_phonenumber, String SU_location, int DIF_KEY) {
        JSONObject user = new JSONObject();
        switch (DIF_KEY) {
            case 0: { // normal user
                JSONArray usersArray = READ_JSON(0);
                user.put("userid", ID_GENERATOR(0));
                user.put("email", SU_email);
                user.put("username", SU_username);
                user.put("password", SU_password);
                user.put("phonenumber", SU_phonenumber);
                user.put("location", SU_location);
                usersArray.add(user);
                WRITE_JSON(usersArray, 0);
                break;
            }
            case 1: { // seller user
                JSONArray usersArray = READ_JSON(1);
                user.put("sellid", ID_GENERATOR(1));
                user.put("email", SU_email);
                user.put("sellname", SU_username);
                user.put("password", SU_password);
                user.put("phonenumber", SU_phonenumber);
                user.put("location", SU_location);
                usersArray.add(user);
                WRITE_JSON(usersArray, 1);
                break;
            }
        }
    }

    // check if the given username and password match an existing user in the JSON.
    private static boolean SIGN_IN(String SI_Xname, String SI_password, int DIF_KEY) {
        switch (DIF_KEY) {
            case 0: { // normal user
                JSONArray usersArray = READ_JSON(0);
                for (Object obj : usersArray) {
                    JSONObject user = (JSONObject) obj;
                    if (user.get("email").equals(SI_Xname) && user.get("password").equals(SI_password)) {
                        return true;
                    }
                }
                System.out.print("User was not found.");
                return false;
            }
            case 1: { // seller user
                JSONArray usersArray = READ_JSON(1);
                for (Object obj : usersArray) {
                    JSONObject user = (JSONObject) obj;
                    if (user.get("email").equals(SI_Xname) && user.get("password").equals(SI_password)) {
                        return true;
                    }
                }
                System.out.print("User was not found.");
                return false;
            }
        }
        return false;
    }

    // generate a random id for a new user.
    private static int ID_GENERATOR(int KEY) {
        Random rand = new Random();
        int id = rand.nextInt(100000);
        switch (KEY) {
            case 0: {
                JSONArray usersArray = READ_JSON(0);
                for (Object obj : usersArray) {
                    JSONObject user = (JSONObject) obj;
                    if ((long) user.get("id") == id) {
                        return ID_GENERATOR(0);
                    }
                }
                return id;
            }
            case 1: {
                JSONArray usersArray = READ_JSON(1);
                for (Object obj : usersArray) {
                    JSONObject user = (JSONObject) obj;
                    if ((long) user.get("id") == id) {
                        return ID_GENERATOR(1);
                    }
                }
                return id;
            }
        }
        return 1;
    }

    // read the user data from the JSON file and return it as a JSONArray object.
    private static JSONArray READ_JSON(int KEY) {
        JSONParser parser = new JSONParser();
        JSONArray usersArray = new JSONArray();
        switch (KEY) {
            case 0: {
                try {
                    FileReader fileReader = new FileReader(USEDATA_JS);
                    usersArray = (JSONArray) parser.parse(fileReader);
                    fileReader.close();
                } catch (IOException | ParseException e) {
                    e.printStackTrace();
                }
                return usersArray;
            }
            case 1: {
                try {
                    FileReader fileReader = new FileReader(BUYDATA_JS);
                    usersArray = (JSONArray) parser.parse(fileReader);
                    fileReader.close();
                } catch (IOException | ParseException e) {
                    e.printStackTrace();
                }
                return usersArray;
            }
        }
        return usersArray;
    }

    // write the given JSONArray object to the JSON file.
    // this will create a new JSON file if it was not found though.
    private static void WRITE_JSON(JSONArray usersArray, int KEY) {
        switch (KEY) {
            case 0:
                try {
                    FileWriter fileWriter = new FileWriter(USEDATA_JS, true); // append to existing file
                    if (usersArray.size() > 0) { // only write to file if there is data to write
                        for (Object obj : usersArray) {
                            JSONObject user = (JSONObject) obj;
                            fileWriter.write(user.toJSONString() + System.lineSeparator());
                        }
                        fileWriter.flush();
                    }
                    fileWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case 1:
                try {
                    FileWriter fileWriter = new FileWriter(BUYDATA_JS, true); // append to existing file
                    if (usersArray.size() > 0) { // only write to file if there is data to write
                        for (Object obj : usersArray) {
                            JSONObject user = (JSONObject) obj;
                            fileWriter.write(user.toJSONString() + System.lineSeparator());
                        }
                        fileWriter.flush();
                    }
                    fileWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    private static class SellF {
        public static void createProducts(String product, long price) {
            JSONObject productObj = new JSONObject();
            productObj.put("product", product);
            productObj.put("price", price);
            JSONArray usersArray = new JSONArray();
            usersArray.add(productObj);
            WRITE_JSON(usersArray, 0);
        }
    }

    private static class LoadShop_V {
        static long timeUsingApp = System.currentTimeMillis() / 60000;
        static double rating = Math.random() * 5;
        static JSONArray usersArray = READ_JSON(0);
        static int randomIndex = (int) (Math.random() * usersArray.size());
        static JSONObject user = (JSONObject) usersArray.get(randomIndex);
        static String userName = (String) user.get("sellname");
        static long userId = (Long) user.get("sellid");
        static String phoneNumber = (String) user.get("phonenumber");
        static String email = (String) user.get("email");
        static String location = (String) user.get("location");

        static JSONArray productsArray = READ_JSON(1);
        static JSONObject seller = (JSONObject) productsArray.get(randomIndex);
        static String product = (String) seller.get("product");
        static String price = (String) seller.get("price");

        public static void showSimpleShop(int TYPE) {
            switch (TYPE) {
                case 0:
                    for (int i = 0; i < 3; i++) {
                        System.out.println("\n" + "User Name: " + userName + "ID:" + userId);
                        System.out.println("\n" + "Product: " + product + "$" + price);
                        System.out.println("Rating: " + rating);
                        System.out.println("Phone number: " + phoneNumber);
                        System.out.println("Location: " + location);
                    }
                    System.out.println("Mostrando 3 objetos.");
                    break;
                case 1:
                    for (int j = 0; j < 5; j++) {
                        System.out.println("\n" + "Product: " + product + "$" + price);
                    }
                    break;
                case 2:
                    for (int k = 0; k < 2; k++) {
                        System.out.println("\n" + "User Name: " + userName + "\tid:" + userId);
                        System.out.println("\n" + "Product: " + product + "$" + price);
                        System.out.println("Rating: " + rating);
                    }
                    System.out.println("Mostrando 2 objetos.");
                    break;
            }
        }
    }

    // idk how to use this lmaoooo
    private static void SIGN_IN_AFTER(int TYPE) {
        System.out.println("Time Using App: " + LoadShop_V.timeUsingApp);

        switch (TYPE) {
            case 0: // sign user use "TYPE" for direct access
                System.out.println("Bienvenido usuario.\n");
                LoadShop_V.showSimpleShop(TYPE);
                break;
            case 1: // sign seller
                Scanner scanner = new Scanner(System.in);
                String LeProduct;
                int LePrice;
                JSONObject user = new JSONObject();
                System.out.println(
                        "Bienvenido vendedor.\n" + "1. Cargar producto." + "2. Mostrar productos" + "0. Salir.");
                int sellerChoice = scanner.nextInt();
                switch (sellerChoice) {
                    case 1:
                        System.out.println("Nombre del producto:");
                        LeProduct = scanner.nextLine();
                        System.out.println("Precio:");
                        LePrice = scanner.nextInt();
                        SellF.createProducts(LeProduct, LePrice);
                        System.out.println("Preview:");
                        LeProduct = (String) user.get("product");
                        LePrice = (int) user.get("price");
                        scanner.close();
                        break;
                    case 2:
                        LoadShop_V.showSimpleShop(1);
                }
                break;
            case 2: { // not sign use "TYPE" for direct access
                LoadShop_V.showSimpleShop(TYPE);
                break;
            }
        }
    }

    // im losing my mind
    public static class LoadShop {
        // istg im gonna end it
        public static void SHOP_ALL(int TYPE) {

            switch (TYPE) {
                case 0: { // not sign in for main case 3
                    SIGN_IN_AFTER(2);
                    break;
                }

                case 1: { // sign in type user
                    SIGN_IN_AFTER(0);
                    break;
                }

                case 2: { // sign in type seller
                    SIGN_IN_AFTER(1);
                    break;
                }
            }
        }
    }

    public static class SIGN_X {
        private static class SIGN_X_V {
            static boolean ssi_loop = true, ssu_loop = true;
            static int ssu_input = 0, ssi_input = 0;
            static String Xname, username, password, phonenumber, location;
        }

        private static void resetLocalVariables() {
            SIGN_X_V.ssu_input = 0;
            SIGN_X_V.ssi_input = 0;
            SIGN_X_V.Xname = "";
            SIGN_X_V.username = "";
            SIGN_X_V.password = "";
            SIGN_X_V.phonenumber = "";
            SIGN_X_V.location = "";
            SIGN_X_V.ssi_loop = true;
            SIGN_X_V.ssu_loop = true;
        }

        private static void printSignXMethod(int SIGNTYPE, int TYPE) {
            resetLocalVariables();
            Scanner scanner = new Scanner(System.in);
            switch (SIGNTYPE) {
                case 0: { // sign up
                    switch (TYPE) {
                        case 0:
                            System.out.println("\nIntroduce tu nombre real:");
                            SIGN_X_V.Xname = scanner.nextLine();
                            break;
                        case 1:
                            System.out.println("\nIntroduce el nombre de tu empresa:");
                            SIGN_X_V.Xname = scanner.nextLine();
                            break;
                    }
                    System.out.println("\nIntroduce tu nombre de usuario:");
                    SIGN_X_V.username = scanner.nextLine();
                    System.out.println("\nIntroduce tu contrasena:");
                    SIGN_X_V.password = scanner.nextLine();
                    System.out.println("\nIntroduce tu numero de contacto:");
                    SIGN_X_V.phonenumber = scanner.nextLine();
                    System.out.println("\nIntroduce tu localizacion:");
                    SIGN_X_V.location = scanner.nextLine();
                    break;
                }
                case 1: { // sign in
                    switch (TYPE) {
                        case 0:
                            System.out.println("\nIntroduce tu nombre real:");
                            SIGN_X_V.Xname = scanner.nextLine();
                            break;
                        case 1:
                            System.out.println("\nIntroduce el nombre de tu empresa:");
                            SIGN_X_V.username = scanner.nextLine();
                            break;
                    }
                    System.out.println("\nIntroduce tu contrasena:");
                    SIGN_X_V.password = scanner.nextLine();
                    break;
                }
            }
            scanner.close();
        }

        public static void SIGN_UP_METHOD() {
            Scanner scanner = new Scanner(System.in);
            while (SIGN_X_V.ssu_loop == true) {
                System.out.println("Como desea registrarse?\n" + "1. Registrarse como comprador.\n" +
                        "2. Registrarse como vendedor.\n" + "0. Salir.\n");
                SIGN_X_V.ssu_input = scanner.nextInt();
                switch (SIGN_X_V.ssu_input) {
                    case 0: // exit
                        SIGN_X_V.ssu_loop = false;
                        break;
                    case 1: // normal user
                        printSignXMethod(0, 0);
                        SIGN_UP(SIGN_X_V.Xname, SIGN_X_V.username, SIGN_X_V.password, SIGN_X_V.phonenumber,
                                SIGN_X_V.location, 0);
                        sso_menu.main(null);
                        break;
                    case 2: // seller user
                        printSignXMethod(0, 1);
                        SIGN_UP(SIGN_X_V.Xname, SIGN_X_V.username, SIGN_X_V.password, SIGN_X_V.phonenumber,
                                SIGN_X_V.location, 0);
                        sso_menu.main(null);
                        break;
                    default:
                        System.out.println("Entrada invalida.");
                        break;
                }
            }
            scanner.close();
        }

        public static void SIGN_IN_METHOD() {
            Scanner scanner = new Scanner(System.in);
            while (SIGN_X_V.ssi_loop == true) {
                System.out.println("Bienvenido de vuelta.\n" + "1. Iniciar sesion como comprador.\n" +
                        "2. Iniciar sesion como vendedor.\n" + "0. Salir.\n");
                SIGN_X_V.ssi_input = scanner.nextInt();
                switch (SIGN_X_V.ssi_input) {
                    case 0: // exit
                        SIGN_X_V.ssi_loop = false;
                        break;
                    case 1: { // normal user
                        printSignXMethod(1, 0);
                        SIGN_IN(SIGN_X_V.Xname, SIGN_X_V.password, 0);
                        break;
                    }
                    case 2: { // seller user
                        printSignXMethod(1, 1);
                        SIGN_IN(SIGN_X_V.Xname, SIGN_X_V.password, 1);
                        break;
                    }
                    default:
                        System.out.println("Entrada invalida.");
                        break;
                }
            }
            scanner.close();
        }
    } // class SIGN_X
} // class UserHandling
