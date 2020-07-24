package com.steveblythe;

public class DropElement {
    private int x;
    private int y;
    private DropElementType dropElementType;

    // Constructors
    public DropElement(int x, int y, DropElementType dropElementType) {
        this.x = x;
        this.y = y;
        this.dropElementType = dropElementType;
    }

    public DropElement(int x, int y) {
        this.x = x;
        this.y = y;
        this.dropElementType = null;
    }

    // Getters
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    // Setters
    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setDropElementType(DropElementType dropElementType) {
        this.dropElementType = dropElementType;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (!(o instanceof DropElement)) {
            return false;
        }

        DropElement dropElement = (DropElement) o;

        return x == dropElement.x && y == dropElement.y;
    }
}
