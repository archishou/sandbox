package BezierCurveGenerator.Visualizer;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class PaintFrame extends JFrame {
    private List<Integer> xCoordinates = new ArrayList<>();
    private List<Integer> yCoordinates = new ArrayList<>();
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
        g.setColor(Color.red);
        int index = 0;
        while (index < xCoordinates.size()) {
            g.fillOval(xCoordinates.get(index), yCoordinates.get(index), POINT_RADIUS, POINT_RADIUS);
        }
    }

    public void appendCoordinate(int x, int y) {
        xCoordinates.add(x + (FRAME_SIZE / 2));
        yCoordinates.add(y + (FRAME_SIZE / 2));
    }
}