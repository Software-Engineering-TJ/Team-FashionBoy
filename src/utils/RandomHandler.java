package utils;

import java.util.HashSet;
import java.util.Random;

public class RandomHandler {

    public static HashSet<Integer> createNonRepeatingRandom(int size, int lowerBound, int upperBound) {
        //HashSet集合中不允许用重复
        HashSet<Integer> set = new HashSet<>();
        Random ra = new Random();
        do {
            int r = ra.nextInt(upperBound) + lowerBound;
            set.add(r);
        } while (set.size() < size);
        return set;
    }
}
