package ingeniousthings.decoder;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import exceptions.SyntaxError;
import exceptions.UnknownTypeException;
import exceptions.UnregisteredVarException;
import ingeniousthings.sigfox.decoder.SigfoxDecoder.*;

public class SigfoxDecoderTest {

    @Test
    public void testDecoder() throws Exception {
    	SigfoxDecoder decoder = new SigfoxDecoder("0-7:char:char 0-47:str:str 48-63:int:int1 64-95:int:int2");
		SigfoxData data = decoder.decode("41424344454601234567890A");

        Assertions.assertThat(data.get("char")).isEqualTo('A');
        Assertions.assertThat(data.get("str")).isEqualTo("ABCDEF");
        Assertions.assertThat(data.get("int1")).isEqualTo(291);
        Assertions.assertThat(data.get("int2")).isEqualTo(1164413194);
    }
    @Test(expected = UnregisteredVarException.class)
    public void testFalseVariale() throws SyntaxError, UnknownTypeException, UnregisteredVarException {
    	SigfoxDecoder decoder = new SigfoxDecoder("0-3:int:temperature");
    	SigfoxData data = decoder.decode("c101013030320e0c03ed8000");

    	
			data.get("false_variable");
		
    }
}
