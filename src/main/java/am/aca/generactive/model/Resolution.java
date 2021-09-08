package am.aca.generactive.model;

public enum Resolution {

    HD(1), FHD(2), _4K(4);

    private final int coefficient;

    Resolution(int coefficient) {
        this.coefficient = coefficient;
    }

    public int getCoefficient() {
        return coefficient;
    }
}
