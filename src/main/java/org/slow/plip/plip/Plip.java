package org.slow.plip.plip;

import net.fabricmc.api.ModInitializer;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.slow.plip.plip.command.Commander;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;


public class Plip implements ModInitializer {
    static JSONObject config;
    @Override
    public void onInitialize() {
        try {
            File configFile = new File("./config/plip.json");
            String defaultConfig = """
                    {
                      "names": [
                        "AltAccount1",
                        "AltAccount2"
                      ]
                    }""";
            if (configFile.createNewFile()){
                Files.write(Paths.get("./config/plip.json"), defaultConfig.getBytes());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        try {
            config = (JSONObject) JSONValue.parse(new String(Files.readAllBytes(Paths.get("./config/plip.json"))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Commander.register();
    }

    public static JSONObject getConfig() {
        return config;
    }
}
