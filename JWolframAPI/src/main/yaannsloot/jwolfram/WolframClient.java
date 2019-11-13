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

/**
 * Controls queries sent to the Wolfram|Alpha API endpoint. Must have a valid
 * API key from Wolfram|Alpha to function.
 */
public class WolframClient {

	private String appid;

	/**
	 * Constructs a new instance of {@linkplain WolframClient}. Note that this
	 * constructor will block whatever thread it is called on to verify with
	 * Wolfram|Alpha that the provided API key is valid.
	 * 
	 * @param appid The API key to use with this client. API keys are obtained at
	 *              <a href=
	 *              "http://developer.wolframalpha.com/">http://developer.wolframalpha.com/</a>
	 * @throws InvalidAppidException If the provided API key is invalid
	 */
	public WolframClient(String appid) throws InvalidAppidException {
		try {
			File raw = File.createTempFile("jwdata", ".xml");
			FileUtils.copyURLToFile(new URL("http://api.wolframalpha.com/v2/query?appid=" + appid), raw);
			Document doc = Jsoup.parse(FileUtils.readFileToString(raw, "UTF-8"), "", Parser.xmlParser());
			if (doc.getElementsByTag("error").size() > 0) {
				if (doc.getElementsByTag("error").get(0).getElementsByTag("code").size() > 0) {
					if (!doc.getElementsByTag("error").get(0).getElementsByTag("code").get(0).text().equals("1000"))
						throw new InvalidAppidException(appid);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.appid = appid;
	}

	/**
	 * Queries the Wolfram|Alpha API endpoint with the provided string.
	 * <p>
	 * Note that this method is blocking. It will block the current thread to
	 * complete the query request, which can cause a noticeable delay. If you need
	 * an asynchronous solution, use
	 * {@link WolframClient#queueQuery(String, Consumer) queueQuery()} or
	 * {@link WolframClient#submitQuery(String) submitQuery()}
	 * 
	 * @param query The query string to pass to the query request
	 * @return A {@linkplain QueryResult} object representing the results of the
	 *         query
	 */
	public QueryResult completeQuery(String query) {
		QueryResult result = null;
		try {
			File raw = File.createTempFile("jwdata", ".xml");
			FileUtils.copyURLToFile(new URL("http://api.wolframalpha.com/v2/query?input=" + query + "&appid=" + appid),
					raw);
			result = new QueryResult(raw);
			raw.delete();
		} catch (UnrecognisedQueryDataException | IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * Queries the Wolfram|Alpha API endpoint with the provided string.
	 * <p>
	 * Note that this method is asynchronous. You should handle the query result in
	 * the {@linkplain Consumer} object that you pass with your query string. If you
	 * need an asynchronous solution involving the use of {@linkplain Future}
	 * objects, use {@link WolframClient#submitQuery(String) submitQuery()}. For
	 * blocking solutions, use {@link WolframClient#completeQuery(String)
	 * completeQuery()}.
	 * 
	 * @param query          The query string to pass to the query request
	 * @param resultConsumer The {@linkplain Consumer} object that will be used to
	 *                       handle the {@linkplain QueryResult} object once it
	 *                       becomes available
	 */
	public void queueQuery(String query, Consumer<QueryResult> resultConsumer) {
		ForkJoinPool.commonPool().execute(() -> {
			try {
				File raw = File.createTempFile("jwdata", ".xml");
				FileUtils.copyURLToFile(
						new URL("http://api.wolframalpha.com/v2/query?input=" + query + "&appid=" + appid), raw);
				resultConsumer.accept(new QueryResult(raw));
				raw.delete();
			} catch (UnrecognisedQueryDataException | IOException e) {
				e.printStackTrace();
				resultConsumer.accept(null);
			}
		});
	}

	/**
	 * Queries the Wolfram|Alpha API endpoint with the provided string.
	 * <p>
	 * Note that this method is asynchronous. This method returns a
	 * {@linkplain Future} object representing the query task. You must use this to
	 * retrieve the resulting {@linkplain QueryResult} object returned once the task
	 * is complete. If you need an asynchronous solution involving the use of a
	 * {@linkplain Consumer}, please use
	 * {@link WolframClient#queueQuery(String, Consumer) queueQuery()}. For blocking
	 * solutions, use {@link WolframClient#completeQuery(String) completeQuery()}.
	 * 
	 * @param query The query string to pass to the query request
	 * @return A {@linkplain Future} object representing the query task
	 */
	public Future<QueryResult> submitQuery(String query) {
		return (Future<QueryResult>) ForkJoinPool.commonPool().submit(() -> {
			try {
				File raw = File.createTempFile("jwdata", ".xml");
				FileUtils.copyURLToFile(
						new URL("http://api.wolframalpha.com/v2/query?input=" + query + "&appid=" + appid), raw);
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
