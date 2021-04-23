package Engine.Rendering;

import de.matthiasmann.twl.utils.PNGDecoder;
import org.lwjgl.opengl.GL30;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public class Texture {
    private int id;

    public Texture(String fileName){
        //if cannot find texture exception is thrown
        try {
            this.id = setTexture(fileName);
        } catch (Exception e){
            System.err.println("failed loading texture : " + fileName);
            e.printStackTrace();
        }
    }

    public int getId() {
        return id;
    }

    public static int setTexture(String path) throws IOException {
        FileInputStream fis = new FileInputStream(path); //read texture from file path
        PNGDecoder decoder = new PNGDecoder(fis); //decode picture w/ pngdecoder
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(4 * decoder.getWidth() * decoder.getHeight());
        decoder.decode(byteBuffer, decoder.getWidth() * 4, PNGDecoder.Format.RGBA);
        byteBuffer.flip();
        int id = GL30.glGenTextures(); //set texture id
        GL30.glBindTexture(GL30.GL_TEXTURE_2D, id);
        GL30.glTexImage2D(GL30.GL_TEXTURE_2D, 0, GL30.GL_RGBA, decoder.getWidth(), decoder.getHeight(), 0, GL30.GL_RGBA, GL30.GL_UNSIGNED_BYTE, byteBuffer);
        GL30.glGenerateMipmap(GL30.GL_TEXTURE_2D);
        fis.close();
        return id;
    }
}
