package org.hale.commons.io.hdfs;

import org.hale.commons.io.StreamReader;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;


/**
 * Created on 2017-03-20.
 * hale
 *
 * @author guilherme
 */
public class HDFSStreamReader implements StreamReader {

    private BufferedReader bufferedReader;
    private boolean closed;


    public HDFSStreamReader(String filePath) throws IOException {
        Path path = new Path(filePath);

        Configuration conf = new Configuration();
        conf.set("fs.hdfs.impl", org.apache.hadoop.hdfs.DistributedFileSystem.class.getName());

        FileSystem fs;
        this.bufferedReader = null;
        this.closed = false;

        try {
            fs = FileSystem.get(URI.create(filePath), conf);
            this.bufferedReader = new BufferedReader(new InputStreamReader(fs.open(path)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Retrieves the next byte sequence from a stream. Should this the stream be closed, this should raise an runtime exception.
     *
     * @return returns byte sequence as a string, if stream is open.
     */
    @Override
    public String next() {
        String line;

        try {
            line = bufferedReader.readLine();
        } catch (IOException e){
            throw new RuntimeException(e);
        }

        if (line == null){
            this.closed = true;
        }

        return line;
    }

    /**
     * Checks if the stream is closed. If so, then no more data can be retrieved
     *
     * @return true is the stream is closed, and false otherwise
     */
    @Override
    public boolean isClosed() {
        return this.closed;
    }
}
