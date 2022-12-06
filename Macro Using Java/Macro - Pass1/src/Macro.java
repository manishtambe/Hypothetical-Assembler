import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;

class MNT{
    String Macro_Name;
    int No_Parameter;
    int Starting_Index;
    public MNT(String Macro_N, int No_Par, int Start_Index)
    {
        this.Macro_Name = Macro_N;
        this.No_Parameter = No_Par;
        this.Starting_Index = Start_Index;
    }
}
public class Macro {
    public void Display(MNT[] arr, HashMap<Integer, String> MDT, HashMap<String, ArrayList<String>> Parameter, ArrayList<String> hpara)
    {
        int cmnt = 1;
        System.out.println("Macro Name Table (MNT): - ");
        for(MNT mnt : arr)
        {
            System.out.println(cmnt+". Macro Name: - "+mnt.Macro_Name+" "+"No.of parameter: - "+mnt.No_Parameter+" "+"Starting Index: -"+mnt.Starting_Index);
            cmnt+=1;
        }
        System.out.println();
        System.out.println("Macro Data Table (MDT): -");
        for(int i = 1; i <= MDT.size(); i++)
        {
            System.out.println(i+" "+MDT.get(i));
        }
        System.out.println();
        System.out.println("Formal v/s positional parameter list: -");
        for(MNT mnt : arr)
        {
            System.out.println("Macro Name: - "+mnt.Macro_Name);
            ArrayList<String> temp = Parameter.get(mnt.Macro_Name);
            if(temp.size() == 0)
            {
                System.out.println("Formal Parameter: - "+" "+"Positional Parameter: -");
            }
            else {
                for (int i = 0; i < temp.size(); i++) {
                    String[] split1 = temp.get(i).split("[=]");
                    if(split1.length == 3)
                    {
                        String[] val1 = temp.get(i).split("[=]");
                        System.out.println("Formal Parameter: - " + split1[0] + " " + "Positional Parameter: -" + split1[2]);
                        //Current_Line = Current_Line.replace(val[0], val1[2]);
                    }
                    else
                    {
                        System.out.println("Formal Parameter: - " + split1[0] + " " + "Positional Parameter: -" + split1[1]);
                    }
                }
            }
        }
        System.out.println();
        System.out.println("Actual v/s positional parameter list: -");
        for(int i = 0; i < hpara.size(); i++) {
            String str[] = hpara.get(i).split("\\[");
            // System.out.println(str.length);
            System.out.println("Macro Name: - "+str[0]);
            for (int j = 1; j < str.length; j++) {

                //         System.out.println(str[j]);
                String val[] = str[j].split("[=]");
                //         //System.out.println(val.length);
                System.out.println("Actual Parameter: -" + val[1].replace("]", ""));
                System.out.println("Positional Parameter: -" + val[0]);
            }
        }
    }

