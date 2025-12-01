package com.lesbijouxdulouvre.team3.utils;

public class GameTimer {

    private float timeRemaining;
    private boolean alarmTriggered = false;

    public GameTimer(float startTimeSeconds) {
        this.timeRemaining = startTimeSeconds;
    }

    public void update(float delta) {
        if (isFinished()) return;

        timeRemaining -= delta;

        if (timeRemaining <= 0) {
            timeRemaining = 0;
        }
    }

    public boolean isCritical() {
        return timeRemaining <= 10f && timeRemaining > 0f;
    }

    public boolean isFinished() {
        return timeRemaining <= 0f;
    }

    public void triggerAlarm() {
        alarmTriggered = true;
    }

    public boolean isAlarmTriggered() {
        return alarmTriggered;
    }

    public String getFormattedTime() {
        int total = (int) timeRemaining;
        int minutes = total / 60;
        int seconds = total % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }
}