package com.volmyr.common_utils;

import static com.google.common.collect.ImmutableList.toImmutableList;

import com.google.common.collect.ImmutableList;

/**
 * Utility class for two list synchronization.
 */
public abstract class ListSynchronizer<T> {

  private final ImmutableList<T> original;
  private final ImmutableList<T> sync;

  /**
   * Constructor with two {@link ImmutableList}s.
   *
   * <p>The <code>sync</code> list compares with the <code>original</code> list.
   */
  public ListSynchronizer(ImmutableList<T> original, ImmutableList<T> sync) {
    this.original = original;
    this.sync = sync;
  }

  /**
   * Returns list of elements from the <code>sync</code> list, that doesn't present in the
   * <code>original</code> list.
   */
  public ImmutableList<T> getToAdd() {
    return original.stream()
        .filter(e -> !contains(e, sync))
        .collect(toImmutableList());
  }

  /**
   * Returns list of elements present in the both lists.
   */
  public ImmutableList<T> getToUpdate() {
    return original.stream()
        .filter(e -> contains(e, sync))
        .collect(toImmutableList());
  }

  /**
   * Returns list of elements from the <code>original</code> list, that doesn't present in the
   * <code>sync</code> list.
   */
  public ImmutableList<T> getToDelete() {
    return sync.stream()
        .filter(e -> !contains(e, original))
        .collect(toImmutableList());
  }

  public ImmutableList<T> getOriginal() {
    return original;
  }

  public ImmutableList<T> getSync() {
    return sync;
  }

  /**
   * Defines contains method.
   *
   * <p>The simplest implementation is <code>list.contains(e)</code>.
   */
  protected abstract boolean contains(T e, ImmutableList<T> list);
}
