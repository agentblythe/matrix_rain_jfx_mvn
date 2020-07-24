package com.steveblythe;

import java.util.ArrayList;
import java.util.List;

public class Drop {
    private List<DropElement> dropElements;

    // Constructors
    public Drop() {
        this.dropElements = new ArrayList<>();
    }

    // Getters
    public DropElement getHead() {
        if (dropElements.size() > 0)
            return dropElements.get(0);
        return null;
    }

    public List<DropElement> getTail() {
        if (dropElements.size() > 1)
            return dropElements.subList(1, dropElements.size());
        return null;
    }

    public DropElement getLastElement() {
        int dropSize = dropElements.size();
        if (dropSize > 0) {
            return dropElements.get(dropSize - 1);
        }
        return null;
    }

    // Setters
    public void reset() {
        dropElements.clear();
    }

    // General Functions
    public void incrementDrop() {
        for (DropElement dropElement : dropElements) {
            int currentY = dropElement.getY();
            dropElement.setY(++currentY);
        }
    }

    public void addHead(DropElement dropElement) {
        dropElement.setDropElementType(DropElementType.HEAD);
        if (dropElements.size() == 0) {
            dropElements.add(dropElement);
        }
    }

    public void initialiseTail(int tailLength) {
        for (int i = 1; i <= tailLength; i++) {
            if (i == 1) {
                dropElements.add(new DropElement(getHead().getX(), getHead().getY() - i, DropElementType.FIRST_TAIL));
            } else {
                dropElements.add(new DropElement(getHead().getX(), getHead().getY() - i, DropElementType.NORMAL));
            }
        }
    }



//    public void setHeadType(DropElement headType) {
//        this.headType = headType;
//    }
//
//    public DropElement getHeadType() {
//        return headType;
//    }
//
//    public List<Point> getPoints() {
//        return points;
//    }
//
//    public Point getHead() {
//        if (points.size() > 0) {
//            return points.get(0);
//        }
//        return null;
//    }
//
//    public void addHead(Point p) {
//        if (points != null) {
//            if (points.size() == 0) {
//                points.add(p);
//            }
//        }
//    }
//
//    public Point getFirstTail() {
//        if (points.size() > 1) {
//            return points.get(1);
//        }
//        return null;
//    }
//
//    public void incrementDrop() {
//        for (Point p : points) {
//            int currentY = p.getY();
//            p.setY(++currentY);
//        }
//    }
//
//    public void initialiseTail(int length) {
//        if (this.points.size() == 1) {
//            for (int i = 1; i <= length; i++) {
//                if (i == 1) {
//                    this.points.add(new Point(getHead().getX(), getHead().getY() - i, DropElement.FirstTail));
//                } else {
//                    this.points.add(new Point(getHead().getX(), getHead().getY() - i, DropElement.Normal));
//                }
//            }
//        }
//    }
//
//    public boolean isFading() {
//        return this.isFading;
//    }
//
//    public void setFading(boolean isFading) {
//        this.isFading = isFading;
//    }
}
