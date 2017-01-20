package ingeniousthings.decoder;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import ingeniousthings.sigfox.decoder.SigfoxDecoder.*;

public class SigfoxDecoderTest {

    @Test
    public void testSomething() throws Exception {
        SigfoxDecoder decoder = new SigfoxDecoder("0-3:int:temperature");
        SigfoxData data = decoder.decode("c101013030320e0c03ed8000");

        Assertions.assertThat(data.getInt("temperature")).isEqualTo(12);
    }
}
