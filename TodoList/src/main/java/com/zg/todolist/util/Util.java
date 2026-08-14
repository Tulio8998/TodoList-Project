package com.zg.todolist.util;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Util {
    public static int leInteiroConsole(Scanner in){
        int r = 0;
        boolean continua = false;
        do{
            try{
                r = in.nextInt();
                in.nextLine();
                continua = false;
            }
            catch(InputMismatchException e){
                System.out.print("Erro ao ler número!\nDigite novamente: ");
                in.nextLine();
                continua = true;
            }
        }while(continua);
        return r;
    }

    public static String leStringConsole(Scanner in){
        String r = null;
        boolean continua = false;
        do{
            try{
                r = in.nextLine().toLowerCase();
                r = r.trim();
                continua = false;
            }
            catch(InputMismatchException e){
                System.out.print("Erro ao fazer a leitura!\nDigite novamente: ");
                in.nextLine();
                continua = true;
            }
        }while(continua);
        return r;
    }
}
