package com.thx.xuitest;

public class LeftBean {
    private int rightPosition;
    private String title;
    private boolean isSelect;
    public LeftBean(int rightPosition, String title) {
        this.rightPosition = rightPosition;
        this.title = title;
    }
    public boolean isSelect() {
        return isSelect;
    }
    public void setSelect(boolean select) {
        isSelect = select;
    }
    public int getRightPosition() {
        return rightPosition;
    }
    public void setRightPosition(int rightPosition) {
        this.rightPosition = rightPosition;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
}
