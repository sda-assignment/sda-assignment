package common;

public class Util {
    public static boolean stringToBoolean(String string) {
        return string.equals("true");
    }

    public static int incrementOrInitialize(Integer integer) {
        if (integer == null)
            return 0;
        return integer + 1;
    }

    public static String separateWithColons(Object[] objs) {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < objs.length - 1; ++i) {
            str.append(objs[i].toString() + ":");
        }
        str.append(objs[objs.length - 1]);
        return str.toString();
    }

    public static boolean isPositiveFloat(String string) {
        return string.matches("[+]?[0-9]*\\.?[0-9]+");
    }

    public static boolean isPositiveInt(String string) {
        return string.matches("[+]?[0-9]+");
    }
}
