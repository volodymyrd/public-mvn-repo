package com.volmyr.telephony.api.requests;

import com.google.auto.value.AutoValue;
import javax.annotation.Nullable;

/**
 * Request for adding channels to a bridge.
 */
@AutoValue
public abstract class AddChannelsToBridgeRequest {

  public abstract String getBridgeId();

  public abstract Iterable<String> getChannelIds();

  @Nullable
  public abstract String getRole();

  @Nullable
  public abstract Boolean getAabsorbDTMF();

  @Nullable
  public abstract Boolean getMute();

  public static Builder builder() {
    return new AutoValue_AddChannelsToBridgeRequest.Builder();
  }

  @AutoValue.Builder
  public abstract static class Builder {

    public abstract Builder setBridgeId(String newBridgeId);

    public abstract Builder setChannelIds(Iterable<String> newChannelIds);

    public abstract Builder setRole(String newRole);

    public abstract Builder setAabsorbDTMF(Boolean newAabsorbDTMF);

    public abstract Builder setMute(Boolean newMute);

    public abstract AddChannelsToBridgeRequest build();
  }
}
