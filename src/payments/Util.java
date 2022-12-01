package payments;

public class Util {
    public static boolean stringToBoolean(String string) {
        return string.equals("true");
    }

    public static int incrementOrInitialize(Integer integer) {
        if (integer == null)
            return 0;
        return integer++;
    }
}
