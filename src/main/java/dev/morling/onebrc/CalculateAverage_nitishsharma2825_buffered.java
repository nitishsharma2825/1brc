/*
 *  Copyright 2023 The original authors
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package dev.morling.onebrc;

import java.io.*;
import java.util.Map;
import java.util.TreeMap;

public class CalculateAverage_nitishsharma2825_buffered {
    private static class TemperatureRecord {
        int totalRecords;
        double minTemp;
        double maxTemp;
        double sumTemp;
    }

    private static final String FILE = "C:\\Users\\sharn\\projects\\1brc\\1brc-java\\measurements.txt";

    public static void main(String[] args) throws IOException {
        double start = System.nanoTime();

        TreeMap<String, TemperatureRecord> weather = new TreeMap<>();

        // default buffer size is 8KB
        try(BufferedReader br = new BufferedReader(new FileReader(FILE), 65536)) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(";");
                String city = parts[0];
                double temp = Double.parseDouble(parts[1]);

                TemperatureRecord record = weather.get(city);
                if (record != null) {
                    record.totalRecords += 1;
                    record.minTemp = Math.min(record.minTemp, temp);
                    record.maxTemp = Math.max(record.maxTemp, temp);
                    record.sumTemp += temp;
                }
                else {
                    record = new TemperatureRecord();
                    record.totalRecords = 1;
                    record.minTemp = record.maxTemp = record.sumTemp = temp;
                    weather.put(city, record);
                }
            }
        }

        double end = System.nanoTime();

        for (Map.Entry<String, TemperatureRecord> m : weather.entrySet()) {
            String city = m.getKey();
            TemperatureRecord record = m.getValue();
            System.out.printf("%s=%.1f/%.1f/%.1f\n", city, record.minTemp, record.sumTemp / record.totalRecords, record.maxTemp);
        }

        System.out.printf("Took %f seconds total\n", (end - start) / 1e9);
    }
}

// Notes:
// records are classes with final fields, immutable data carriers
// FileReader = InputStreamReader(FileInputStream, default charset)
// a binary raw bytes stream FileInputStream is = new FileInputStream(FILE);
// a character stream InputStreamReader isr = new InputStreamReader(is);
// a buffered wrapper BufferedReader br = new BufferedReader(isr);
// a buffered wrapper over byte stream BufferedInputStream bis = new BufferedInputStream(is);
// Scanner is a wrapper over BufferedReader with more convenient methods.
// System.in/System.out is InputStream/OutputStream i.e byte stream already so can be used with InputStreamReader directly for char stream, and then bufferedReader


// Time taken: 178s with 8KB buffer, 174s with 64KB buffer