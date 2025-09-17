package org.firstinspires.ftc.teamcode.control;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.datatypes.InputState;

public class InputDevice {



    volatile Gamepad device;

    public InputState previousState;
    public InputDevice(Gamepad device) {
        this.device = device;
        previousState = new InputState(device);
    }

    public void updatePreviousState() {
        this.previousState.updateState(device);
    }








}
