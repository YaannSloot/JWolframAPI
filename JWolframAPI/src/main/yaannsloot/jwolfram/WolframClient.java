package main.yaannsloot.jwolfram;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.function.Consumer;

import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;

import main.yaannsloot.jwolfram.entities.QueryResult;
import main.yaannsloot.jwolfram.exceptions.InvalidAppidException;
import main.yaannsloot.jwolfram.exceptions.UnrecognisedQueryDataException;

public class WolframClient {

	private String appid;

	public WolframClient(String appid) throws InvalidAppidException {
		try {
			File raw = File.createTempFile("jwdata", ".xml");
			FileUtils.copyURLToFile(new URL("http://api.wolframalpha.com/v2/query?appid=" + appid), raw);
			Document doc = Jsoup.parse(FileUtils.readFileToString(raw, "UTF-8"), "", Parser.xmlParser());
			if(doc.getElementsByTag("error").size() > 0) {
				if(doc.getElementsByTag("error").get(0).getElementsByTag("code").size() > 0) {
					if(!doc.getElementsByTag("error").get(0).getElementsByTag("code").get(0).text().equals("1000"))
						throw new InvalidAppidException(appid);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.appid = appid;
	}

	public QueryResult completeQuery(String query) {
		QueryResult result = null;
		try {
			File raw = File.createTempFile("jwdata", ".xml");
			FileUtils.copyURLToFile(new URL("http://api.wolframalpha.com/v2/query?input=" + query + "&appid=" + appid), raw);
			result = new QueryResult(raw);
			raw.delete();
		} catch (UnrecognisedQueryDataException | IOException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public void queueQuery(String query, Consumer<QueryResult> resultConsumer) {
		ForkJoinPool.commonPool().execute(() -> {
			try {
				File raw = File.createTempFile("jwdata", ".xml");
				FileUtils.copyURLToFile(new URL("http://api.wolframalpha.com/v2/query?input=" + query + "&appid=" + appid), raw);
				resultConsumer.accept(new QueryResult(raw));
				raw.delete();
			} catch (UnrecognisedQueryDataException | IOException e) {
				e.printStackTrace();
				resultConsumer.accept(null);
			}
		});
	}
	
	public Future<QueryResult> submitQuery(String query) {
		return (Future<QueryResult>)ForkJoinPool.commonPool().submit(() -> {
			try {
				File raw = File.createTempFile("jwdata", ".xml");
				FileUtils.copyURLToFile(new URL("http://api.wolframalpha.com/v2/query?input=" + query + "&appid=" + appid), raw);
				QueryResult result = new QueryResult(raw);
				raw.delete();
				return result;
			} catch (UnrecognisedQueryDataException | IOException e) {
				e.printStackTrace();
				return null;
			}
		});
	}

}
