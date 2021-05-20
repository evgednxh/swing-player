package services.storage;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.*;

public class Storage implements IStorage {
    private static final String DIR_NAME = ".spotify_player";
    private static final String FILE_NAME = "data.json";

    private final String path;
    private final Gson gson;

    public Storage() {
        this.gson = new Gson();
        this.path = System.getProperty("user.home") + "/" + DIR_NAME + "/" + FILE_NAME;
        System.out.println("File services.storage path " + this.path);
    }

    @Override
    public void writeToFile(StorageModel model) {
        try {
            // check file and dir by path
            File newFile = new File(this.path);

            // todo: check the dir and file in path
            if (!newFile.exists()) {
                File dir = new File(newFile.getParent());
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                newFile.createNewFile();
            }

            // write file
            FileWriter fileWriter = new FileWriter(newFile);

            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(model.toString());
            bufferedWriter.close();

        } catch (IOException e) {
            System.out.println("Unhandled services.storage exception " + e.getMessage());
        }
    }

    @Override
    public StorageModel loadFromFile() {
        File file = new File(this.path);

        StorageModel storageModel;

        if (!file.exists()) {
            storageModel = new StorageModel();
            return storageModel;
        }

        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);

            JsonReader jsonReader = new JsonReader(inputStreamReader);
            storageModel = this.gson.fromJson(jsonReader, StorageModel.class);
            return storageModel;

        } catch (FileNotFoundException e) {
            System.out.println("Unhandled read from services.storage exception " + e.getMessage());
        }

        return new StorageModel();
    }
}
