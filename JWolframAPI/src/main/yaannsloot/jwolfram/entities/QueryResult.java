package main.yaannsloot.jwolfram.entities;

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

import main.yaannsloot.jwolfram.exceptions.UnrecognisedQueryDataException;

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
	
	public QueryResult(File queryData) throws UnrecognisedQueryDataException, IOException {
		Document rawData = Jsoup.parse(FileUtils.readFileToString(queryData, "UTF-8"), "", Parser.xmlParser());
		if (rawData.getElementsByTag("queryresult").size() > 0) {
			this.pods = new ArrayList<Pod>();
			Attributes queryAttributes = rawData.getElementsByTag("queryresult").get(0).attributes();
			Elements pods = rawData.getElementsByTag("pod");
			this.success = (queryAttributes.hasKey("success")) ? Boolean.parseBoolean(queryAttributes.get("success")) : false;
			this.error = (queryAttributes.hasKey("error")) ? Boolean.parseBoolean(queryAttributes.get("error")) : false;
			this.version = (queryAttributes.hasKey("version")) ? queryAttributes.get("version") : null;
			this.datatypes = (queryAttributes.hasKey("datatypes")) ? queryAttributes.get("datatypes") : null;
			this.timing = (queryAttributes.hasKey("timing")) ? safeParseDouble(queryAttributes.get("timing")) : 0;
			this.timeoutamount = (queryAttributes.hasKey("timedoutpods")) ? safeParseInt(queryAttributes.get("timedoutpods")) : 0;
			this.parsedtime = (queryAttributes.hasKey("parsetiming")) ? safeParseDouble(queryAttributes.get("parsetiming")) : 0;
			this.parsetimeout = (queryAttributes.hasKey("parsetimedout")) ? Boolean.parseBoolean(queryAttributes.get("parsetimedout")) : false;
			this.recalurl = (queryAttributes.hasKey("recalculate")) ? queryAttributes.get("recalculate") : null;
			for(Element pod : pods) {
				List<Subpod> subpods = new ArrayList<Subpod>();
				for(Element subpod : pod.getElementsByTag("subpod")) {
					subpods.add(new Subpod((subpod.hasAttr("title")) ? subpod.attr("title") : null,
							(subpod.getElementsByTag("plaintext").size() > 0) ? subpod.getElementsByTag("plaintext").get(0).text() : null,
							(subpod.getElementsByTag("img").size() > 0) ? subpod.getElementsByTag("img").get(0).attr("src") : null));
				}
				this.pods.add(new Pod((pod.hasAttr("title")) ? pod.attr("title") : null,
						(pod.hasAttr("error")) ? Boolean.parseBoolean(pod.attr("error")) : false,
						(pod.hasAttr("position")) ? safeParseInt(pod.attr("position")) : 0,
						(pod.hasAttr("scanner")) ? pod.attr("scanner") : null,
						(pod.hasAttr("id")) ? pod.attr("id") : null,
						subpods));
			}
		} else {
			throw new UnrecognisedQueryDataException();
		}
	}

	public boolean wasSuccess() {
		return success;
	}

	public boolean getError() {
		return error;
	}

	public List<Pod> getPods() {
		return pods;
	}

	public String getVersion() {
		return version;
	}

	public String getDatatypes() {
		return datatypes;
	}

	public double getTiming() {
		return timing;
	}

	public int getAmountTimedout() {
		return timeoutamount;
	}

	public double getParseTime() {
		return parsedtime;
	}

	public boolean didParseTimeout() {
		return parsetimeout;
	}

	public String getRecalculateUrl() {
		return recalurl;
	}

}
