public class PartQuant {
    
    private Part part;
    private int quant;

    PartQuant(Part part, int quant) {
        this.part = part;
        this.quant = quant;
    }

    public Part getPart() {
        return part;
    }

    public int getQuant() {
        return quant;
    }
}
