// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.cameraserver.CameraServer;

import edu.wpi.first.wpilibj.DigitalInput;

import edu.wpi.first.wpilibj.Joystick;

import edu.wpi.first.wpilibj.SPI;

import edu.wpi.first.wpilibj.TimedRobot;

import edu.wpi.first.wpilibj.Timer;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;

import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;

import edu.wpi.first.wpilibj2.command.CommandScheduler;

import edu.wpi.first.wpilibj2.command.button.JoystickButton;

public class Robot extends TimedRobot {

  DigitalInput newOrOldAutonomous = new DigitalInput(1);

  DigitalInput revisedTwoBallOrRegularTwoBallAutonomousOrInstantOrDelayedOneBallAutonomous = new DigitalInput(2);

  public static WPI_TalonSRX m_motorZero = new WPI_TalonSRX(2);

  public static WPI_TalonSRX m_motorOne = new WPI_TalonSRX(4);

  public static WPI_TalonSRX m_motorTwo = new WPI_TalonSRX(1);

  public static WPI_TalonSRX m_motorThree = new WPI_TalonSRX(7);

  static MotorControllerGroup m_Right = new MotorControllerGroup(m_motorZero, m_motorOne);

  static MotorControllerGroup m_Left = new MotorControllerGroup(m_motorTwo, m_motorThree);

  public static DifferentialDrive m_robotDrive = new DifferentialDrive(m_Left, m_Right);

  private final Joystick m_stick = new Joystick(0);

  private final Joystick m_stickTwo = new Joystick(1);

  public static IntakeSubsystem intake;

  public static SecondaryIntakeSubsystem secondaryintake;

  public static FlywheelSubsystem flywheel;

  public static Bling bling;

  public static ClimberSubsystem climber;

  public static double previousEncoderDelta = 0;

  double flywheelSpeedLimit = 1;

  private double startTime;

  //ADXRS450_Gyro Gyro = new ADXRS450_Gyro();

  AHRS gyro = new AHRS(SPI.Port.kMXP);
  
  @Override

  public void robotInit() {

    CameraServer.startAutomaticCapture();

    m_Left.setInverted(true);

    bling = new Bling();

    intake = new IntakeSubsystem();

    secondaryintake = new SecondaryIntakeSubsystem();

    flywheel = new FlywheelSubsystem();

    climber = new ClimberSubsystem();

    //Good Code- JW

    JoystickButton intakeInButton = new JoystickButton(m_stick, 5);

    JoystickButton outtakeButton = new JoystickButton(m_stick, 3);

    JoystickButton secondaryIntakeButton = new JoystickButton(m_stickTwo, 6);

    JoystickButton secondaryOuttakeButton = new JoystickButton(m_stickTwo, 4);

    JoystickButton slowFlywheelButton = new JoystickButton(m_stickTwo, 1);

    JoystickButton fastFlywheelButton = new JoystickButton(m_stickTwo, 2);

    JoystickButton reverseFlywheelButton = new JoystickButton(m_stickTwo, 8);

    JoystickButton climberUp = new JoystickButton(m_stick, 11);

    JoystickButton climberDown = new JoystickButton(m_stick, 12);

    JoystickButton climberStay = new JoystickButton(m_stick, 7);

    JoystickButton move5feet = new JoystickButton(m_stickTwo, 12);

    move5feet.whileActiveOnce(new DriveDistanceCommand(60));

    intakeInButton.whileActiveOnce(new IntakeCommand(-1));

    outtakeButton.whileActiveOnce(new IntakeCommand(1));

    secondaryIntakeButton.whileActiveOnce(new SecondaryIntakeCommand(-1));

    secondaryOuttakeButton.whileActiveOnce(new SecondaryIntakeCommand(1));

    slowFlywheelButton.whileActiveOnce(new FlywheelCommand(-0.6));

    fastFlywheelButton.whileActiveOnce(new FlywheelCommand(-0.25));

    reverseFlywheelButton.whileActiveOnce(new FlywheelCommand(0.5));

    climberUp.whileActiveOnce(new ClimberCommand(1));

    climberDown.whileActiveOnce(new ClimberCommand(-1));

    climberDown.whenInactive(new ClimberCommand(0));

    climberUp.whenInactive(new ClimberCommand(0));

    climberStay.whileActiveOnce(new ClimberCommand(0));

  }

