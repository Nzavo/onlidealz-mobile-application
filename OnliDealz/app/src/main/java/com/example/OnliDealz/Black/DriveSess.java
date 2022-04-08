package com.example.OnliDealz.Black;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Date;

public class DriveSess {
    private static final String preference = "NatelaAbashwili";
    private static final String keyReg = "reg_id";
    private static final String keyLicense = "license";
    private static final String keyfull = "fullname";
    private static final String keyUsername = "username";
    private static final String keygender = "gender";
    private static final String keymobile = "mobile";
    private static final String keyaddress = "address";
    private static final String keyemail = "email";
    private static final String keyapproval = "status";
    private static final String keydate = "reg_date";
    private static final String keyoff = "expires";
    private static final String keyempty = "";
    private Context mContext;
    private SharedPreferences.Editor mEditor;
    private SharedPreferences mPreferences;
    //reg_id,license,fullname,username,gender,mobile,address,email,approval,reg_date
    public DriveSess(Context mContext) {
        this.mContext = mContext;
        mPreferences = mContext.getSharedPreferences(preference, Context.MODE_PRIVATE);
        this.mEditor = mPreferences.edit();
    }

    /**
     * Logs in the user by saving user details and setting session
     *
     * @param fullname
     * @param email
     */
    //reg_id,license,fullname,username,gender,mobile,address,email,approval,reg_date
    public void tryDri(String reg_id,String license, String fullname, String username,String gender, String mobile, String address, String email,
                       String approval, String reg_date) {
        mEditor.putString(keyReg, reg_id);
        mEditor.putString(keyLicense, license);
        mEditor.putString(keyfull, fullname);
        mEditor.putString(keyUsername, username);
        mEditor.putString(keygender, gender);
        mEditor.putString(keymobile, mobile);
        mEditor.putString(keyaddress, address);
        mEditor.putString(keyemail, email);
        mEditor.putString(keyapproval, approval);
        mEditor.putString(keydate, reg_date);

        Date date = new Date();

        long millis = date.getTime() + (6 * 60 * 1000);
        mEditor.putLong(keyoff, millis);
        mEditor.commit();
    }

    /**
     * Checks whether user is logged in
     *
     * @return
     */
    public boolean inDriTry() {
        Date currentDate = new Date();

        long millis = mPreferences.getLong(keyoff, 0);

        if (millis == 0) {
            return false;
        }
        Date expiryDate = new Date(millis);


        return currentDate.before(expiryDate);
    }

    /**
     * Fetches and returns user details
     *
     * @return user details
     */
    public DriveMd getDriDetails() {

        if (!inDriTry()) {
            return null;
        }
        DriveMd custModel = new DriveMd();
        custModel.setReg_id(mPreferences.getString(keyReg, keyempty));
        custModel.setLicense(mPreferences.getString(keyLicense, keyempty));
        custModel.setFullname(mPreferences.getString(keyfull, keyempty));
        custModel.setUsername(mPreferences.getString(keyUsername, keyempty));
        custModel.setGender(mPreferences.getString(keygender, keyempty));
        custModel.setMobile(mPreferences.getString(keymobile, keyempty));
        custModel.setAddress(mPreferences.getString(keyaddress, keyempty));
        custModel.setEmail(mPreferences.getString(keyemail, keyempty));
        custModel.setApproval(mPreferences.getString(keyapproval, keyempty));
        custModel.setReg_date(mPreferences.getString(keydate, keyempty));

        //reg_id,license,fullname,username,gender,mobile,address,email,approval,reg_date
        return custModel;
    }

    /**
     * Logs out user by clearing the session
     */
    public void signOutDri() {
        mEditor.clear();
        mEditor.commit();
    }
}