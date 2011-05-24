
public class ChatterProfileImage implements Downloadable {
	
	String smallPhotoUrl;
	String fullPhotoUrl;
	String userId;
	String username;
	
	public ChatterProfileImage(String s, String f, String id, String n) {
		smallPhotoUrl = s;
		fullPhotoUrl = f;
		userId = id;
		username = n;
	}
	
	public String getFileName() {
		return userId + '_' + username.toLowerCase().replace('@', '_').replace('.', '_');
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
