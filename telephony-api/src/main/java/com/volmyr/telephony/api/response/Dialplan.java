package com.volmyr.telephony.api.response;

import com.google.auto.value.AutoValue;

/**
 * Dialplan info.
 */
@AutoValue
public abstract class Dialplan {

  public abstract String getContext();

  public abstract String getExtension();

  public static Builder builder() {
    return new AutoValue_Dialplan.Builder();
  }

  @AutoValue.Builder
  public abstract static class Builder {

    public abstract Builder setContext(String newContext);

    public abstract Builder setExtension(String newExtension);

    public abstract Dialplan build();
  }
}
