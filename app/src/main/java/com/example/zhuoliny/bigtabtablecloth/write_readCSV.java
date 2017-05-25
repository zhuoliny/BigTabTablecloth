package com.example.zhuoliny.bigtabtablecloth;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class write_readCSV {
    private static final Character BREAK = '\n';
   private static scannerHelper sHelper;

    public static void writeCsvFile(Context c, String fileName, ArrayList<String> results, String userName) {
        try {
            if (isExternalStorageWritable()) {
                File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "TbClth_"+userName+"/"+fileName);
                PrintWriter csvWriter;
                if (!file.mkdirs()) {
                    Log.e("Error", "(Centralized) Directory not created");
                }
                if (file.exists()) {
                    file.delete();
                    file.createNewFile();
                    csvWriter = new PrintWriter(new FileWriter(file, true));
                    for (String aLine: results) {
                        csvWriter.print(aLine);
                        csvWriter.append(BREAK);
                    }
                    csvWriter.close();
                    sHelper = new scannerHelper(c, file);
                    Log.i("Message","(Centralized) CSV file was created successfully");
                }
            }
        } catch (Exception e) {
            Log.e("Error", "(Centralized) Error in CsvFileWriter");
            e.printStackTrace();
        }
    }

    public static ArrayList<String> readCsvFile(String fileName){
        ArrayList<String> results = new ArrayList<String>();
        String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath()+"/"+fileName;
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(path));
            String line = "";
            while ((line = reader.readLine()) != null) {
                results.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            if (reader != null) {
                reader.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return results;
    }

    public static boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }
}
