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

    public static String[] categories = {
            "A", "Action", "Adult", "Adventure",
            "B", "Bomber",
            "C", "Comedy", "Completed", "Cooking",
            "D", "Doujinshi", "Drama", "Drop",
            "E", "Ecchi",
            "F", "Fantasy",
            "G", "Gender bender",
            "H", "Harem", "Historical", "Horror",
            "I", "Infected",
            "J", "Jose",
            "K", "Killer",
            "L", "Latest",
            "M", "Manhua", "Manhwa", "Material arts", "Mature", "Mecha", "Medical", "Mystery",
            "N", "Newest",
            "O", "One shot", "Ongoing",
            "P", "Psychological",
            "Q", "Quit",
            "R", "Romance",
            "S", "School life", "Sci fi", "Seinen", "Shoujo", "Shoujo a", "Shounen", "Shounen ai", "Slice of life", "Smut", "Sports", "Superhero", "Supernatural",
            "T", "Top Read", "Tragedy",
            "U", "Ubit",
            "V", "Virus",
            "W", "Webtoons",
            "Y", "Yaoi", "Yuri",
            "Z", "Zombie"
    };
}
