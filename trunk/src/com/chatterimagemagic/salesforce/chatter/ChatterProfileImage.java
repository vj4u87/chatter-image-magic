package com.chatterimagemagic.salesforce.chatter;

import java.io.*;

import com.chatterimagemagic.common.Downloadable;

public class ChatterProfileImage implements Downloadable {
	
	ChatterUser user;
	ChatterImageType type;
	
	public enum ChatterImageType {SMALL, FULL}
	public static String IMAGE_FORMAT = "jpg";
	
	public ChatterProfileImage(ChatterUser u, ChatterImageType t) {
		user = u;
		type = t;
	}
	
	public String getFormat() {
		return IMAGE_FORMAT;
	}
	
	public File getFile() {
		return new File(user.getUserId() + '_' + user.getName().toLowerCase().replace('\'', '_').replace(' ', '_') + "." + getFormat());
	}

	public File getDirectory() {
		return new File(type.toString().toLowerCase()); 
	}
	
	public String getUrl() {
		return type == ChatterImageType.SMALL ? user.getSmallPhotoUrl() : user.getFullPhotoUrl();
	}
	
}
