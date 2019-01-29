package com.lwang.library;

/**
 * @author lwang
 * @date 2019/01/29
 * @description 转盘item中的内容
 */
public class ItemDataBean {

    private String name;
    private int image;

    public ItemDataBean() {
    }

    public ItemDataBean(String name, int image) {
        this.name = name;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

}
