package com.converter;

import com.converter.domain.Area;
import com.converter.domain.Region;
import com.google.gson.Gson;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.util.SystemOutLogger;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Main {

    private static final int REGION = 2;
    private static final int AREA = 3;
    private static final int CITY = 1;


    public static void main(String[] args) {

        try {
            FileInputStream file = new FileInputStream("/home/maksim/Downloads/CITY.xls");

            Workbook workbook = WorkbookFactory.create(file);

            Sheet sheet = workbook.getSheetAt(0);

            Iterator<Row> rowIterator = sheet.rowIterator();

            Map<String, Map<String, Set<String>>> map = new TreeMap<String, Map<String, Set<String>>>();

            Map<String, Region> regions = new HashMap<String, Region>();

            while (rowIterator.hasNext()){
                Set<String> city = new TreeSet<String>();
                Map<String, Set<String>> area = new HashMap<String, Set<String>>();
//                Region region = new Region();
//                Area area = new Area();
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

//                System.out.println(sRegion + " " + sArea + " " + sCity);

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


//            JSONArray jsonArray = new JSONArray(map);

            Map<String, Set<Area>> listMap = new HashMap<String, Set<Area>>();
            Region region = new Region();
            region.setName("Minskaya oblast");
            Set<Area> areas = new TreeSet<Area>();
            Area area = new Area();
            area.setName("Minski raion");
            Set<String> cities = new TreeSet<String>();
            cities.add("Misk");
            cities.add("Ne Minsk");
            area.setCity(cities);
            areas.add(area);
            region.setArea(areas);
            listMap.put("Minskaya oblast", areas);
            JSONObject jsonObject = new JSONObject(listMap);

            Gson gson = new Gson();
            String json = gson.toJson(map);
            System.out.println(json);
            try {
                FileWriter fileWriter = new FileWriter("city.json");
                fileWriter.write(json);
                fileWriter.flush();

                // /jsonObject.write(fileWriter).flush();

                System.out.println("Successfully Copied JSON Object to File...");

            }catch (Exception e){
                e.printStackTrace();
            }
//            JSONObject jsonObject = new JSONObject(map);
//            System.out.println(jsonObject.toString());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
