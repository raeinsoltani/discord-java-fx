package org.ce.ap.discord.common.util.io;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author Parham Ahmady
 * @since 27/5/2022
 */
public class ClassPathLoader {
    private static final String CLASS_PATH_URL = "src/resources/";

    public File getFile(String address) {
        Path resource = Paths.get(CLASS_PATH_URL, address);
        return resource.toFile();
    }
}
