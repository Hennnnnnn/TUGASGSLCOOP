package Model;

public class Team extends Model {
	public String namaTim;
	
	public Team(Integer idTim, String namaTim) {
		super(idTim);
		this.namaTim = namaTim;
	}
}
