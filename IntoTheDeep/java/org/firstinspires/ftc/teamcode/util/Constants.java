package org.firstinspires.ftc.teamcode.util;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;

public class Constants {
    public static int BACK_RIGHT = 0;
    public static int BACK_LEFT = 1;

    public static int FRONT_RIGHT = 2;
    public static int FRONT_LEFT = 3;
    //public static String[] MOTOR_MAP = {"backRight", "backLeft", "frontRight", "frontLeft"};
    Map<String, Integer> map = new HashMap<String, Integer>()
    {{put("backRight", 0);}};

}
