package de.techsails.Control;

import java.io.IOException;
import java.net.MalformedURLException;

import de.techsails.JavaJSON.JSONException;

public class SkyScannerTest {
    public static String SKY_SCANNER_KEY = "skyscanner-guts2019";
    public static void main(String[] args) throws MalformedURLException, JSONException, IOException{

        SkyScanner travel = new SkyScanner(SKY_SCANNER_KEY);
    }
}
