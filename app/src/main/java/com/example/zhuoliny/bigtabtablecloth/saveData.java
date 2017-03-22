package com.example.zhuoliny.bigtabtablecloth;

import android.content.Context;
import android.os.Environment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Calendar;

public class saveData {
    private FileOutputStream outputStream;
    private FileInputStream inputStream;
    private Context _context;
    public static String _fileName;
    private Calendar cal = Calendar.getInstance();
    private int month = cal.get(Calendar.MONTH) + 1;
    private int day = cal.get(Calendar.DAY_OF_MONTH);
    private int year = cal.get(Calendar.YEAR);
    private int hour = cal.get(Calendar.HOUR_OF_DAY);
    private int minute = cal.get(Calendar.MINUTE);
    private int second = cal.get(Calendar.SECOND);

    public saveData(Context context) {
        FileOutputStream outputStream;
        FileInputStream inputStream;
        _context = context;
        _fileName = "TbClth_" + month + "/" + day + "/" + year + "/_" + hour + ":" + minute + ":" + second + ".csv";
    }

    public void save(long mSecond, float xPosi, float yPosi, UserInfo user) {

        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);

        try {
            String string = "";
            string += load();
            String value = "";
            string += hour + ":" + minute + ":" + second + "," + mSecond + "," + xPosi + "," + yPosi + ";";
            outputStream = _context.openFileOutput(_fileName, Context.MODE_WORLD_READABLE);
            outputStream.write(string.getBytes());
            outputStream.close();
            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), _fileName);
            PrintWriter csvWriter;
            if (!file.exists()) {
                try {
                    file.createNewFile();
                    csvWriter = new PrintWriter(new FileWriter(file, true));
                    int last = 0;
                    csvWriter.print("TbClth [" + month + "/" + day + "/" + year + "],Gender: " +  user.getGender() + ",Age: " + user.getAge() + "," +user.getMoreInfo() + "\nTime,MilliSecond,XPosition,YPosition");

                    csvWriter.append('\n');
                    for (int i = 0; i < string.length(); i++) {
                        if (string.charAt(i) == ';') {
                            csvWriter.print(string.substring(last + 1, i));
                            csvWriter.append('\n');
                            last = i;
                        }
                    }
                    csvWriter.print(string.substring(last, string.length() - 1));
                    csvWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                file.delete();
                try {
                    file.createNewFile();
                    csvWriter = new PrintWriter(new FileWriter(file, true));
                    csvWriter.print("TbClth [" + month + "/" + day + "/" + year + "],Gender: " + user.getGender() + "Age: " + user.getAge() + user.getMoreInfo() + "\nTime,MilliSecond,XPosition,YPosition");
                    csvWriter.append('\n');
                    int last = 0;

                    for (int i = 0; i < string.length(); i++) {
                        if (string.charAt(i) == ';') {
                            csvWriter.print(string.substring(last + 1, i));
                            csvWriter.append('\n');
                            last = i;
                        }
                    }
                    csvWriter.print(string.substring(last, string.length() - 1));
                    csvWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String load() {
        String line1 = "";
        try {
            inputStream = _context.openFileInput(_fileName);
            if (inputStream != null) {
                InputStreamReader inputreader = new InputStreamReader(inputStream);
                BufferedReader buffreader = new BufferedReader(inputreader);
                String line = "";
                try {
                    while ((line = buffreader.readLine()) != null) {
                        line1 += line;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {

            String error = "";
            error = e.getMessage();
        }
        return line1;
    }
}