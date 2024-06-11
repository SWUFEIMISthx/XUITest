package com.thx.xuitest;

import android.content.Context;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;

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

    Account(String username, Context mContext) {
        this.username = username;
        this.password = null;
        this.mContext = mContext;
    }

    Account(String username, String password) {
        this.username = username;
        this.password = password;
        mContext = null;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (obj instanceof Account) {
            Account a = (Account) obj;
            return this.username.equals(a.username) && this.password.equals(a.password);
        }
        System.out.println("没有创造的实例");
        return false;
    }

    public boolean saveAccount() {
        try {
            FileInputStream fis;
            if (mContext != null) {
                fis = mContext.openFileInput("user_info.txt");
                Reader in = new InputStreamReader(fis, StandardCharsets.UTF_8);
                LineNumberReader reader = new LineNumberReader(in);
                String s;

                while ((s = reader.readLine()) != null) {
                    System.out.println(s);
                    String[] list = s.split(",");
                    if ((list[0]).equalsIgnoreCase(username)) {
                        System.out.println("equal");
                        reader.close();
                        in.close();
                        return false;
                    }
                }
                reader.close();
                in.close();
                FileOutputStream fos = mContext.openFileOutput("user_info.txt", mContext.MODE_APPEND);
                String info = username + "," + password + "\n";
                fos.write(info.getBytes(StandardCharsets.UTF_8));
                fos = mContext.openFileOutput(username + "bill.txt", mContext.MODE_APPEND);
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("读取出错");
        }
        return true;
    }

    public void saveBill(String takeAway, String cost) {
        try {
            FileOutputStream fos = mContext.openFileOutput(username + "bill.txt", mContext.MODE_APPEND);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");// HH:mm:ss
            SimpleDateFormat billDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");// HH:mm:ss
            int line = getTotalLines();
            //获取当前时间
            Date date = new Date(System.currentTimeMillis());
            String orderNumber = billDateFormat.format(date) + String.format("%05d", line);
            String info = orderNumber + "," + simpleDateFormat.format(date) + "," + takeAway + "," + cost + "\n";
            System.out.println(info);
            fos.write(info.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("写入错误");
        }
    }

    private int getTotalLines() throws IOException {
        FileInputStream fis = mContext.openFileInput(username + "bill.txt");
        Reader in = new InputStreamReader(fis, StandardCharsets.UTF_8);
        LineNumberReader reader = new LineNumberReader(in);
        String s = reader.readLine();
        int lines = 0;
        while (s != null) {
            lines++;
            s = reader.readLine();
        }
        reader.close();
        in.close();
        return lines;
    }

    public boolean verifyAccount() {
        try {
            FileInputStream fis = mContext.openFileInput("user_info.txt");
            Reader in = new InputStreamReader(fis, StandardCharsets.UTF_8);
            LineNumberReader reader = new LineNumberReader(in);
            String s;

            while ((s = reader.readLine()) != null) {
                String[] list = s.split(",");
                if (list[0].equalsIgnoreCase(username) && list[1].equals(password)) {
                    reader.close();
                    in.close();
                    return true;
                }
            }
            reader.close();
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("读取出错");
        }
        return false;
    }
}
