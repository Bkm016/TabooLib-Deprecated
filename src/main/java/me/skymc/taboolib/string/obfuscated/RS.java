package me.skymc.taboolib.string.obfuscated;

import java.util.Random;

@Deprecated
public class RS {

    private static final Random RANDOM = new Random();
    private static final char[] charsDown = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();
    private static final char[] charsUp = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();

    public static char getRandomCharacter() {
        return charsDown[RANDOM.nextInt(charsDown.length)];
    }

    public static char getRandomCharacter(Boolean isUp) {
        return isUp ? charsUp[RANDOM.nextInt(charsUp.length)] : charsDown[RANDOM.nextInt(charsDown.length)];
    }

    public static String getRandomString(int length) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            builder.append(getRandomCharacter());
        }
        return builder.toString();
    }

    public static String getRandomString(String prefix, String rule, Boolean isUp) {
        StringBuilder builder = new StringBuilder(prefix + "-");
        String[] value = rule.split("-");

        int size = 0;
        for (String subrule : value) {
            size++;

            for (int i = 0; i < Integer.valueOf(subrule); i++) {
                builder.append(getRandomCharacter(isUp));
            }
            if (!(size == value.length)) {
                builder.append("-");
            }
        }

        return builder.toString();
    }
}
