package com.yuguo;

import java.util.List;

public abstract class UnZipUtil {

    public static final int BYTE_SIZE = 1024;

    public abstract List<String> unZipFile(String path, String fileName) throws Exception;

}
