package ru.spbau.mit.commands;

import org.junit.Test;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;

import static junitx.framework.Assert.assertEquals;

public class LsTest {
    @Test
    public void testLsNoArg() throws Exception {
        Command ls = new Ls(new ArrayList<>());
        String files = ls.run(System.in).toString().trim();
        int countFiles = files.length() - files.replace("\n", "").length();
        // There are 9 files in project's root directory (including hidden).
        // They are separated by 8 newlines.
        assertEquals(8, countFiles);
    }

    @Test
    public void testLsOtherDir() throws Exception {
        Command ls = new Ls(new ArrayList<>(Arrays.asList("./src")));
        String files = ls.run(System.in).toString().trim();
        int countFiles = files.length() - files.replace("\n", "").length();
        // There are 2 files in ./src directory.
        assertEquals(1, countFiles);
    }
}
