package ru.ncedu.nfetissow.archiver;

/*@author Fetissow

 */
public interface Archiver {
    /***
     * This method takes path to a directory or file and makes an archive with
     set name
     * @param path Name of file or directory to archive
     * @param archiveName Name of the archive
     * @return msg - either success or error
     */
    public String createArchive(String path, String archiveName);
    /***
     * This method takes path to a zip archive and decompresses it in
     set directory
     * @param path Name of a zip archive
     * @param dirWhereTo path to directory in which archive will be decompressed
     * @return msg - either success or error
     */
    public String deArchive(String path, String dirWhereTo);
}


