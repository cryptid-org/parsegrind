package cryptid.parsegrind;

import java.util.List;

import static java.util.Collections.unmodifiableList;
import static java.util.Objects.isNull;
import static java.util.Objects.requireNonNull;

public final class Configuration {
    private final List<String> sourceGlobs;

    private final List<String> xmlGlobs;

    private final String baseDirectory;

    private final int linesBefore;

    private final int linesAfter;

    private final String repositoryBaseLink;

    public static Builder builder() {
        return new Builder();
    }

    private Configuration(final Builder builder) {
        this.sourceGlobs = builder.sourceGlobs;
        this.xmlGlobs = builder.xmlGlobs;
        this.baseDirectory = builder.baseDirectory;
        this.linesBefore = builder.linesBefore;
        this.linesAfter = builder.linesAfter;
        this.repositoryBaseLink = builder.repositoryBaseLink;
    }

    public List<String> getSourceGlobs() {
        return sourceGlobs;
    }

    public List<String> getXmlGlobs() {
        return xmlGlobs;
    }

    public String getBaseDirectory() {
        return baseDirectory;
    }

    public int getLinesBefore() {
        return linesBefore;
    }

    public int getLinesAfter() {
        return linesAfter;
    }

    public String getRepositoryBaseLink() {
        return repositoryBaseLink;
    }

    public static final class Builder {
        private List<String> sourceGlobs;

        private List<String> xmlGlobs;

        private String baseDirectory;

        private int linesBefore;

        private int linesAfter;

        private String repositoryBaseLink;

        public Builder sourceGlobs(final List<String> sourceGlobs) {
            requireNonNull(sourceGlobs);

            this.sourceGlobs = unmodifiableList(sourceGlobs);

            return this;
        }

        public Builder xmlGlobs(final List<String> xmlGlobs) {
            requireNonNull(xmlGlobs);

            this.xmlGlobs = unmodifiableList(xmlGlobs);

            return this;
        }

        public Builder baseDirectory(final String baseDirectory) {
            this.baseDirectory = requireNonNull(baseDirectory);

            return this;
        }

        public Builder linesBefore(final int linesBefore) {
            if (linesBefore < 0) {
                throw new IllegalArgumentException("linesBefore must be greater than or equal to 0!");
            }

            this.linesBefore = linesBefore;

            return this;
        }

        public Builder linesAfter(final int linesAfter) {
            if (linesAfter < 0) {
                throw new IllegalArgumentException("linesBefore must be greater than or equal to 0!");
            }

            this.linesAfter = linesAfter;

            return this;
        }

        public Builder repositoryBaseLink(final String repositoryBaseLink) {
            this.repositoryBaseLink = requireNonNull(repositoryBaseLink);

            return this;
        }

        public Configuration build() {
            validateContents();

            return new Configuration(this);
        }

        private void validateContents() {
            checkNonNull(sourceGlobs, "sourceGlobs is unset!");
            checkNonNull(xmlGlobs, "xmlGlobs is unset!");
            checkNonNull(baseDirectory, "baseDirectory is unset!");
        }

        private void checkNonNull(final Object obj, final String message) {
            if (isNull(obj)) {
                throw new NullPointerException(message);
            }
        }
    }
}
