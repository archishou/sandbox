package BezierCurveGenerator.Visualizer;

import BezierCurveGenerator.Point;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class PaintFrame extends JFrame {
    private List<Point> points = new ArrayList<>();
    private final int FRAME_SIZE = 1000;
    private final int POINT_RADIUS = 10;
    PaintFrame() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Plotter");
        setSize(FRAME_SIZE,FRAME_SIZE);
        setVisible(true);
        setBackground(Color.black);

    }

    public void paint(Graphics g) {
        int index = 0;
        while (index < points.size()) {
            Point p = points.get(index);
            g.setColor(p.getColor());
            ///g.fillOval(p.getX(), p.getY(), POINT_RADIUS, POINT_RADIUS);
        }
    }

    public void appendCoordinate(Point point) {
        point.setX(point.getX() + (FRAME_SIZE / 2));
        point.setY(point.getY() + (FRAME_SIZE / 2));
        points.add(point);
    }
}