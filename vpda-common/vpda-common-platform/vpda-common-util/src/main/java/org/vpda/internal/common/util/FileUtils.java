package org.vpda.internal.common.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.stream.Stream;

public final class FileUtils {
    private FileUtils() {}
    
    public static void deleteDirectory(Path directory) throws IOException {
        if(!directory.toFile().exists()) {
            return;
        }
        try(Stream<Path> walk = Files.walk(directory)){
            walk
            .sorted(Comparator.reverseOrder())
            .map(Path::toFile)
            .forEach(File::delete);
        }
    }

}
