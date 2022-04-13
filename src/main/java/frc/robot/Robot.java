// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.cameraserver.CameraServer;

import edu.wpi.first.wpilibj.Joystick;

import edu.wpi.first.wpilibj.SPI;

import edu.wpi.first.wpilibj.TimedRobot;

import edu.wpi.first.wpilibj.Timer;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;

import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;

import edu.wpi.first.wpilibj2.command.CommandScheduler;

import edu.wpi.first.wpilibj2.command.button.JoystickButton;

public class Robot extends TimedRobot {

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

    slowFlywheelButton.whileActiveOnce(new FlywheelCommand(-0.25));

    fastFlywheelButton.whileActiveOnce(new FlywheelCommand(-0.6));

    reverseFlywheelButton.whileActiveOnce(new FlywheelCommand(1));

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

    if (time - startTime < 1.5 && time - startTime > 0) {

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

    if (time - startTime < 4.2 && time - startTime > 1.6) {

      /*double currentm_motorRightEncoderPosition = m_motorTwo.getSelectedSensorPosition();

      double currentm_motorLeftEncoderPosition = m_motorZero.getSelectedSensorPosition();

      //double encoderTicksToSpinRight = m_motorRightEncoderPositionBeforeSpin + 2288;

      if (Gyro.getAngle()) {

        m_robotDrive.arcadeDrive(0, -1);

      }

      else {

        m_robotDrive.arcadeDrive(0, 0);

      }*/
      
      m_robotDrive.arcadeDrive(0.1, 0.5);

      IntakeSubsystem.setSpeed(-1);

    }

    if (time - startTime < 4.3 && time - startTime > 4.2) {

      m_motorTwo.setSelectedSensorPosition(0);

      m_motorZero.setSelectedSensorPosition(0);

      System.out.println(m_motorTwo.getSelectedSensorPosition());

      System.out.println(m_motorZero.getSelectedSensorPosition());

      IntakeSubsystem.setSpeed(0);

    }

    if (time - startTime < 7.8 && time - startTime > 4.3) {

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

    if (time - startTime < 9.8 && time - startTime > 7.8) {

      SecondaryIntakeSubsystem.setSpeed(-1);

      FlywheelSubsystem.setSpeed(-0.6);

    }

    if (time - startTime < 9.9 && time - startTime > 9.8) {

      m_motorTwo.setSelectedSensorPosition(0);

      m_motorZero.setSelectedSensorPosition(0);

      System.out.println(m_motorTwo.getSelectedSensorPosition());

      System.out.println(m_motorZero.getSelectedSensorPosition());

      IntakeSubsystem.setSpeed(0);

      SecondaryIntakeSubsystem.setSpeed(0);

      FlywheelSubsystem.setSpeed(0);

    }


    if (time - startTime < 13.7 && time - startTime > 9.9) {

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

    if (time - startTime < 15 && time - startTime > 13.7) {

      IntakeSubsystem.setSpeed(0);

      SecondaryIntakeSubsystem.setSpeed(0);

      FlywheelSubsystem.setSpeed(0);

      m_robotDrive.arcadeDrive(0, 0);

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