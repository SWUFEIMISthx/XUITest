package com.thx.xuitest;

import android.content.Context;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class Account {
    private final String username;
    private final String password;
    private final Context mContext;

    Account(String username, String password, Context mContext) {
        this.username = username;
        this.password = password;
        this.mContext = mContext;
    }
    Account(String username) {
        this.username = username;
        this.password = "";
        mContext = null;
    }
    Account(String username, Context mContext){
        this.username = username;
        this.password = null;
        this.mContext = mContext;
    }
    Account(String username, String password) {
        this.username = username;
        this.password = password;
        mContext = null;
    }
    public boolean equals(Object obj){
        if(obj == this)
        {
            return true;
        }
        if(obj == null)
        {
            return false;
        }
        if(obj instanceof Account)
        {
            Account a = (Account) obj;
            return this.username.equals(a.username) && this.password.equals(a.password);
        }
        System.out.println("没有创造的实例");
        return false;
    }

}
