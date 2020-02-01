package org.jenkinsci.plugins.valgrind.util;

import java.util.AbstractMap.SimpleEntry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;

public class ValgrindSourceResolver {

	private static Logger logger = Logger.getLogger(ValgrindSourceResolver.class.getName());

	protected final List<SimpleEntry<String, String>> substitutionPathList = new ArrayList<SimpleEntry<String, String>>();

	public ValgrindSourceResolver() {
	}
	public ValgrindSourceResolver(String sourcePathSubstitutions) {
		parseSubstitutions(sourcePathSubstitutions);
	}

	public String resolveFilePath(String filePath) {
		if (filePath == null) return null;
		for (SimpleEntry<String,String> substitute: substitutionPathList){
			if (filePath.startsWith(substitute.getKey())){
				return filePath.replaceFirst(substitute.getKey(), substitute.getValue());
			}
		}
		return filePath;
	}
	private void parseSubstitutions(String sourcePathSubstitutions) {
		if (sourcePathSubstitutions != null) {
			String[] sourcePathSubstitutionArray = sourcePathSubstitutions
					.split(",");
			if (sourcePathSubstitutionArray != null) {
				for (String sourcePathSubstitution : sourcePathSubstitutionArray) {
					sourcePathSubstitution = sourcePathSubstitution.trim();
					if (sourcePathSubstitution.isEmpty()) {
						continue;
					}

					String[] sourcePathsPair = sourcePathSubstitution.split(":");
					if (sourcePathsPair.length != 2) {
						logger.warning(String.format("Source path substitution '%s' is incorrect", sourcePathSubstitution));
					} else {
						SimpleEntry<String, String> sourcePathSubstitutionKVP = new SimpleEntry<String, String>(
								sourcePathsPair[0].trim(),
								sourcePathsPair[1].trim());
						logger.fine(
								String.format("Adding source path substitution '%s:%s'",
										sourcePathSubstitutionKVP.getKey(), sourcePathSubstitutionKVP.getValue()));
						substitutionPathList
								.add(sourcePathSubstitutionKVP);
					}
				}
			}
		}

		Comparator<SimpleEntry<String, String>> longestFirstComparator = new Comparator<SimpleEntry<String, String>>() {
			@Override
			public int compare(SimpleEntry<String, String> o1, SimpleEntry<String, String> o2) {
				return Integer.compare(o2.getKey().length(),o1.getKey().length());
			}

		};
		Collections.sort(substitutionPathList, longestFirstComparator);
	}
}
