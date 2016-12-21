package ingeniousthings.sigfox.decoder;

public class SigfoxDecoder {

    private String format;

    public SigfoxDecoder(String format) {
        this.format = format;
    }

    public SigfoxData decode(String bytes) {
        return new SigfoxData();
    }
}
