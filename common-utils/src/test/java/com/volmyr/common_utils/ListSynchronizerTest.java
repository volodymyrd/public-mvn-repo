package com.volmyr.common_utils;

import static org.assertj.core.api.Assertions.assertThat;

import com.google.common.collect.ImmutableList;
import org.junit.Test;

/**
 * Tests for {@link ListSynchronizer}.
 */
public class ListSynchronizerTest {

  static class ListStringSynchronizer extends ListSynchronizer<String> {

    ListStringSynchronizer(ImmutableList<String> original, ImmutableList<String> sync) {
      super(original, sync);
    }

    @Override
    protected boolean contains(String e, ImmutableList<String> list) {
      return list.contains(e);
    }
  }

  private final static ListStringSynchronizer listStringSynchronizer =
      new ListStringSynchronizer(
          ImmutableList.of("10", "5", "7", "1", "8"),
          ImmutableList.of("3", "5", "7", "2"));

  @Test
  public void shouldAdd() {
    assertThat(listStringSynchronizer.getToAdd())
        .containsExactlyElementsOf(ImmutableList.of("10", "1", "8"));
  }

  @Test
  public void shouldDelete() {
    assertThat(listStringSynchronizer.getToDelete())
        .containsExactlyElementsOf(ImmutableList.of("3", "2"));
  }

  @Test
  public void shouldUpdate() {
    assertThat(listStringSynchronizer.getToUpdate())
        .containsExactlyElementsOf(ImmutableList.of("5", "7"));
  }
}
