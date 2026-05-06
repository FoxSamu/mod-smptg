package net.foxboi.summon.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;

interface FileCopyHandler {
    void copy(Path fromPath, Path rootDir) throws IOException;
    void remove(Path path, Path rootDir) throws IOException;

    static FileCopyHandler none() {
        return new FileCopyHandler() {
            @Override
            public void copy(Path fromPath, Path rootDir) throws IOException {
            }

            @Override
            public void remove(Path path, Path rootDir) throws IOException {
            }
        };
    }

    static FileCopyHandler copyTo(Path otherDir) {
        return new FileCopyHandler() {
            @Override
            public void copy(Path from, Path root) throws IOException {
                var relative = root.relativize(from);
                var to = otherDir.resolve(relative);

                Files.createDirectories(to.getParent());
                Files.copy(from, to, StandardCopyOption.REPLACE_EXISTING);
            }

            @Override
            public void remove(Path path, Path root) throws IOException {
                var relative = root.relativize(path);
                var other = otherDir.resolve(relative);
                Files.deleteIfExists(other);
            }
        };
    }

    static FileCopyHandler copyTo(List<Path> otherDirs) {
        return new FileCopyHandler() {
            @Override
            public void copy(Path from, Path root) throws IOException {
                var relative = root.relativize(from);

                for (var otherDir : otherDirs) {
                    var to = otherDir.resolve(relative);
                    Files.createDirectories(to.getParent());
                    Files.copy(from, to, StandardCopyOption.REPLACE_EXISTING);
                }
            }

            @Override
            public void remove(Path path, Path root) throws IOException {
                var relative = root.relativize(path);

                for (var otherDir : otherDirs) {
                    var other = otherDir.resolve(relative);
                    Files.deleteIfExists(other);
                }
            }
        };
    }
}
