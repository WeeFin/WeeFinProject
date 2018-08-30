package ConfigurationTest;

import com.finaxys.loader.LoadModel;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class TestLoadModelClasses {

    @BeforeAll
    void setUp() throws Exception {
        LoadModel lc = new LoadModel("/home/finaxys/blocks.csv");
    }

    @AfterAll
    void tearDown() throws Exception {
    }

    @Test
    void newLoaderNoFile() throws Exception {
        Assertions.assertThrows(NullPointerException.class, () -> new LoadModel(null));
    }

}
