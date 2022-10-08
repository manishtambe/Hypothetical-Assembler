SymbolTable = []   # This table contains all the symbols along with their addresses.
LiteralTable = []  # This table contains all the Literals along with their addresses.
PoolTable = []     # This table contains all the Pool Entries occured due to LTORG instruction.
temp = []          # This list checks for the second occurence of Symbols while converting to Intermediate Code.
LTindex = 0        # This variable is used as index in Literal table to maintain Literal table entries.
PTindex = 0        # This variable is used as index in pool table to maintain Pool table entries.
count = 0
STindex = 0
SymLC = 0
LitLC = 0

def STInsertion(temp_list,LC):
    temp1 = []
    global STindex
    temp1.append(STindex)
    STindex += 1
    temp1.append(temp_list[0])
    temp1.append(LC)
    SymbolTable.append(temp1)
    LC += 1
    return LC

def LTInsertion(temp_list,LC):
    temp2 = []
    for i in range(len(temp_list)):
        for j in range(len(temp_list[i])):
            if(temp_list[i][j] == "="):
                global LTindex
                temp2.append(LTindex)
                temp2.append(temp_list[i][2])
                LTindex += 1
    LiteralTable.append(temp2)  
    return LC

def PTInsertion(LC):
    temp3 = []
    #global count
    count = -1
    for i in range(len(LiteralTable)):
        count += 1
    global PTindex
    temp3.append(PTindex)
    temp3.append(count)
    PTindex += 1
    PoolTable.append(temp3)
    return LC

def LTAddLoc(LC):
    for i in range(len(LiteralTable)):
        if (len(LiteralTable[i]) < 3):
            LiteralTable[i].append(LC)
            LC += 1
    return LC

def UpdateLC(temp_list,LC):
    for i in range(len(temp_list)):
        for j in range(len(SymbolTable)):    
            if (temp_list[i] == SymbolTable[j][1]):
                LC = int(temp_list[2]) + SymbolTable[j][2]
    return LC


def PrintTable():
    print("\nSymbol Table : ")
    print("\n".join(["  ".join(map(str, i)) for i in SymbolTable]))
    print("\nLiteral Table : ")
    print("\n".join(["  ".join(map(str, i)) for i in LiteralTable]))
    print("\nPool Table : ")
    print("\n".join(["  ".join(map(str, i)) for i in PoolTable]))
    

def checkinSYM(Ele):      
    for i in range(len(SymbolTable)):
        if (Ele == SymbolTable[i][1]):
            global SymLC
            temp.append(SymbolTable[i][1])
            SymLC = SymbolTable[i][0]
            return True
    return False
            

def checkinLT(Ele):
    for j in range(len(Ele)):
            if(Ele[j] == "="):
                for i in range(len(LiteralTable)):
                    if (Ele[j+2] == LiteralTable[i][1]):
                        global LitLC
                        LitLC = LiteralTable[i][0]
                        print(LitLC)
                        return True
    return False
        
    