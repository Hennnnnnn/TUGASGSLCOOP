package Model;

public class User extends Model {
	public String namaUser;
	public String userNIM;
	
	public User(String namaUser, String userNIM, Integer idTim) {
		super(idTim);
		this.namaUser = namaUser;
		this.userNIM = userNIM;
	}

}
