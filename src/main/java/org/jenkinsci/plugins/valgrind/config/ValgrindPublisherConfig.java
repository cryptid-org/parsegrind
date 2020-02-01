package org.jenkinsci.plugins.valgrind.config;

import java.io.Serializable;

import org.kohsuke.stapler.DataBoundConstructor;

public class ValgrindPublisherConfig implements Serializable
{
	private static final long serialVersionUID = 1335068144678253494L;

	private String pattern = "*.memcheck";
	private String failThresholdInvalidReadWrite;
	private String failThresholdDefinitelyLost;
	private String failThresholdTotal;
	private String unstableThresholdInvalidReadWrite;
	private String unstableThresholdDefinitelyLost;
	private String unstableThresholdTotal;
	private String sourceSubstitutionPaths;
	private boolean publishResultsForAbortedBuilds;
	private boolean publishResultsForFailedBuilds;
	private boolean failBuildOnMissingReports;
	private boolean failBuildOnInvalidReports;

	private static String saveTrim(String s)
	{
		if (s == null)
			return "";

		return s.trim();
	}

	@DataBoundConstructor
	public ValgrindPublisherConfig( String pattern,
			String failThresholdInvalidReadWrite,
			String failThresholdDefinitelyLost,
			String failThresholdTotal,
			String unstableThresholdInvalidReadWrite,
			String unstableThresholdDefinitelyLost,
			String unstableThresholdTotal,
			String sourceSubstitutionPaths,
			boolean publishResultsForAbortedBuilds,
			boolean publishResultsForFailedBuilds,
			boolean failBuildOnMissingReports,
			boolean failBuildOnInvalidReports)
	{
		this.pattern = pattern.trim();
		this.failThresholdInvalidReadWrite = saveTrim(failThresholdInvalidReadWrite);
		this.failThresholdDefinitelyLost = saveTrim(failThresholdDefinitelyLost);
		this.failThresholdTotal = saveTrim(failThresholdTotal);
		this.unstableThresholdInvalidReadWrite = saveTrim(unstableThresholdInvalidReadWrite);
		this.unstableThresholdDefinitelyLost = saveTrim(unstableThresholdDefinitelyLost);
		this.unstableThresholdTotal = saveTrim(unstableThresholdTotal);
		this.sourceSubstitutionPaths = saveTrim(sourceSubstitutionPaths);
		this.publishResultsForAbortedBuilds = publishResultsForAbortedBuilds;
		this.publishResultsForFailedBuilds = publishResultsForFailedBuilds;
		this.failBuildOnMissingReports = failBuildOnMissingReports;
		this.failBuildOnInvalidReports = failBuildOnInvalidReports;
	}

	public ValgrindPublisherConfig()
	{
	}

	public String getPattern()
	{
		return pattern;
	}

	public String getFailThresholdInvalidReadWrite()
	{
		return failThresholdInvalidReadWrite;
	}

	public String getFailThresholdDefinitelyLost()
	{
		return failThresholdDefinitelyLost;
	}

	public String getFailThresholdTotal()
	{
		return failThresholdTotal;
	}

	public String getUnstableThresholdInvalidReadWrite()
	{
		return unstableThresholdInvalidReadWrite;
	}

	public String getUnstableThresholdDefinitelyLost()
	{
		return unstableThresholdDefinitelyLost;
	}

	public String getUnstableThresholdTotal()
	{
		return unstableThresholdTotal;
	}

	public String getSourceSubstitutionPaths(){
		return sourceSubstitutionPaths;
	}

	public boolean isPublishResultsForAbortedBuilds()
	{
		return publishResultsForAbortedBuilds;
	}

	public boolean isPublishResultsForFailedBuilds()
	{
		return publishResultsForFailedBuilds;
	}

	public boolean isFailBuildOnMissingReports()
	{
		return failBuildOnMissingReports;
	}

	public boolean isFailBuildOnInvalidReports()
	{
		return failBuildOnInvalidReports;
	}
}
