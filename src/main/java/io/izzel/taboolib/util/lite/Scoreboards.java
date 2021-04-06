package io.izzel.taboolib.util.lite;

import io.izzel.taboolib.Version;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Scoreboard;

import java.util.*;
import java.util.stream.IntStream;

public class Scoreboards {

    static String fixTitle(String title) {
        if (title == null) {
            return "Unamed board";
        }
        if (title.length() > 32) {
            return title.substring(0, 32);
        }
        return title;
    }

    static String[] fixLines(String[] content) {
        String[] elements = Arrays.copyOf(content, 16);
        if (elements[0] == null) {
            elements[0] = "Unamed board";
        }
        if (elements[0].length() > 32) {
            elements[0] = elements[0].substring(0, 32);
        }
        IntStream.range(1, elements.length)
                .filter(i -> elements[i] != null)
                .filter(i -> Version.isBefore(Version.v1_13) && elements[i].length() > 40)
                .forEach(i -> elements[i] = elements[i].substring(0, 40));
        return elements;
    }

    static HashMap<String, Integer> fixLines(Map<String, Integer> content) {
        HashMap<String, Integer> elements = new HashMap<>(content);
        while (elements.size() > 15) {
            String minimumKey = (String) elements.keySet().toArray()[0];
            int minimum = elements.get(minimumKey);
            for (String string : elements.keySet()) {
                if (elements.get(string) < minimum || (elements.get(string) == minimum && string.compareTo(minimumKey) < 0)) {
                    minimumKey = string;
                    minimum = elements.get(string);
                }
            }
            elements.remove(minimumKey);
        }
        for (String string : new ArrayList<>(elements.keySet())) {
            if (string != null && string.length() > 40) {
                int value = elements.get(string);
                elements.remove(string);
                elements.put(string.substring(0, 40), value);
            }
        }
        return elements;
    }

    public static Scoreboard display(Player player, String... elements) {
        elements = fixLines(elements);
        try {
            if (player.getScoreboard() == null || player.getScoreboard() == Bukkit.getScoreboardManager().getMainScoreboard() || player.getScoreboard().getObjectives().size() != 1) {
                player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
            }
            if (player.getScoreboard().getObjective(player.getUniqueId().toString().substring(0, 16)) == null) {
                player.getScoreboard().registerNewObjective(player.getUniqueId().toString().substring(0, 16), "dummy");
                player.getScoreboard().getObjective(player.getUniqueId().toString().substring(0, 16)).setDisplaySlot(DisplaySlot.SIDEBAR);
            }
            player.getScoreboard().getObjective(DisplaySlot.SIDEBAR).setDisplayName(elements[0]);
            for (int i = 1; i < elements.length; i++) {
                if (elements[i] != null) {
                    if (player.getScoreboard().getObjective(DisplaySlot.SIDEBAR).getScore(elements[i]).getScore() != 16 - i) {
                        player.getScoreboard().getObjective(DisplaySlot.SIDEBAR).getScore(elements[i]).setScore(16 - i);
                        for (String string : player.getScoreboard().getEntries()) {
                            if (player.getScoreboard().getObjective(player.getUniqueId().toString().substring(0, 16)).getScore(string).getScore() == 16 - i) {
                                if (!string.equals(elements[i])) {
                                    player.getScoreboard().resetScores(string);
                                }
                            }
                        }
                    }
                }
            }
            for (String entry : player.getScoreboard().getEntries()) {
                boolean toErase = true;
                for (String element : elements) {
                    if (element != null && element.equals(entry) && player.getScoreboard().getObjective(player.getUniqueId().toString().substring(0, 16)).getScore(entry).getScore() == 16 - Arrays.asList(elements).indexOf(element)) {
                        toErase = false;
                        break;
                    }
                }
                if (toErase) {
                    player.getScoreboard().resetScores(entry);
                }
            }
            return player.getScoreboard();
        } catch (Exception e) {
            return null;
        }
    }

    public static boolean display(Collection<Player> players, String[] elements) {
        return players.stream().noneMatch(player -> display(player, elements) == null);
    }

    public static boolean display(Collection<Player> players, Scoreboard board, String... elements) {
        try {
            String objName = "COLLAB-SB-WINTER";
            if (board == null) {
                board = Bukkit.getScoreboardManager().getNewScoreboard();
            }
            elements = fixLines(elements);
            for (Player player : players) {
                if (player.getScoreboard() != board) {
                    player.setScoreboard(board);
                }
            }
            if (board.getObjective(objName) == null) {
                board.registerNewObjective(objName, "dummy");
                board.getObjective(objName).setDisplaySlot(DisplaySlot.SIDEBAR);
            }
            board.getObjective(DisplaySlot.SIDEBAR).setDisplayName(elements[0]);
            for (int i = 1; i < elements.length; i++) {
                if (elements[i] != null && board.getObjective(DisplaySlot.SIDEBAR).getScore(elements[i]).getScore() != 16 - i) {
                    board.getObjective(DisplaySlot.SIDEBAR).getScore(elements[i]).setScore(16 - i);
                    for (String string : board.getEntries()) {
                        if (board.getObjective(objName).getScore(string).getScore() == 16 - i) {
                            if (!string.equals(elements[i])) {
                                board.resetScores(string);
                            }
                        }
                    }
                }
            }
            for (String entry : board.getEntries()) {
                boolean toErase = true;
                for (String element : elements) {
                    if (element != null && element.equals(entry) && board.getObjective(objName).getScore(entry).getScore() == 16 - Arrays.asList(elements).indexOf(element)) {
                        toErase = false;
                        break;
                    }
                }
                if (toErase) {
                    board.resetScores(entry);
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}