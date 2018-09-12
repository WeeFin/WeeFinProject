package ConfigurationTest;

import com.finaxys.loader.LoadModel;
import com.finaxys.model.BlocksTransactions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

public class LoadModelTest {

    public static LoadModel lc;

    @BeforeAll
    static void setUp() throws Exception {
        lc = new LoadModel("testFiles/blocksTransactionsTest.csv");
    }

    @AfterAll
    static void tearDown() throws Exception {
        lc = null;
    }

    @Test
    void newLoaderNoFileProvided() {
        Assertions.assertThrows(NullPointerException.class, () -> new LoadModel(null));
    }

    @Test
    void testGetListOfBlocksTransactionsFromCSV() {
        List<BlocksTransactions> blocks = lc.getListOfBlocksTransactionsFromCSV();
        blocks.forEach(System.out::println);
    }

}
