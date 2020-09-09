import java.io.FileInputStream;
import java.util.Scanner;
import java.io.DataInputStream;
//import java.io.FileInputStream;

//data structures: first is 3d array and the second is data files



public class array3d {


    public int get_pages(){return numPages;};
    public int get_rows(){return numRows;};
    public int get_columns(){return numColumns;};

    public array3d (){LiDarData = null; numPages =0; numColumns=0; numRows=0;} //initializes pointer to null

    //this method will open the file, read the dimensions of the matriz, allocate
    //the space for the matrix, fill the matrix and then close the file
    //public boolean read(string fname)
    //the entire data file is the a 3d volume

    public boolean read(String fname)
    {
        //array3d storesBinary = new array3d();
        //byte[] bytes = storesBinary.readarray3d(C:\Users\beshh15\IdeaProjects\LAB 1\src\ig339_pcr_be.dat);
        //FileInputStream inputFileStream = new FileInputStream(fname);

        //opens the file
        FileInputStream bfile = null; //created an object called bfile and intialized it to null
        try{
            bfile = new FileInputStream(fname);
        }
        catch(Exception ex_open){         //goes here if not able to open file
            System.out.println("Unable to open file '"+ fname + "'");
            return false;
        }


        //reads the dimensions
        DataInputStream data = new DataInputStream(bfile); //all the data gets copied into the object called "data"
        try{
            numPages = data.readInt(); //copies the dimensions of the data file into the each data piece
            numColumns = data.readInt();
            numRows = data.readInt();
        }
        catch(Exception ex_readint){ //goes here is unable to read the file's dimensions
            System.out.println("Unable to read dimensions from the file '"+ fname + "'");
            return false;
        }

        //read the data
        LiDarData = new byte[numPages][numColumns][numRows];

        for (int k=0; k<numPages;k++){ //this loops the pages and on each pages goes through column and moves row by row
            for(int j=0; j<numColumns;j++){ //reading the data then it increments until the whole file is read
                for(int i=0; i<numRows;i++){
                    try{
                        LiDarData[k][j][i] = data.readByte(); //reads the data
                    }
                    catch(Exception ex_readbyte){
                        System.out.println("Unable to read byte from file '"+ fname + "'"); //if unable to read the data, it goes here
                        return false;
                    }
                }
            }
        }

        //closes the file
        try{
            data.close();
        }
        catch(Exception ex_close){ //if file cannot be closed, program goes here
            System.out.println("Unable to close file '"+ fname + "'");
            return false;
        }
        return  true;
    }


    //this is a while loop
    //we want to search for the top of the image position (i, j)
    public int get_zmap_value(int i, int j) //this starts at the top of the images and moves down until there is a '1'
                                            //this tells us that there is something present there, otherwise it keeps
                                            //moving down
    {
        int k = numPages-1;
        while((k >0 && LiDarData[k][j][i] == 0)){
            k--;
        }
        return k;
    }


    private byte [][][] LiDarData;//= new byte[numPages][numColumns][numRows]; //a pointer with a wrapper around it
    private int numPages;
    private int numColumns;
    private int numRows;



    };