  @Override

  public void autonomousInit() {

    startTime = Timer.getFPGATimestamp();

    m_motorTwo.setSelectedSensorPosition(0);

    m_motorZero.setSelectedSensorPosition(0);

  }

  @Override

  public void autonomousPeriodic() {

    double time = Timer.getFPGATimestamp();

    if (newOrOldAutonomous.get()) {

      if (revisedTwoBallOrRegularTwoBallAutonomousOrInstantOrDelayedOneBallAutonomous.get()) {

        if (time - startTime < 2.5 && time - startTime > 0) {

          double currentm_motorRightEncoderPosition = m_motorTwo.getSelectedSensorPosition();

          double currentm_motorLeftEncoderPosition = m_motorZero.getSelectedSensorPosition();

          System.out.println("MotorRightOutput " + currentm_motorRightEncoderPosition);

          System.out.println("MotorLeftOutput " + currentm_motorLeftEncoderPosition);

          double currentEncoderDelta = currentm_motorRightEncoderPosition - (-currentm_motorLeftEncoderPosition);

          double autonomousDrivetrainRotation = (((currentEncoderDelta) / 4000));

          //System.out.println("autonomousDrivetrainRotation " + autonomousDrivetrainRotation);

          m_robotDrive.arcadeDrive(0.70, -autonomousDrivetrainRotation, true); //change value to 0.65 later

          previousEncoderDelta = currentEncoderDelta;

          IntakeSubsystem.setSpeed(-1);

        }

        if (time -startTime < 2.6 && time - startTime > 2.5) {

          m_motorTwo.setSelectedSensorPosition(0);

          m_motorZero.setSelectedSensorPosition(0);

          IntakeSubsystem.setSpeed(-1);

          //double gyroCalibration = Gyro.getAngle();

        }

        if (time - startTime < 4.08 && time - startTime > 2.6) {

          m_robotDrive.arcadeDrive(-0.65, 0);

        }

        if (time - startTime < 5.1 && time - startTime > 4.08) {

          //slayyyyyyyyy
          //yassssss

          m_robotDrive.arcadeDrive(0, 0);

          m_motorTwo.setSelectedSensorPosition(0);

          m_motorZero.setSelectedSensorPosition(0);

        }

        if (time - startTime < 7.5 && time - startTime > 5.1) { //should be 4.2

          double currentm_motorRightEncoderPosition = m_motorTwo.getSelectedSensorPosition();

          double currentm_motorLeftEncoderPosition = m_motorZero.getSelectedSensorPosition();
          
          System.out.println("always print 2");

          if (currentm_motorLeftEncoderPosition < 11000 && currentm_motorRightEncoderPosition < 11000) {

            m_robotDrive.arcadeDrive(0.05, 0.65);

            System.out.println("firstprint");

          }


          else if (currentm_motorLeftEncoderPosition < 11000) {

              m_robotDrive.tankDrive(0.65, 0);

              System.out.println("secondprint");

          }

          else if (currentm_motorRightEncoderPosition < 11000) {

              m_robotDrive.tankDrive(0, 0.65);         

              System.out.println("thirdprint");

          }

          else {

            System.out.println("STOPPING");

            m_robotDrive.arcadeDrive(0, 0);

          }

          System.out.println(currentm_motorLeftEncoderPosition);

          System.out.println(currentm_motorRightEncoderPosition);

        

          /*double currentm_motorRightEncoderPosition = m_motorTwo.getSelectedSensorPosition();

          double currentm_motorLeftEncoderPosition = m_motorZero.getSelectedSensorPosition();

          //double encoderTicksToSpinRight = m_motorRightEncoderPositionBeforeSpin + 2288;

          if (Gyro.getAngle()) {

            m_robotDrive.arcadeDrive(0, -1);

          }

          else {

            m_robotDrive.arcadeDrive(0, 0);

          }*/
          
          //nom-nom turny turns
          
          //m_motorTwo.setSelectedSensorPosition(0);

          //m_motorZero.setSelectedSensorPosition(0);

          /*double currentm_motorRightEncoderPosition = m_motorTwo.getSelectedSensorPosition();

          double currentm_motorLeftEncoderPosition = m_motorZero.getSelectedSensorPosition();
          
          if (currentm_motorLeftEncoderPosition < 100 && currentm_motorRightEncoderPosition < 100) {

            m_robotDrive.arcadeDrive(0.05, 0.65);

          }*/

          /*else if (currentm_motorLeftEncoderPosition < 2288) {

              m_robotDrive.tankDrive(0, 0.65);

          }

          else if (currentm_motorRightEncoderPosition < 2288) {

              m_robotDrive.tankDrive(0.65, 0);         

          } */

         // m_robotDrive.arcadeDrive(0.1, 0.7);
          
          IntakeSubsystem.setSpeed(-1);

        }

        if (time - startTime < 7.7 && time - startTime > 7.5) {

          m_motorTwo.setSelectedSensorPosition(0);

          m_motorZero.setSelectedSensorPosition(0);

          System.out.println(m_motorTwo.getSelectedSensorPosition());

          System.out.println(m_motorZero.getSelectedSensorPosition());

          IntakeSubsystem.setSpeed(0);

        }

        if (time - startTime < 9.7 && time - startTime > 7.7) {

          double currentm_motorRightEncoderPosition = m_motorTwo.getSelectedSensorPosition();

          double currentm_motorLeftEncoderPosition = m_motorZero.getSelectedSensorPosition();

          //System.out.println("MotorRightOutput " + currentm_motorRightEncoderPosition);

          //System.out.println("MotorLeftOutput " + currentm_motorLeftEncoderPosition);

          double currentEncoderDelta = currentm_motorRightEncoderPosition - (-currentm_motorLeftEncoderPosition);

          double autonomousDrivetrainRotation = (((currentEncoderDelta) / 4000));

          //System.out.println("autonomousDrivetrainRotation " + autonomousDrivetrainRotation);

          m_robotDrive.arcadeDrive(0.65, -autonomousDrivetrainRotation, true); //change value to 0.65 later

          previousEncoderDelta = currentEncoderDelta;

          IntakeSubsystem.setSpeed(0);

        }

        if (time - startTime < 11.7 && time - startTime > 9.7) {

          SecondaryIntakeSubsystem.setSpeed(-1);

          FlywheelSubsystem.setSpeed(-0.6);

        }

        if (time - startTime < 11.8 && time - startTime > 11.7) {

          m_motorTwo.setSelectedSensorPosition(0);

          m_motorZero.setSelectedSensorPosition(0);

          System.out.println(m_motorTwo.getSelectedSensorPosition());

          System.out.println(m_motorZero.getSelectedSensorPosition());

          IntakeSubsystem.setSpeed(0);

          SecondaryIntakeSubsystem.setSpeed(0);

          FlywheelSubsystem.setSpeed(0);

        }


        /*if (time - startTime < 14.2 && time - startTime > 10.4) {

          double currentm_motorRightEncoderPosition = m_motorTwo.getSelectedSensorPosition();

          double currentm_motorLeftEncoderPosition = m_motorZero.getSelectedSensorPosition();

          System.out.println("MotorRightOutput " + currentm_motorRightEncoderPosition);

          System.out.println("MotorLeftOutput " + currentm_motorLeftEncoderPosition);

          double currentEncoderDelta = currentm_motorRightEncoderPosition - (-currentm_motorLeftEncoderPosition);

          double autonomousDrivetrainRotation = (((currentEncoderDelta) / 4000));

          //System.out.println("autonomousDrivetrainRotation " + autonomousDrivetrainRotation);

          m_robotDrive.arcadeDrive(-0.65, -autonomousDrivetrainRotation, true); //change value to 0.65 later

          previousEncoderDelta = currentEncoderDelta;

        }*/

        if (time - startTime < 15 && time - startTime > 11.8) {

          IntakeSubsystem.setSpeed(0);

          SecondaryIntakeSubsystem.setSpeed(0);

          FlywheelSubsystem.setSpeed(0);

          m_robotDrive.arcadeDrive(0, 0);

        }

      }

      else {

        System.out.println("always print");

        if (time - startTime < 3 && time - startTime > 0) {

          m_motorZero.setSelectedSensorPosition(0);

          m_motorTwo.setSelectedSensorPosition(0);

        }

        if (time - startTime > 3) {

        double currentm_motorRightEncoderPosition = m_motorTwo.getSelectedSensorPosition();

          double currentm_motorLeftEncoderPosition = m_motorZero.getSelectedSensorPosition();
          
          System.out.println("always print 2");

          if (currentm_motorLeftEncoderPosition < 11000 && currentm_motorRightEncoderPosition < 11000) {

            m_robotDrive.arcadeDrive(0.05, 0.65);

            System.out.println("firstprint");

          }


          else if (currentm_motorLeftEncoderPosition < 11000) {

              m_robotDrive.tankDrive(0.65, 0);

              System.out.println("secondprint");

          }

          else if (currentm_motorRightEncoderPosition < 11000) {

              m_robotDrive.tankDrive(0, 0.65);         

              System.out.println("thirdprint");

          }

          else {

            System.out.println("STOPPING");

            m_robotDrive.arcadeDrive(0, 0);

          }

          System.out.println(currentm_motorLeftEncoderPosition);

          System.out.println(currentm_motorRightEncoderPosition);

        }  

        if (time - startTime < 15 && time - startTime > 3) { //should be 4.2

          /*double currentm_motorRightEncoderPosition = m_motorTwo.getSelectedSensorPosition();

          double currentm_motorLeftEncoderPosition = m_motorZero.getSelectedSensorPosition();

          //double encoderTicksToSpinRight = m_motorRightEncoderPositionBeforeSpin + 2288;

          if (Gyro.getAngle()) {

            m_robotDrive.arcadeDrive(0, -1);

          }

          else {

            m_robotDrive.arcadeDrive(0, 0);

          }*/
          
          //nom-nom turny turns
          
          //m_motorTwo.setSelectedSensorPosition(0);

          //m_motorZero.setSelectedSensorPosition(0);

          

         // m_robotDrive.arcadeDrive(0.1, 0.7);
          
          //IntakeSubsystem.setSpeed(-1);

        }

        /* if (time - startTime < 1.5 && time - startTime > 0) {

          double currentm_motorRightEncoderPosition = m_motorTwo.getSelectedSensorPosition();

          double currentm_motorLeftEncoderPosition = m_motorZero.getSelectedSensorPosition();

          System.out.println("MotorRightOutput " + currentm_motorRightEncoderPosition);

          System.out.println("MotorLeftOutput " + currentm_motorLeftEncoderPosition);

          double currentEncoderDelta = currentm_motorRightEncoderPosition - (-currentm_motorLeftEncoderPosition);

          double autonomousDrivetrainRotation = (((currentEncoderDelta) / 4000));

          //System.out.println("autonomousDrivetrainRotation " + autonomousDrivetrainRotation);

          m_robotDrive.arcadeDrive(0.70, -autonomousDrivetrainRotation, true); //change value to 0.65 later

          previousEncoderDelta = currentEncoderDelta;

          IntakeSubsystem.setSpeed(-1);

        }

        if (time -startTime < 1.6 && time - startTime > 1.5) {

          m_motorTwo.setSelectedSensorPosition(0);

          m_motorZero.setSelectedSensorPosition(0);

          IntakeSubsystem.setSpeed(-1);

          //double gyroCalibration = Gyro.getAngle();

        }

        if (time - startTime < 2.6 && time - startTime > 1.6) {

          double currentm_motorRightEncoderPosition = m_motorTwo.getSelectedSensorPosition();

          double currentm_motorLeftEncoderPosition = m_motorZero.getSelectedSensorPosition();

          System.out.println("MotorRightOutput " + currentm_motorRightEncoderPosition);

          System.out.println("MotorLeftOutput " + currentm_motorLeftEncoderPosition);

          double currentEncoderDelta = currentm_motorRightEncoderPosition - (-currentm_motorLeftEncoderPosition);

          double autonomousDrivetrainRotation = (((currentEncoderDelta) / 4000));

          //System.out.println("autonomousDrivetrainRotation " + autonomousDrivetrainRotation);

          //slaaaay sksksk -Liam

          m_robotDrive.arcadeDrive(-0.70, -autonomousDrivetrainRotation, true); //change value to 0.65 later

          previousEncoderDelta = currentEncoderDelta;

          IntakeSubsystem.setSpeed(-1);

        }

        if (time - startTime < 3.8 && time - startTime > 2.6) { //should be 4.2

          /*double currentm_motorRightEncoderPosition = m_motorTwo.getSelectedSensorPosition();

          double currentm_motorLeftEncoderPosition = m_motorZero.getSelectedSensorPosition();

          //double encoderTicksToSpinRight = m_motorRightEncoderPositionBeforeSpin + 2288;

          if (Gyro.getAngle()) {

            m_robotDrive.arcadeDrive(0, -1);

          }

          else {

            m_robotDrive.arcadeDrive(0, 0);

          }
          
          m_robotDrive.arcadeDrive(0.1, -0.7);
          
          IntakeSubsystem.setSpeed(-1);

        }

        if (time - startTime < 5.3 && time - startTime > 3.8) { //should be 4.2

          m_motorTwo.setSelectedSensorPosition(0);

          m_motorZero.setSelectedSensorPosition(0);

          System.out.println(m_motorTwo.getSelectedSensorPosition());

          System.out.println(m_motorZero.getSelectedSensorPosition());

          IntakeSubsystem.setSpeed(0);

        }

        if (time - startTime < 8.8 && time - startTime > 5.3) {

          double currentm_motorRightEncoderPosition = m_motorTwo.getSelectedSensorPosition();

          double currentm_motorLeftEncoderPosition = m_motorZero.getSelectedSensorPosition();

          //System.out.println("MotorRightOutput " + currentm_motorRightEncoderPosition);

          //System.out.println("MotorLeftOutput " + currentm_motorLeftEncoderPosition);

          double currentEncoderDelta = currentm_motorRightEncoderPosition - (-currentm_motorLeftEncoderPosition);

          double autonomousDrivetrainRotation = (((currentEncoderDelta) / 4000));

          //System.out.println("autonomousDrivetrainRotation " + autonomousDrivetrainRotation);

          m_robotDrive.arcadeDrive(0.65, -autonomousDrivetrainRotation, true); //change value to 0.65 later

          previousEncoderDelta = currentEncoderDelta;

          IntakeSubsystem.setSpeed(0);

        }

        if (time - startTime < 10.8 && time - startTime > 8.8) {

          SecondaryIntakeSubsystem.setSpeed(-1);

          FlywheelSubsystem.setSpeed(-0.6);

        }

        if (time - startTime < 10.9 && time - startTime > 10.8) {

          m_motorTwo.setSelectedSensorPosition(0);

          m_motorZero.setSelectedSensorPosition(0);

          System.out.println(m_motorTwo.getSelectedSensorPosition());

          System.out.println(m_motorZero.getSelectedSensorPosition());

          IntakeSubsystem.setSpeed(0);

          SecondaryIntakeSubsystem.setSpeed(0);

          FlywheelSubsystem.setSpeed(0);

        }


        if (time - startTime < 14.7 && time - startTime > 10.9) {

          double currentm_motorRightEncoderPosition = m_motorTwo.getSelectedSensorPosition();

          double currentm_motorLeftEncoderPosition = m_motorZero.getSelectedSensorPosition();

          System.out.println("MotorRightOutput " + currentm_motorRightEncoderPosition);

          System.out.println("MotorLeftOutput " + currentm_motorLeftEncoderPosition);

          double currentEncoderDelta = currentm_motorRightEncoderPosition - (-currentm_motorLeftEncoderPosition);

          double autonomousDrivetrainRotation = (((currentEncoderDelta) / 4000));

          //System.out.println("autonomousDrivetrainRotation " + autonomousDrivetrainRotation);

          m_robotDrive.arcadeDrive(-0.65, -autonomousDrivetrainRotation, true); //change value to 0.65 later

          previousEncoderDelta = currentEncoderDelta;

        }

        if (time - startTime < 15 && time - startTime > 14.7) {

          IntakeSubsystem.setSpeed(0);
f
          SecondaryIntakeSubsystem.setSpeed(0);

          FlywheelSubsystem.setSpeed(0);

          m_robotDrive.arcadeDrive(0, 0);

        }

        */
        System.out.println("-------\n\n");

      } 

    }

    else {

      if (revisedTwoBallOrRegularTwoBallAutonomousOrInstantOrDelayedOneBallAutonomous.get()) {

        if (time - startTime < 5 && time - startTime > 0) {

          SecondaryIntakeSubsystem.setSpeed(-1);

          FlywheelSubsystem.setSpeed(-0.65);

          m_robotDrive.arcadeDrive(0, 0);

        }

        if (time - startTime < 8.5 && time - startTime > 5) {

          SecondaryIntakeSubsystem.setSpeed(0);

          FlywheelSubsystem.setSpeed(0);

          m_robotDrive.arcadeDrive(-0.65, 0);

        }

        if (time - startTime < 15 && time - startTime > 8.5) {

          SecondaryIntakeSubsystem.setSpeed(0);

          FlywheelSubsystem.setSpeed(0);

          m_robotDrive.arcadeDrive(0, 0);

        }

      }

      else {

        if (time - startTime < 5 && time - startTime > 0) {

          SecondaryIntakeSubsystem.setSpeed(-1);

          FlywheelSubsystem.setSpeed(-0.65);

          m_robotDrive.arcadeDrive(0, 0);

        }

        if (time - startTime < 11.5 && time - startTime > 5) {

          SecondaryIntakeSubsystem.setSpeed(0);

          FlywheelSubsystem.setSpeed(0);

          m_robotDrive.arcadeDrive(0, 0);

        }

        if (time - startTime < 15 && time - startTime > 11.5) {

          m_robotDrive.arcadeDrive(-0.65, 0);

        }

      }

    }
    
  }

