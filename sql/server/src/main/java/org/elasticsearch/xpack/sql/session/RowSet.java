/*
 * Copyright Elasticsearch B.V. and/or licensed to Elasticsearch B.V. under one
 * or more contributor license agreements. Licensed under the Elastic License;
 * you may not use this file except in compliance with the Elastic License.
 */
package org.elasticsearch.xpack.sql.session;

import org.elasticsearch.action.ActionListener;
import org.elasticsearch.xpack.sql.type.Schema;
import org.elasticsearch.xpack.sql.execution.PlanExecutor;

import java.util.function.Consumer;

/**
 * A set of rows to be returned at one time and a way
 * to get the next set of rows.
 */
public interface RowSet extends RowView {

    boolean hasCurrentRow();

    boolean advanceRow();

    // number or rows in this set; while not really necessary (the return of advanceRow works)
    int size();

    void reset();

    /**
     * The key used by {@link PlanExecutor#nextPage(Cursor, ActionListener)} to fetch the next page.
     */
    Cursor nextPageCursor();

    default void forEachRow(Consumer<? super RowView> action) {
        for (boolean hasRows = hasCurrentRow(); hasRows; hasRows = advanceRow()) {
            action.accept(this);
        }
    }
}
