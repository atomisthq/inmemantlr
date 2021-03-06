/*
* Inmemantlr - In memory compiler for Antlr 4
*
* Copyright 2016, Julian Thomé <julian.thome@uni.lu>
*
* Licensed under the EUPL, Version 1.1 or – as soon they will be approved by
* the European Commission - subsequent versions of the EUPL (the "Licence");
* You may not use this work except in compliance with the Licence. You may
* obtain a copy of the Licence at:
*
* https://joinup.ec.europa.eu/sites/default/files/eupl1.1.-licence-en_0.pdf
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the Licence is distributed on an "AS IS" basis, WITHOUT
* WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the Licence for the specific language governing permissions and
* limitations under the Licence.
*/

package org.snt.inmemantlr.utils;

import org.apache.commons.io.IOUtils;

import java.io.InputStream;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;

public final class FileUtils {

    private FileUtils() {
    }

    /**
     * load file content
     *
     * @param path path of the file to load
     * @return file content as string
     */
    public static String loadFileContent(String path) {
        byte[] bytes;
        try {
            RandomAccessFile f = new RandomAccessFile(path, "r");
            bytes = new byte[(int) f.length()];
            f.read(bytes);
        } catch (Exception e) {
            return null;
        }
        return new String(bytes);
    }

    /**
     * load input stream
     *
     * @param is input stream
     * @return stream content as string
     */
    public static String getStringFromStream(InputStream is) {
        try {
            return IOUtils.toString(is, StandardCharsets.UTF_8);
        } catch (Exception e) {
            return null;
        }
    }
}
