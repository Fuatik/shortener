package src;



import src.strategy.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Solution {
    public static void main(String[] args) {
        long testElementsNumber = 1000;
        testStrategy(new HashMapStorageStrategy(), testElementsNumber);
        testStrategy(new OurHashMapStorageStrategy(), testElementsNumber);
        testStrategy(new FileStorageStrategy(), testElementsNumber);
        testStrategy(new OurHashBiMapStorageStrategy(), testElementsNumber);
        testStrategy(new HashBiMapStorageStrategy(), testElementsNumber);
        testStrategy(new DualHashBidiMapStorageStrategy(), testElementsNumber);
    }

    public static Set<Long> getIds(Shortener shortener, Set<String> strings) {
        Set<Long> keys = new HashSet<>();
        for (String string : strings) {
            keys.add(shortener.getId(string));
        }
        return keys;
    }

    public static Set<String> getStrings(Shortener shortener, Set<Long> keys) {
        Set<String> strings = new HashSet<>();
        for (Long key : keys) {
            strings.add(shortener.getString(key));
        }
        return strings;
    }

    public static void testStrategy(StorageStrategy strategy, long elementsNumber) {

        Helper.printMessage(strategy.getClass().getSimpleName() + ":");

        Set<String> testStrings = new HashSet<>();
        for (int i = 0; i < elementsNumber; i++) {
            testStrings.add(Helper.generateRandomString());
        }

        Shortener shortener = new Shortener(strategy);

        Date startDate = new Date();
        Set<Long> keys = getIds(shortener, testStrings);
        Date endDate = new Date();
        long time = endDate.getTime() - startDate.getTime();
        Helper.printMessage("Время получения идентификаторов для " + elementsNumber + " строк: " + time + "мс");

        startDate = new Date();
        Set<String> strings = getStrings(shortener, keys);
        endDate = new Date();
        time = endDate.getTime() - startDate.getTime();
        Helper.printMessage("Время получения строк для " + elementsNumber + " идентификаторов: " + time + "мс");

        if (testStrings.equals(strings)) {
            Helper.printMessage("Тест пройден.");
        } else {
            Helper.printMessage("Тест не пройден.");
        }

        Helper.printMessage("");
    }
}
