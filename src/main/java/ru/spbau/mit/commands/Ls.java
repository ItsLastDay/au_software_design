package ru.spbau.mit.commands;

import ru.spbau.mit.utils.Environment;

import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * The {@code Ls} class implements `ls` command.
 * It lists all files and folders inside a folder.
 */
public class Ls implements Command {
    private ArrayList<String> arguments;

    public Ls(ArrayList<String> args) {
        arguments = args;
    }

    /**
     * List all entities in folder.
     * If no argument is provided, list current directory.
     * Otherwise, list provided directory.
     */
    @Override
    public OutputStream run(InputStream in) throws IOException {
        OutputStream out = new ByteArrayOutputStream();
        BufferedWriter outWriter = new BufferedWriter(new OutputStreamWriter(out));

        String curDir = Environment.getCurrentDir();
        if (arguments.size() > 0) {
            curDir = Paths.get(curDir, arguments.get(0)).toString();
        }

        File folder = new File(curDir);
        if (!folder.isDirectory()) {
            throw new IOException("Is not a directory: " + folder.toString());
        }

        for (File fl : folder.listFiles()) {
            out.write(fl.toString().getBytes());
            out.write('\n');
        }

        outWriter.flush();
        return out;
    }
}
