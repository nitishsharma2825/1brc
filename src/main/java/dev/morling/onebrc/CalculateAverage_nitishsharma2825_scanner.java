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

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class CalculateAverage_nitishsharma2825_scanner {
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

        // should use some low level interface: Mmap + buffered + parallel
        Scanner scanner = new Scanner(new File(FILE));
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] parts = line.split(";");
            String city = parts[0];
            double temp = Double.parseDouble(parts[1]);

            if (weather.containsKey(city)) {
                TemperatureRecord record = weather.get(city);
                record.totalRecords += 1;
                record.minTemp = Math.min(record.minTemp, temp);
                record.maxTemp = Math.max(record.maxTemp, temp);
                record.sumTemp += temp;
            }
            else {
                TemperatureRecord record = new TemperatureRecord();
                record.totalRecords = 1;
                record.minTemp = record.maxTemp = record.sumTemp = temp;
                weather.put(city, record);
            }
        }

        scanner.close();
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

// Time taken: 400s