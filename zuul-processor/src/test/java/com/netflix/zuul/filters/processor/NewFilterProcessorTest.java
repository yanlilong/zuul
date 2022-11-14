package com.netflix.zuul.filters.processor;

import com.google.common.truth.Truth;
import com.netflix.zuul.StaticFilterLoader;
import com.netflix.zuul.filters.ZuulFilter;
import com.netflix.zuul.filters.processor.override.NewSubpackageFilter;
import com.netflix.zuul.filters.processor.override.SubpackageFilter;
import com.netflix.zuul.filters.processor.subpackage.NewOverrideFilter;
import com.netflix.zuul.filters.processor.subpackage.OverrideFilter;
import org.junit.jupiter.api.Test;

import java.util.Collection;

public class NewFilterProcessorTest {
    @Test
    void allFileterClassedRecorded() throws Exception {
        Collection<Class<ZuulFilter<?, ?>>> filter = StaticFilterLoader.loadFilterTypesFromResources(getClass().getClassLoader());
        Truth.assertThat(filter).containsExactly(
                OuterClassFilter.class,
                NewOuterClassFilter.class,
                NewTopLevelFilter.class,
                TopLevelFilter.class,
                NewTopLevelFilter.NewStaticSubclassFilter.class,
                NewTopLevelFilter.NewSubclassFilter.class,
                TopLevelFilter.StaticSubclassFilter.class,
                TopLevelFilter.SubclassFilter.class,
                OverrideFilter.class,
                SubpackageFilter.class,
                NewOverrideFilter.class,
                NewSubpackageFilter.class
                );
    }
}