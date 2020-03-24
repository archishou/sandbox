package BezierCurveGenerator;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.Graphics.*;
import java.util.ArrayList;
import java.util.List;


public class Plotter {
    public static void main(String args[]) {
        PaintFrame p = new PaintFrame();
        p.appendCoordinate(0, 0);
        p.setVisible(true);
    }
}
