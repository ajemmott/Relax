package com.example.jmbwb.relax;

//Esta clase solo es para los permisos de youtube
public class YoutubeConfig {
    private static String ClaveAPI = "AIzaSyD_n01hq00Noh-RLG6bGiejjgfj1jHbdhs";

    public YoutubeConfig() {

    }

    public static String getClaveAPI() {
        return ClaveAPI;
    }

    public static void setClaveAPI(String claveAPI) {
        ClaveAPI = claveAPI;
    }
}
