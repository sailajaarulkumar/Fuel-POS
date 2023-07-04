package com.sunmi.printerhelper.bean;

/**
 * Created by Administrator on 2017/5/24.
 */

public class TableItem {
    private String[] text;
    private int[] width;
    private int[] align;

    public TableItem(String l1,String l2,String l3) {
        text = new String[]{l1,l2,l3};
        width = new int[]{1,1,1};
        align = new int[]{0,0,0};
    }

    public String[] getText() {
        return text;
    }

    public void setText(String[] text) {
        this.text = text;
    }

    public int[] getWidth() {
        return width;
    }

    public void setWidth(int[] width) {
        this.width = width;
    }

    public int[] getAlign() {
        return align;
    }

    public void setAlign(int[] align) {
        this.align = align;
    }
}
