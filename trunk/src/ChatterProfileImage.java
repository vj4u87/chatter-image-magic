
public class ChatterProfileImage implements Downloadable {
	
	String smallPhotoUrl;
	String fullPhotoUrl;
	String userId;
	
	public ChatterProfileImage(String s, String f, String id) {
		smallPhotoUrl = s;
		fullPhotoUrl = f;
		userId = id;
	}
	
	public String getFileName() {
		return userId;
	}
	
	public String getUrl() {
		return smallPhotoUrl;
	}
	
	public String getUserId() {
		return null;
	}
	
	public String getUsername() {
		return null;
	}
	
	public String getFullName() {
		return null;
	}
	
	public String getSmallPhotoUrl() {
		return smallPhotoUrl;
	}
	
	public String getFullPhotoUrl() {
		return null;
	}
}
