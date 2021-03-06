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

package net.morimekta.providence.reflect.parser;

import net.morimekta.providence.model.ProgramType;
import net.morimekta.providence.serializer.JsonSerializer;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

/**
 * @author Stein Eldar Johnsen
 * @since 05.09.15
 */
public class MessageProgramParserTest {
    @Test
    public void testParse() throws IOException {
        MessageProgramParser parser = new MessageProgramParser(new JsonSerializer());
        ByteArrayInputStream in = new ByteArrayInputStream(
                ("{\n" +
                 "  \"namespaces\": {\n" +
                 "    \"java\": \"net.morimekta.providence\"\n" +
                 "  }\n" +
                 "}").getBytes(StandardCharsets.UTF_8));

        ProgramType program = parser.parse(in, new File("test.json"), ImmutableSet.of());

        assertThat(program.getNamespaces(), is(ImmutableMap.of(
                "java", "net.morimekta.providence"
        )));
    }

    @Test
    public void testFail() throws IOException {
        MessageProgramParser parser = new MessageProgramParser(new JsonSerializer());
        ByteArrayInputStream in = new ByteArrayInputStream(
                ("{\n" +
                 "  \"namespaces\": {\n" +
                 "    \"java\": \"net.morimekta.providence\"\n" +
                 "  },\n" +
                 "}").getBytes(StandardCharsets.UTF_8));

        try {
            parser.parse(in, new File("test.json"), ImmutableSet.of());
            fail("no exception");
        } catch (ParseException e) {
            assertThat(e.getMessage(), is("Failed to deserialize definition file test.json"));
        }
    }
}
