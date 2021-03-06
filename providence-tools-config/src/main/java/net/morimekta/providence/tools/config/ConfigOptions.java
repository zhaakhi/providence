/*
 * Copyright (c) 2016, Providence Authors
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

package net.morimekta.providence.tools.config;

import net.morimekta.console.args.ArgumentParser;
import net.morimekta.console.args.Flag;
import net.morimekta.console.args.Option;
import net.morimekta.console.args.SubCommand;
import net.morimekta.console.args.SubCommandSet;
import net.morimekta.console.util.STTY;
import net.morimekta.providence.tools.common.CommonOptions;
import net.morimekta.providence.tools.config.cmd.Command;
import net.morimekta.providence.tools.config.cmd.Help;
import net.morimekta.providence.tools.config.cmd.Print;
import net.morimekta.providence.tools.config.cmd.Validate;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static net.morimekta.console.util.Parser.dir;

/**
 * ConfigOptions used by the providence converter.
 */
// @SuppressWarnings("all")
public class ConfigOptions extends CommonOptions {
    private List<File> includes = new ArrayList<>();
    private Command    command  = null;
    private boolean    strict   = false;

    private SubCommandSet<Command> commandSet;

    public ConfigOptions(STTY tty) {
        super(tty);
    }

    @Override
    public ArgumentParser getArgumentParser(String prog, String description) throws IOException {
        ArgumentParser parser = super.getArgumentParser(prog, description);

        parser.add(new Flag("--strict", "S", "Parse config strictly", this::setStrict, false));
        parser.add(new Option("--include", "I", "dir", "Read config definitions from these directories.", dir(includes::add), null, true, false, false));

        commandSet = new SubCommandSet<>("cmd", "Config action.", this::setCommand, null, true, getArgumentOptions());
        commandSet.add(new SubCommand<>("help", "Show help for sub-commands.", false, () -> new Help(commandSet, parser), cmd -> cmd.parser(parser)));
        commandSet.add(new SubCommand<>("print", "Print the resulting config.", false, Print::new, cmd -> cmd.parser(parser), "p", "pr"));
        commandSet.add(new SubCommand<>("validate", "Validate the file, print an error if not valid.", false, Validate::new, cmd -> cmd.parser(parser)));
        parser.add(commandSet);

        return parser;
    }

    private void setStrict(boolean strict) {
        this.strict = strict;
    }

    SubCommandSet<Command> getCommandSet() {
        return commandSet;
    }

    private void setCommand(Command command) {
        this.command = command;
    }

    public boolean showHelp() {
        return (super.showHelp() || command == null) && !showVersion();
    }

    public void execute() throws IOException {
        command.execute(this);
    }

    public boolean isStrict() {
        return strict;
    }

    public List<File> getIncludes() {
        return includes;
    }
}
