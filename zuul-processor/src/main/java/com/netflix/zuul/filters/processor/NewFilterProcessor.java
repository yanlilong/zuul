package com.netflix.zuul.filters.processor;

import com.google.common.annotations.VisibleForTesting;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.tools.FileObject;
import javax.tools.StandardLocation;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.NoSuchFileException;
import java.util.*;

@SupportedAnnotationTypes("FILTER_TYPE")
public final class NewFilterProcessor extends AbstractProcessor {
    static final String FILTER_TYPE = "com.netflix.zuul.Filter";
    private final Set<String> annotatedElements = new HashSet<>();

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        Set<? extends Element> annotated = roundEnv.getElementsAnnotatedWith(processingEnv.getElementUtils().getTypeElement(FILTER_TYPE));
        for (Element element : annotated) {
            if (element.getModifiers().contains(Modifier.ABSTRACT)) {
                continue;
            }
            annotatedElements.add(processingEnv.getElementUtils().getBinaryName((TypeElement) element).toString());
        }
        if (roundEnv.processingOver()) {
            try {
                addNewClasses(processingEnv.getFiler(), annotatedElements);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } finally {
                annotatedElements.clear();
            }
        }
        return true;
    }

    static void addNewClasses(Filer filer, Collection<String> elements) throws IOException {
        String resourceName = "META-INF/zuul/allfilters";
        List<String> existing = Collections.emptyList();
        try {
            FileObject existingFilters = filer.getResource(StandardLocation.CLASS_OUTPUT, "", resourceName);
            try (InputStream is = existingFilters.openInputStream();
                 InputStreamReader reader = new InputStreamReader(is, StandardCharsets.UTF_8)) {
                existing = readResourceFile(reader);
            }
        } catch (FileNotFoundException | NoSuchFileException e) {

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        int sizeBefore = existing.size();
        Set<String> existingSet = new LinkedHashSet<>();
        List<String> newElements = new ArrayList<>(existingSet);
        for (String element : elements) {
            if (existingSet.add(element)) {
                newElements.add(element);
            }
        }
        if (newElements.size() == sizeBefore) {
            return;
        }
        newElements.sort(String::compareTo);

        FileObject dest = filer.createResource(StandardLocation.CLASS_OUTPUT, "", resourceName);
        try (OutputStream os = dest.openOutputStream();
             OutputStreamWriter osw = new OutputStreamWriter(os, StandardCharsets.UTF_8)) {
            writeResourceFile(osw, newElements);
        }
    }

    @VisibleForTesting
    static List<String> readResourceFile(Reader reader) throws IOException {
        BufferedReader br = new BufferedReader(reader);
        String line;
        List<String> lines = new ArrayList<>();
        while ((line = br.readLine()) != null) {
            if (line.trim().isEmpty()) {
                continue;
            }
            lines.add(line);
        }
        return Collections.unmodifiableList(lines);
    }

    static void writeResourceFile(Writer writer, Collection<?> elements) throws IOException {
        BufferedWriter bw = new BufferedWriter(writer);
        for (Object element : elements) {
            bw.write(String.valueOf(element));
            bw.newLine();
        }
        bw.flush();
    }
}
