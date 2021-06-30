package com.home.mydict.service;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface FileService {
    void readFile() throws IOException;
    void writeFile() throws IOException;
}
