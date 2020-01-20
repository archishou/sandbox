public class gotoXYTankTest {
    public static void main(String[] args) {
        MasqVector current = new MasqVector(5, 5);
        MasqVector initial = new MasqVector(0, 0);
        MasqVector target = new MasqVector(20, 20);
        double heading = 45;
        heading = Math.toRadians(heading);
        double[] power = calculate(current, initial, target, heading);
        System.out.println("Left Power: " + power[0]);
        System.out.println("Right Power: " + power[1]);
    }
    static double[] calculate(MasqVector current, MasqVector initial, MasqVector target, double heading) {
        System.out.println("----------------Starting Calculation----------------");
        MasqPIDController xySpeedController = new MasqPIDController(0.04, 0, 0);
        MasqPIDController travelAngleController = new MasqPIDController(0.01, 0, 0);
        double lookAhead = 10;
        MasqVector pathDisplacement = initial.displacement(target);
        MasqVector headingUnitVector = new MasqVector(Math.sin(heading), Math.cos(heading));
        MasqVector untransformedProjection = new MasqVector(
                current.projectOnTo(pathDisplacement).getX() - initial.getX(),
                current.projectOnTo(pathDisplacement).getY() - initial.getY()).projectOnTo(pathDisplacement);
        MasqVector projection = new MasqVector(
                untransformedProjection.getX() + initial.getX(),
                untransformedProjection.getY() + initial.getY());
        double theta = Math.atan2(pathDisplacement.getY(), pathDisplacement.getX());
        MasqVector lookahead = new MasqVector(
                projection.getX() + (lookAhead * Math.cos(theta)),
                projection.getY() + (lookAhead * Math.sin(theta)));
        if (initial.displacement(lookahead).getMagnitude() > pathDisplacement.getMagnitude()) lookahead = new MasqVector(target.getX(), target.getY());
        MasqVector lookaheadDisplacement = current.displacement(lookahead);
        double pathAngle = headingUnitVector.angleDeg(lookaheadDisplacement);
        System.out.println("Heading Unit Vector: " + headingUnitVector.toString());
        System.out.println("Lookahead Vector: " + lookahead.toString());
        System.out.println("Lookahead Displacement Vector: " + lookaheadDisplacement.toString());
        System.out.println("Path Angle: " + pathAngle);
        double speed = xySpeedController.getOutput(current.displacement(target).getMagnitude());
        double powerAdjustment = travelAngleController.getOutput(pathAngle);
        System.out.println("Power Adjustment: " + powerAdjustment);
        double leftPower = speed + powerAdjustment;
        double rightPower = speed - powerAdjustment;
        System.out.println("----------------Completed Calculation---------------");
        return new double[]{
                leftPower, rightPower
        };
    }
}
