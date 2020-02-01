/**
 * Copyright 2011-2015 GatlingCorp (http://gatling.io)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 		http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jenkinsci.plugins.valgrind.steps;

import hudson.DescriptorExtensionList;
import hudson.Extension;
import hudson.util.FormValidation;
import jenkins.model.Jenkins;

import org.jenkinsci.plugins.workflow.steps.AbstractStepDescriptorImpl;
import org.jenkinsci.plugins.workflow.steps.AbstractStepImpl;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.QueryParameter;

import javax.annotation.Nonnull;
import javax.servlet.ServletException;

import java.io.IOException;
import java.io.Serializable;

import org.jenkinsci.plugins.valgrind.ValgrindBuilder.ValgrindTool;
import org.jenkinsci.plugins.valgrind.config.ValgrindBuilderConfig;

/**
 * Publisher for valgrind reports.
 */
public class ValgrindBuilderStep extends AbstractStepImpl implements Serializable {
	private static final long serialVersionUID = 2920077056924894969L;

	private ValgrindBuilderConfig valgrindBuilderConfig;

    @DataBoundConstructor
	public ValgrindBuilderStep(String valgrindExecutable,
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
		valgrindBuilderConfig = new ValgrindBuilderConfig(
			valgrindExecutable.trim(),
			workingDirectory.trim(),
			includePattern.trim(),
			excludePattern,
			outputDirectory.trim(),
			outputFileEnding.trim(),
			programOptions,
			tool,
			valgrindOptions,
			ignoreExitCode,
			traceChildren,
			childSilentAfterFork,
			generateSuppressions,
			suppressionFiles,
			removeOldReports
		);
	}

    @Extension
    public static final class DescriptorImpl extends AbstractStepDescriptorImpl {
        public DescriptorImpl()
		{
		    super(ValgrindBuilderStepExecution.class);
		}

        @Override
        public String getFunctionName() {
            return "runValgrind";
        }

        @Nonnull
        @Override
        public String getDisplayName() {
            return "Run valgrind";
        }

		public static DescriptorExtensionList<ValgrindTool,ValgrindTool.ValgrindToolDescriptor> getToolDescriptors()
		{
			return Jenkins.getInstance().<ValgrindTool,ValgrindTool.ValgrindToolDescriptor>getDescriptorList(ValgrindTool.class);
		}

		public FormValidation doCheckIncludePattern(@QueryParameter String includePattern) throws IOException, ServletException
		{
			if (includePattern.length() == 0)
				return FormValidation.error("Please set a pattern");

			return FormValidation.ok();
		}

		public FormValidation doCheckOutputFileEnding(@QueryParameter String value) throws IOException, ServletException
		{
			if (value.length() == 0)
				return FormValidation.error("Please set a file ending for generated xml reports");
			if (value.charAt(0) != '.' )
				return FormValidation.warning("File ending does not start with a dot");

			return FormValidation.ok();
		}
    }

    public ValgrindBuilderConfig getValgrindBuilderConfig()
    {
        return valgrindBuilderConfig;
    }

    public void setValgrindBuilderConfig(ValgrindBuilderConfig valgrindBuilderConfig)
    {
        this.valgrindBuilderConfig = valgrindBuilderConfig;
    }


	public String getValgrindExecutable() {
		return valgrindBuilderConfig.valgrindExecutable;
	}

	public String getWorkingDirectory() {
		return valgrindBuilderConfig.workingDirectory;
	}

	public String getIncludePattern() {
		return valgrindBuilderConfig.includePattern;
	}

	public String getExcludePattern() {
		return valgrindBuilderConfig.excludePattern;
	}

	public String getOutputDirectory() {
		return valgrindBuilderConfig.outputDirectory;
	}

	public String getOutputFileEnding() {
		return valgrindBuilderConfig.outputFileEnding;
	}

	public String getProgramOptions() {
		return valgrindBuilderConfig.programOptions;
	}

	public ValgrindTool getTool() {
		return valgrindBuilderConfig.tool;
	}

	public String getValgrindOptions() {
		return valgrindBuilderConfig.valgrindOptions;
	}

	public boolean isIgnoreExitCode() {
		return valgrindBuilderConfig.ignoreExitCode;
	}

	public boolean isTraceChildren() {
		return valgrindBuilderConfig.traceChildren;
	}

	public boolean isChildSilentAfterFork() {
		return valgrindBuilderConfig.childSilentAfterFork;
	}

	public boolean isGenerateSuppressions() {
		return valgrindBuilderConfig.generateSuppressions;
	}

	public String getSuppressionFiles() {
		return valgrindBuilderConfig.suppressionFiles;
	}

	public boolean isRemoveOldReports() {
		return valgrindBuilderConfig.removeOldReports;
	}

}
