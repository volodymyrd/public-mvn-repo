package com.volmyr.telephony_api.response;

import com.google.auto.value.AutoValue;
import javax.annotation.Nullable;

/**
 * Response for playing a media in a channel.
 */
@AutoValue
public abstract class PlayChannelResponse {

  public abstract FullMapResponse getBasicResponse();

  @Nullable
  public abstract String getPlaybackId();

  public static Builder builder() {
    return new AutoValue_PlayChannelResponse.Builder();
  }

  @AutoValue.Builder
  public abstract static class Builder {

    public abstract Builder setBasicResponse(FullMapResponse newBasicResponse);

    public abstract Builder setPlaybackId(String newPlaybackId);

    public abstract PlayChannelResponse build();
  }
}
