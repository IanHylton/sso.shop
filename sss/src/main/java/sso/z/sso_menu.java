package sso.z;

import sso.z.libs.UserHandling;
import sso.z.libs.UserHandling.LoadShop;

import java.util.Scanner;

public final class sso_menu {
    private static class MainVariables {
        static boolean ssm_loop = true;
        static int ssm_input;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (MainVariables.ssm_loop == true) {

            System.out.println("Bienvenido.\n" + "1. Iniciar sesion.\n" +
                    "2. Registrarse.\n" + "3. Mostrar tienda de productos sin iniciar sesion.\n" +
                    "0. Salir.\n");
            MainVariables.ssm_input = scanner.nextInt();

            switch (MainVariables.ssm_input) {
                case 0:
                    MainVariables.ssm_loop = false;
                    break;
                case 1:
                    UserHandling.SIGN_X.SIGN_IN_METHOD();
                    break;
                case 2:
                    UserHandling.SIGN_X.SIGN_UP_METHOD();
                    break;
                case 3:
                    LoadShop.SHOP_ALL(0);
                    break;
                default:
                    System.out.println("Entrada invalida.");
                    break;
            }
        }
        scanner.close();
    }
}
// i hate my life