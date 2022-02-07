// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Bling extends SubsystemBase {
  // Create variables for the LED strip and LED buffer
  private AddressableLED m_led;
  private AddressableLEDBuffer m_ledBuffer;

  
  private Joystick driverController;
  
  // In the constructor I initialized the LED strip as well as the LED buffer
  // and a joystick
  
  public Bling() {
    m_led = new AddressableLED(5);
    m_ledBuffer = new AddressableLEDBuffer(60);
    m_led.setLength(m_ledBuffer.getLength());
    m_led.setData(m_ledBuffer);
    m_led.start();

    
    driverController = new Joystick(0);

  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run

    // If one of the buttons is pressed - you can change this to any input you want
    if (driverController.getRawButton(9)) {
      // Run setColorRGBAll method (declared below) to set the color to red
      setColorRGBAll(255, 0, 0);

    // If the B button is pressed
    } else if (driverController.getRawButton(10)) {
      // Run setColorRGBAll method to set the color to blue
      setColorRGBAll(0, 0, 255);

    // If neither button is pressed
    } else {
      // Run setColorRGBAll method to set the color to black (off)
      //setColorRGBAll(0, 0, 0);

    }

    // Set the LEDs to the color held in the LED buffer variable
    m_led.setData(m_ledBuffer);
  }

  // setColorRGBAll sets the LEDs all to one color
  // Requires parameters for the RGB color values
  public void setColorRGBAll(int r, int g, int b) {
    // For each LED
    for (var i = 0; i < (m_ledBuffer.getLength()); i++) {
      // Set the LED buffer of the LED to the predetermined color
      m_ledBuffer.setRGB(i, r, g, b);
    }
  }
}
