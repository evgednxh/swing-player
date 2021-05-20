package services.storage;

import java.io.IOException;

public interface IStorage {
    void writeToFile(StorageModel model) throws IOException;

    StorageModel loadFromFile();
}
