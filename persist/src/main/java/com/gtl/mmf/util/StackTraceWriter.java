/**
 * 
 */
package com.gtl.mmf.util;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @author 08237
 *
 */
public final class StackTraceWriter {
	private static final String EMPTY_STRING = "";
	private StackTraceWriter(){}
	public static String getStackTrace(final Throwable throwable){
		String stackTrace = EMPTY_STRING;
		StringWriter stringWriter = null;
		PrintWriter printWriter = null;
		
		stringWriter = new StringWriter();
		printWriter = new PrintWriter(stringWriter);
		throwable.printStackTrace(printWriter);
		if(stringWriter != null) {
			stackTrace = stringWriter.toString();
		} 
		return stackTrace;
	}
}
