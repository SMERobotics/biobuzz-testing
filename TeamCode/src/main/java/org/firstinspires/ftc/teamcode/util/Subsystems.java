package org.firstinspires.ftc.teamcode.util;

import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.seattlesolvers.solverslib.command.Command;
import com.seattlesolvers.solverslib.command.InstantCommand;
import com.seattlesolvers.solverslib.command.SubsystemBase;
import com.seattlesolvers.solverslib.drivebase.MecanumDrive;
import com.seattlesolvers.solverslib.hardware.motors.Motor;
import com.seattlesolvers.solverslib.util.TelemetryData;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

/** Hardware subsystems shared by the robot's OpModes. */
public final class Subsystems {
    private Subsystems() {
        // Utility class.
    }

    public static final class Intake extends SubsystemBase {

        private final DcMotor intake1;
        private final DcMotor intake2;


        private static final double INTAKE_POWER = 1.0;
        private static final double REVERSE_INTAKE_POWER = -0.5;

        private boolean reversed;

        public Intake(HardwareMap hardwareMap) {
            intake1 = hardwareMap.get(DcMotor.class, "intake1");
            intake2 = hardwareMap.get(DcMotor.class, "intake2");
        }

        public void setReversed(boolean reversed) {
            this.reversed = reversed;
        }

        public boolean isReversed() {
            return reversed;
        }

        public void stop() {
            intake1.setPower(0.0);
            intake2.setPower(0.0);
        }

        @Override
        public void periodic() {
            intake1.setPower(reversed ? REVERSE_INTAKE_POWER : INTAKE_POWER);
            intake2.setPower(reversed ? REVERSE_INTAKE_POWER : INTAKE_POWER * -1);
        }
    }

    public static final class Drive extends SubsystemBase {
        private final MecanumDrive mecanum;

        private GoBildaPinpointDriver pinpoint;
        public double heading;

        public Drive(HardwareMap hardwareMap) {
            Motor frontLeft = new Motor(hardwareMap, "frontLeft");
            Motor frontRight = new Motor(hardwareMap, "frontRight");
            Motor backLeft = new Motor(hardwareMap, "backLeft");
            Motor backRight = new Motor(hardwareMap, "backRight");

            pinpoint = hardwareMap.get(GoBildaPinpointDriver.class, "pinpoint");

            mecanum = new MecanumDrive(frontLeft, frontRight, backLeft, backRight);
        }

        @Override
        public void periodic() {
            super.periodic();

            pinpoint.update();

            heading = pinpoint.getHeading(AngleUnit.DEGREES);
        }

        public void driveFieldCentric(double strafe, double forward, double turn) {
            mecanum.driveFieldCentric(strafe, forward, turn, heading);
        }

        public Command recalibratePinpoint = new InstantCommand(() -> pinpoint.recalibrateIMU());

        public void stop() {
            mecanum.stop();
        }
    }
}
