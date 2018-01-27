/*
 * Copyright 2017 Providence Authors
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
package net.morimekta.providence.config.impl;

import net.morimekta.providence.PMessage;
import net.morimekta.providence.PMessageBuilder;
import net.morimekta.providence.config.ProvidenceConfigException;
import net.morimekta.providence.descriptor.PContainer;
import net.morimekta.providence.descriptor.PDescriptor;
import net.morimekta.providence.descriptor.PField;
import net.morimekta.providence.descriptor.PMap;
import net.morimekta.providence.descriptor.PMessageDescriptor;
import net.morimekta.providence.serializer.pretty.Tokenizer;
import net.morimekta.providence.serializer.pretty.TokenizerException;
import net.morimekta.providence.util.TypeRegistry;
import net.morimekta.util.io.Utf8StreamReader;
import net.morimekta.util.json.JsonException;
import net.morimekta.util.json.JsonToken;
import net.morimekta.util.json.JsonTokenizer;

import javax.annotation.Nonnull;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static net.morimekta.providence.config.impl.ProvidenceConfigUtil.canonicalFileLocation;

/**
 * This parser parses config files. The class in itself should be stateless, so
 * can safely be used in multiple threads safely. This is a utility class created
 * in order to simplify testing.
 */
public class IntermediateConfigParser {
    /**
     * Create a providence config parser instance.
     *
     * @param registry The type registry used.
     * @param strict If config should be parsed and handled strictly.
     */
    public IntermediateConfigParser(TypeRegistry registry, boolean strict) {
        this.registry = registry;
        this.strict = strict;
    }

    <M extends PMessage<M, F>, F extends PField>
    M parseConfig(@Nonnull Path configFile, M parent) throws ProvidenceConfigException {
        try {
            configFile = canonicalFileLocation(configFile);
        } catch (IOException e) {
            throw new ProvidenceConfigException(e, "Unable to resolve config file " + configFile)
                    .setFile(configFile.getFileName().toString());
        }
        try (BufferedInputStream in = new BufferedInputStream(new FileInputStream(configFile.toFile()))) {
            return parseConfigRecursively(in, parent);
        } catch (JsonException e) {
            throw new ProvidenceConfigException(e, e.getMessage())
                    .setFile(configFile.getFileName().toString())
                    .setLine(e.getLine())
                    .setLineNo(e.getLineNo())
                    .setLinePos(e.getLinePos())
                    .setLength(e.getLen());
        } catch (IOException e) {
            if (e instanceof ProvidenceConfigException) {
                ProvidenceConfigException pce = (ProvidenceConfigException) e;
                if (pce.getFile() == null) {
                    pce.setFile(configFile.getFileName().toString());
                }
                throw pce;
            }
            if (e instanceof TokenizerException) {
                TokenizerException te = (TokenizerException) e;
                if (te.getFile() == null) {
                    te.setFile(configFile.getFileName().toString());
                }
                throw new ProvidenceConfigException(te);
            }
            throw new ProvidenceConfigException(e, e.getMessage())
                    .setFile(configFile.getFileName().toString());
        }
    }

    @SuppressWarnings("unchecked")
    private <M extends PMessage<M, F>, F extends PField>
    M parseConfigRecursively(@Nonnull InputStream in, M parent) throws IOException, JsonException {
        // Non-enclosed content, meaning we should read the whole file immediately.
        JsonTokenizer tokenizer = new JsonTokenizer(new Utf8StreamReader(in), Tokenizer.DEFAULT_BUFFER_SIZE);

        tokenizer.expectSymbol("Config start", JsonToken.kMapStart);

        JsonToken token = tokenizer.expectString("Schema name");
        String schema = token.rawJsonLiteral();
        PMessageDescriptor<M, F> descriptor;
        try {
            descriptor = registry.getMessageType(schema);
        } catch (IllegalArgumentException e) {
            throw new JsonException("Unknown schema name " + schema, tokenizer, token);
        }

        tokenizer.expectSymbol("Schema message sep", JsonToken.kKeyValSep);
        tokenizer.expectSymbol("Message start", JsonToken.kMapStart);

        Map<String, Object> result = parseMessage(tokenizer, descriptor);

        tokenizer.expectSymbol("Config end", JsonToken.kMapEnd);

        if (tokenizer.hasNext()) {
            JsonToken next = tokenizer.peek("");
            throw new JsonException("Garbage at end of file", tokenizer, next);
        }

        if (parent == null) {
            parent = descriptor.builder().build();
        }

        return fromIntermediate(result, parent);
    }

