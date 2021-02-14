package com.volmyr.telephony.api.requests;

import com.google.auto.value.AutoValue;
import javax.annotation.Nullable;

/**
 * Request continue a channel.
 */
@AutoValue
public abstract class ContinueChannelRequest {

  public abstract String getChannelId();

  @Nullable
  public abstract String getContext();

  @Nullable
  public abstract String getExtension();

  public static Builder builder() {
    return new AutoValue_ContinueChannelRequest.Builder();
  }

  @AutoValue.Builder
  public abstract static class Builder {

    public abstract Builder setChannelId(String newChannelId);

    public abstract Builder setContext(String newContext);

    public abstract Builder setExtension(String newExtension);

    public abstract ContinueChannelRequest build();
  }
}
