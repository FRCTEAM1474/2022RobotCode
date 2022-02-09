package frc.robot;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class FlywheelCommand extends CommandBase {

    double m_Speed;

    public FlywheelCommand(double speed){

        m_Speed = speed;
        
    }
    
    @Override

    public void initialize() {

        Robot.flywheel.setSpeed(m_Speed);

    }

    @Override

    public void execute() {

        Robot.flywheel.setSpeed(m_Speed);

    }

    @Override

    public void end(boolean interup){

        Robot.flywheel.setSpeed(0);

    }
}