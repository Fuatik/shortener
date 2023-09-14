package src.strategy;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileBucket {
    private Path path;

    public FileBucket() {
        try {
            path = Files.createTempFile(Integer.toHexString(hashCode()), ".tmp");
            path.toFile().deleteOnExit();

            Files.deleteIfExists(path);
            Files.createFile(path);
        } catch (IOException ignore) {}
    }

    public long getFileSize() {
        try {
            return Files.size(path);
        } catch (IOException ignore) {}
        return 0;
    }

    public void putEntry(Entry entry) {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(Files.newOutputStream(path))) {
            outputStream.writeObject(entry);
        } catch (IOException ignore) {}
    }

    public Entry getEntry() {
        if (getFileSize() == 0) return null;
        try (ObjectInputStream inputStream = new ObjectInputStream(Files.newInputStream(path))) {
            return  (Entry) inputStream.readObject();
        } catch (IOException | ClassNotFoundException ignore) {}
        return null;
    }

    public void remove() {
        try {
            Files.delete(path);
        } catch (IOException ignore) {}
    }
}
