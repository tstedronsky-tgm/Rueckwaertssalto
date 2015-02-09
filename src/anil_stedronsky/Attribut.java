package anil_stedronsky;

/**
 * Attribut mit Eigenschaft
 * @author Stedronsky Thomas
 * @version 2015-02-09
 */
public class Attribut {
	private String att="";
	private Datenbank db;
	
	/**
	 * Konstruktor
	 * @param Datenbank
	 * @param Attrbutname
	 */
	public Attribut(Datenbank db, String att){
		this.db=db;
		this.att=att;
	}
}