package br.com.fiap.chatbotdatabase1.util;

import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;

public class SanitizerUtil {
    private SanitizerUtil() {
    }

    public static String sanitize(String input) {
        return Jsoup.clean(input, Safelist.basic());
    }
}
