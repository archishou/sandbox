package Sandbox.HermiteCurveSandbox.SandboxToys;

import java.awt.*;

public class Point {
    private double x, y;
    private Color color;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
        this.color = Color.BLACK;
    }

    public Point(int x, int y, Color color) {
        this.x = x;
        this.y = y;
        this.color = color;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
