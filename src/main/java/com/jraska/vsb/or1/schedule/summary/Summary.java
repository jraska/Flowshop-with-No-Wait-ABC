package com.jraska.vsb.or1.schedule.summary;

import com.jraska.common.ArgumentCheck;

import java.util.Collections;
import java.util.List;

public class Summary {
  //region Fields

  private final List<SummaryItem> _items;

  //endregion

  //region Constants

  public Summary(List<SummaryItem> items) {
    ArgumentCheck.notNull(items);

    _items = Collections.unmodifiableList(items);
  }

  //endregion

  //region Properties

  public List<SummaryItem> getItems() {
    return _items;
  }

  //endregion
}
