package com.example.capston.pcjari.PC;

/**
 * Created by KangSeungho on 2017-09-25.
 */

import java.io.Serializable;

public class PCListItem implements Serializable{

    private int pcID;
    private String notice;
    private String icon;
    private String title;
    private String si;
    private String gu;
    private String dong;
    private String etc_juso;
    private String tel;
    private String cpu;
    private String ram;
    private String vga;
    private String peripheral;
    private int price;
    private boolean card=false;
    private int seatLength;

    private double location_x;
    private double location_y;

    private int totalSeat;
    private int spaceSeat;
    private int usingSeat;

    private double dist;


    public void setPcID(int pcID) {
        this.pcID = pcID;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSi(String si) {
        this.si = si;
    }

    public void setGu(String gu) {
        this.gu = gu;
    }

    public void setDong(String dong) {
        this.dong = dong;
    }

    public void setEtc_juso(String etc_juso) {
        this.etc_juso = etc_juso;
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

    public void setPeripheral(String peripheral) {
        this.peripheral = peripheral;
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

    public void setSeatLength(int seatLength) {
        this.seatLength = seatLength;
    }

    public void setDist(double dist) {
        this.dist = dist;
    }


    public int getPcID() {
        return pcID;
    }

    public String getNotice() {
        return notice;
    }

    public String getIcon() {
        return this.icon;
    }

    public String getTitle() {
        return this.title;
    }

    public String getSi() {
        return this.si;
    }

    public String getGu() {
        return gu;
    }

    public String getDong() {
        return dong;
    }

    public String getEtc_juso() {
        return etc_juso;
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

    public String getPeripheral() {
        return peripheral;
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

    public int getSeatLength() {
        return seatLength;
    }

    public double getDist() {
        return dist;
    }
}
