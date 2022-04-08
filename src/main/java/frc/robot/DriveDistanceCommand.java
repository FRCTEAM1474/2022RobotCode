package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class DriveDistanceCommand extends CommandBase {

    double distanceInInches;

    public DriveDistanceCommand(double distance){

        distanceInInches = distance;
        SmartDashboard.putString("CommandStatus", "Constructor");
    }
    
    @Override

    public void initialize() {

        System.out.println("init");
        Robot.m_motorTwo.setSelectedSensorPosition(0); //reset Encoder
        SmartDashboard.putString("CommandStatus", "Init");

    }

    @Override
    public void execute() {

        SmartDashboard.putString("CommandStatus", "Execute");

    SmartDashboard.putNumber("encoderpositionright", Robot.m_motorTwo.getSelectedSensorPosition());

    SmartDashboard.putNumber("encoderpositionleft", Robot.m_motorZero.getSelectedSensorPosition());

    System.out.println("MotorTwoOutput" + Robot.m_motorTwo.getSelectedSensorPosition());

    System.out.println("MotorZeroOutput" + Robot.m_motorZero.getSelectedSensorPosition());

    double leftMotorEncoderTicks = Robot.m_motorZero.getSelectedSensorPosition();

    double leftMotorEncoderRevolutions = leftMotorEncoderTicks / 4096;

    double leftMotorEncoderDistance = leftMotorEncoderRevolutions * 3.141592653589792 * 4;

    double rightMotorEncoderTicks = Robot.m_motorTwo.getSelectedSensorPosition();

    double rightMotorEncoderRevolutions = rightMotorEncoderTicks / 4096;

    double rightMotorEncoderDistance = rightMotorEncoderRevolutions * 3.141592653589792 * 4;

    SmartDashboard.putNumber("rightsidedistance", rightMotorEncoderDistance);

    SmartDashboard.putNumber("leftsidedistance", leftMotorEncoderDistance);

    Robot.m_robotDrive.arcadeDrive(0.75, 0);

    System.out.println("driving");

    }

    public double encodertickstoinches(double ticks) {

            return (ticks / 4096) * 3.1415 * 4;

    }

    @Override

    public void end(boolean interup){
        SmartDashboard.putString("CommandStatus", "End");

        Robot.m_robotDrive.arcadeDrive(0, 0);

    }

    @Override

    public boolean isFinished() {
        SmartDashboard.putString("CommandStatus", "Finished?");

        double leftMotorEncoderTicks = Robot.m_motorZero.getSelectedSensorPosition();
        
        return encodertickstoinches(leftMotorEncoderTicks) < distanceInInches;



    }

}