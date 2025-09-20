package org.firstinspires.ftc.teamcode.util;

import org.firstinspires.ftc.teamcode.datatypes.Pose;

public class FieldConstants {
    public static class Blue {
        public static class Left { // Closest to blue scoring basket area
            public static Pose startPos = new Pose(-36, -63.5, 0);
            public static Pose closeSample = new Pose(-49, -40.5, 0);
            public static Pose midSample = new Pose(-59.75, -40.5, 0);
            public static Pose wallSample = new Pose(-56.75, -26.25, -90);
            public static Pose park = new Pose(-23.5, -9.5, 90);
        }
        public static class Right {
            public static Pose startPos;
            public static Pose rightSample;
            public static Pose leftSample;
            public static Pose midSample;
            public static Pose park;
        }
        public static Pose scoringPos = new Pose(-56.75, -56.75, 50);
    }
    public static class Red {
        public static class Left {   // Closest to red scoring basket area
            public static Pose startPos;
            public static Pose rightSample;
            public static Pose leftSample;
            public static Pose midSample;
            public static Pose park;
        }
        public static class Right {
            public static Pose startPos;
            public static Pose rightSample;
            public static Pose leftSample;
            public static Pose midSample;
            public static Pose park;
        }
        public static Pose scoringPos;
    }
}
