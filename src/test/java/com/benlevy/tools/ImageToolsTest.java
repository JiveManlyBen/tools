package com.benlevy.tools;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;

import org.junit.Test;

import com.drew.imaging.ImageProcessingException;

public class ImageToolsTest {
	static final String IMAGE_DIRECTORY = "src" + File.separator + "test" + File.separator + "resources" + File.separator;

    @Test
    public void testNoImageDate() throws ImageProcessingException, IOException {
    	assertNull("date should be null for image without Exif", ImageTools.getJpegTakenDate(IMAGE_DIRECTORY + "img_4304s.jpg"));
    }
    
    @Test
    public void testImageDate() throws ImageProcessingException, IOException {
    	Calendar cal = ImageTools.getJpegTakenDate(IMAGE_DIRECTORY + "img_9815.jpg");
    	assertNotNull("date should be found when Exif exists", cal);
    	assertEquals("year pic taken should be 2014", 2014, cal.get(Calendar.YEAR));
    	assertEquals("month pic taken should be January", 0, cal.get(Calendar.MONTH));
    	assertEquals("day pic taken should be 2nd", 2, cal.get(Calendar.DAY_OF_MONTH));
    }
}
