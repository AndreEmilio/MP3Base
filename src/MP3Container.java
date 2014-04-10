import java.io.File;
import java.util.Iterator;
import java.util.Vector;


public class MP3Container {
	private Vector<MP3> list;
	private int tagCount;
	
	public MP3Container() {
		list = new Vector<MP3>();
		tagCount = 0;
	}
	
	public MP3Container(Vector<MP3> list) {
		this.list = list;
		tagCount = 0;
	}
	
	public int size() {
		return list.size();
	}
	
	public MP3 get(int i) {
		return list.get(i);
	}
	
	public MP3Container lookFor(MP3Filter filter) {
		Vector<MP3> searchList = new Vector<MP3>();
		
		for (int i = 0; i < size();i++) { 
			  MP3 s = list.get(i); 
			  if (filter.fit(s)) {
	             searchList.add(s);
			  }
	    }
		
		return new MP3Container(searchList);
	}

//	public String getFirst(String word) {
//		return lookFor(word).get(0).getPath();
//	}
	
	public void add(File f,FilterPanel hits) {
		File[] fileArray = f.listFiles();
		int n = fileArray.length;
		
		 for (int i = 0; i<n ;i++) { 
			    if (fileArray[i].isDirectory()) {
			    	add(fileArray[i],hits);
			    } else {
			    	if (fileArray[i].getName().toLowerCase().contains(".mp3")) {
			    		list.add(new MP3(fileArray[i].getPath(),fileArray[i].getName()));
			    	}
			    }
		 }
		 hits.setMax(list.size());
	}
	
	public void add(MP3 mp3,FilterPanel hits) {
		list.add(mp3);
		
		hits.setMax(list.size());
	}
	
	public void refreshTagData() {
		
		Thread t = new Thread() {
			public void run() {
				while (tagCount < list.size()) {
					list.get(tagCount).getData();
					tagCount++;
				}
					}
		    };
		
		t.start();
	}
	
	public Vector<MP3> getList() {
		return list;
	}

	public void setList(Vector<MP3> list) {
		this.list = list;
	}

	public static void print(Vector<MP3> sublist) {
		
		  for (Iterator<MP3> it = sublist.iterator(); it.hasNext();) { 
			  MP3 s = it.next(); 
	          System.out.println(s.getPath()); // "15" noch enthalten 
	      }

	}

	public int getTagCount() {
		return tagCount;
	}
}
