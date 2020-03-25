package Sandbox.HermiteCurveSandbox;

import BezierCurveGenerator.Point;
import BezierCurveGenerator.Segment;
import Jama.Matrix;

import java.util.ArrayList;
import java.util.List;

public class NPointGenerator {
    private static List<Point> knots = new ArrayList<>();

    public static void main(String[] args) {
        knots.add(new Point(0, 0));
        knots.add(new Point(20, 50));
        knots.add(new Point(50, 50));
        knots.add(new Point(80, 50));
        knots.add(new Point(30, 30));

        List<Point> points = getPoints(knots);
        for (Point p : points) {
            System.out.printf("(%f, %f), ", p.getX(), p.getY());
        }
    }

    private static Matrix generateConstraintMatrix(List<Double> ns) {
        // Each point apart from the initial point, requires 4 constraints. N represents the number of constraints.
        int numPoints = ns.size();
        int n = (numPoints - 1) * 4;
        double matrix[][] = new double[n][n];
        if (numPoints == 1) return null;
        // Initial velocity = 0
        matrix[0][1] = 1;
        // Final velocity = 0
        matrix[1][n-1] = 3;
        matrix[1][n-2] = 2;
        matrix[1][n-3] = 1;

        // We filled equations 0 & 1 with initial and final velocity constraint.
        int equationIndex = 2;
        int pointIndex = 1;
        while (equationIndex < n) {
            // Start of segment is equal to initial point.
            int segmentIndexStart = ((pointIndex - 1) * 4);
            matrix[equationIndex][segmentIndexStart] = 1;
            equationIndex++;
            // End of segment is equal to destination.
            int segmentCoeffIndex = 0;
            while (segmentCoeffIndex < 4) {
                matrix[equationIndex][segmentCoeffIndex + segmentIndexStart] = 1;
                segmentCoeffIndex++;
            }
            equationIndex++;
            // Smooth velocities and accelerations if there is another point left to complete
            if (pointIndex < numPoints - 1) {
                // Velocity Constraint
                matrix[equationIndex][segmentIndexStart + 1] = 1;
                matrix[equationIndex][segmentIndexStart + 2] = 2;
                matrix[equationIndex][segmentIndexStart + 3] = 3;
                matrix[equationIndex][segmentIndexStart + 5] = -1;
                equationIndex++;

                matrix[equationIndex][segmentIndexStart + 2] = 2;
                matrix[equationIndex][segmentIndexStart + 3] = 6;
                matrix[equationIndex][segmentIndexStart + 6] = -2;
                equationIndex++;
            }

            pointIndex++;
        }
        return new Matrix(matrix);
    }

    private static Matrix generateSolutionsMatrix(List<Double> ns) {
        int numPoints = ns.size();
        int n = (numPoints - 1) * 4;
        double[][] matrix = new double[n][1];
        int pointIndex = 0, solutionIndex = 0;
        while (solutionIndex < n) {
            matrix[solutionIndex][0] = 0;
            matrix[solutionIndex + 1][0] = 0;
            matrix[solutionIndex + 2][0] = ns.get(pointIndex);
            matrix[solutionIndex + 3][0] = ns.get(pointIndex + 1);
            solutionIndex += 4;
            pointIndex++;
        }
        return new Matrix(matrix);
    }

    private static List<Segment> getSegments(List<Double> ns) {
        List<Segment> segments = new ArrayList<>();
        Matrix constraints = generateConstraintMatrix(ns);
        Matrix solutions = generateSolutionsMatrix(ns);
        Matrix coeffs = constraints.solve(solutions);
        int n = coeffs.getArray().length;
        int index = 0;
        while (index < n) {
            segments.add(new Segment(coeffs.get(index + 3, 0), coeffs.get(index + 2, 0), coeffs.get(index + 1, 0), coeffs.get(index, 0)));
            index += 4;
        }
        return segments;
    }

    private static List<Point> getPoints(List<Point> knots) {
        List<Double> xs = new ArrayList<>();
        List<Double> ys = new ArrayList<>();
        for (Point p : knots) {
            xs.add(p.getX());
            ys.add(p.getY());
        }

        List<Segment> xSegments = getSegments(xs);
        List<Segment> ySegments = getSegments(ys);
        List<Point> points = new ArrayList<>();

        double t = 0;
        double resolution = 0.1;
        // segments = knots - 1
        int n = knots.size() - 1;
        int index = 0;
        while (index < n) {
            while (t < 1) {
                points.add(new Point(xSegments.get(index).compute(t), ySegments.get(index).compute(t)));
                t += resolution;
            }
            t = 0;
            index++;
        }
        return points;
    }

    private static void printMatrix(double[][] matrix) {
        for (double[] aMatrix : matrix) {
            for (double anAMatrix : aMatrix)
                System.out.print(anAMatrix + " ");
            System.out.println();
        }
    }

}
