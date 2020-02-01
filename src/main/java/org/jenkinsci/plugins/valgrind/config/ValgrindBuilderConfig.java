package org.jenkinsci.plugins.valgrind.config;

import java.io.Serializable;

import org.jenkinsci.plugins.valgrind.ValgrindBuilder.ValgrindTool;
import org.kohsuke.stapler.DataBoundConstructor;

public class ValgrindBuilderConfig implements Serializable
{
	private static final long serialVersionUID = 4498050016131703792L;

	public String valgrindExecutable;
	public String workingDirectory;
	public String includePattern;
	public String excludePattern;
	public String outputDirectory;
	public String outputFileEnding;
	public String programOptions;
	public ValgrindTool tool;
	public String valgrindOptions;
	public boolean ignoreExitCode;
	public boolean traceChildren;
	public boolean childSilentAfterFork;
	public boolean generateSuppressions;
	public String  suppressionFiles;
	public boolean removeOldReports;

	@DataBoundConstructor
	public ValgrindBuilderConfig(String valgrindExecutable,
			String workingDirectory,
			String includePattern,
			String excludePattern,
			String outputDirectory,
			String outputFileEnding,
			String programOptions,
			ValgrindTool tool,
			String valgrindOptions,
			boolean ignoreExitCode,
			boolean traceChildren,
			boolean childSilentAfterFork,
			boolean generateSuppressions,
			String  suppressionFiles,
			boolean removeOldReports)
	{
		this.valgrindExecutable = valgrindExecutable.trim();
		this.workingDirectory = workingDirectory.trim();
		this.includePattern = includePattern.trim();
		this.excludePattern = excludePattern;
		this.outputDirectory = outputDirectory.trim();
		this.outputFileEnding = outputFileEnding.trim();
		this.programOptions = programOptions;
		this.tool = tool;
		this.valgrindOptions = valgrindOptions;
		this.ignoreExitCode = ignoreExitCode;
		this.traceChildren = traceChildren;
		this.childSilentAfterFork = childSilentAfterFork;
		this.generateSuppressions = generateSuppressions;
		this.suppressionFiles = suppressionFiles;
		this.removeOldReports = removeOldReports;
	}
}
