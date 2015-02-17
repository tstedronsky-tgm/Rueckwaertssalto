package anil_stedronsky;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;

/**
 * Die Klasse ist zuständig um in die jeweiligen Formate zu exportieren 
 * @author Stedronsky Thomas
 * @version 2015-02-17
 */
public class Exporter {
	private Datenbank db;
	
	/**
	 * Konstruktor in der die DB übergeben wird
	 * @param db Die DB
	 */
	public Exporter(Datenbank db){
		this.db=db;
	}

	/**
	 * Generiert ein fertiges RM und speichert es in ein ein .txt File
	 */
	public void createRMTxt(){
		ArrayList<Tabelle> tabs = db.getTables();
		String rm="";
		for(int i=0; i<tabs.size();++i){
			ArrayList<Attribut> att = tabs.get(i).getAttributs();
			rm+=tabs.get(i).getName()+"(";
			for(int j=0; j<att.size();++j){
				if((j+1)==att.size()){
					if(att.get(j).isInstanceOfPK()){
						rm+=att.get(j).getName()+"<<PK>>";
					}
					else {
						rm+=att.get(j).getName();
					}
				}
				else {
					if(att.get(j).isInstanceOfPK()){
						rm+=att.get(j).getName()+"<<PK>>, ";
					}
					else {
						rm+=att.get(j).getName()+", ";
					}
				}
			}
			rm+=")\n";
		}
		
		//Random Acces File im txt Format
		RandomAccessFile f = null;
		try {
			f = new RandomAccessFile("rm.txt", "rwd");
		} catch (FileNotFoundException  e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			f.writeBytes(rm);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Generiert ein fertiges RM und speichert es in ein Html File
	 */
	public void createRMHtml(){
		ArrayList<Tabelle> tabs = db.getTables();
		String rm="<html><head><title>RM"+db.getName()+"</title></head><body>";
		for(int i=0; i<tabs.size();++i){
			ArrayList<Attribut> att = tabs.get(i).getAttributs();
			rm+=tabs.get(i).getName()+"(";
			for(int j=0; j<att.size();++j){
				if((j+1)==att.size()){
					if(att.get(j).isInstanceOfPK()){
						rm+="<u>"+att.get(j).getName()+"</u>";
					}
					else {
						rm+=att.get(j).getName();
					}
				}
				else {
					if(att.get(j).isInstanceOfPK()){
						rm+="<u>"+att.get(j).getName()+"</u>, ";
					}
					else {
						rm+=att.get(j).getName()+", ";
					}
				}
			}
			rm+=")<br>";
		}
		rm +="</body>\n</html>";
		//RAF in HTML Format
		RandomAccessFile f = null;
		try {
			f = new RandomAccessFile("rm.html", "rwd");
		} catch (FileNotFoundException  e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			f.writeBytes(rm);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Erzeugt ein Dot File mit dem ein ERD erzeugt wird.
	 * 
	 * mittels GrpahViz kann dieses geöffnet werden
	 */
	public void createER(){
		ArrayList<Tabelle> tabs = db.getTables();
		String dot="digraph G {\nsize=\"40,40\"\n"; //Größe
		for(int i=0; i<tabs.size();++i){
			dot +=tabs.get(i).getName() + " [shape=box];\n"; //Stellt die Ausgabe auf ein Rechteck (Box) Objekttyp
			ArrayList<Attribut> att=tabs.get(i).getAttributs();
			for(int j=0; j<att.size();++j){ 
				dot +=tabs.get(i).getName()+" -> "+att.get(j).getName()+";\n"; //Attribute
			}
		}

		dot +="}";
		
		//dot File mit dem dann mit GraphViz das ER angezeigt werden kann.
		RandomAccessFile f = null;
		try {
			f = new RandomAccessFile("er.dot", "rwd");
		} catch (FileNotFoundException  e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			f.writeBytes(dot);
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println(dot);


	}
}