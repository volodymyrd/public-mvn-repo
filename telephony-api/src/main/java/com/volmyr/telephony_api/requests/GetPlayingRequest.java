package com.volmyr.telephony_api.requests;

import com.google.auto.value.AutoValue;

/**
 * Request for getting a media.
 */
@AutoValue
public abstract class GetPlayingRequest {

  public abstract String getPlaybackId();

  public static Builder builder() {
    return new AutoValue_GetPlayingRequest.Builder();
  }

  @AutoValue.Builder
  public abstract static class Builder {

    public abstract Builder setPlaybackId(String newPlaybackId);

    public abstract GetPlayingRequest build();
  }
}
