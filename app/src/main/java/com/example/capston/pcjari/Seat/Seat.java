package com.example.capston.pcjari.Seat;

/**
 * Created by KangSeungho on 2017-12-01.
 */

public class Seat {
    private int pc_id;
    private int place;
    private int seat_id;
    private int pc_state;
    private int pc_time;

    public int getPc_id() {
        return pc_id;
    }

    public void setPc_id(int pc_id) {
        this.pc_id = pc_id;
    }

    public int getPlace() {
        return place;
    }

    public void setPlace(int place) {
        this.place = place;
    }

    public int getSeat_id() {
        return seat_id;
    }

    public void setSeat_id(int seat_id) {
        this.seat_id = seat_id;
    }

    public int getPc_state() {
        return pc_state;
    }

    public void setPc_state(int pc_state) {
        this.pc_state = pc_state;
    }

    public int getPc_time() {
        return pc_time;
    }

    public void setPc_time(int pc_time) {
        this.pc_time = pc_time;
    }
}
