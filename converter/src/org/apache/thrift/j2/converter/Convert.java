/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.apache.thrift.j2.converter;

import org.apache.thrift.j2.descriptor.TDeclaredDescriptor;
import org.apache.thrift.j2.descriptor.TStructDescriptor;
import org.apache.thrift.j2.mio.TMessageReader;
import org.apache.thrift.j2.mio.TMessageWriter;
import org.apache.thrift.j2.reflect.TTypeLoader;
import org.apache.thrift.j2.reflect.parser.TParseException;
import org.apache.thrift.j2.reflect.parser.TParser;
import org.apache.thrift.j2.serializer.TSerializeException;
import org.apache.thrift.j2.util.TStringUtils;
import org.apache.utils.FormatString;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author Stein Eldar Johnsen
 * @since 27.09.15
 */
public class Convert {
    private final ConvertOptions mOpts;

    public Convert() {
        mOpts = new ConvertOptions();
    }

    @SuppressWarnings("unchecked")
    public void run(String... args) {
        CmdLineParser cli = new CmdLineParser(mOpts);
        try {
            cli.parseArgument(args);
            if (mOpts.isHelp()) {
                System.out.println("convert --def file --type type.Name --if [format] --of [format] file");
                System.out.println();
                cli.printUsage(System.out);
                System.out.println();
                System.out.println("Available formats are:");
                for (ConvertOptions.Format format : ConvertOptions.Format.values()) {
                    System.out.println(String.format(" - %-18s : %s", format.name(), format.desc));
                }
                return;
            }

            TParser parser = mOpts.getParser(cli);
            List<File> includes = mOpts.getIncludes(cli);
            File definition = mOpts.getDefinition(cli);

            TTypeLoader loader = new TTypeLoader(includes, parser);

            loader.load(definition);

            TDeclaredDescriptor tmpDesc = loader.getRegistry().getDescriptor(mOpts.mType, null);
            if (tmpDesc == null) {
                throw new CmdLineException(cli, new FormatString("Unknown type: %s"), mOpts.mType);
            }
            if (!(tmpDesc instanceof TStructDescriptor)) {
                throw new CmdLineException(cli, new FormatString("Not a message type: "), mOpts.mType);
            }

            TStructDescriptor<?> desc = (TStructDescriptor<?>) tmpDesc;
            TMessageReader input = mOpts.getInput(cli, desc);
            TMessageWriter output = mOpts.getOutput(cli);

            input.each(output);

            System.out.flush();
            System.out.println();
            System.out.flush();
            System.exit(0);
            return;
        } catch (CmdLineException e) {
            System.out.flush();
            System.err.println(e.getMessage());
            System.err.println("convert --def file --type type.Name --if [format] --of [format] file");
            System.err.println();
            cli.printUsage(System.err);
            System.err.println();
        } catch (TParseException e) {
            System.out.flush();
            System.err.println();
            if (e.getLine() != null) {
                System.err.format("Error at line %d, pos %d-%d: %s\n" +
                                  "    %s\n" +
                                  "    %s%c\n",
                                  e.getLineNo(), e.getPos(), e.getPos() + e.getLen(),
                                  e.getLocalizedMessage(),
                                  e.getLine(),
                                  TStringUtils.times("~", e.getPos()), '^');
            } else {
                System.err.println("Parser error: " + e.getLocalizedMessage());
            }
        } catch (TSerializeException e) {
            System.out.flush();
            System.err.println();
            System.err.print("Serialization error: ");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.flush();
            System.err.println();
            System.err.print("I/O error: ");
            e.printStackTrace();
        }
        System.exit(1);
    }

    public static void main(String[] args) throws Throwable {
        new Convert().run(args);
    }
}
