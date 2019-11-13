package com.yaannsloot.jwolfram.entities;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;

import com.yaannsloot.jwolfram.exceptions.UnrecognisedQueryDataException;

/**
 * Represents a query result from the Wolfram|Alpha API endpoint. Query results
 * are retrieved in xml form and contain elements named pods and subpods. For
 * reference, here is the documentation that covers all of the Wolfram|Alpha
 * API, including the structure of query results: <a href=
 * "http://products.wolframalpha.com/docs/WolframAlpha-API-Reference.pdf">http://products.wolframalpha.com/docs/WolframAlpha-API-Reference.pdf</a>
 */
public class QueryResult {

	private boolean success;
	private boolean error;
	private List<Pod> pods;
	private String version;
	private String datatypes;
	private double timing;
	private int timeoutamount;
	private double parsedtime;
	private boolean parsetimeout;
	private String recalurl;

	private static int safeParseInt(String input) {
		try {
			return Integer.parseInt(input);
		} catch (NumberFormatException e) {
			return 0;
		}
	}

	private static double safeParseDouble(String input) {
		try {
			return Double.parseDouble(input);
		} catch (NumberFormatException | NullPointerException e) {
			return 0;
		}
	}

	/**
	 * Creates an instance of {@linkplain QueryResult}. This will parse an xml file
	 * containing data from a Wolfram|Alpha API query. File data must be in xml
	 * format.
	 * 
	 * @param queryData A {@linkplain File} containing xml data from a completed
	 *                  Wolfram|Alpha API query
	 * @throws UnrecognisedQueryDataException If the file data could not be
	 *                                        interpreted as query data
	 * @throws IOException                    If the file could not be read
	 */
	public QueryResult(File queryData) throws UnrecognisedQueryDataException, IOException {
		Document rawData = Jsoup.parse(FileUtils.readFileToString(queryData, "UTF-8"), "", Parser.xmlParser());
		if (rawData.getElementsByTag("queryresult").size() > 0) {
			this.pods = new ArrayList<Pod>();
			Attributes queryAttributes = rawData.getElementsByTag("queryresult").get(0).attributes();
			Elements pods = rawData.getElementsByTag("pod");
			this.success = (queryAttributes.hasKey("success")) ? Boolean.parseBoolean(queryAttributes.get("success"))
					: false;
			this.error = (queryAttributes.hasKey("error")) ? Boolean.parseBoolean(queryAttributes.get("error")) : false;
			this.version = (queryAttributes.hasKey("version")) ? queryAttributes.get("version") : null;
			this.datatypes = (queryAttributes.hasKey("datatypes")) ? queryAttributes.get("datatypes") : null;
			this.timing = (queryAttributes.hasKey("timing")) ? safeParseDouble(queryAttributes.get("timing")) : 0;
			this.timeoutamount = (queryAttributes.hasKey("timedoutpods"))
					? safeParseInt(queryAttributes.get("timedoutpods"))
					: 0;
			this.parsedtime = (queryAttributes.hasKey("parsetiming"))
					? safeParseDouble(queryAttributes.get("parsetiming"))
					: 0;
			this.parsetimeout = (queryAttributes.hasKey("parsetimedout"))
					? Boolean.parseBoolean(queryAttributes.get("parsetimedout"))
					: false;
			this.recalurl = (queryAttributes.hasKey("recalculate")) ? queryAttributes.get("recalculate") : null;
			for (Element pod : pods) {
				List<Subpod> subpods = new ArrayList<Subpod>();
				for (Element subpod : pod.getElementsByTag("subpod")) {
					subpods.add(new Subpod((subpod.hasAttr("title")) ? subpod.attr("title") : null,
							(subpod.getElementsByTag("plaintext").size() > 0)
									? subpod.getElementsByTag("plaintext").get(0).text()
									: null,
							(subpod.getElementsByTag("img").size() > 0)
									? subpod.getElementsByTag("img").get(0).attr("src")
									: null));
				}
				this.pods.add(new Pod((pod.hasAttr("title")) ? pod.attr("title") : null,
						(pod.hasAttr("error")) ? Boolean.parseBoolean(pod.attr("error")) : false,
						(pod.hasAttr("position")) ? safeParseInt(pod.attr("position")) : 0,
						(pod.hasAttr("scanner")) ? pod.attr("scanner") : null,
						(pod.hasAttr("id")) ? pod.attr("id") : null, subpods));
			}
		} else {
			throw new UnrecognisedQueryDataException();
		}
	}

	/**
	 * If the query was successful, this will return true
	 * 
	 * @return A {@linkplain Boolean} representing the success flag from this query
	 */
	public boolean wasSuccess() {
		return success;
	}

	/**
	 * If an error happened in this query, this will return true
	 * 
	 * @return A {@linkplain Boolean} representing the error flag from this query
	 */
	public boolean getError() {
		return error;
	}

	/**
	 * Retrieves a {@linkplain List} of {@linkplain Pod}s representing all of the
	 * pods that were retrieved from this query
	 * 
	 * @return A {@linkplain List} of {@linkplain Pod}s representing the pods from
	 *         this query
	 */
	public List<Pod> getPods() {
		return pods;
	}

	/**
	 * Retrieves the version of the API endpoint used to resolve this query
	 * 
	 * @return A {@linkplain String} representing the version flag from this query
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * Retrieves the datatypes that can be found in this query
	 * 
	 * @return A {@linkplain String} representing the datatypes found in this query
	 */
	public String getDatatypes() {
		return datatypes;
	}

	/**
	 * Retrieves the time in seconds that it took for the API endpoint to retrieve
	 * the results for this query
	 * 
	 * @return A {@linkplain Double} representing the time in seconds it took for
	 *         this query to complete
	 */
	public double getTiming() {
		return timing;
	}

	/**
	 * Retrieves the amount of pods that were discarded due to a timeout in the
	 * query request
	 * 
	 * @return An {@linkplain Integer} representing the amount of pods that are not
	 *         present from this query
	 */
	public int getAmountTimedout() {
		return timeoutamount;
	}

	/**
	 * Retrieves the time it took for the API endpoint to parse the input for this
	 * query
	 * 
	 * @return A {@linkplain Double} representing the time in seconds that it took
	 *         for the API endpoint to interpret the input for this query
	 */
	public double getParseTime() {
		return parsedtime;
	}

	/**
	 * If the query request timed out when attempting to parse the input, this returns true
	 * @return A {@linkplain Boolean} representing the parse timeout flag from this query
	 */
	public boolean didParseTimeout() {
		return parsetimeout;
	}

	/**
	 * If any pods timedout or the parser timedout, this url can be used to retrieve the missing data that was not present in this query
	 * @return A {@linkplain String} representing the recalculate url if it was present in this query
	 */
	public String getRecalculateUrl() {
		return recalurl;
	}

}
