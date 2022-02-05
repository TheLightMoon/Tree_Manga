package common;

import java.util.ArrayList;
import java.util.List;

import model.Chapter;
import model.Manga;

public class Common {
    public static List<Manga> mangaList = new ArrayList<>();
    public static Manga mangaSelected;
    public static List<Chapter> chapterList;
    public static Chapter chapterSelected;
    public static int chapterIndex =-1;

    public static String formatString(String name) {
    StringBuilder finalResult = new StringBuilder(name.length() > 15?name.substring(0, 15) + "...":name);
    return finalResult.toString();
    }
}
