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


package org.snt.inmemantlr;

import javax.tools.SimpleJavaFileObject;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;

class MemoryByteCode extends SimpleJavaFileObject {
    private ByteArrayOutputStream baos;

    public MemoryByteCode(String name) {
        super(URI.create("byte:///" + name + ".class"), Kind.CLASS);
    }

    public CharSequence getCharContent(boolean ignoreEncodingErrors) {
        throw new IllegalStateException();
    }

    public OutputStream openOutputStream() {
        baos = new ByteArrayOutputStream();
        return baos;
    }

    public InputStream openInputStream() {
        throw new IllegalStateException();
    }

    public byte[] getBytes() {
        return baos.toByteArray();
    }
}
