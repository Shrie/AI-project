package aifinalproject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.util.ArrayList;


public class FileManager {
	
	private BufferedReader r;
	private BufferedWriter w;
	private File data;
	

	public FileManager(){
		
		data = new File("data");
		
	}
	
	

	public ArrayList<AFile> getClassiferFiles(){
		
		ArrayList<AFile> files = new ArrayList<AFile>();
		
		File[] f = data.listFiles();
				
		if(f != null)
			for(int i=0; i<f.length; i++)
				if(f[i].getName().contains(".cls"))
					files.add(new AFile(f[i].getPath()));
		
		return files;
		
	}
	
	public class AFile extends File{
		
		public AFile(String f){
			super(f);
			
		}
		
		public String toString(){
			
			return this.getName().replace(".cls", "");
		}
		
	}
	
}
