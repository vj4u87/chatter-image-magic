
public class ChatterUser {
	
	String smallPhotoUrl;
	String fullPhotoUrl;
	String userId;
	String username;
	String name;
	
	public ChatterUser(String s, String f, String id, String u, String n) {
		smallPhotoUrl = s;
		fullPhotoUrl = f;
		userId = id;
		username = u;
		name = n;
	}
	
	public String getSmallPhotoUrl() {
		return smallPhotoUrl;
	}
	
	public String getFullPhotoUrl() {
		return fullPhotoUrl;
	}
}
