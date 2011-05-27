package com.chatterimagemagic.common;
import java.io.*;

public interface Downloadable {
	public String getUrl();
	
	public File getFile();
	
	public File getDirectory();
}
