import TableGenerator as TB

Classc = 0
Opcode = 0

EMOTable = [["STOP",1,0],
            ["ADD",1,1],
            ["SUB",1,2],
            ["MULT",1,3],
            ["MOVER",1,4],
            ["MOVEM",1,5],
            ["COMP",1,6],
            ["BC",1,7],
            ["DIV",1,8],
            ["READ",1,9],
            ["PRINT",1,10],
            ["START",3,1],
            ["END",3,2],
            ["ORIGIN",3,3],
            ["EQU",3,4],
            ["LTORG",3,5],
            ["DS",2,1],
            ["DC",2,2],
            ["AREG",4,1],
            ["BREG",4,2],
            ["CREG",4,3],
            ["EQ",5,1],
            ["LT",5,2],
            ["GT",5,3],
            ["NE",5,4],
            ["LE",5,5],
            ["GT",5,6],
            ["ANY",5,7]]

InstructionType =  [[1,"IS"],
                    [2,"DL"],
                    [3,"AD"],
                    [4,"RG"],
                    [5,"CC"]]

def CheckinEMOT(Instr):
    for i in range(len(EMOTable)):
        if (Instr == EMOTable[i][0]):
            global Classc,Opcode
            Classc = EMOTable[i][1]
            Opcode = EMOTable[i][2]
            return True
    return False

def checkInsType(Instr):
    result = []
    for i in range(len(InstructionType)):
        if (Classc == InstructionType[i][0]):
            result.append(InstructionType[i][1])
            result.append(Opcode)
            Str1 = ','.join(map(str,result))
            return Str1
        

def OutWrite(LC,temp_list):
    Str1 = ' '.join(map(str,temp_list))
    f = open("output.txt", "a")
    f.write(str(LC) + " " + Str1 + '\n')
    f.close()
    

def Instructions(temp_list,LC):
    if ( temp_list[0] == 'START' ):
        LC = int(temp_list[1])
        OutWrite(LC,temp_list)
        LC

    elif ( temp_list[0] == 'READ' ):
        OutWrite(LC,temp_list)
        LC += 1

    elif ( temp_list[0] == 'MOVER' ):
        OutWrite(LC,temp_list)
        LC += 1

    elif ( temp_list[0] == 'MOVEM' ):
        OutWrite(LC,temp_list)
        LC += 1
        
    elif ( temp_list[0] == 'ADD' ):
        OutWrite(LC,temp_list)
        LC += 1

    elif ( temp_list[0] == 'MULT' ):
        OutWrite(LC,temp_list)
        LC += 1
    
    elif ( temp_list[0] == 'SUB' ):
        OutWrite(LC,temp_list)
        LC += 1
    
    elif ( temp_list[0] == 'COMP'):
        OutWrite(LC,temp_list)
        LC += 1
    
    elif ( temp_list[0] == 'PRINT'):
        OutWrite(LC,temp_list)
        LC += 1
        
    elif ( len(temp_list) == 4 ):
        OutWrite(LC,temp_list)
        LC = TB.STInsertion(temp_list,LC)
        
    elif ( temp_list[0] == "LTORG"):
        OutWrite(LC,temp_list)
        LC = TB.LTAddLoc(LC)
        LC = TB.PTInsertion(LC)
        
    elif ( temp_list[0] == "STOP"):
        OutWrite(LC,temp_list)
        LC += 1
    
    elif ( temp_list[0] == "END"):
        OutWrite(LC,temp_list)
        LC = TB.LTAddLoc(LC)
    
    elif ( temp_list[1] == "EQU"):
        OutWrite(LC,temp_list)
        LC = TB.STInsertion(temp_list,LC)
        
    elif ( temp_list[0] == "BC"):
        OutWrite(LC,temp_list)
        LC += 1
    
    elif ( temp_list[0] == "ORIGIN"):
        OutWrite(LC,temp_list)
        LC = TB.UpdateLC(temp_list,LC)
        
    else:
        for k  in range(len(temp_list)):
            if (temp_list[k] == "DS"):
                OutWrite(LC,temp_list)
                LC = TB.STInsertion(temp_list,LC) + (int(temp_list[2]) - 1)
                
    for i in range(len(temp_list)):
        for j in range(len(temp_list[i])):
            if(temp_list[i][j] == "="):
                LC = TB.LTInsertion(temp_list,LC)

    return LC


def Converter(temp_list):
    f = open("IC.txt", "a")

    for i in range(len(temp_list)):
        if (CheckinEMOT(temp_list[i])):
            IClist = checkInsType(temp_list[i])
            f.write("(" + str(IClist) + ")")

        elif (TB.checkinSYM(temp_list[i])): 
            f.write("(" + "S" + "," + str(TB.SymLC) + ")")

        elif (TB.checkinLT(temp_list[i])):
            if (i != 0):
                f.write("(" + "L" + "," + str(TB.LitLC) + ")")

        else:
            if(i == 0):
                f.write(str(temp_list[i]) + " ")
            else:
                f.write("(" + "C" + "," + str(temp_list[i]) + ")")
                
    f.close()
