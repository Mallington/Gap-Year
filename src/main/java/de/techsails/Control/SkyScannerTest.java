package de.techsails.Control;

import java.io.IOException;

public class SkyScannerTest {
    public static String SKY_SCANNER_KEY = "jacobs-2019";
    public static void main(String[] args){
        Request request = new Request();

        TravelCommon travel = new TravelCommon(SKY_SCANNER_KEY, request);
        try {
            System.out.println(travel.getCurrencies());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
