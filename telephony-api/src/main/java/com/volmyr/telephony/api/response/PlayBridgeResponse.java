package com.volmyr.telephony.api.response;

import com.google.auto.value.AutoValue;
import javax.annotation.Nullable;

/**
 * Response for playing a media in a bridge.
 */
@AutoValue
public abstract class PlayBridgeResponse {

  public abstract FullMapResponse getBasicResponse();

  @Nullable
  public abstract String getPlaybackId();

  public static Builder builder() {
    return new AutoValue_PlayBridgeResponse.Builder();
  }

  @AutoValue.Builder
  public abstract static class Builder {

    public abstract Builder setBasicResponse(FullMapResponse newBasicResponse);

    public abstract Builder setPlaybackId(String newPlaybackId);

    public abstract PlayBridgeResponse build();
  }
}
