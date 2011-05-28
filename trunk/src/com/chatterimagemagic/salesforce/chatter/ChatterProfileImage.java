package com.chatterimagemagic.salesforce.chatter;

import java.io.*;

import com.chatterimagemagic.common.Downloadable;

public class ChatterProfileImage implements Downloadable {
	
	ChatterUser user;
	ChatterImageType type;
	
	public enum ChatterImageType {SMALL, FULL}
	
	public ChatterProfileImage(ChatterUser u, ChatterImageType t) {
		user = u;
		type = t;
	}
	
	public File getLocalFileName() {
		return new File(user.getUserId() + '_' + user.getName().toLowerCase().replace('\'', '_').replace(' ', '_'));
	}

	public File getDirectory() {
		return new File(type.toString().toLowerCase()); 
	}
	
	public String getUrl() {
		return type == ChatterImageType.SMALL ? user.getSmallPhotoUrl() : user.getFullPhotoUrl();
	}
	
}
