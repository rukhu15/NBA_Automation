package framework.utility.common;

import framework.entity.User;
import framework.utility.globalConst.ConfigInput;
import framework.utility.globalVars.GlobalData;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;


public class DataFactory {

    public static void loadSiqUser() {
        List<User> tList = new ArrayList<>();
        User adminUser = new User(ConfigInput.siqAdminId, ConfigInput.siqAdminPwd, true);
        User hoUser = new User(ConfigInput.siqUserId, ConfigInput.siqUserPwd, false);
        tList.add(adminUser);
        tList.add(hoUser);
        GlobalData.siqUsers = tList;
    }

    public static User getSiqUser(String name) {
        for (User usr : GlobalData.siqUsers) {
            if (usr.loginId.equalsIgnoreCase(name))
                return usr;
        }
        return null;
    }

    public static String getRandomNumberAsString(int length) {
        return "" + getRandomNumber(length);
    }

    /**
     * Generate Random Number
     *
     * @param length
     * @return
     */
    public static int getRandomNumber(int length) {
        Random r = new Random(System.currentTimeMillis());
        switch (length) {
            case 3: {
                return r.nextInt(999 - 100 + 1) + 100;
            }
            case 4: {
                return r.nextInt(9999 - 1000 + 1) + 1000;
            }
            case 5: {
                return r.nextInt(99999 - 10000 + 1) + 10000;
            }
            case 6: {
                return r.nextInt(999999 - 100000 + 1) + 100000;
            }
            case 7: {
                return r.nextInt(9999999 - 1000000 + 1) + 1000000;
            }
            case 8: {
                return r.nextInt(99999999 - 10000000 + 1) + 10000000;
            }
            case 9: {
                return r.nextInt(999999999 - 100000000 + 1) + 100000000;
            }
        }
        return length;
    }

    public static String getRandomString(int length) {
        char[] chars = "abcdefghijklmnopqrstuvwxyzhahaha".toCharArray();
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            char c = chars[random.nextInt(chars.length)];
            sb.append(c);
        }
        return sb.toString();
    }

    public static String getTimeStamp() {
        return new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
    }


}
