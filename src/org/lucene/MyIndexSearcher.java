package org.lucene;

import java.io.File;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
//除去例子外还有PhraseQuery和FuzzyQuery，此处不进行举例
public class MyIndexSearcher {
	public static void main(String[] args) {
		Directory directory=null;
		IndexReader reader=null;
		IndexSearcher searcher=null;
		try {
			directory = FSDirectory.open(new File("/home/hadoop/index"));
			reader = IndexReader.open(directory);
			searcher =new IndexSearcher(reader);
			
			//========================================================================================================================================
			//TermQuery:精确匹配
			//========================================================================================================================================
//			Query query = new TermQuery(new Term("path","/home/hadoop/test/scoring.html"));
//			TopDocs tds = searcher.search(query, 10);
//			System.out.println("一共查询了:"+tds.totalHits);
			
			//========================================================================================================================================
			//TermRangeQuery:字符串范围匹配，注意查询的字段类型必须是字符串
			//========================================================================================================================================
//			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//			long timeStart=sdf.parse("2000-07-07 12:30:45").getTime();
//			long timeEnd=sdf.parse("2014-09-20 12:30:45").getTime();
//			System.out.println(timeadd0(timeStart+"")); 
//			System.out.println(timeadd0(timeEnd+""));
//			Query query = new TermRangeQuery("date",timeadd0(timeStart+""),timeadd0(timeEnd+""),true, true);
//			TopDocs tds = searcher.search(query, 10);
//			System.out.println("一共查询了:"+tds.totalHits);
			
			//========================================================================================================================================
			//NumericRangeQuery:数字范围匹配
			//========================================================================================================================================
//			Query query = NumericRangeQuery.newIntRange("size",0, 18,true,true);
//			TopDocs tds = searcher.search(query, 10);
//			System.out.println("一共查询了:"+tds.totalHits);
			
			//========================================================================================================================================
			//PrefixQuery:前缀匹配
			//========================================================================================================================================
//			Query query = new PrefixQuery(new Term("path","/home/hadoop/test/d"));
//			TopDocs tds = searcher.search(query, 10);
//			System.out.println("一共查询了:"+tds.totalHits);
			
			//========================================================================================================================================
			//WildcardQuery:通配符匹配
			//========================================================================================================================================
			//在传入的value中可以使用通配符:?和*,?表示匹配一个字符，*表示匹配任意多个字符
//			Query query = new WildcardQuery(new Term("path","/home/hadoop/test/*2*"));
//			TopDocs tds = searcher.search(query, 10);
//			System.out.println("一共查询了:"+tds.totalHits);
			
			//========================================================================================================================================
			//BooleanQuery:复合搜索:
			//     --BooleanQuery可以连接多个子查询
			//     --Occur.MUST表示必须出现
			//     --Occur.SHOULD表示可以出现
			//     --Occur.MUSE_NOT表示不能出现
			//========================================================================================================================================
//			BooleanQuery query = new BooleanQuery();
//			query.add(new PrefixQuery(new Term("path","/home/hadoop/test/d")), Occur.MUST_NOT);
//			query.add(NumericRangeQuery.newIntRange("size",0, 18,true,true),Occur.MUST);
//			QueryParser parser = new QueryParser(Version.LUCENE_35,"content",new StandardAnalyzer(Version.LUCENE_35));
//			Query q = parser.parse("lucene");
//			query.add(q,Occur.MUST);
//			TopDocs tds = searcher.search(query, 10);
//			System.out.println("一共查询了:"+tds.totalHits);
			
			//========================================================================================================================================
			//QueryParser
			//========================================================================================================================================
//			QueryParser parser = new QueryParser(Version.LUCENE_35,"content",new StandardAnalyzer(Version.LUCENE_35));
			MultiFieldQueryParser parser = new MultiFieldQueryParser(Version.LUCENE_35, new String[]{"path","content"}, new StandardAnalyzer(Version.LUCENE_35));
			Query q = parser.parse("index.html");
			TopDocs tds = searcher.search(q, 10);
			System.out.println("一共查询了:"+tds.totalHits);
			
			//=========================================================================================================================================
			//打印搜索结果
			//=========================================================================================================================================
			ScoreDoc[] sds = tds.scoreDocs;
			for(int i=0;i<sds.length;i++) {
				Document doc = searcher.doc(sds[i].doc);
				System.out.println(sds[i].doc+":"+doc.get("path")+"-->"+doc.get("filename")+"-->"+doc.get("date")+"-->"+doc.get("size"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(searcher!=null) {
					searcher.close();
				}
				if(reader!=null) {
					reader.close();
				}
				if(directory!=null){
					directory.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	public static String timeadd0(String str){
		StringBuffer sb=new StringBuffer();
		for(int i=str.length();i<13;i++){
			sb.append("0");
		}
		sb.append(str);
		return sb.toString();
	}
	
}
