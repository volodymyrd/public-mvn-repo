package com.volmyr.telephony_api.response;

import com.google.auto.value.AutoValue;
import javax.annotation.Nullable;

/**
 * Response for start snooping a channel.
 */
@AutoValue
public abstract class SnoopChannelResponse {

  public abstract FullMapResponse getBasicResponse();

  @Nullable
  public abstract String getChannelId();

  public static Builder builder() {
    return new AutoValue_SnoopChannelResponse.Builder();
  }

  @AutoValue.Builder
  public abstract static class Builder {

    public abstract Builder setBasicResponse(FullMapResponse newBasicResponse);

    public abstract Builder setChannelId(String newChannelId);

    public abstract SnoopChannelResponse build();
  }
}
