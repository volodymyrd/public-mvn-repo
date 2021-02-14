package com.volmyr.telephony.api.requests;

import com.google.auto.value.AutoValue;
import javax.annotation.Nullable;

/**
 * Request for start snooping a channel.
 */
@AutoValue
public abstract class SnoopChannelRequest {

  public abstract String getChannelId();

  @Nullable
  public abstract String getSpy();

  @Nullable
  public abstract String getWhisper();

  @Nullable
  public abstract String getApp();

  @Nullable
  public abstract String getAppArgs();

  public static Builder builder() {
    return new AutoValue_SnoopChannelRequest.Builder();
  }

  @AutoValue.Builder
  public abstract static class Builder {

    public abstract Builder setChannelId(String newChannelId);

    public abstract Builder setSpy(String newSpy);

    public abstract Builder setWhisper(String newWhisper);

    public abstract Builder setApp(String newApp);

    public abstract Builder setAppArgs(String newAppArgs);

    public abstract SnoopChannelRequest build();
  }
}
