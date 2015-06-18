package org.lucene;

import java.io.File;
import java.io.FileReader;
import java.text.SimpleDateFormat;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.NumericField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

public class MyCreateIndex {

	public static void main(String[] args) {
		Directory directory = null;
		IndexWriter writer = null;
		try {
			directory = FSDirectory.open(new File("/home/hadoop/index"));
			writer = new IndexWriter(directory, new IndexWriterConfig(Version.LUCENE_35, new StandardAnalyzer(Version.LUCENE_35)));
			writer.deleteAll();
			File file = new File("/home/hadoop/test");
			Document doc = null;
			for(File f:file.listFiles()) {
				doc = new Document();
				doc.add(new Field("content",new FileReader(f)));
				doc.add(new Field("filename",f.getName(),Field.Store.YES,Field.Index.NOT_ANALYZED));
				doc.add(new Field("path",f.getAbsolutePath(),Field.Store.YES,Field.Index.ANALYZED));
//				doc.add(new NumericField("date",Field.Store.YES,true).setLongValue(f.lastModified()));
				String d = f.lastModified()+"";
				doc.add(new Field("date",d,Field.Store.YES,Field.Index.ANALYZED));
				doc.add(new NumericField("size",Field.Store.YES,true).setIntValue((int)(f.length()/1024)));
				writer.addDocument(doc);
				writer.commit();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(writer!=null) {
					writer.close();
				}
				if(directory!=null){
					directory.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
