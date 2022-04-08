package com.example.OnliDealz.Black;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Date;

public class CustomerSess {
    private static final String preference = "Maksuudi";
    private static final String keyReg = "reg_id";
    private static final String keyfull = "fullname";
    private static final String keyUsername = "username";
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

    //reg_id,fullname,username,mobile,address,email,approval,reg_date
    public CustomerSess(Context mContext) {
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
    //reg_id,fullname,username,mobile,address,email,approval,reg_date
    public void tryCust(String reg_id, String fullname, String username, String mobile, String address, String email,
                        String approval, String reg_date) {
        mEditor.putString(keyReg, reg_id);
        mEditor.putString(keyfull, fullname);
        mEditor.putString(keyUsername, username);
        mEditor.putString(keymobile, mobile);
        mEditor.putString(keyaddress, address);
        mEditor.putString(keyemail, email);
        mEditor.putString(keyapproval, approval);
        mEditor.putString(keydate, reg_date);

        Date date = new Date();

//id, fname, lname, contact, email, address, status, reg_date
        long millis = date.getTime() + (6 * 60 * 1000);
        mEditor.putLong(keyoff, millis);
        mEditor.commit();
    }

    /**
     * Checks whether user is logged in
     *
     * @return
     */
    public boolean inCustTry() {
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
    public CustomerMod getCustDetails() {

        if (!inCustTry()) {
            return null;
        }
        CustomerMod custModel = new CustomerMod();
        custModel.setReg_id(mPreferences.getString(keyReg, keyempty));
        custModel.setFullname(mPreferences.getString(keyfull, keyempty));
        custModel.setUsername(mPreferences.getString(keyUsername, keyempty));
        custModel.setMobile(mPreferences.getString(keymobile, keyempty));
        custModel.setAddress(mPreferences.getString(keyaddress, keyempty));
        custModel.setEmail(mPreferences.getString(keyemail, keyempty));
        custModel.setApproval(mPreferences.getString(keyapproval, keyempty));
        custModel.setReg_date(mPreferences.getString(keydate, keyempty));

        //reg_id,fullname,username,mobile,address,email,approval,reg_date
        return custModel;
    }

    /**
     * Logs out user by clearing the session
     */
    public void signOutCust() {
        mEditor.clear();
        mEditor.commit();
    }
}

