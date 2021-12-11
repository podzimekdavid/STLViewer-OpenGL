package com.dp.main.managers;

import com.dp.main.utils.Utils;
import lwjglutils.OGLTexture2D;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class TextureManager {

    private final Map<String, OGLTexture2D> textures;
    public static final String TEXTURES_PATH = "textures";
    public static final String DEFAULT_TEXTURE = "default.jpg";

    public TextureManager() {

        textures = new HashMap<>();

        init();
    }

    public OGLTexture2D getDefaultTexture() {
        return defaultTexture;
    }

    private OGLTexture2D defaultTexture;

    private void init() {
        loadTextures(Paths.get(Objects.requireNonNull(Utils.resourcePath(TEXTURES_PATH))));
    }

    public void loadTextures(final Path folder) {

        try {
            Files.list(folder).forEach(file -> {
                try {
                    String name = file.getFileName().toString();
                    if (name.contains(".jpg"))
                        textures.put(name, new OGLTexture2D(TEXTURES_PATH + "/" + name));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            if (!textures.containsKey(DEFAULT_TEXTURE))
                throw new FileNotFoundException("Missing default texture!");

            defaultTexture = new OGLTexture2D(TEXTURES_PATH + "/" + DEFAULT_TEXTURE);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public OGLTexture2D getTexture(String name) {

        if (textures.containsKey(name))
            return textures.get(name);
        else
            return textures.get(DEFAULT_TEXTURE);
    }

    public OGLTexture2D getTexture() {
        return getTexture(DEFAULT_TEXTURE);
    }

    private int index;

    public void bindNextTexture(int shaderProgram, String inName, int slot) {
        bindTexture(textures.keySet().toArray()[index].toString(), shaderProgram, inName, slot);
    }

    public void switchTexture() {
        if (index < textures.size()-1)
            index++;
        else
            index = 0;

    }

    public void bindTexture(String name, int shaderProgram, String inName, int slot) {

        var texture = getTexture(name);
        texture.bind(shaderProgram, inName, slot);

    }

    public void bindTexture(int shaderProgram, String inName, int slot) {

        bindTexture(DEFAULT_TEXTURE, shaderProgram, inName, slot);
    }


}
