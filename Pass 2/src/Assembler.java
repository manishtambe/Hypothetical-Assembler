import java.io.*;
import java.nio.file.Files;
import java.util.*;

class EMOT{
    String Mnemonic;
    int Class;
    String Opcode;
}

class Litral{
    String str;
    int address;
    public Litral(String string, int i) {
        this.str = string;
        this.address = i;
    }
}

public class Assembler {
    public void EMOTable(EMOT[] em)
    {
        String ReadPath;
        FileReader fr1;
        BufferedReader br1;

        ReadPath = "C:\\Users\\tambe\\Desktop\\Java Workspace\\Pass 2\\src\\EMOT.txt";
        try {
            fr1 = new FileReader(ReadPath);
            br1 = new BufferedReader(fr1);
            String Current_Line;
            int i = 0;

            while((Current_Line = br1.readLine())!= null) {
                em[i] = new EMOT();
                em[i].Mnemonic = Current_Line.split(" ")[0];
                em[i].Class = Integer.parseInt(Current_Line.split(" ")[1]);
                em[i].Opcode = Current_Line.split(" ")[2];
                i++;
            }

            } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

    }

    public void Display(HashMap<String, Integer> sym_t, ArrayList<Litral> lobj, ArrayList<Integer> pool_t)
    {
        System.out.println("Symbol Table: -");
        for (String key: sym_t.keySet()){
            System.out.println(key+ " = " + sym_t.get(key));
        }

        System.out.println("\nLiteral Table: -");
        Litral[] arr = lobj.toArray(new Litral[0]);
        for (Litral litral : arr) {
            System.out.println(litral.str + " " + litral.address);
        }

        System.out.println("\nPool Table: -");
        for(int i = 0; i < pool_t.size(); i++)
        {
            System.out.println(i + " " + pool_t.get(i));
        }
    }

