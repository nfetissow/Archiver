import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import ru.ncedu.nfetissow.archiver.Archiver;
import ru.ncedu.nfetissow.archiver.ArchiverApp;
import ru.ncedu.nfetissow.archiver.ArchiverImpl;

public class ArchiverTest {
    @Test
    public void testDearchiving() {
        Archiver arch = ArchiverImpl.getInstance();
        String actual = arch.deArchive("/test/testResources/toUnZip/main.c.zip", "/test/dearchiveResult");
        Assert.assertEquals(actual, "Success");
        actual = arch.deArchive("/test/testResources/toUnZip/HelloWorld.zip", "/test/testResources/dearchiveResult");
        Assert.assertEquals(actual, "Success");
        actual = arch.deArchive("notExisting.zip", "/test/dearchiveResult");
        Assert.assertEquals(actual, "Error");
    }
    @Test
    public void testArchiving() {
        Archiver arch = ArchiverImpl.getInstance();
        String actual = arch.createArchive("/test/testResources/ToZip/folder", "/test/testResources/folder.zip");
        Assert.assertEquals(actual, "Success");
        actual = arch.createArchive("/test/testResources/ToZip/main", "/test/testResources/main.zip");
        Assert.assertEquals(actual, "Success");
    }
}
