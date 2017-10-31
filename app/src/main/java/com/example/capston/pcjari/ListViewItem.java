package com.example.capston.pcjari;

/**
 * Created by 94tig on 2017-09-25.
 */

import android.graphics.drawable.Drawable;

public class ListViewItem {
    private Drawable iconDrawable ;
    private String title ;
    private String Address ;
    private int price ;
    private boolean card ;
    private int totalSeat ;
    private int spaceSeat ;

    public void setIcon(Drawable iconDrawable) {
        this.iconDrawable = iconDrawable ;
    }
    public void setTitle(String title) {
        this.title = title ;
    }
    public void setAddress(String Address) {
        this.Address = Address ;
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

    public Drawable getIcon() {
        return this.iconDrawable ;
    }
    public String getTitle() {
        return this.title ;
    }
    public String getAddress() {
        return this.Address ;
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
}
