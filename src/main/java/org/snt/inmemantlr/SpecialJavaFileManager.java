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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.snt.inmemantlr.memobjects.MemoryByteCode;

import javax.tools.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

/**
 * file manager for in-memory compilation
 */
class SpecialJavaFileManager extends ForwardingJavaFileManager<JavaFileManager> {

    private static final Logger LOGGER = LoggerFactory.getLogger(SpecialJavaFileManager.class);

    private SpecialClassLoader xcl;
    private HashMap<String, MemoryByteCode> mb;

    /**
     * constructor
     *
     * @param sjfm a StandardJavaFileManager
     * @param xcl a SpecialClassLoader
     */
    public SpecialJavaFileManager(StandardJavaFileManager sjfm, SpecialClassLoader xcl) {
        super(sjfm);
        this.xcl = xcl;
        mb = new HashMap<>();
    }

    /**
     * get a java file (memory byte code)
     *
     * @param location path
     * @param name filename
     * @param kind file kind
     * @param sibling file sibling
     * @return memory byte code object
     * @throws IOException if an error occurs getting the java file
     */
    public JavaFileObject getJavaFileForOutput(Location location, String name, JavaFileObject.Kind kind, FileObject sibling) throws IOException {
        MemoryByteCode mbc = new MemoryByteCode(name);
        // bookkeeping of memory bytecode
        mb.put(mbc.getClassName(), mbc);
        LOGGER.debug("add bytecode " + name);
        xcl.addClass(mbc);
        return mbc;
    }

    /**
     * get special class loader
     *
     * @param location file location
     * @return class loader
     */
    public ClassLoader getClassLoader(Location location) {
        return xcl;
    }

    /**
     * get the bytecode of a class
     *
     * @param cname the name of the class for which one would like to get the bytecode
     * @return the bytecode of class cname
     */
    public Set<MemoryByteCode> getByteCodeFromClass(String cname) {
        LOGGER.debug("get cname " + cname);
        assert mb.containsKey(cname);

        return mb.values().stream()
                .filter(m -> m.getClassName().equals(cname) || m.getClassName().matches(cname + "\\$.*"))
                .collect(toSet());
    }
}
