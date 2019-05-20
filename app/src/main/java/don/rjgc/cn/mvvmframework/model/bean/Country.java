package don.rjgc.cn.mvvmframework.model.bean;

import com.google.gson.annotations.SerializedName;

/**
 * <pre>
 *     author : Don
 *     e-mail : donlee.chn@gmail.com
 *     time   : 2019/05/17
 *     desc   :
 *     version: 1.0
 * </pre>
 */

public class Country {
    // 国家id
    @SerializedName("country_id")
    private int countryId;
    // 国家编码
    @SerializedName("country_code")
    private int countryCode;
    // 国家英文名
    @SerializedName("country_name_en")
    private String countryNameEn;
    // 国家中文名
    @SerializedName("country_name_cn")
    private String countryNameCn;
    // 国家英文缩写
    private String ab;

    public int getCountryId() {
        return countryId;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    public int getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(int countryCode) {
        this.countryCode = countryCode;
    }

    public String getCountryNameEn() {
        return countryNameEn;
    }

    public void setCountryNameEn(String countryNameEn) {
        this.countryNameEn = countryNameEn;
    }

    public String getCountryNameCn() {
        return countryNameCn;
    }

    public void setCountryNameCn(String countryNameCn) {
        this.countryNameCn = countryNameCn;
    }

    public String getAb() {
        return ab;
    }

    public void setAb(String ab) {
        this.ab = ab;
    }
}
