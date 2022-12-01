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

    public static String separateWithColons(Object[] objs) {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < objs.length - 1; ++i) {
            str.append(objs[i].toString() + ":");
        }
        str.append(objs[objs.length - 1]);
        return str.toString();
    }
}
