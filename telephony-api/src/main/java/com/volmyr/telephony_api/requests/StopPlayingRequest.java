package com.volmyr.telephony_api.requests;

import com.google.auto.value.AutoValue;

/**
 * Request for stopping playing a media.
 */
@AutoValue
public abstract class StopPlayingRequest {

  public abstract String getPlaybackId();

  public static Builder builder() {
    return new AutoValue_StopPlayingRequest.Builder();
  }

  @AutoValue.Builder
  public abstract static class Builder {

    public abstract Builder setPlaybackId(String newPlaybackId);

    public abstract StopPlayingRequest build();
  }
}
