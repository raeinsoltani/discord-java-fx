package org.ce.ap.discord.common.util.io;

import org.ce.ap.discord.common.entity.boot.ApplicationContext;
import org.ce.ap.discord.common.util.logger.Logger;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @author Parham Ahmady
 * @since 5/30/2022
 */
public class ObjectIOHelper {

    private static final Logger LOGGER = Logger.getLogger(ObjectIOHelper.class.getName());

    private final String rootDirectory;

    public ObjectIOHelper(ApplicationContext applicationContext) {
        this.rootDirectory = (String) applicationContext.getApplicationProperties().get("files.root.dir");
        if (rootDirectory == null || rootDirectory.isEmpty())
            LOGGER.warn("rootDirectory not set at Properties");
    }

    public List<Object> loadObjectsOfDirectory(String subdirectory) {
        Path path = Paths.get(rootDirectory, subdirectory);
        LOGGER.info("Starts To Load Objects From {}", path.toString());
        File mainFolder = path.toFile();
        if (!mainFolder.exists()) {
            LOGGER.warn("Folder {} Not Exists", path.toString());
            return null;
        }
        List<Object> loadedObjects = new ArrayList<>();
        Arrays.stream(Objects.requireNonNull(mainFolder.listFiles())).forEach(file -> {
                    try (final ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                        loadedObjects.add(ois.readObject());
                    } catch (IOException | ClassNotFoundException e) {
                        LOGGER.error("Cant Load Object: {}", file.getPath());
                    }
                }
        );
        return loadedObjects;
    }

    public Object loadObject(String filePath) {
        Path path = Paths.get(rootDirectory, filePath);
        LOGGER.info("Starts To Load Object From {}", path.toString());
        File file = path.toFile();
        if (!file.exists()) {
            LOGGER.warn("File {} Not Exists", path.toString());
            return null;
        }
        Object loadedObject = null;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            loadedObject = ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            LOGGER.error("Cant Load Object: {}", file.getPath());
        }
        return loadedObject;
    }

    public boolean saveObject(String subDirectory, Object object, String fileName) {
        Path folderPath = Paths.get(rootDirectory, subDirectory);
        if (!Files.exists(folderPath)) {
            LOGGER.info("Folder {} not Exists", folderPath.toString());
            try {
                Files.createDirectories(folderPath);
            } catch (IOException e) {
                LOGGER.error("Cant Make Directory {}", folderPath.toString());
                return false;
            }
        }
        final Path filePath = Paths.get(rootDirectory, subDirectory, fileName);
        if (filePath.toFile().exists())
            //noinspection ResultOfMethodCallIgnored
            filePath.toFile().delete();
        try (final ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath.toFile()))) {
            oos.writeObject(object);
            return true;
        } catch (IOException e) {
            LOGGER.error("Error while saving {}", filePath.toString());
            return false;
        }
    }
}
