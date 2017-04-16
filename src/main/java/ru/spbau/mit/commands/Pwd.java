package ru.spbau.mit.commands;

import ru.spbau.mit.utils.Environment;

import java.io.*;
import java.util.ArrayList;

/**
* The {@code Pwd} class implements the command that prints current working directory path to its standard output.
*/

public class Pwd implements Command {
    public Pwd(ArrayList<String> args) {}

    /**
     * Prints working directory to standard output, ignores standard input.
     */

    @Override
    public OutputStream run(InputStream in) throws IOException {
        OutputStream out = new ByteArrayOutputStream();
        BufferedWriter outWriter = new BufferedWriter(new OutputStreamWriter(out));

        outWriter.write(Environment.getCurrentDir());

        outWriter.flush();
        return out;
    }
}