    @SuppressWarnings("unchecked")
    private <M extends PMessage<M, F>, F extends PField>
    Map<String,Object> parseMessage(@Nonnull JsonTokenizer tokenizer,
                                    @Nonnull PMessageDescriptor<M,F> descriptor) throws IOException, JsonException {
        Map<String, Object> result = new TreeMap<>();

        // Non-enclosed content, meaning we should read the whole file immediately.
        if (tokenizer.peek("Message end or field").isSymbol(JsonToken.kMapEnd)) {
            tokenizer.next();
            return result;
        }
        char sep = JsonToken.kMapStart;
        while (sep != JsonToken.kMapEnd) {
            JsonToken token = tokenizer.expectString("Message field");
            String name = token.rawJsonLiteral();
            if (name.startsWith("@")) {
                // key: value only.
                tokenizer.expectSymbol("Message key value sep", JsonToken.kKeyValSep);
                token = tokenizer.expectString("Annotation value");
                String annotation = token.rawJsonLiteral();
                result.put(name, annotation);
            } else {
                PField field = descriptor.findFieldByName(name);
                if (field == null) {
                    if (strict) {
                        throw new JsonException("Unknown field " + name + " in " + descriptor.getQualifiedName(), tokenizer, token);
                    }
                    tokenizer.expectSymbol("Message key value sep", JsonToken.kKeyValSep);
                    consumeIntermediateJsonValue(tokenizer);
                } else {
                    tokenizer.expectSymbol("Message key value sep", JsonToken.kKeyValSep);
                    token = tokenizer.expect("Field value");
                    if (token.isNull()) {
                        result.put(name, null);
                    } else {
                        switch (field.getType()) {
                            case MESSAGE:
                                result.put(name, parseMessage(tokenizer, (PMessageDescriptor) field.getDescriptor()));
                                break;
                            default:
                                result.put(name, parseValue(token, tokenizer, field.getDescriptor()));
                                break;
                        }
                    }
                }
            }
            sep = tokenizer.expectSymbol("Message end or sep", JsonToken.kListSep, JsonToken.kMapEnd);
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    private Object parseValue(JsonToken token, JsonTokenizer tokenizer, PDescriptor descriptor)
            throws IOException, JsonException {
        switch (descriptor.getType()) {
            case VOID:
            case BOOL:
                if (token.isBoolean()) {
                    return token.booleanValue();
                } else {
                    throw new JsonException("Unexpected boolean value", tokenizer, token);
                }
            case BYTE:
            case I16:
            case I32:
            case I64:
                if (token.isInteger()) {
                    return token.longValue();
                } else {
                    throw new JsonException("Unexpected integer value", tokenizer, token);
                }
            case DOUBLE:
                if (token.isNumber()) {
                    return token.doubleValue();
                } else {
                    throw new JsonException("Unexpected double value", tokenizer, token);
                }
            case STRING:
                if (token.isLiteral()) {
                    return token.decodeJsonLiteral();
                } else {
                    throw new JsonException("Unexpected string value", tokenizer, token);
                }
            case BINARY:
                if (token.isLiteral()) {
                    return token.rawJsonLiteral();
                } else {
                    throw new JsonException("Unexpected binary value", tokenizer, token);
                }
            case ENUM:
                if (token.isInteger()) {
                    return token.intValue();
                } else if (token.isLiteral()) {
                    return token.rawJsonLiteral();
                } else {
                    throw new JsonException("Unexpected enum value", tokenizer, token);
                }
            case SET:
            case LIST:
                if (token.isSymbol(JsonToken.kListStart)) {
                    List<Object> out = new ArrayList<>();
                    PContainer pls = (PContainer) descriptor;
                    while (!token.isSymbol(JsonToken.kListEnd)) {
                        out.add(parseValue(tokenizer.expect("list value"), tokenizer, pls.itemDescriptor()));
                        token = tokenizer.expect("List end or sep");
                        if (!token.isSymbol(JsonToken.kListEnd) && !token.isSymbol(JsonToken.kListSep)) {
                            throw new JsonException("Expected list end or sep", tokenizer, token);
                        }
                    }
                    return out;
                } else {
                    throw new JsonException("Unexpected start of list type", tokenizer, token);
                }
            case MAP:
                if (token.isSymbol(JsonToken.kMapStart)) {
                    Map<Object,Object> out = new TreeMap<>();
                    PMap map = (PMap) descriptor;
                    while (!token.isSymbol(JsonToken.kMapEnd)) {
                        Object key = ProvidenceConfigUtil.asType(map.keyDescriptor(), tokenizer.expectString("Map key").decodeJsonLiteral());
                        tokenizer.expectSymbol("Map kv sep", JsonToken.kKeyValSep);
                        Object value = parseValue(tokenizer.expect("map value"), tokenizer, map.itemDescriptor());
                        out.put(key, value);
                        token = tokenizer.expect("Map end or sep");
                        if (!token.isSymbol(JsonToken.kMapEnd) && !token.isSymbol(JsonToken.kListSep)) {
                            throw new JsonException("Expected list end or sep", tokenizer, token);
                        }
                    }
                    return out;
                } else {
                    throw new JsonException("Unexpected start of map type", tokenizer, token);
                }
            case MESSAGE:
                return parseMessage(tokenizer, (PMessageDescriptor) descriptor);
            default:
                throw new IllegalStateException("Impossible!");
        }

    }

    private void consumeIntermediateJsonValue(JsonTokenizer tokenizer) throws IOException, JsonException {
        JsonToken token = tokenizer.expect("Json value");
        if (token.isSymbol(JsonToken.kListStart)) {
            // parse list and all in it.
            while (!token.isSymbol(JsonToken.kListEnd)) {
                consumeIntermediateJsonValue(tokenizer);
                token = tokenizer.expect("List sep or end");
                if (!token.isSymbol(JsonToken.kListSep) && !token.isSymbol(JsonToken.kListEnd)) {
                    throw new JsonException("Expected list separator or end", tokenizer, token);
                }
            }
        } else if (token.isSymbol(JsonToken.kMapStart)) {
            // parse map and all in it.
            while (!token.isSymbol(JsonToken.kListEnd)) {
                tokenizer.expectString("Map key");
                tokenizer.expectSymbol("Map KV sep", JsonToken.kKeyValSep);

                consumeIntermediateJsonValue(tokenizer);
                token = tokenizer.expect("Map entry sep or end");
                if (!token.isSymbol(JsonToken.kListSep) && !token.isSymbol(JsonToken.kMapEnd)) {
                    throw new JsonException("Expected list separator or end", tokenizer, token);
                }
            }
        } else if (token.isSymbol()) {
            // not allowed.
            throw new JsonException("Unexpected symbol '" + token.asString() + "'", tokenizer, token);
        }
    }

    /**
     * Type registry for looking up the base config types.
     */
    private final TypeRegistry registry;

    /**
     * If config should be parsed strictly.
     */
    private final boolean strict;

    // --- static

    @SuppressWarnings("unchecked")
    private static <M extends PMessage<M,F>, F extends PField>
    M fromIntermediate(@Nonnull Map<String, Object> intermediate, @Nonnull M parent) throws ProvidenceConfigException {
        PMessageDescriptor<M,F> descriptor = parent.descriptor();
        PMessageBuilder<M,F> builder = parent.mutate();
        for (Map.Entry<String,Object> entry : intermediate.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();

            if (key.startsWith("@")) {
                continue;
            }
            F field = descriptor.findFieldByName(key);
            if (field == null) {
                continue;
            }
            if (value == null) {
                builder.clear(field);
                continue;
            }

            switch (field.getType()) {
                case MESSAGE: {
                    if (!(value instanceof Map)) {
                        continue;
                    }
                    Map<String, Object> map = (Map) value;

                    PMessage fieldParent = parent.get(field);
                    if (fieldParent == null || REPLACE.equals(intermediate.get(AT_RESOLVE + field.getName()))) {
                        fieldParent = ((PMessageDescriptor<?, ?>) field.getDescriptor()).builder().build();
                    }

                    builder.set(field, fromIntermediate(map, fieldParent));
                    break;
                }
                case MAP: {
                    if (!(value instanceof Map)) {
                        continue;
                    }
                    PMap fieldDescriptor = (PMap) field.getDescriptor();
                    Map<Object, Object> fieldParent = parent.get(field);
                    if (fieldParent == null || REPLACE.equals(intermediate.get(AT_RESOLVE + field.getName()))) {
                        fieldParent = new TreeMap<>();
                    } else {
                        fieldParent = new TreeMap<>(fieldParent);
                    }
                    fieldParent.putAll(ProvidenceConfigUtil.asMap(value, fieldDescriptor.keyDescriptor(), fieldDescriptor.itemDescriptor()));
                    builder.set(field, fieldParent);
                    break;
                }
                default:
                    builder.set(field, ProvidenceConfigUtil.asType(field.getDescriptor(), value));
                    break;
            }
        }

        return builder.build();
    }

    static final String REPLACE = "replace";
    static final String AT_RESOLVE = "@resolve:";
}
