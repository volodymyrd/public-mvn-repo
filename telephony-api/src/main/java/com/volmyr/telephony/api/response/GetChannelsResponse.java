package com.volmyr.telephony.api.response;

import com.google.auto.value.AutoValue;
import java.util.List;

/**
 * Response for getting all channels.
 */
@AutoValue
public abstract class GetChannelsResponse {

  public abstract FullListMapResponse getBasicResponse();

  public abstract List<Channel> getChannels();

  public static Builder builder() {
    return new AutoValue_GetChannelsResponse.Builder();
  }

  @AutoValue.Builder
  public abstract static class Builder {

    public abstract Builder setBasicResponse(FullListMapResponse newBasicResponse);

    public abstract Builder setChannels(List<Channel> newChannels);

    public abstract GetChannelsResponse build();
  }
}
