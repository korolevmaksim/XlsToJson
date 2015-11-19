package com.converter;

import com.google.gson.Gson;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Main {


    public static void main(String[] args) {

        try {
            FileInputStream file = new FileInputStream("/home/maksim/Downloads/CITY.xls");

            Workbook workbook = WorkbookFactory.create(file);

            Sheet sheet = workbook.getSheetAt(0);

            Iterator<Row> rowIterator = sheet.rowIterator();

            Map<String, Map<String, Set<String>>> map = new TreeMap<String, Map<String, Set<String>>>();

            while (rowIterator.hasNext()){
                Set<String> city = new TreeSet<String>();
                Map<String, Set<String>> area = new HashMap<String, Set<String>>();
                Row row = rowIterator.next();

                String sRegion = row.getCell(2).getStringCellValue();
                sRegion = sRegion.toUpperCase().substring(0,1) + sRegion.toLowerCase().substring(1);
                String sArea = "";
                try {
                    sArea = row.getCell(3).getStringCellValue() + " район";
                }catch (NullPointerException npe){
                    sArea = "";
                }

                String sovet = "";
                try{
                    sovet = row.getCell(4).getStringCellValue();
                    if(sovet.contains("поселковый Совет")){
                        sovet = " (" + sovet + ")";
                    }else{
                        sovet = " (" + sovet + " поселковый Совет)";
                    }

                }catch (NullPointerException e){
                    sovet = "";
                }
                String sCity = row.getCell(1).getStringCellValue() + sovet;

                if(!"OBL".toLowerCase().equals(sRegion.toLowerCase())) {

                    if (map.containsKey(sRegion)) {
                        area = map.get(sRegion);
                        if (area.containsKey(sArea)) {

                            city = area.get(sArea);
                            city.add(sCity);
                            area.put(sArea, city);
                            map.put(sRegion, area);

                        } else {

                            city.add(sCity);
                            area.put(sArea, city);
                            map.put(sRegion, area);
                        }


                    } else {

                        city.add(sCity);
                        area.put(sArea, city);
                        map.put(sRegion,
                                area);

                    }
                }
            }

            Gson gson = new Gson();
            String json = gson.toJson(map);
            System.out.println(json);
            try {
                FileWriter fileWriter = new FileWriter("city.json");
                fileWriter.write(json);
                fileWriter.flush();

                System.out.println("Successfully Copied JSON Object to File...");

            }catch (Exception e){
                e.printStackTrace();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
