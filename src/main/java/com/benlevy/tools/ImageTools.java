package com.benlevy.tools;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.lang.Rational;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifSubIFDDirectory;
import com.drew.metadata.exif.GpsDirectory;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;

public class ImageTools {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
	}

	public static Calendar getJpegTakenDate(String filePath) throws IOException, ImageProcessingException {
		File jpegFile = new File(filePath);
		Metadata metadata = ImageMetadataReader.readMetadata(jpegFile);
		return getJpegTakenDate(metadata);
	}

	public static Calendar getJpegTakenDate(Metadata metadata) throws IOException, ImageProcessingException {
		try {
			ExifSubIFDDirectory dir = metadata.getDirectory(ExifSubIFDDirectory.class);
			Calendar cal = Calendar.getInstance();
			cal.setTime(dir.getDate(ExifSubIFDDirectory.TAG_DATETIME_ORIGINAL));
			return cal;
		} catch (NullPointerException ex) {
			return null;
		}
	}

	public static double getJpegLongitude(String filePath) throws IOException, ImageProcessingException {
		File jpegFile = new File(filePath);
		Metadata metadata = ImageMetadataReader.readMetadata(jpegFile);
		return getJpegLongitude(metadata);
	}

	public static double getJpegLongitude(Metadata metadata) throws IOException, ImageProcessingException {
		try {
			GpsDirectory dir = metadata.getDirectory(GpsDirectory.class);			
			Rational[] longitudeArray = dir.getRationalArray(GpsDirectory.TAG_GPS_LONGITUDE);
			double longitude = longitudeArray[0].doubleValue() + longitudeArray[1].doubleValue()/60 + longitudeArray[2].doubleValue()/3600;
			if (dir.getString(GpsDirectory.TAG_GPS_LONGITUDE_REF).equals("W")) {
				return - longitude;
			}
			else {
				return longitude;
			}
		} catch (NullPointerException ex) {
			throw new NullPointerException("No GPS data was found for this image");
		}
	}

	public static double getJpegLatitude(String filePath) throws IOException, ImageProcessingException {
		File jpegFile = new File(filePath);
		Metadata metadata = ImageMetadataReader.readMetadata(jpegFile);
		return getJpegLatitude(metadata);
	}

	public static double getJpegLatitude(Metadata metadata) throws IOException, ImageProcessingException {
		try {
			GpsDirectory dir = metadata.getDirectory(GpsDirectory.class);
			Rational[] latitudeArray = dir.getRationalArray(GpsDirectory.TAG_GPS_LATITUDE);
			double latitude = latitudeArray[0].doubleValue() + latitudeArray[1].doubleValue()/60 + latitudeArray[2].doubleValue()/3600;
			if (dir.getString(GpsDirectory.TAG_GPS_LATITUDE_REF).equals("S")) {
				return - latitude;
			}
			else {
				return latitude;
			}
		} catch (NullPointerException ex) {
			throw new NullPointerException("No GPS data was found for this image");
		}
	}
}
