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

        ReadPath = "C:\\Users\\tambe\\Desktop\\Java Workspace\\Pass 1\\src\\EMOT.txt";
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

//        System.out.println("\nPool Table: -");
//        for(int i = 0; i < pool_t.size(); i++)
//        {
//            System.out.println(i + " " + pool_t.get(i));
//        }
    }


    public static void main(String[] args){

        EMOT[] em= new EMOT[28];
        ArrayList<Litral> lobj = new ArrayList<>();
        ArrayList<String> lobj1 = new ArrayList<>();
        int iCount = 0;
        int Pflag = 0;

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
            ReadPath = "C:\\Users\\tambe\\Desktop\\Java Workspace\\Pass 1\\src\\sample.txt";
            fr1 = new FileReader(ReadPath);
            br1 = new BufferedReader(fr1);


            WritePath1 = "C:\\Users\\tambe\\Desktop\\Java Workspace\\Pass 1\\src\\Output.txt";
            f = new File(WritePath1);

            HashMap<String, Integer> sym_t = new HashMap<>();
            ArrayList<Integer> pool_t = new ArrayList<>();

            String Current_Line;
            int lc = 0;

            Current_Line = br1.readLine();

            String temp = Current_Line.split(" ")[0];

            if(temp.equals("START"))
            {
                lc = Integer.parseInt(Current_Line.split(" ")[1]);
            }

            if(f.exists())
            {
                Files.deleteIfExists(f.toPath());
            }

            fr2 = new FileWriter(f, true);
            br2 = new BufferedWriter(fr2);

            while((Current_Line = br1.readLine())!= null) {

//                if(Current_Line.contains("LTORG"))
//                {
//                    for (String s : lobj1) {
//                        for (Litral litral : lobj) {
//                            if (litral.str.contains(s)) {
//                                if (Pflag == 0) {
//                                    pool_t.add(iCount);
//                                    Pflag = 1;
//                                }
//                                iCount++;
//                                litral.address = lc;
//                                br2.write(Integer.toString(lc));
//                                br2.write(System.getProperty("line.separator"));
//                                lc++;
//                            }
//                        }
//                    }
//                    Pflag = 0;
//                    lobj1.clear();
//                    continue;
//                }
//
//                if(Current_Line.contains("END"))
//                {
//                    for (Litral litral : lobj) {
//                        if ((litral.address) == -1) {
//                            litral.address = lc;
//                            br2.write(Integer.toString(lc));
//                            br2.write(System.getProperty("line.separator"));
//                            lc++;
//                        }
//                    }
//                    break;
//                }

                if(Current_Line.split("[ ,]").length == 4)
                {
                    sym_t.put(Current_Line.split(" ")[0], lc);
                }

                if(Current_Line.contains("DS") || Current_Line.contains("DC"))
                {
                    sym_t.put(Current_Line.split(" ")[0], lc);
                    br2.write(Integer.toString(lc));
                    br2.write(System.getProperty("line.separator"));
                    lc++;
                    continue;
                }
                else {
                    String[] str = Current_Line.split("[ ,]");
                    for (String s : str) {
                        if (s.contains("='")) {
                            lobj.add(new Litral(s.split("[=']")[2], -1));
                            lobj1.add(s.split("[=']")[2]);
                            break;
                        }
                    }
                }
                br2.write(lc +" "+Current_Line);
                br2.write(System.getProperty("line.separator"));
                lc++;
            }
            br2.close();
            sobj.Display(sym_t, lobj, pool_t);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
