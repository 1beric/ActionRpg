package citiesGame.util;

import java.io.FileOutputStream;
import java.io.IOException;

public interface Writable {

    public void write(FileOutputStream fos) throws IOException;
}
