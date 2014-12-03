package ru.ncedu.nfetissow.archiver;

import java.io.*;
import java.util.Enumeration;
import java.util.zip.*;

//TODO Class with private constructor should be final
public class ArchiverImpl implements Archiver {

    private static ArchiverImpl instance = null;
    private int BUFFER;
    private ArchiverImpl() {
        BUFFER = 2048;
    }

    //TODO 1)this singlton isn't correct http://habrahabr.ru/post/129494/
    public static ArchiverImpl getInstance() {
        if(instance == null) {
            instance = new ArchiverImpl();
        }
        return instance;
    }

    public void setBuffer(int buf) {
        this.BUFFER = buf;
    }

    public int getBuffer() {
        return this.BUFFER;
    }

    //TODO This code is very hard to read!
    // 1)Avoid short variables like e, etc.
    // 2)use helper methods to extend your functionality
    @Override
    public String deArchive(String path, String dirWhereTo) {
        try {
            BufferedOutputStream dest;
            BufferedInputStream is;
            ZipEntry entry;
            ZipFile zipfile = new ZipFile(path);
            Enumeration e = zipfile.entries();
            while(e.hasMoreElements()) {
                entry = (ZipEntry) e.nextElement();
                if(entry.isDirectory()) {
                    continue;
                }

                is = new BufferedInputStream
                        (zipfile.getInputStream(entry));
                int count;
                byte data[] = new byte[BUFFER];
                FileOutputStream fos;
                //Create directories for archived file to avoid FileNotFound exception
                String dir = dirWhereTo;
                //TODO avoid RepetingLiterals / -appears 8 times
                if(entry.getName().lastIndexOf("/") != -1) {
                    //TODO Prefer StringBuilder for concatenating Strings
                    dir += "/" + entry.getName().substring(0, entry.getName().lastIndexOf("/"));
                }
                File f = new File(dir);
                f.mkdirs();

                fos = new
                        FileOutputStream(dirWhereTo + "/" + entry.getName());
                dest = new
                        BufferedOutputStream(fos, BUFFER);
                while ((count = is.read(data, 0, BUFFER))
                        != -1) {
                    dest.write(data, 0, count);
                }
                dest.flush();
                dest.close();
                is.close();
            }
            //TODO Avoid checking general exception This is a bad style
        } catch(Exception e) {
            //TODO this error isn't user friendly
            return "Error";
        }
        return "Success";
    }

    private void archiveDir(ZipOutputStream out, String path, String parent) throws IOException {
        File subDir = new File(path);
        String subDirList[] = subDir.list();
        for(String sd: subDirList) {
            File f = new File(path + "/" + sd);
            if(f.isDirectory()) {
                archiveDir(out, path + "/" + sd, parent + "/" + sd);
            }
            else {
                archiveFile(out, parent + "/" + sd, f);
            }
        }
    }

    private void archiveFile(ZipOutputStream out, String entryName, File f) throws IOException {
        byte data[] = new byte[BUFFER];
        FileInputStream fi = new FileInputStream(f);
        BufferedInputStream origin = new BufferedInputStream(fi, BUFFER);
        ZipEntry entry = new ZipEntry(entryName);
        out.putNextEntry(entry);
        int count;
        while ((count = origin.read(data, 0, BUFFER)) != -1) {
            out.write(data, 0, count);
            out.flush();
        }
        origin.close();
    }

    @Override
    public String createArchive(String path, String archiveName) {
        try {
            FileOutputStream dest = new FileOutputStream(new File(archiveName));
            ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(dest));
            File f = new File(path);
            if(f.isDirectory()) {
                archiveDir(out, path, "");
            } else {
                archiveFile(out, f.getName(), f);
            }
            out.flush();
            out.close();
            //TODO Avoid checking general exception This is a bad style
        } catch (Exception e) {
            return "Error";
        }
        return "Success";
    }
}