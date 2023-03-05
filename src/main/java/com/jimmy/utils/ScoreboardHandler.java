package com.jimmy.utils;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import net.minecraft.client.Minecraft;
import net.minecraft.scoreboard.*;
import net.minecraft.util.StringUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class ScoreboardHandler {

    public static Minecraft mc = Minecraft.getMinecraft();
    
    

    //PIZZA CLIENT LOCATION IMPLEMENTATION

    public static String cleanSB(String scoreboard) {
        char[] nvString = StringUtils.stripControlCodes(scoreboard).toCharArray();
        StringBuilder cleaned = new StringBuilder();
        for (char c : nvString) {
            if (c > '\024' && c < ' ')
                cleaned.append(c);
        }
        return cleaned.toString();
    }

    public static List<String> getSidebarLines() {
        List<String> lines = new ArrayList<>();
        Scoreboard scoreboard = mc.thePlayer.getWorldScoreboard();
        if (scoreboard == null)
            return lines;
        ScoreObjective objective = scoreboard.getObjectiveInDisplaySlot(1);
        if (objective == null)
            return lines;
        Collection<Score> scores = scoreboard.getSortedScores(objective);
        List<Score> list = (List<Score>)scores.stream().filter(input -> (input != null && input.getPlayerName() != null && !input.getPlayerName().startsWith("#"))).collect(Collectors.toList());
        scores = (list.size() > 15) ? Lists.newArrayList(Iterables.skip(list, scores.size() - 15)) : list;
        for (Score score : scores) {
            ScorePlayerTeam team = scoreboard.getPlayersTeam(score.getPlayerName());
            lines.add(ScorePlayerTeam.formatPlayerName((Team)team, score.getPlayerName()));
        }
        return lines;
    }

    public static boolean isScoreboardEmpty() {
        return (mc.thePlayer.getWorldScoreboard().getObjectiveInDisplaySlot(1) == null);
    }
}
