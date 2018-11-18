package com.example.chiragmadan.mycompanion;

public class Menu {
   private String Day;
   private String Breakfast;
   private String Lunch;
   private String Dinner;

    public Menu(String day, String breakfast, String lunch, String dinner) {
        Day = day;
        Breakfast = breakfast;
        Lunch = lunch;
        Dinner = dinner;
    }

    public String getDay() {
        return Day;
    }

    public void setDay(String day) {
        Day = day;
    }

    public String getBreakfast() {
        return Breakfast;
    }

    public void setBreakfast(String breakfast) {
        Breakfast = breakfast;
    }

    public String getLunch() {
        return Lunch;
    }

    public void setLunch(String lunch) {
        Lunch = lunch;
    }

    public String getDinner() {
        return Dinner;
    }

    public void setDinner(String dinner) {
        Dinner = dinner;
    }
}
