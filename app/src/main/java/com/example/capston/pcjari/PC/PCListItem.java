package com.example.capston.pcjari.PC;

/**
 * Created by KangSeungho on 2017-09-25.
 */

import android.graphics.drawable.Drawable;

import java.io.Serializable;

public class PCListItem implements Serializable{

    private int pcID;
    private String notice;
    private int icon;
    private String title;
    private String address;
    private String tel;
    private String cpu;
    private String ram;
    private String vga;
    private int price;
    private boolean card;

    private double location_x;
    private double location_y;

    private int totalSeat;
    private int spaceSeat;
    private int usingSeat;


    public void setPcID(int pcID) {
        this.pcID = pcID;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public void setCpu(String cpu) {
        this.cpu = cpu;
    }

    public void setRam(String ram) {
        this.ram = ram;
    }

    public void setVga(String vga) {
        this.vga = vga;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setCard(boolean card) {
        this.card = card;
    }

    public void setTotalSeat(int totalSeat) {
        this.totalSeat = totalSeat;
    }

    public void setSpaceSeat(int spaceSeat) {
        this.spaceSeat = spaceSeat;
    }

    public void setUsingSeat(int usingSeat) {
        this.usingSeat = usingSeat;
    }

    public void setLocation_x(double location_x) {
        this.location_x = location_x;
    }

    public void setLocation_y(double location_y) {
        this.location_y = location_y;
    }


    public int getPcID() {
        return pcID;
    }

    public String getNotice() {
        return notice;
    }

    public int getIcon() {
        return this.icon;
    }

    public String getTitle() {
        return this.title;
    }

    public String getAddress() {
        return this.address;
    }

    public String getTel() {
        return tel;
    }

    public String getCpu() {
        return cpu;
    }

    public String getRam() {
        return ram;
    }

    public String getVga() {
        return vga;
    }

    public int getPrice() {
        return price;
    }

    public boolean isCard() {
        return card;
    }

    public int getTotalSeat() {
        return totalSeat;
    }

    public int getSpaceSeat() {
        return spaceSeat;
    }

    public int getUsingSeat() {
        return usingSeat;
    }

    public double getLocation_x() {
        return location_x;
    }

    public double getLocation_y() {
        return location_y;
    }
}
