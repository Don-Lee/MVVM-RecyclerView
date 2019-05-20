package don.rjgc.cn.mvvmframework.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import don.rjgc.cn.mvvmframework.BuildConfig;
import don.rjgc.cn.mvvmframework.MyApplication;

/**
 * <pre>
 *     author : Don
 *     e-mail : donlee.chn@gmail.com
 *     time   : 2019/05/17
 *     desc   :
 *     version: 1.0
 * </pre>
 */

public class Utils {
    //检查手机号码格式
    public static boolean checkPhoneNumber(String phoneNumber) {
        if (TextUtils.isEmpty(phoneNumber)) {
            return false;
        }
        try {
            String telRegex = "^([+][0-9]{1,3})?[1][3|4|5|7|8][0-9]{9}$";
            Pattern pattern = Pattern.compile(telRegex);
            Matcher matcher = pattern.matcher(phoneNumber);
            return matcher.matches();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    //11位数字检查
    public static boolean checkNumberCount(String number) {
        if (number.length() != 11) {
            return false;
        }

        for (char c : number.toCharArray()) {
//            isDigit() 方法用于判断指定字符是否为数字
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }

    //检查密码
    public static boolean checkPassword(String password) {
        return !TextUtils.isEmpty(password) && password.length() > 4;
    }

    /**
     * 从assets读取文本信息
     */
    public static String getTextFromAssets(Context context, String fileName) {
        InputStreamReader inputStreamReader;
        try {
            inputStreamReader = new InputStreamReader(context.getAssets().open(fileName));
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line;
            StringBuilder result = new StringBuilder();
            while ((line = bufferedReader.readLine()) != null) {
                result.append(line);
            }
            if (inputStreamReader != null) {
                inputStreamReader.close();
            }
            if (bufferedReader != null) {
                bufferedReader.close();
            }
            return result.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }


    /*
     * 获取应用名
     */
    public static String getVersionName(Context context) {
        String versionName = null;
        try {
            //获取包管理者
            PackageManager pm = context.getPackageManager();
            //获取packageInfo
            PackageInfo info = pm.getPackageInfo(context.getPackageName(), 0);
            //获取versionName
            versionName = info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionName;
    }

    /*
     * 获取应用版本
     */
    public static int getVersionCode(Context context) {
        int versionCode = 0;
        try {
            //获取包管理者
            PackageManager pm = context.getPackageManager();
            //获取packageInfo
            PackageInfo packageInfo = pm.getPackageInfo(context.getPackageName(), 0);
            //获取versionCode
            versionCode = packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }

    public static int stringParseInt(final String string, final int def) {
        try {
            return (string == null || string.length() <= 0) ? def : Integer.parseInt(string);

        } catch (Exception e) {
        }
        return def;
    }

    /**
     *  dp 转 px
     * @param value dp值
     * @return
     */
    public static int dp2px(int value) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value,
                MyApplication.getContext().getResources().getDisplayMetrics());
    }
    /**
     *  sp 转 px
     * @param value sp值
     * @return
     */
    public static int sp2px(int value) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, value,
                MyApplication.getContext().getResources().getDisplayMetrics());
    }


    /**
     * 对字符串md5加密
     *
     * @param str
     * @return
     */
    public static String getMD5(String str) {
        try {
            // 生成一个MD5加密计算摘要
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(str.getBytes());
            //进行哈希计算并返回结果
            byte[] btResult = md5.digest();
            BigInteger bigInteger = new BigInteger(1, btResult);//将byte数组转换为BigInteger
            str = bigInteger.toString(16);//转化为十六进制的 32 位长度的字符串
        } catch (NoSuchAlgorithmException e) {
            if(BuildConfig.DEBUG){
                Log.e("Don", "MD5加密失败"+e.getMessage());
            }
        }
        return str;
    }

    //图片转字节数组
    public static byte[] bmpToByteArray(final Bitmap bmp, final boolean needRecycle) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, output);
        if (needRecycle) {
            bmp.recycle();
        }

        byte[] result = output.toByteArray();
        try {
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
}
