package aifinalproject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


public class FileManager {
	
	private BufferedReader r;
	private BufferedWriter w;
	private File data;
	

	public FileManager(){
		
		data = new File("data");
		
	}
	
	public FileManager(File data){
		
		this.data = data;
	}
	
	public void writeOut(String text){
		
		try {
			w = new BufferedWriter(new FileWriter(data));
			
			w.write(text);
			w.flush();
			w.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
