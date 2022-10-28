package com.jojo.lambda.example;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.function.Function;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import com.amazonaws.services.lambda.runtime.events.SQSEvent;

import io.avaje.inject.BeanScope;
import io.avaje.jsonb.Json;
import io.avaje.jsonb.JsonType;
import io.avaje.jsonb.Jsonb;

@Json.Import({SQSEvent.class})
public final class Handler implements RequestStreamHandler {

  private final JsonType<SQSEvent> sqsType;
  private final JsonType<List<String>> strType;
  private final Function<SQSEvent, List<String>> func;

  private Handler() {
    final var scope = BeanScope.builder().build();
    final var jsonb = scope.get(Jsonb.class);
    func = scope.get(Function.class);
    sqsType = jsonb.type(SQSEvent.class);
    strType = jsonb.type(String.class).list();
  }

  @Override
  public void handleRequest(InputStream inputStream, OutputStream outputStream, Context context)
      throws IOException {
    final var logger = context.getLogger();

    final var event = sqsType.fromJson(inputStream);
    logger.log("EVENT TYPE: " + event.getClass().toString());
    logger.log("EVENT TYPE: " + event.getClass().toString());

    strType.toJson(func.apply(event), outputStream);
  }
}
