import java.util.*;

interface Part {

    int getPartCode();
    String getPartName();
    String getPartDesc();
    List<PartQuant> getSubParts();
}