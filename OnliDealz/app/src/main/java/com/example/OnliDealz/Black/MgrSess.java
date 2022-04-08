package com.example.OnliDealz.Black;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Date;

public class MgrSess {
    private static final String preference = "MussaKazija";
    private static final String keyReg = "reg_id";
    private static final String keyfull = "fullname";
    private static final String keyUsername = "username";
    private static final String keymobile = "mobile";
    private static final String keyemail = "email";
    private static final String keyrole = "role";
    private static final String keyapproval = "status";
    private static final String keydate = "reg_date";
    private static final String keyoff = "expires";
    private static final String keyempty = "";
    private Context mContext;
    private SharedPreferences.Editor mEditor;
    private SharedPreferences mPreferences;
    //reg_id,fullname,username,mobile,email,role,approval,reg_date
    public MgrSess(Context mContext) {
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
    //reg_id,fullname,username,mobile,email,role,approval,reg_date
    public void tryMgr(String reg_id, String fullname, String username, String mobile, String email,String role,
                        String approval, String reg_date) {
        mEditor.putString(keyReg, reg_id);
        mEditor.putString(keyfull, fullname);
        mEditor.putString(keyUsername, username);
        mEditor.putString(keymobile, mobile);
        mEditor.putString(keyemail, email);
        mEditor.putString(keyrole, role);
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
    public boolean inMgrTry() {
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
    public StaffMod getMgrDetails() {

        if (!inMgrTry()) {
            return null;
        }
        StaffMod custModel = new StaffMod();
        custModel.setReg_id(mPreferences.getString(keyReg, keyempty));
        custModel.setFullname(mPreferences.getString(keyfull, keyempty));
        custModel.setUsername(mPreferences.getString(keyUsername, keyempty));
        custModel.setMobile(mPreferences.getString(keymobile, keyempty));
        custModel.setEmail(mPreferences.getString(keyemail, keyempty));
        custModel.setRole(mPreferences.getString(keyrole, keyempty));
        custModel.setApproval(mPreferences.getString(keyapproval, keyempty));
        custModel.setReg_date(mPreferences.getString(keydate, keyempty));

        //reg_id,license,fullname,username,gender,mobile,address,email,approval,reg_date
        return custModel;
    }

    /**
     * Logs out user by clearing the session
     */
    public void signOutMgr() {
        mEditor.clear();
        mEditor.commit();
    }
}
