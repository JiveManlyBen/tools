package com.benlevy.tools;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.AfterClass;
import org.junit.Test;

public class FileToolsTest {
	static final String NEW_PARENT = "src" + File.separator + "test" + File.separator + "resources";
	static final String NEW_DIRECTORY = NEW_PARENT + File.separator + "New Folder";

    @Test
    public void testNewDirectory() {
        FileTools.createDirectory(NEW_DIRECTORY);
        assertTrue("should have created new directory", new File(NEW_DIRECTORY).exists());
    }
    
    @AfterClass
    public static void removeFiles() throws IOException {
    	FileUtils.deleteDirectory(new File(NEW_PARENT));
    }
}
