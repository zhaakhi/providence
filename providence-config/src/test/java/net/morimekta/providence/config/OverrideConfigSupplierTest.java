/*
 * Copyright (c) 2016, Stein Eldar Johnsen
 *
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
package net.morimekta.providence.config;

import net.morimekta.providence.config.util.TestConfigSupplier;
import net.morimekta.providence.testing.generator.GeneratorWatcher;
import net.morimekta.providence.testing.generator.SimpleGeneratorWatcher;
import net.morimekta.test.providence.config.Credentials;
import net.morimekta.test.providence.config.Database;
import net.morimekta.test.providence.config.RefConfig1;
import net.morimekta.test.providence.config.Value;
import net.morimekta.testing.time.FakeClock;
import net.morimekta.util.Binary;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;
import java.time.Clock;
import java.util.Properties;

import static net.morimekta.providence.testing.ProvidenceMatchers.equalToMessage;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

/**
 * Tests for the message config wrapper.
 */
public class OverrideConfigSupplierTest {
    private TestConfigSupplier<Database, Database._Field> base;

    @Rule
    public SimpleGeneratorWatcher generator = GeneratorWatcher.create();

    Clock clock;

    @Before
    @SuppressWarnings("unchecked")
    public void setUp() {
        clock = new FakeClock();
        base = new TestConfigSupplier<>(clock);
        base.testUpdate(Database.builder()
                                .setUri("http://hostname:9057/path")
                                .setDriver("driver")
                                .setCredentials(new Credentials("username",
                                                            "complicated password that no one guesses"))
                                .build());
    }

    @Test
    public void testSupplier() throws IOException {
        OverrideConfigSupplier<Database, Database._Field> supplier = new OverrideConfigSupplier<>(
                clock,
                base, ImmutableMap.of(
                        "credentials.password", "password",
                        "uri", "undefined"),
                true);

        Database instance = supplier.get();

        assertThat(supplier.get(), is(sameInstance(instance)));
        assertThat(instance.getUri(), is(nullValue()));  // it was reset.
        assertThat(instance.getDriver(), is("driver"));
        assertThat(instance.getCredentials(), is(not(nullValue())));
        assertThat(instance.getCredentials().getUsername(), is("username"));
        assertThat(instance.getCredentials().getPassword(), is("password"));  // it was updated.

        base.testUpdate(Database.builder()
                                .setUri("http://hostname:9057/path")
                                .setDriver("otherDriver")
                                .setCredentials(new Credentials("username", "complicated password that no one guesses"))
                                .build());

        // updating the base updates the result.
        assertThat(supplier.get(), is(not(sameInstance(instance))));

        instance = supplier.get();

        assertThat(supplier.get(), is(sameInstance(instance)));
        assertThat(supplier.configTimestamp(), is(clock.millis()));
        assertThat(instance.getUri(), is(nullValue()));  // it was reset.
        assertThat(instance.getDriver(), is("otherDriver"));
        assertThat(instance.getCredentials(), is(not(nullValue())));
        assertThat(instance.getCredentials().getUsername(), is("username"));
        assertThat(instance.getCredentials().getPassword(), is("password"));  // it was updated.
    }

    @Test
    public void testFailures() throws IOException {
        try {
            new OverrideConfigSupplier<>(
                    clock,
                    base, ImmutableMap.of(
                            "pass.password", "password",
                            "uri", "undefined"),
                    true);
            fail("No exception when strict");
        } catch (ProvidenceConfigException e) {
            assertThat(e.getMessage(), is("No such field pass in config.Database [pass.password]"));
        }

        try {
            new OverrideConfigSupplier<>(
                    clock,
                    base, ImmutableMap.of(
                    "credentials.pass", "password",
                    "uri", "undefined"),
                    true);
            fail("No exception when strict");
        } catch (ProvidenceConfigException e) {
            assertThat(e.getMessage(), is("No such field pass in config.Credentials [credentials.pass]"));
        }
    }

    @Test
    public void testFailures_noStrict() throws IOException {
        // no exception. See testFailures()
        new OverrideConfigSupplier<>(
                base, ImmutableMap.of(
                "pass.password", "password",
                "uri", "undefined"));
        new OverrideConfigSupplier<>(
                base, ImmutableMap.of(
                "credentials.pass", "password",
                "uri", "undefined"));

        try {
            Properties properties = new Properties();
            properties.setProperty("value", "FIRST SECOND");
            new OverrideConfigSupplier<>(base, properties);
            fail("No exception");
        } catch (ProvidenceConfigException e) {
            assertThat(e.getMessage(),
                       is("No config.Value value for 'FIRST SECOND' [value]"));
        }
    }

