package com.volmyr.telephony.api.response;

import com.google.auto.value.AutoValue;
import javax.annotation.Nullable;

/**
 * Response for getting a channel.
 */
@AutoValue
public abstract class GetChannelResponse {

  public abstract FullMapResponse getBasicResponse();

  @Nullable
  public abstract Channel getChannel();

  public static Builder builder() {
    return new AutoValue_GetChannelResponse.Builder();
  }

  @AutoValue.Builder
  public abstract static class Builder {

    public abstract Builder setBasicResponse(FullMapResponse newBasicResponse);

    public abstract Builder setChannel(Channel newChannel);

    public abstract GetChannelResponse build();
  }
}
