package ru.ncedu.nfetissow.archiver;

public class ArchiverApp {
    public static void execute(String[] args) {
        try {
            if(args.length > 3 || args.length < 2) {
                throw new IllegalArgumentException();
            }
            if(args[1].indexOf(".zip") == -1) {
                throw new IllegalArgumentException();
            }
            if(args[0].equals("compress")) {
                if(args.length != 3) {
                    throw new IllegalArgumentException();
                }
                ArchiverImpl arch = ArchiverImpl.getInstance();
                String msg = arch.createArchive(args[2], args[1]);
                System.out.print(msg);
            } else if(args[0].equals("decompress")) {
                ArchiverImpl arch = ArchiverImpl.getInstance();
                String msg = arch.deArchive(args[1], args.length == 3 ? args[2] : "");
                System.out.print(msg);
            } else {
                throw new IllegalArgumentException();
            }
        } catch (IllegalArgumentException e) {
            System.out.print("Wrong input!\n" +
                    "Input format:\n" +
                    "To compress: compress *.zip <directory of file to compress>\n" +
                    "To decompress: decompress *zip [directory where to decompress\n");
        }
    }
}
