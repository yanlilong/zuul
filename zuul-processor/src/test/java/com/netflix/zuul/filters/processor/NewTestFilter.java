package com.netflix.zuul.filters.processor;

import com.netflix.zuul.exception.ZuulFilterConcurrencyExceededException;
import com.netflix.zuul.filters.ZuulFilter;
import com.netflix.zuul.message.ZuulMessage;
import io.netty.handler.codec.http.HttpContent;
import rx.Observable;

public abstract class NewTestFilter implements ZuulFilter<ZuulMessage, ZuulMessage> {
    @Override
    public boolean shouldFilter(ZuulMessage msg) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isDisabled() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String filterName() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean overrideStopFilterProcessing() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void incrementConcurrency() throws ZuulFilterConcurrencyExceededException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Observable applyAsync(ZuulMessage input) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void decrementConcurrency() {
        throw new UnsupportedOperationException();
    }

    @Override
    public ZuulMessage getDefaultOutput(ZuulMessage input) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean needsBodyBuffered(ZuulMessage input) {
        throw new UnsupportedOperationException();
    }

    @Override
    public HttpContent processContentChunk(ZuulMessage zuulMessage, HttpContent chunk) {
        throw new UnsupportedOperationException();
    }
}
