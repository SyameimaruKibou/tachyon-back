package imserver.data;

public class MinSessionInfo {
    private Long mostsignbits;
    private Long leastsignbits;

    public MinSessionInfo(Long mostsignbits, Long leastsignbits) {
        this.mostsignbits = mostsignbits;
        this.leastsignbits = leastsignbits;
    }

    public Long getMostsignbits() {
        return mostsignbits;
    }

    public void setMostsignbits(Long mostsignbits) {
        this.mostsignbits = mostsignbits;
    }

    public Long getLeastsignbits() {
        return leastsignbits;
    }

    public void setLeastsignbits(Long leastsignbits) {
        this.leastsignbits = leastsignbits;
    }
}
