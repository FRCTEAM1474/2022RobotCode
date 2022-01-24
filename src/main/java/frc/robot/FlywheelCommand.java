package frc.robot;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class FlywheelCommand extends CommandBase {
    double m_Speed;
    public FlywheelCommand(double speed){
        m_Speed = speed;
    }
    @Override
    public void initialize() {
        //System.out.println("im initializing");
        Robot.flywheel.setSpeed(m_Speed);
    }

    @Override
    public void execute() {
        //System.out.println("im executing");
        Robot.flywheel.setSpeed(m_Speed);
    }

    @Override
    public void end(boolean interup){
        //System.out.println("im ending");
        Robot.flywheel.setSpeed(0);
    }
}