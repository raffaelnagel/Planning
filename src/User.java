
public class User {

	private String id;
	private String name;
	private String login;
	private String password;
	
	public User(String name, String login, String password){
		this.setName(name);
		this.setLogin(login);
		this.setPassword(password);
	}
	
	public User(){
		new User(null,null,null);
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
}
