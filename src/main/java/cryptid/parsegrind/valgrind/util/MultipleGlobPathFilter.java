package cryptid.parsegrind.valgrind.util;

import java.nio.file.DirectoryStream;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class MultipleGlobPathFilter implements DirectoryStream.Filter<Path> {
    private final List<PathMatcher> matchers;

    public MultipleGlobPathFilter(final List<String> globs) {
        this.matchers = globs.stream()
                .map(pattern -> "glob:" + pattern)
                .map(syntaxAndPattern -> FileSystems.getDefault().getPathMatcher(syntaxAndPattern))
                .collect(toList());
    }

    @Override
    public boolean accept(final Path entry) {
        final boolean result = matchers.stream()
                .anyMatch(matcher -> matcher.matches(entry));

        return result;
    }
}