    public static void main(String[] args){

        Macro sobj = new Macro();

        String Macro_Name = "";
        int mflag = 0;
        int cflag = 0;
        int iflag = 0;
        int MDT_Index = 1;

        ArrayList<MNT> mobj = new ArrayList<>();

        String ReadPath;
        FileReader fr1;
        BufferedReader br1;

        String WritePath1;
        FileWriter fr2;
        BufferedWriter br2;

        String WritePath2;
        FileWriter fr3;
        BufferedWriter br3;

        File f;
        File f1;
        try {
            ReadPath = "C:\\Users\\tambe\\Desktop\\Java Workspace\\Macro - Pass1\\src\\Script1.txt";
            fr1 = new FileReader(ReadPath);
            br1 = new BufferedReader(fr1);

            WritePath1 = "C:\\Users\\tambe\\Desktop\\Java Workspace\\Macro - Pass1\\src\\Output1.txt";
            f = new File(WritePath1);

            WritePath2 = "C:\\Users\\tambe\\Desktop\\Java Workspace\\Macro - Pass1\\src\\F_Output.txt";
            f1 = new File(WritePath2);

            HashMap<Integer, String> MDT = new HashMap<>();
            ArrayList<String> hpara = new ArrayList<String>();

            HashMap<String, ArrayList<String>> Parameter = new HashMap<String, ArrayList<String>>();
            HashMap<String, ArrayList<String>> Parameter1 = new HashMap<String, ArrayList<String>>();
            HashMap<String, ArrayList<String>> para= new HashMap<String, ArrayList<String>>();
            String Current_Line;
            int lc = 0;
            int lc1 = 0;

            if(f.exists())
            {
                Files.deleteIfExists(f.toPath());
            }

            if(f1.exists())
            {
                Files.deleteIfExists(f1.toPath());
            }

            fr2 = new FileWriter(f, true);
            br2 = new BufferedWriter(fr2);

            fr3 = new FileWriter(f1, true);
            br3 = new BufferedWriter(fr3);

            while((Current_Line = br1.readLine())!= null) {

                if(Current_Line.contains("MACRO"))
                {
                    String[] str = Current_Line.split("[ ,]");
                    if(str.length == 2)
                    {
                        ArrayList<String> para_list = new ArrayList<String>();
                        Macro_Name = str[1];
                        mobj.add(new MNT(str[1], 0, MDT_Index));
                        Parameter.put(str[1], para_list);
                    }
                    else
                    {
                        int No_Parameter = (str.length) - 2;
                        Macro_Name = str[1];
                        ArrayList<String> para_list = new ArrayList<String>();
                        mobj.add(new MNT(str[1], No_Parameter, MDT_Index));
                        int count = 1;
                        for(int i = 2; i < str.length; i++)
                        {
                            if(str[i] != "") {
                                para_list.add(str[i]+"=#"+count);
                                count++;
                            }
                        }
                        Parameter.put(str[1], para_list);
                    }
                    mflag = 1;
                    continue;
                }

                if(mflag == 1)
                {
                    String[] str = Current_Line.split("[ ,]");
                    if(Parameter.containsKey(Macro_Name))
                    {
                        ArrayList<String> parameter = Parameter.get(Macro_Name);
                        for(int i = 0; i < parameter.size(); i++)
                        {
                            String[] val = parameter.get(i).split("[=]");
                            if(Current_Line.contains(val[0]))
                            {
                                if(val.length == 3)
                                {
                                    String[] val1 = parameter.get(i).split("[=]");
                                    Current_Line = Current_Line.replace(val[0], val1[2]);
                                }
                                else {
                                    String[] val1 = parameter.get(i).split("[=]");
                                    Current_Line = Current_Line.replace(val[0], val1[1]);
                                }
//                                System.out.println(Current_Line);
                            }
                        }
                    }

                    MNT[] arr = mobj.toArray(new MNT[0]);
                    for (MNT mnt : arr) {
                        if(mnt.Macro_Name.equals(str[0]))
                        {
                            int Count = 1;
                            ArrayList<ArrayList<String>> temp1 = new ArrayList<ArrayList<String>>();
                            HashMap<String,ArrayList<String>> temp2 = new HashMap<String,ArrayList<String>>();

                            ArrayList<String> Para = new ArrayList<>();
                            String app = str[0];
                            for(int i = 1; i < str.length; i++)
                            {
                                app = app+"["+"#"+Count+"="+str[i]+"]";
                                Para.add("#"+Count+"="+str[i]);
                                Count++;
                            }
                            hpara.add(app);
                            para.put(str[0], Para);
                            int S_Index = mnt.Starting_Index;
                            while(!MDT.get(S_Index).equals("MEND"))
                            {
                                if(Parameter.containsKey(str[0])&&para.containsKey(str[0]))
                                {
                                    String line = MDT.get(S_Index);
                                    String[] split = line.split("[ ,]");
                                    ArrayList<String> temp = para.get(str[0]);
//                                    ArrayList<String> par = Parameter.get(str[0]);
//                                    System.out.println(par);
                                    for(int i = 0; i < temp.size(); i++)
                                    {
                                        String[] split1 = temp.get(i).split("[=]");
                                        for(int j = 0; j < split.length; j++) {
                                            if (split1[0].equals(split[j])) {
                                                line = line.replace(split1[0], split1[1]);
                                            }
                                        }
                                    }
                                    MDT.put(MDT_Index, line);
                                    MDT_Index++;
                                    S_Index++;
                                }
                            }
                            cflag = 1;
                            break;
                        }
                    }

                    if(cflag == 0) {
                        MDT.put(MDT_Index, Current_Line);
                        if (Current_Line.contains("MEND")) {
                            mflag = 0;
                            MDT_Index++;
                            continue;
                        }
                        MDT_Index++;
                        continue;
                    }
                    else
                    {
                        cflag = 0;
                        continue;
                    }
                }
                br2.write(lc +" "+Current_Line);
                br2.write(System.getProperty("line.separator"));
                lc++;

                MNT[] arr = mobj.toArray(new MNT[0]);
                for(MNT mnt : arr)
                {
                    if(Current_Line.contains(mnt.Macro_Name))
                    {
                        String str[] = Current_Line.split("[ ,]");
                        int No_Parameter = (str.length) - 1;
                        if(mnt.No_Parameter == 0)
                        {
                            int S_Index = mnt.Starting_Index;
                            while(!MDT.get(S_Index).equals("MEND"))
                            {
                                br3.write(lc1 +" "+MDT.get(S_Index));
                                br3.write(System.getProperty("line.separator"));
                                S_Index++;
                                lc1++;
                            }
                            iflag = 1;
                            break;
                        }
                        else
                        {
                            ArrayList<String> arr1 = new ArrayList<>();
                            for(int i = 1; i < str.length; i++)
                            {
//                                if(true)
//                                {
//                                    System.out.println(str[i].lastIndexOf(" "));
//                                }
                                arr1.add("#"+i+"="+str[i]);
                            }
                            Parameter1.put(str[0],arr1);
                            int S_Index = mnt.Starting_Index;
                            while(!MDT.get(S_Index).equals("MEND"))
                            {
                                String line = MDT.get(S_Index);
                                String[] split = line.split("[ ,]");
                                ArrayList<String> temp = Parameter1.get(str[0]);
//                                    ArrayList<String> par = Parameter.get(str[0]);
//                                    System.out.println(par);
                                for(int i = 0; i < temp.size(); i++)
                                {
                                    String[] split1 = temp.get(i).split("[=]");
                                    for(int j = 0; j < split.length; j++) {
                                        if (split1[0].equals(split[j])) {
                                            line = line.replace(split1[0], split1[1]);
                                        }
                                    }
                                }
                                br3.write(lc1 +" "+line);
                                br3.write(System.getProperty("line.separator"));
                                S_Index++;
                                lc1++;
                            }
                            iflag = 1;
                            break;
                        }
                    }
                }

                if (iflag == 1)
                {
                    iflag = 0;
                    continue;
                }
                br3.write(lc1 +" "+Current_Line);
                br3.write(System.getProperty("line.separator"));
                lc1++;
            }
            br3.close();
            fr3.close();
            br2.close();
            fr2.close();
            fr1.close();

            MNT[] arr = mobj.toArray(new MNT[0]);
            sobj.Display(arr, MDT, Parameter, hpara);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}