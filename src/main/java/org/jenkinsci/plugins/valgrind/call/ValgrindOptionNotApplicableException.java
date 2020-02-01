package org.jenkinsci.plugins.valgrind.call;

@SuppressWarnings("serial")
public class ValgrindOptionNotApplicableException extends Exception
{
	public ValgrindOptionNotApplicableException(String message)
	{
		super(message);
	}
}
