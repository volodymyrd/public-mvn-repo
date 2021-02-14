package com.volmyr.telephony.api.response;

import com.google.auto.value.AutoValue;
import javax.annotation.Nullable;

/**
 * Response for creating a channel.
 */
@AutoValue
public abstract class CreateChannelResponse {

  public abstract FullMapResponse getBasicResponse();

  @Nullable
  public abstract String getChannelId();

  public static Builder builder() {
    return new AutoValue_CreateChannelResponse.Builder();
  }

  @AutoValue.Builder
  public abstract static class Builder {

    public abstract Builder setBasicResponse(FullMapResponse newBasicResponse);

    public abstract Builder setChannelId(String newChannelId);

    public abstract CreateChannelResponse build();
  }
}
