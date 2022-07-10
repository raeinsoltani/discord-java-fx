package org.ce.ap.discord.common.util.io;

import org.ce.ap.discord.common.entity.boot.ApplicationContext;
import org.ce.ap.discord.common.util.logger.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author Parham Ahmady
 * @since 5/30/2022
 */
public class RootDirectoryIOHelper {

    private static final Logger LOGGER = Logger.getLogger(RootDirectoryIOHelper.class.getName());

    private final String rootDirectory;

    public RootDirectoryIOHelper(ApplicationContext applicationContext) {
        this.rootDirectory = (String) applicationContext.getApplicationProperties().get("files.root.dir");
        if (rootDirectory == null || rootDirectory.isEmpty()) {
            LOGGER.warn("rootDirectory not set at Properties");
            return;
        }
        makeRootDirectory(Paths.get(rootDirectory));
    }

    public RootDirectoryIOHelper(ApplicationContext applicationContext, String subDirectory) {
        this.rootDirectory = (String) applicationContext.getApplicationProperties().get("files.root.dir");
        if (rootDirectory == null || rootDirectory.isEmpty()) {
            LOGGER.warn("rootDirectory not set at Properties");
            return;
        }
        makeRootDirectory(Paths.get(rootDirectory, subDirectory));
    }

    private void makeRootDirectory(Path path) {
        File rootFolder = path.toFile();
        if (!rootFolder.exists()) {
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                LOGGER.error("Cant Make RootDirectory {}", rootFolder.getPath());
            }
        }
    }
}
