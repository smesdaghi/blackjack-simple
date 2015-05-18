package com.syrusm.blackjack;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Console {

    public static void showMessage(String message) {
        System.out.println(message);
    }

    public static String getUserInput() {
        String string = "";
        InputStreamReader streamReader = new InputStreamReader(System.in);
        BufferedReader bufferedReader = new BufferedReader(streamReader);

        try {
            string = bufferedReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return string;
    }
}
