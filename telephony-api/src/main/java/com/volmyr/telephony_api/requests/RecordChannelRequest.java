package com.volmyr.telephony_api.requests;

import com.google.auto.value.AutoValue;
import javax.annotation.Nullable;

/**
 * Request for a channel recording.
 */
@AutoValue
public abstract class RecordChannelRequest {

  public abstract String getChannelId();

  public abstract String getFileName();

  public abstract String getFileType();

  @Nullable
  public abstract Integer getMaxDurationSeconds();

  @Nullable
  public abstract Integer getMaxSilenceSeconds();

  @Nullable
  public abstract String getIfExists();

  public static Builder builder() {
    return new AutoValue_RecordChannelRequest.Builder();
  }

  @AutoValue.Builder
  public abstract static class Builder {

    public abstract Builder setChannelId(String newChannelId);

    public abstract Builder setFileName(String newFileName);

    public abstract Builder setFileType(String newFileType);

    public abstract Builder setMaxDurationSeconds(Integer newMaxDurationSeconds);

    public abstract Builder setMaxSilenceSeconds(Integer newMaxSilenceSeconds);

    public abstract Builder setIfExists(String newIfExists);

    public abstract RecordChannelRequest build();
  }
}
