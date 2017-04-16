package ru.spbau.mit.commands;

import ru.spbau.mit.utils.Environment;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * The {@code Cd} class implements `cd` command.
 * It changes current working directory, prints nothing.
 */
public class Cd implements Command {
    private ArrayList<String> arguments;

    public Cd(ArrayList<String> args) {
        arguments = args;
    }

    /**
     * Change current working directory, return nothing.
     * If no argument is supplied, just do nothing.
     */
    @Override
    public OutputStream run(InputStream in) throws IOException {
        OutputStream out = new ByteArrayOutputStream();
        BufferedWriter outWriter = new BufferedWriter(new OutputStreamWriter(out));

        String curDir = Environment.getCurrentDir();

        if (arguments.size() == 0) {
            return out;
        }

        String dirTo = arguments.get(0);
        Path newPath = Paths.get(curDir, dirTo).toAbsolutePath();

        if (!newPath.toFile().isDirectory()) {
            throw new IOException("Target is not a directory: " + newPath.toString());
        }

        Environment.setCurrentDir(newPath.toString());

        outWriter.flush();
        return out;
    }
}
