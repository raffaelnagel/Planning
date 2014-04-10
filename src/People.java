
public class People {

	private String id;
	private String name;
	private String code;
	private Login login;
	
	public People(String name, String code){
		this.setName(name);
		this.setCode(code);
		this.setLogin(new Login());
	}
	
	public People(){
		new People(null,null);
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Login getLogin() {
		return login;
	}

	public void setLogin(Login login) {
		this.login = login;
	}
	
	public void setLogin(String user, String password, int permissionLevel) {
		this.login.setUser(user);
		this.login.setPassword(password);
		this.login.setPermissionLevel(permissionLevel);
	}
	
}
