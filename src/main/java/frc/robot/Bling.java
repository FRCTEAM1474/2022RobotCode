// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.AddressableLED;

import edu.wpi.first.wpilibj.AddressableLEDBuffer;

import edu.wpi.first.wpilibj.Joystick;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Bling extends SubsystemBase {

  private AddressableLED m_led;

  private AddressableLEDBuffer m_ledBuffer;

  public int m_redflux_FirstPixelHue;

  public int m_blueflux_FirstPixelHue;

  public static int color = 1;
  
  private Joystick driverController = new Joystick(2);
  
  public Bling() {

    m_led = new AddressableLED(5);

    m_ledBuffer = new AddressableLEDBuffer(150);

    m_led.setLength(m_ledBuffer.getLength());

    m_led.setData(m_ledBuffer);

    m_led.start();
    
    driverController = new Joystick(1);

  }

  @Override

  public void periodic() {

    if (driverController.getRawButtonPressed(9)) {

        color = 0;

    } else if (driverController.getRawButtonPressed(10)) {

        color = 1;

    } else {

      setColorRGBAll(0, 0, 0);

    }

    if (color == 1) {

        blueflux();

    }

    if (color == 0) {

        redflux();

    }

    m_led.setData(m_ledBuffer);

  }

  public void setColorRGBAll(int r, int g, int b) {

    for (var i = 0; i < (m_ledBuffer.getLength()); i++) {

      m_ledBuffer.setRGB(i, r, g, b);

    }
  }

    public void blueflux(){

    for (var i = 0; i < m_ledBuffer.getLength(); i++) {

        final var blue = (m_blueflux_FirstPixelHue + (i * 180 / m_ledBuffer.getLength())) % 180;

        m_ledBuffer.setRGB(i, 0, 0, blue);

    }

    m_blueflux_FirstPixelHue++;

    m_blueflux_FirstPixelHue %= 180;

    }

    public void redflux(){

    for (var i = 0; i < m_ledBuffer.getLength(); i++) {

        final var red = (m_redflux_FirstPixelHue + (i * 180 / m_ledBuffer.getLength())) % 180;

        m_ledBuffer.setRGB(i, red, 0, 0);

    }

    m_redflux_FirstPixelHue++;

    m_redflux_FirstPixelHue %= 180;

  }
}
