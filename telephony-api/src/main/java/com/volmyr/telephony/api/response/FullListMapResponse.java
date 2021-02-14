package com.volmyr.telephony.api.response;

import com.google.auto.value.AutoValue;
import java.util.List;
import java.util.Map;

/**
 * Basic Ari response as a <code>List of Map<String, Object></code>.
 */
@AutoValue
public abstract class FullListMapResponse {

  public abstract List<Map<String, Object>> getList();

  public static Builder builder() {
    return new AutoValue_FullListMapResponse.Builder();
  }

  @AutoValue.Builder
  public abstract static class Builder {

    public abstract Builder setList(List<Map<String, Object>> newList);

    public abstract FullListMapResponse build();
  }
}
