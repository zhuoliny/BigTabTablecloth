package com.example.zhuoliny.bigtabtablecloth;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class writeCSV {
    private static final String COMMA = ",";
    private static final Character BREAK = '\n';
    private static final String FILE_MARKER = "Name,Gender,Age,Description";
    private static final String FILE_HEADER = "StandardTime,X_Position,Y_Position";
    private static scannerHelper sHelper;

    public static void writeCsvFile(Context c, String fileName, ArrayList<String> allTouch, UserInfo userInfo) {
        try {
            if (isExternalStorageWritable()) {
                File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), fileName);
                PrintWriter csvWriter;
                if (!file.mkdirs()) {
                    Log.e("Error: ", "Directory not created");
                }
                if (file.exists()) {
                    file.delete();
                    file.createNewFile();
                    csvWriter = new PrintWriter(new FileWriter(file, true));
                    csvWriter.print(FILE_MARKER);
                    csvWriter.print(BREAK);
                    csvWriter.append(BREAK);
                    csvWriter.print(userInfo.getName()+COMMA+userInfo.getGender()+COMMA+userInfo.getAge()+COMMA+userInfo.getMoreInfo());
                    csvWriter.append(BREAK);
                    csvWriter.print(FILE_HEADER);
                    csvWriter.append(BREAK);
                    for (String aTouch : allTouch) {
                        csvWriter.print(aTouch);
                        csvWriter.append(BREAK);
                    }
                    csvWriter.close();
                    sHelper = new scannerHelper(c, file);
                    System.out.println("CSV file was created successfully");
                }
            }
        } catch (Exception e) {
            System.out.println("Error in CsvFileWriter");
            e.printStackTrace();
        }
    }

    public static boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }
}