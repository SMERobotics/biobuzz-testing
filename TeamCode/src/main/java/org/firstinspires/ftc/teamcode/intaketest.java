package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name="Intake Test")
public class intaketest extends LinearOpMode {

    private DcMotor intake1;
    private DcMotor intake2;

    final double INTAKE_SPEED = -.5;
    final double REV_INTAKE_SPEED = .25;

    @Override
    public void runOpMode() {
        intake1 = hardwareMap.dcMotor.get("intake1");
        intake2 = hardwareMap.dcMotor.get("intake2");

        waitForStart();

        while (opModeIsActive()) {
            if (gamepad1.left_bumper) {
                intake1.setPower(REV_INTAKE_SPEED);
                intake2.setPower(REV_INTAKE_SPEED);
            } else if (gamepad1.right_bumper) {
                intake1.setPower(INTAKE_SPEED);
                intake2.setPower(INTAKE_SPEED);
            } else {
                intake1.setPower(0);
                intake2.setPower(0);
            }
        }

    }
}