  @Override

  public void teleopPeriodic() {

    m_robotDrive.arcadeDrive(-m_stick.getY(), -m_stick.getX(), false);

    //System.out.println("MotorTwoOutput " + m_motorTwo.getSelectedSensorPosition());

    //System.out.println("MotorZeroOutput " + m_motorZero.getSelectedSensorPosition());

    if (m_stick.getRawButtonPressed(9)) {

      gyro.calibrate();

    }
    
  }

  @Override

  public void robotPeriodic() {

    CommandScheduler.getInstance().run();

    /*SmartDashboard.putNumber("encoderpositionright", Robot.m_motorTwo.getSelectedSensorPosition());

    SmartDashboard.putNumber("encoderpositionleft", Robot.m_motorZero.getSelectedSensorPosition());

    double leftMotorEncoderTicks = Robot.m_motorZero.getSelectedSensorPosition();

    double leftMotorEncoderRevolutions = leftMotorEncoderTicks / 4096;

    double leftMotorEncoderDistance = leftMotorEncoderRevolutions * 3.141592653589792 * 4;

    double rightMotorEncoderTicks = Robot.m_motorTwo.getSelectedSensorPosition();

    double rightMotorEncoderRevolutions = rightMotorEncoderTicks / 4096;

    double rightMotorEncoderDistance = rightMotorEncoderRevolutions * 3.141592653589792 * 4;

    SmartDashboard.putNumber("rightsidedistance", rightMotorEncoderDistance);

    SmartDashboard.putNumber("leftsidedistance", leftMotorEncoderDistance);*/
    
    //System.out.println("gyro output for pitch AKA x axis " + gyro.getPitch());

    //System.out.println("gyro output for roll AKA y axis " + gyro.getRoll());

    //System.out.println("gyro output for yaw AKA z axis " + gyro.getYaw());

  }

}