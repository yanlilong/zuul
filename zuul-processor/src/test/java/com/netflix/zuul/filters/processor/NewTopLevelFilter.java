package com.netflix.zuul.filters.processor;

import com.netflix.zuul.Filter;
import com.netflix.zuul.filters.FilterType;

@Filter(order = 20, type = FilterType.INBOUND)
final class NewTopLevelFilter extends NewTestFilter {
    @Filter(order = 21, type = FilterType.INBOUND)
    static final class NewStaticSubclassFilter extends NewTestFilter {
    }

    @Filter(order = 22, type = FilterType.INBOUND)
    static abstract class NewAbstractSubclassFilter extends NewTestFilter {
    }

    @Filter(order = 23, type = FilterType.INBOUND)
    final class NewSubclassFilter extends NewTestFilter {
    }

    static {
        // This should be ignored by the processor, since it is private.
        // See https://bugs.openjdk.java.net/browse/JDK-6587158
        @SuppressWarnings("unused")
        @Filter(order = 23, type = FilterType.INBOUND)
        final class NewMethodClassFilter {
        }

    }
}
@Filter(order = 24, type = FilterType.INBOUND)
final class NewOuterClassFilter extends NewTestFilter {}