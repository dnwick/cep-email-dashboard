package org.wso2.cep.email.monitor.query.api.expressions;


public class TimeExpr extends Expression {

    private int year;
    private int month;
    private int week;
    private int day;
    private int hour;
    private int miniute;
    private int second;
    private int milisecond;

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMiniute() {
        return miniute;
    }

    public void setMiniute(int miniute) {
        this.miniute = miniute;
    }

    public int getSecond() {
        return second;
    }

    public void setSecond(int second) {
        this.second = second;
    }

    public int getMilisecond() {
        return milisecond;
    }

    public void setMilisecond(int milisecond) {
        this.milisecond = milisecond;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        if (year > 0) {
            stringBuilder.append(year + " years ");
        }
        if (month > 0) {
            stringBuilder.append(month + " months ");
        }
        if (week > 0) {
            stringBuilder.append(week + " weeks ");
        }
        if (day > 0) {
            stringBuilder.append(day + " days");
        }
        if (hour > 0) {
            stringBuilder.append(hour + " hours ");
        }
        if (miniute > 0) {
            stringBuilder.append(miniute + " miniutes ");
        }
        if (second > 0) {
            stringBuilder.append(second + " seconds ");
        }
        if (milisecond > 0) {
            stringBuilder.append(milisecond + " miliseconds ");
        }
        return stringBuilder.toString();
    }
}