    public void Interpreate(HashMap<String, Integer> sym_t, ArrayList<Litral> lobj, ArrayList<Integer> pool_t, EMOT[] em, HashMap<Integer, String> Meaning_t)
    {
        String ReadPath;
        FileReader fr1;
        BufferedReader br1;

        String WritePath1;
        FileWriter fr2;
        BufferedWriter br2;

        File f;

        int j = 0;
        int ltcount = 0;
        int tem = 0;
        int store = 0;
        int get = 0;
        int m = 0;
        int n = 1;

        try {
            ReadPath = "C:\\Users\\tambe\\Desktop\\Java Workspace\\Pass 2\\src\\Script.txt";
            fr1 = new FileReader(ReadPath);
            br1 = new BufferedReader(fr1);

            WritePath1 = "C:\\Users\\tambe\\Desktop\\Java Workspace\\Pass 2\\src\\FinalOutput.txt";
            f = new File(WritePath1);

            String Current_Line;
            int lc = 0;

            Current_Line = br1.readLine();

            String temp = Current_Line.split(" ")[0];

            if(f.exists())
            {
                Files.deleteIfExists(f.toPath());
            }

            fr2 = new FileWriter(f, true);
            br2 = new BufferedWriter(fr2);

            if(temp.equals("START"))
            {
                lc = Integer.parseInt(Current_Line.split(" ")[1]);
                for(int i = 0; i < em.length; i++)
                {
                    if(em[i].Mnemonic.equals("START"))
                    {
                        String str = "("+Meaning_t.get(em[i].Class)+","+em[i].Opcode+")"+"(C,"+lc+")";
                        br2.write(str);
                        br2.write(System.getProperty("line.separator"));
                    }
                }
            }

            while((Current_Line = br1.readLine())!= null) {

                if(Current_Line.contains("LTORG"))
                {
                    String str1 = "";
                    if(pool_t.size() == 1)
                    {
                        str1 = lc + "(DL,02)"+"(C,"+lobj.get(m).str+")";
                        br2.write(str1);
                        br2.write(System.getProperty("line.separator"));
                        //m++;
                        lc++;
                        ltcount++;
                        continue;
                    }
                    while(m !=  pool_t.get(n))
                    {
                        if(m < lobj.size())
                        {
                            str1 = lc + "(DL,02)"+"(C,"+lobj.get(m).str+")";
                            br2.write(str1);
                            br2.write(System.getProperty("line.separator"));
                            m++;
                            lc++;
                            ltcount++;
                        }
                    }
                    m = pool_t.get(n);
                    if(n < pool_t.size())
                    {
                        n++;
                    }
                    continue;
                }

                if(Current_Line.contains("STOP"))
                {
                    String str1 = "";
                    for(int i = 0; i < em.length; i++)
                    {
                        if(em[i].Mnemonic.equals("STOP"))
                        {
                            str1 = lc+"("+Meaning_t.get(em[i].Class)+","+em[i].Opcode+")";
                            lc++;
                            break;
                        }
                    }
                    br2.write(str1);
                    br2.write(System.getProperty("line.separator"));
                    continue;
                }

                if(Current_Line.contains("END"))
                {
                    String str1 = "";
                    for(int i = 0; i < em.length; i++)
                    {
                        if(em[i].Mnemonic.equals("END"))
                        {
                            str1 = lc+"("+Meaning_t.get(em[i].Class)+","+em[i].Opcode+")";
                            break;
                        }
                    }
                    br2.write(str1);
                    br2.write(System.getProperty("line.separator"));
                    if(ltcount < lobj.size())
                    {
                        for(int i = ltcount; i < lobj.size(); i++)
                        {
                            str1 = lc + "(DL,02)"+"(C,"+lobj.get(m).str+")";
                            br2.write(str1);
                            br2.write(System.getProperty("line.separator"));
                        }
                    }
                    break;
                }

                if(Current_Line.contains("ORIGIN"))
                {
                    String str1 = "";
                    String[] str = Current_Line.split("[ +]");
                    int iStore = 0;
                    int fbreak = 0;
                    for(int i = 0; i < em.length; i++)
                    {
                        if(em[i].Mnemonic.equals(str[0]))
                        {
                            str1 = lc+"("+Meaning_t.get(em[i].Class)+","+em[i].Opcode+")";
                            break;
                        }
                    }

                    for(int i = 0; i < sym_t.size(); i++)
                    {
                        if(sym_t.containsKey(str[1]))
                        {
                            iStore = sym_t.get(str[1]);
                            lc = iStore + Integer.parseInt(str[2]);
                            fbreak = 1;
                            break;
                        }
                    }
                    if(fbreak == 1)
                    {
                        fbreak = 0;
                        str1 = str1 + "(C,"+lc+")";
                        br2.write(str1);
                        br2.write(System.getProperty("line.separator"));
                        continue;
                    }
                }

                if(Current_Line.contains("EQU"))
                {
                    String[] str = Current_Line.split("[ +]");
                    String str1 = "";
                    if(str[1].equals("EQU"))
                    {
                        for(int i = 0; i < em.length; i++)
                        {
                            if(em[i].Mnemonic.equals(str[1]))
                            {
                                str1 = lc + "("+Meaning_t.get(em[i].Class)+","+em[i].Opcode+")";
                                break;
                            }
                        }

                        for(int i = 0; i < sym_t.size(); i++)
                        {
                            if(sym_t.containsKey(str[2]))
                            {
                                str1 = str1 + "(C,"+sym_t.get(str[2])+")";
                                break;
                            }
                        }
                    }
                    br2.write(str1);
                    br2.write(System.getProperty("line.separator"));
                    lc++;
                    continue;
                }

                if(Current_Line.contains(",='")) {
                    String[] str = Current_Line.split("[ ,]");
                    String str1 = "";
                    for (String s : str) {
                        if (s.contains("='")) {
                            if (str.length == 3) {
                                for (int i = 0; i < em.length; i++) {
                                    if (em[i].Mnemonic.equals(str[0])) {
                                        str1 = "(" + Meaning_t.get(em[i].Class) + "," + em[i].Opcode + ")";
                                        break;
                                    }
                                }
                                for (int i = 0; i < em.length; i++) {
                                    if (em[i].Mnemonic.equals(str[1])) {
                                        str1 = str1 + "(" + Meaning_t.get(em[i].Class) + "," + em[i].Opcode + ")";
                                        str1 = lc + str1;
                                        for (j = tem; j < lobj.size(); j++) {
                                            if (lobj.get(j).str.equals(s.split("[=']")[2])) {
                                                str1 = str1 + "(L," + j + ")";
                                                if (j < lobj.size()) {
                                                    tem = j + 1;
                                                    break;
                                                }
                                                break;
                                            }
                                        }
                                        break;
                                    }
                                }
                            } else if (str.length == 4) {
                                for (int i = 0; i < em.length; i++) {
                                    if (em[i].Mnemonic.equals(str[1])) {
                                        str1 = "(" + Meaning_t.get(em[i].Class) + "," + em[i].Opcode + ")";
                                        break;
                                    }
                                }
                                for (int i = 0; i < em.length; i++) {
                                    if (em[i].Mnemonic.equals(str[2])) {
                                        str1 = str1 + "(" + Meaning_t.get(em[i].Class) + "," + em[i].Opcode + ")";
                                        str1 = lc + str1;
                                        for (j = tem; j < lobj.size(); j++) {
                                            if (lobj.get(j).str.equals(s.split("[=']")[2])) {
                                                str1 = str1 + "(L," + j + ")";
                                                if (j < lobj.size()) {
                                                    tem = j + 1;
                                                    break;
                                                }
                                                break;
                                            }
                                        }
                                        break;
                                    }
                                }
                            }
                            br2.write(str1);
                            br2.write(System.getProperty("line.separator"));
                            lc++;
                        }
                    }
                    continue;
                }

                if(Current_Line.contains("DS") || Current_Line.contains("DC"))
                {
                    String[] str2 = Current_Line.split(" ");
                    String str3 = "";
                    if(str2[1].equals("DS"))
                    {
                        for(int i = 0; i < em.length; i++)
                        {
                            if(em[i].Mnemonic.equals(str2[1]))
                            {
                                str3 = lc + "("+Meaning_t.get(em[i].Class)+","+em[i].Opcode+")";
                                break;
                            }
                        }
                        str3 = str3 + "(C,"+str2[2]+")";
                    }
                    br2.write(str3);
                    br2.write(System.getProperty("line.separator"));
                    lc++;
                    continue;
                }
                else{
                    String[] str2 = Current_Line.split("[ ,]");
                    String str3 = "";
                    List keys = null;

                    for(int k = 0; k < str2.length; k++)
                    {
                        for(int i = 0; i < em.length; i++)
                        {
                            if(em[i].Mnemonic.equals(str2[k]))
                            {
                                if(str3.contains(String.valueOf(lc)))
                                {
                                    str3 = str3+"("+Meaning_t.get(em[i].Class)+","+em[i].Opcode+")";
                                }
                                else
                                {
                                    str3 = lc+str3+"("+Meaning_t.get(em[i].Class)+","+em[i].Opcode+")";
                                }
                                //System.out.println(str3);
                                break;
                            }
                        }
                        keys = new ArrayList(sym_t.keySet());
                        for (int l = 0; l < keys.size(); l++) {
                            if (keys.get(l).equals(str2[k])) {
                                str3 = str3 + "(S," + l + ")";
                                break;
                            }
                        }
                    }
                    br2.write(str3);
                    br2.write(System.getProperty("line.separator"));
                    lc++;
                }
            }
            br2.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static void main(String[] args){

        EMOT[] em= new EMOT[28];
        ArrayList<Litral> lobj = new ArrayList<>();
        ArrayList<Litral> lobj1 = new ArrayList<>();

        HashMap<Integer, String> Meaning_t = new HashMap<>();
        Meaning_t.put(1, "IS");
        Meaning_t.put(2, "DL");
        Meaning_t.put(3, "AD");
        Meaning_t.put(4, "RG");
        Meaning_t.put(5, "CC");

        int iCount = 0;
        int Pflag = 0;
        int lflag = 0;

        Assembler sobj = new Assembler();
        sobj.EMOTable(em);

        String ReadPath;
        FileReader fr1;
        BufferedReader br1;

        String WritePath1;
        FileWriter fr2;
        BufferedWriter br2;

        File f;
        try {
            ReadPath = "C:\\Users\\tambe\\Desktop\\Java Workspace\\Pass 2\\src\\Script.txt";
            fr1 = new FileReader(ReadPath);
            br1 = new BufferedReader(fr1);


            WritePath1 = "C:\\Users\\tambe\\Desktop\\Java Workspace\\Pass 2\\src\\Output.txt";
            f = new File(WritePath1);

            HashMap<String, Integer> sym_t = new HashMap<>();
            ArrayList<Integer> pool_t = new ArrayList<>();

            String Current_Line;
            int lc = 0;

            Current_Line = br1.readLine();

            String temp = Current_Line.split(" ")[0];

            if(f.exists())
            {
                Files.deleteIfExists(f.toPath());
            }

            fr2 = new FileWriter(f, true);
            br2 = new BufferedWriter(fr2);

            if(temp.equals("START"))
            {
                lc = Integer.parseInt(Current_Line.split(" ")[1]);
//                for(int i = 0; i < em.length; i++)
//                {
//                    if(em[i].Mnemonic.equals("START"))
//                    {
//                        String str = "("+Meaning_t.get(em[i].Class)+","+em[i].Opcode+")"+"(C,"+lc+")";
//                        br2.write(str);
//                        br2.write(System.getProperty("line.separator"));
//                    }
//                }
            }

            while((Current_Line = br1.readLine())!= null) {

                if(Current_Line.contains("ORIGIN"))
                {
                    String[] str = Current_Line.split("[ +]");
                    int iStore = 0;
                    int fbreak = 0;
                    for(int i = 0; i < sym_t.size(); i++)
                    {
                        if(sym_t.containsKey(str[1]))
                        {
                            iStore = sym_t.get(str[1]);
                            lc = iStore + Integer.parseInt(str[2]);
                            fbreak = 1;
                            break;
                        }
                    }
                    if(fbreak == 1)
                    {
                        fbreak = 0;
                        continue;
                    }
                }

                if(Current_Line.contains("LTORG"))
                {
                    for(int i = 0; i < lobj1.size(); i++)
                    {
                        if (Pflag == 0) {
                            pool_t.add(iCount);
                            Pflag = 1;
                        }
                        iCount++;
                        lobj.add(new Litral(lobj1.get(i).str, lc));
                        br2.write(Integer.toString(lc));
                        br2.write(System.getProperty("line.separator"));
                        lc++;
                    }
//                    for (String s : lobj1) {
////                        for (Litral litral : lobj) {
////                            if (litral.str.contains(s)) {
////                                if (Pflag == 0) {
////                                    pool_t.add(iCount);
////                                    Pflag = 1;
////                                }
////                                iCount++;
////                                litral.address = lc;
////                                br2.write(Integer.toString(lc));
////                                br2.write(System.getProperty("line.separator"));
////                                lc++;
////                            }
////                        }
////                    }
                    Pflag = 0;
                    lobj1.clear();
                    continue;
                }

                if(Current_Line.contains("END"))
                {
                    if(lobj1.size() != 0)
                    {
                        for(int i = 0; i < lobj1.size(); i++)
                        {
                            try {
                                lobj.add(new Litral(lobj1.get(i).str, lc));
                                if (Pflag == 0) {
                                    pool_t.add(iCount);
                                    Pflag = 1;
                                }
                                br2.write(Integer.toString(lc));
                                br2.write(System.getProperty("line.separator"));
                                lc++;
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                        break;
                    }
                    else
                    {
                        br2.write(Integer.toString(lc));
                        br2.write(System.getProperty("line.separator"));
                        lc++;
                    }
                    Pflag = 0;
                    lobj1.clear();
                    continue;
                }

                if(Current_Line.split("[ ,]").length == 4)
                {
                    sym_t.put(Current_Line.split(" ")[0], lc);
                }

                if(Current_Line.contains("EQU"))
                {
                    String[] str = Current_Line.split("[ +]");
                    sym_t.put(str[0], sym_t.get(str[2]));
                    lc++;
                    continue;
                }
                if(Current_Line.contains("DS") || Current_Line.contains("DC"))
                {
                    sym_t.put(Current_Line.split(" ")[0], lc);
                    br2.write(Integer.toString(lc));
                    br2.write(System.getProperty("line.separator"));
                    lc = lc + Integer.parseInt(Current_Line.split(" ")[2]);
                    continue;
                }
                else {
                    String[] str = Current_Line.split("[ ,]");
                    String str1 = "";
                    for (String s : str) {
                        if (s.contains("='")) {
                            if(lobj1.size() == 0)
                            {
                                lobj1.add(new Litral(s.split("[=']")[2], -1));
                                //lobj1.add(s.split("[=']")[2]);
                                break;
                            }
                            else {
                                for (int i = 0; i < lobj1.size(); i++) {
                                    if (!lobj1.get(i).str.contains(s.split("[=']")[2])) {
                                        lobj1.add(new Litral(s.split("[=']")[2], -1));
                                        //lobj1.add(s.split("[=']")[2]);
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
                br2.write(lc +" "+Current_Line);
                br2.write(System.getProperty("line.separator"));
                lc++;
            }
            br2.close();
            fr2.close();
            fr1.close();
//            for(Litral litral2 : set)
//            {
//                System.out.println(litral2.str+" "+litral2.address);
//            }
            sobj.Display(sym_t, lobj, pool_t);
            sobj.Interpreate(sym_t, lobj, pool_t, em, Meaning_t);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
