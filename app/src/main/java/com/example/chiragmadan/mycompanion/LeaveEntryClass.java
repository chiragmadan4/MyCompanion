package com.example.chiragmadan.mycompanion;

public class LeaveEntryClass {
    private  String Name;
    private  String Roll_no;
    private  String Room_no;
    private  String From_date;
    private  String To_date;

    public LeaveEntryClass(String name, String roll_no, String room_no, String from_date, String to_date) {
        Name = name;
        Roll_no = roll_no;
        Room_no = room_no;
        From_date = from_date;
        To_date = to_date;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getRoll_no() {
        return Roll_no;
    }

    public void setRoll_no(String roll_no) {
        Roll_no = roll_no;
    }

    public String getRoom_no() {
        return Room_no;
    }

    public void setRoom_no(String room_no) {
        Room_no = room_no;
    }

    public String getFrom_date() {
        return From_date;
    }

    public void setFrom_date(String from_date) {
        From_date = from_date;
    }

    public String getTo_date() {
        return To_date;
    }

    public void setTo_date(String to_date) {
        To_date = to_date;
    }
}
