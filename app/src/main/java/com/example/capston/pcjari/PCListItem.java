package com.example.capston.pcjari;

/**
 * Created by 94tig on 2017-09-25.
 */

import android.graphics.drawable.Drawable;

public class PCListItem {
    private int pcID;
    private String info;
    private Drawable icon;
    private String title;
    private String address;
    private String tel;
    private String cpu;
    private String ram;
    private String vga;
    private int price;
    private boolean card;

    private int totalSeat;
    private int spaceSeat;
    private boolean favorite = false;


    public void setPcID(int pcID) {
        this.pcID = pcID;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public void setIcon(Drawable icon) {
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

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }


    public int getPcID() {
        return pcID;
    }

    public String getInfo() {
        return info;
    }

    public Drawable getIcon() {
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

    public boolean isFavorite() {
        return favorite;
    }
}
