package com.volmyr.telephony.api.requests;

import com.google.auto.value.AutoValue;
import java.util.Map;
import javax.annotation.Nullable;

/**
 * Request for creating channel.
 */
@AutoValue
public abstract class CreateChannelRequest {

  public abstract String getEndpoint();

  @Nullable
  public abstract String getExtension();

  @Nullable
  public abstract String getContext();

  @Nullable
  public abstract String getApp();

  @Nullable
  public abstract Map<String, String> getVariables();

  @Nullable
  public abstract String getCallerId();

  public static Builder builder() {
    return new AutoValue_CreateChannelRequest.Builder();
  }

  @AutoValue.Builder
  public abstract static class Builder {

    public abstract Builder setEndpoint(String newEndpoint);

    public abstract Builder setExtension(String newExtension);

    public abstract Builder setContext(String newContext);

    public abstract Builder setApp(String newApp);

    public abstract Builder setVariables(Map<String, String> newVariables);

    public abstract Builder setCallerId(String newCallerId);

    public abstract CreateChannelRequest build();
  }
}
