/*
 * MIT License
 * 
 * Copyright (c) 2025 Douglas Marcelo Monquero
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, subject to the following conditions:
 * 
 * 1. **Attribution Required**: Any use, modification, or distribution of this
 *    software must include credit to the original author, Douglas Marcelo Monquero,
 *    in any derivative works or publications.
 *
 * 2. **Disclaimer**: THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 *    EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *    FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *    AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 *    WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR
 *    IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package br.com.infox.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class EnvLoader {

    private static final Properties properties = new Properties();

    static {
        try (FileInputStream fileInputStream = new FileInputStream(".env")) {
            properties.load(fileInputStream);
        } catch (IOException e) {
            throw new RuntimeException("Falha ao carregar o arquivo .env", e);
        }
    }

    public static String get(String key) {
        return properties.getProperty(key);
    }
}
