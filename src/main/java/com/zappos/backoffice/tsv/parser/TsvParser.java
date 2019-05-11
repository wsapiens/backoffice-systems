package com.zappos.backoffice.tsv.parser;

import java.io.File;
import java.util.List;

/**
 * Tab Separated Value File Parser
 * @author spark
 *
 * @param <T> entity type
 */
public interface TsvParser<T> {
    /**
     * Parse TSV file
     * @param file input filepath
     * @return list of target entities
     */
    public List<T> parse(File file);
}