    @Test
    public void testOverrideEveryType() throws IOException {
        TestConfigSupplier<RefConfig1,RefConfig1._Field> ref = new TestConfigSupplier<>(generator.generate(RefConfig1.kDescriptor)
                                                                                                 .mutate()
                                                                                                 .clearMsgValue()
                                                                                                 .clearMapValue()
                                                                                                 .clearStrValue()
                                                                                                 .clearListValue()
                                                                                                 .clearSetValue()
                                                                                                 .clearSimpleMap()
                                                                                                 .build());
        OverrideConfigSupplier<RefConfig1,RefConfig1._Field> supplier = new OverrideConfigSupplier<>(
                ref,
                ImmutableMap.<String,String>builder()
                            .put("bool_value", "true")
                            .put("byte_value", "123")
                            .put("i16_value", "12345")
                            .put("i32_value", "1234567890")
                            .put("i64_value", "12345678901234567")
                            .put("double_value", "1234567.1234567")
                            .put("enum_value", "SECOND")
                            .put("bin_value", "hex(01020304)")
                            .put("str2_value", "This is also a string")
                            .build(),
                true);

        // Make sure every field is overridden.
        assertThat(supplier.get(), is(equalToMessage(
                RefConfig1.builder()
                          .setBoolValue(true)
                          .setByteValue((byte) 123)
                          .setI16Value((short) 12345)
                          .setI32Value(1234567890)
                          .setI64Value(12345678901234567L)
                          .setDoubleValue(1234567.1234567)
                          .setEnumValue(Value.SECOND)
                          .setBinValue(Binary.fromHexString("01020304"))
                          .setStr2Value("This is also a string")
                          .build())));
    }

    @Test
    public void testOverrideFailures() {
        assertOverrideFailure("bool_value.failure", "true",
                              "'bool_value' is not a message field in config.RefConfig1 [bool_value.failure]");
        assertOverrideFailure("bool_value", "55",
                              "Invalid bool value 55 [bool_value]");
        assertOverrideFailure("byte_value", "foo",
                              "Invalid byte value foo [byte_value]");
        assertOverrideFailure("byte_value", "1234",
                              "Invalid byte value 1234 [byte_value]");
        assertOverrideFailure("i16_value", "foo",
                              "Invalid i16 value foo [i16_value]");
        assertOverrideFailure("i16_value", "123456",
                              "Invalid i16 value 123456 [i16_value]");
        assertOverrideFailure("i32_value", "foo",
                              "Invalid i32 value foo [i32_value]");
        assertOverrideFailure("i32_value", "123456789012",
                              "Invalid i32 value 123456789012 [i32_value]");
        assertOverrideFailure("i64_value", "foo",
                              "Invalid i64 value foo [i64_value]");
        assertOverrideFailure("double_value", "foo",
                              "Invalid double value foo [double_value]");
        assertOverrideFailure("list_value", "[foo]",
                              "Overrides not allowed on list fields [list_value]");
        assertOverrideFailure("bin_value", "mee",
                              "Missing binary format mee [bin_value]");
        assertOverrideFailure("bin_value", "hex(AAf_)",
                              "Invalid hex binary value hex(AAf_) [bin_value]");
        assertOverrideFailure("bin_value", "b64(AA/,)",
                              "Invalid b64 binary value b64(AA/,) [bin_value]");
        assertOverrideFailure("enum_value", "THIRD",
                              "No config.Value value for 'THIRD' [enum_value]");
        assertOverrideFailure("msg_value", "{}",
                              "Overrides not allowed on message fields [msg_value]");
        assertOverrideFailure("map_value", "not",
                              "Overrides not allowed on map fields [map_value]");
        assertOverrideFailure("list_value", "\"woot\"",
                              "Overrides not allowed on list fields [list_value]");
        assertOverrideFailure("set_value", "1234",
                              "Overrides not allowed on set fields [set_value]");
    }

    private void assertOverrideFailure(String key, String value, String message) {
        try {
            FixedConfigSupplier<RefConfig1,RefConfig1._Field> ref = new FixedConfigSupplier<>(generator.generate(RefConfig1.kDescriptor));

            new OverrideConfigSupplier<>(
                    ref,
                    ImmutableMap.<String,String>builder()
                            .put(key, value)
                            .build(),
                    true);
            fail("No exception");
        } catch (ProvidenceConfigException e) {
            if (!e.getMessage().equals(message)) {
                e.printStackTrace();
            }
            assertThat(e.getMessage(), is(message));
        }
    }
}
