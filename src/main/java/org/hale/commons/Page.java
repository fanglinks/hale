package org.hale.commons;

/**
 * Created by guilherme on 7/20/17.
 * hale
 */
public class Page {

    int number;
    int size;

    public Page(){}

    public Page(int number, int size) {
        this.number = number;
        this.size = size;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
