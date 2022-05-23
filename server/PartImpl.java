import java.util.List;

public class PartImpl implements Part{

    int partCode;
    String partName;
    String partDesc;
    List<PartQuant> subParts;

    PartImpl(int partCode, String partName, String partDesc, List<PartQuant> subParts) {
        this.partCode = partCode;
        this.partName = partName;
        this.partDesc = partDesc;
        this.subParts = subParts;
    }

    @Override
    public int getPartCode() {
        return partCode;
    }

    @Override
    public String getPartName() {
        return partName;
    }

    @Override
    public String getPartDesc() {
        return partDesc;
    }

    @Override
    public List<PartQuant> getSubParts() {
        return subParts;
    }
    
}