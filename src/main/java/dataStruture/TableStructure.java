package dataStruture;

import com.sun.istack.internal.NotNull;

import java.util.ArrayList;
import java.util.List;

public class TableStructure implements Cloneable{
    private String tbname;
    private List<ListStructure> listStructureList;
    private int readnum=-1;
    public TableStructure()
    {
        listStructureList=new ArrayList<>();
    }

    public void addlist(@NotNull String listname, String ListType, String defaultDataType, boolean isSingal, boolean isDefault, String defaultStr, int[] Range,double[] Numberarea)
    {
        ListStructure ls=new ListStructure(listname,ListType,isSingal,isDefault,defaultStr);
        ls.setDefaultType(defaultDataType);
        ls.setRange(Range);
        ls.setNumberarea(Numberarea);
        listStructureList.add(ls);
    }

    public boolean hasNext()
    {
        if(readnum>=listStructureList.size()-1) {
            readnum = -1;
            return false;
        }
        return true;
    }
    public ListStructure getNextStruc()
    {
        readnum++;
        if(readnum==listStructureList.size())
            readnum = -1;
        return listStructureList.get(readnum);
    }
    public List<ListStructure> getStruc()
    {
        return listStructureList;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        TableStructure newT= new TableStructure();
        for(int loop=0;loop<this.listStructureList.size();loop++)
            newT.listStructureList.add((ListStructure) this.listStructureList.get(loop).clone());
        return newT;
    }

    public String getTbname() {
        return tbname;
    }

    public void setTbname(String tbname) {
        this.tbname = tbname;
    }
}
