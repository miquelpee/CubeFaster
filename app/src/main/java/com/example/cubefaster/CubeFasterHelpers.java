package com.example.cubefaster;

import java.util.concurrent.TimeUnit;

public class CubeFasterHelpers {

    //Helpers to show formatted times in activities.

    //Converting times to milliseconds.
    public int timeToMilliseconds(CharSequence time){
        String timeX = (String) time;
        String[] units = timeX.split(":");
        int minutes = Integer.parseInt(units[0].trim());
        int seconds = Integer.parseInt(units[1].trim());
        int milliseconds = Integer.parseInt(units[2].trim());
        int duration = (60 * 1000 * minutes) + (1000 * seconds) + milliseconds;
        return duration;
    }

    //Formatting time that it's always mm:ss:mss
    public String timeConvert(int milliseconds){
        long minutes = TimeUnit.MILLISECONDS.toMinutes(milliseconds);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(milliseconds);
        long mseconds = milliseconds - ((60 * 1000 * minutes) + (1000 * seconds));

        if(seconds < 10 && mseconds < 10) {
            String time = minutes + ":0" + seconds + ":00" + mseconds;
            return time;
        }
        else if (seconds < 10 && mseconds < 100) {
            String time = minutes + ":0" + seconds + ":0" + mseconds;
            return time;
        }
        else if (seconds < 10) {
            String time = minutes + ":0" + seconds + ":" + mseconds;
            return time;
        }
        else if (mseconds < 10) {
            String time = minutes + ":" + seconds + ":00" + mseconds;
            return time;
        }
        else if (mseconds < 100) {
            String time = minutes + ":" + seconds + ":0" + mseconds;
            return time;
        }
        else {
            String time = minutes + ":" + seconds + ":" + mseconds;
            return time;
        }
    }
}
