package ru.spbau.mit.commands;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.spbau.mit.utils.Environment;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class CdTest {
    private String currentDir;

    @Before
    public void initialize() {
        currentDir = Environment.getCurrentDir();
    }

    @After
    public void restore() {
        Environment.setCurrentDir(currentDir);
    }

    @Test
    public void testCdOneDir() throws Exception {
        List<String> args = Arrays.asList("src");
        Command cd = new Cd(new ArrayList<>(args));
        String currentDir = Environment.getCurrentDir();
        final OutputStream out = cd.run(System.in);
        assertNotEquals(currentDir, Environment.getCurrentDir());
    }

    @Test
    public void testCdEmpty() throws Exception {
        Command cd = new Cd(new ArrayList<>());
        String currentDir = Environment.getCurrentDir();
        final OutputStream out = cd.run(System.in);
        assertEquals(currentDir, Environment.getCurrentDir());
    }

    @Test(expected = IOException.class)
    public void testCdNonexistant() throws Exception {
        List<String> args = Arrays.asList("BogusDirectoryName");
        Command cd = new Cd(new ArrayList<>(args));
        String currentDir = Environment.getCurrentDir();
        final OutputStream out = cd.run(System.in);
    }

    @Test
    public void testCdThenLs() throws Exception {
        Command lsInitial = new Ls(new ArrayList<>());
        final OutputStream initialFiles = lsInitial.run(System.in);

        List<String> args = Arrays.asList("src");
        Command cd = new Cd(new ArrayList<>(args));
        cd.run(System.in);

        Command lsLater = new Ls(new ArrayList<>());
        assertNotEquals(initialFiles, lsLater.run(System.in));
    }

    @Test
    public void testCdThenCat() throws Exception {
        Command catInitial = new Cat(new ArrayList<>(Arrays.asList("CLI.iml")));
        final OutputStream cliImlContent = catInitial.run(System.in);

        List<String> args = Arrays.asList("src");
        Command cd = new Cd(new ArrayList<>(args));
        cd.run(System.in);

        Command catLater = new Cat(new ArrayList<>(Arrays.asList("../CLI.iml")));
        assertEquals(cliImlContent.toString(), catLater.run(System.in).toString());
    }
}
