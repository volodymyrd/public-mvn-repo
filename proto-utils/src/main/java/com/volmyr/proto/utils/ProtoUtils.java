package com.volmyr.proto.utils;

import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message;
import com.google.protobuf.util.JsonFormat;

import java.io.IOException;
import java.io.Reader;

/**
 * Utils for working with Google proto.
 */
public final class ProtoUtils {

  /**
   * Converts proto {@link Message} to {@link String} <code>JSON</code>.
   */
  public static String toJson(Message message) throws IOException {
    return JsonFormat.printer()
        .includingDefaultValueFields()
        .omittingInsignificantWhitespace()
        .print(message);
  }

  /**
   * Converts {@link String} <code>JSON</code> to proto {@link Message};
   */
  public static Message fromJson(String json, Message.Builder builder)
      throws InvalidProtocolBufferException {
    JsonFormat.parser().merge(json, builder);
    return builder.build();
  }

  public static Message fromJson(Reader reader, Message.Builder builder) throws IOException {
    JsonFormat.parser().merge(reader, builder);
    return builder.build();
  }

  private ProtoUtils() {
  }
}
