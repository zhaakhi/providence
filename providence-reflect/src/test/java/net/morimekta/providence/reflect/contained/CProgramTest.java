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
package net.morimekta.providence.reflect.contained;

import net.morimekta.providence.descriptor.PPrimitive;
import net.morimekta.providence.reflect.util.ConstProvider;
import net.morimekta.providence.reflect.util.ProgramRegistry;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import org.junit.Test;

import java.util.Collections;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;

public class CProgramTest {
    @Test
    public void testProgram() {
        ProgramRegistry registry = new ProgramRegistry();
        CStructDescriptor declaredDesc = new CStructDescriptor(null, "prog", "Struct", ImmutableList.of(), ImmutableMap.of());
        CService service = new CService("Documentation",
                                        "prog",
                                        "Service",
                                        null,
                                        ImmutableList.of(),
                                        ImmutableMap.of());
        CConst constant = new CConst(null,
                                     "kC",
                                     PPrimitive.STRING.provider(),
                                     new ConstProvider(registry.registryForPath("prog.thrift"),
                                                       "string",
                                                       "prog",
                                                       "\"value\"",
                                                       2,
                                                       10),
                                     ImmutableMap.of());

        CProgram program = new CProgram(
                "prog.thrift",
                "Documentation",
                "prog",
                ImmutableMap.of("java", "net.morimekta.providence",
                                "cpp", "morimekta.providence"),
                ImmutableSet.of("first", "second"),
                ImmutableList.of("../something/first.thrift", "second.thrift"),
                ImmutableMap.of("S", "Struct"),
                ImmutableList.of(declaredDesc),
                ImmutableList.of(service),
                ImmutableList.of(constant));

        assertThat(program.getDocumentation(), is("Documentation"));
        assertThat(program.getProgramName(), is("prog"));
        assertThat(program.getTypedefs().entrySet(), hasSize(1));
        assertThat(program.getTypedefs().get("S"), is("Struct"));

        assertThat(program.getConstants(), hasSize(1));
        assertThat(program.getConstants(), hasItem(constant));
        assertThat(program.getServices(), hasSize(1));
        assertThat(program.getServices(), hasItem(service));
        assertThat(program.getDeclaredTypes(), hasSize(1));
        assertThat(program.getDeclaredTypes(), hasItem(declaredDesc));

        assertThat(program.getNamespaceForLanguage("java"), is("net.morimekta.providence"));
        assertThat(program.getNamespaceForLanguage("cpp"), is("morimekta.providence"));
    }

    @Test
    public void testEmptyProgram() {
        CProgram program = new CProgram("prog.thrift",null, "prog", null, null, null, null, null, null, null);

        assertThat(program.getProgramFilePath(), is(notNullValue()));
        assertThat(program.getDocumentation(), is(nullValue()));
        assertThat(program.getProgramName(), is("prog"));
        assertThat(program.getNamespaces(), is(Collections.EMPTY_MAP));
        assertThat(program.getIncludedPrograms(), is(Collections.EMPTY_SET));
        assertThat(program.getIncludedFiles(), is(Collections.EMPTY_LIST));
        assertThat(program.getTypedefs(), is(Collections.EMPTY_MAP));
        assertThat(program.getDeclaredTypes(), is(Collections.EMPTY_LIST));
        assertThat(program.getServices(), is(Collections.EMPTY_LIST));
        assertThat(program.getConstants(), is(Collections.EMPTY_LIST));
    }
}
