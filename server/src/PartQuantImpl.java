import java.io.Serializable;
import java.rmi.RemoteException;

public class PartQuantImpl implements PartQuant, Serializable {

    private Part part;
    private int quant;

    PartQuantImpl(Part part, int quant) {
        this.part = part;
        this.quant = quant;
    }

    public Part getPart() throws RemoteException {
        return this.part;
    }

    public int getQuant() throws RemoteException {
        return this.quant;
    }

}
