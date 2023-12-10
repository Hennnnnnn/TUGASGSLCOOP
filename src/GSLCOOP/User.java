package GSLCOOP;

public class User extends Model {
	protected String namaUser;
	protected String userNIM;
	
	public User(String namaUser, String userNIM, Integer idTim) {
		super(idTim);
		this.namaUser = namaUser;
		this.userNIM = userNIM;
	}

}
