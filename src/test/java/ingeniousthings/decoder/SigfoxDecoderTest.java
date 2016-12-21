package ingeniousthings.decoder;

import ingeniousthings.sigfox.decoder.SigfoxData;
import ingeniousthings.sigfox.decoder.SigfoxDecoder;
import org.assertj.core.api.Assertions;
import org.junit.Test;

public class SigfoxDecoderTest {

    @Test
    public void testSomething() throws Exception {
        SigfoxDecoder decoder = new SigfoxDecoder("0-7:int:temperature");
        SigfoxData data = decoder.decode("c101013030320e0c03ed8000");
        Assertions.assertThat(data.getInt("temperature")).isEqualTo(12);
    }
}
