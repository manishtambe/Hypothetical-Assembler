from InstructionParser import Converter,Instructions
import re
import TableGenerator as TB
import os

def main():
    if os.path.exists("output.txt"):
        os.remove("output.txt")

    if os.path.exists("IC.txt"):
        os.remove("IC.txt")

    txt_file = open("Script.txt", "r")
    file_content = txt_file.read()
    content_list = file_content.split("\n")
    txt_file.close()                        

    LC = 0

    for i in content_list:
        delimiters = " ",",","+"
        regexPattern = '|'.join(map(re.escape, delimiters))
        temp_list = re.split(regexPattern, i)
        LC = Instructions(temp_list,LC)

    txt_file = open("output.txt", "r")
    file_content = txt_file.read()
    content_list = file_content.split("\n")
    txt_file.close()  


    for i in content_list:
        delimiters = " "
        regexPattern = '|'.join(map(re.escape, delimiters))
        temp_list = re.split(regexPattern, i)
        LC = Converter(temp_list)
        f = open("IC.txt", "a")
        f.write("\n")
        f.close() 
        
    TB.PrintTable()


if __name__ == '__main__':
    main()
    