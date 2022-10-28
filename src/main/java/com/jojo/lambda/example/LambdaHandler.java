package com.jojo.lambda.example;

import java.io.InputStream;
import java.io.OutputStream;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;

import io.avaje.inject.BeanScope;

public final class LambdaHandler implements RequestStreamHandler {

  private static final EventHandler handler = BeanScope.builder().build().get(EventHandler.class);

  @Override
  public void handleRequest(InputStream inputStream, OutputStream outputStream, Context context) {
    handler.handleRequest(inputStream, outputStream, context);
  }
}
