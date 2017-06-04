package org.hale.commons.io;

import com.google.common.base.Joiner;
import com.google.common.io.Files;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.hale.commons.io.hdfs.HDFSStreamReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.*;

/**
 * Created on 2017-03-21
 * hale
 *
 * @author guilherme
 */
public class HDFSReaderTest {

    private final Logger logger = LoggerFactory.getLogger(HDFSReaderTest.class);

    private File file;
    private String[] data = new String[]{
            "Hello", "This is a test", "Of file IO"
    };

    @Before
    public void setUp() throws IOException {
       this.file = File.createTempFile(UUID.randomUUID().toString(), ".tmp");
       Files.write(Joiner.on("\n").join(data).getBytes(), this.file);

       assertTrue(this.file.exists());
    }

    @After
    public void tearDown(){
        assertTrue(this.file.delete());
    }

    @Test
    public void testShouldReadFileStream() throws IOException {
        StreamReader streamReader = new HDFSStreamReader(this.file.getPath());

        assertNotNull(streamReader);
        assertFalse(streamReader.isClosed());

        String line;
        int c = 0;

        while(!streamReader.isClosed() && (line = streamReader.next()) != null){
            assertEquals(this.data[c], line);
            c+= 1;
        }

        assertEquals(this.data.length, c);
    }
}
