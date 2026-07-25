package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.seattlesolvers.solverslib.command.CommandOpMode;
import com.seattlesolvers.solverslib.command.button.GamepadButton;
import com.seattlesolvers.solverslib.gamepad.GamepadEx;
import com.seattlesolvers.solverslib.gamepad.GamepadKeys;
import com.seattlesolvers.solverslib.util.TelemetryData;

import org.firstinspires.ftc.teamcode.util.Subsystems;

@TeleOp(name = "Biobuzz TeleOp")
public class BioBuzzTele extends CommandOpMode {

    private Subsystems.Intake intake;
    private Subsystems.Drive drive;
    private GamepadEx driverOp;

    private final TelemetryData telemetryData = new TelemetryData(telemetry);

    @Override
    public void initialize() {
        super.reset();

        driverOp = new GamepadEx(gamepad1);
        intake = new Subsystems.Intake(hardwareMap);
        drive = new Subsystems.Drive(hardwareMap);

        new GamepadButton(driverOp, GamepadKeys.Button.LEFT_BUMPER)
                .whenPressed(() -> intake.setReversed(true))
                .whenReleased(() -> intake.setReversed(false));

        new GamepadButton(driverOp, GamepadKeys.Button.START)
                .whenPressed(drive.recalibratePinpoint);
    }

    @Override
    public void run() {
        drive.driveFieldCentric(
                driverOp.getLeftX(),
                driverOp.getLeftY(),
                driverOp.getRightX()
        );
        super.run();

        telemetryData.addData("Intake", intake.isReversed() ? "Reverse" : "Forward");
        telemetryData.addData("Heading", drive.heading);
        telemetryData.update();
    }

    @Override
    public void end() {
        drive.stop();
        intake.stop();
    }
}
