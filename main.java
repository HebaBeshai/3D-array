/*
public class Main {

    public static void main(String[] args) {
        package com.company;
*/

import java.io.FileInputStream ;
import java.io.DataInputStream ;


// Main program to produce 3D LiDaR image from
// input file specified on command line
//
// Todd Torgersen
// January 2017
//
//

// imports to support file input
import java.io.FileOutputStream      ;
import java.io.DataOutputStream      ;

// imports to support rendering of image
import java.awt.image.BufferedImage  ;
import java.awt.Graphics             ;
import java.awt.Graphics2D           ;
import java.awt.Color                ;
import java.awt.RenderingHints       ;
import java.awt.Dimension            ;
import javax.swing.JFrame            ;
import javax.swing.JPanel            ;

        class main {
            // Data --------------------------------------------
            static int mrows, ncolumns, ppages ;
            static int [][]B = null ;
            static int bmax ;

            // 64X3 matrix of RGB values used as a palet of colors
            static final float jet[][] = {
                    {   0.0000000e+00f,  0.0000000e+00f,  5.6250000e-01f } ,
                    {   0.0000000e+00f,  0.0000000e+00f,  6.2500000e-01f } ,
                    {   0.0000000e+00f,  0.0000000e+00f,  6.8750000e-01f } ,
                    {   0.0000000e+00f,  0.0000000e+00f,  7.5000000e-01f } ,
                    {   0.0000000e+00f,  0.0000000e+00f,  8.1250000e-01f } ,
                    {   0.0000000e+00f,  0.0000000e+00f,  8.7500000e-01f } ,
                    {   0.0000000e+00f,  0.0000000e+00f,  9.3750000e-01f } ,
                    {   0.0000000e+00f,  0.0000000e+00f,  1.0000000e+00f } ,
                    {   0.0000000e+00f,  6.2500000e-02f,  1.0000000e+00f } ,
                    {   0.0000000e+00f,  1.2500000e-01f,  1.0000000e+00f } ,
                    {   0.0000000e+00f,  1.8750000e-01f,  1.0000000e+00f } ,
                    {   0.0000000e+00f,  2.5000000e-01f,  1.0000000e+00f } ,
                    {   0.0000000e+00f,  3.1250000e-01f,  1.0000000e+00f } ,
                    {   0.0000000e+00f,  3.7500000e-01f,  1.0000000e+00f } ,
                    {   0.0000000e+00f,  4.3750000e-01f,  1.0000000e+00f } ,
                    {   0.0000000e+00f,  5.0000000e-01f,  1.0000000e+00f } ,
                    {   0.0000000e+00f,  5.6250000e-01f,  1.0000000e+00f } ,
                    {   0.0000000e+00f,  6.2500000e-01f,  1.0000000e+00f } ,
                    {   0.0000000e+00f,  6.8750000e-01f,  1.0000000e+00f } ,
                    {   0.0000000e+00f,  7.5000000e-01f,  1.0000000e+00f } ,
                    {   0.0000000e+00f,  8.1250000e-01f,  1.0000000e+00f } ,
                    {   0.0000000e+00f,  8.7500000e-01f,  1.0000000e+00f } ,
                    {   0.0000000e+00f,  9.3750000e-01f,  1.0000000e+00f } ,
                    {   0.0000000e+00f,  1.0000000e+00f,  1.0000000e+00f } ,
                    {   6.2500000e-02f,  1.0000000e+00f,  1.0000000e+00f } ,
                    {   1.2500000e-01f,  1.0000000e+00f,  9.3750000e-01f } ,
                    {   1.8750000e-01f,  1.0000000e+00f,  8.7500000e-01f } ,
                    {   2.5000000e-01f,  1.0000000e+00f,  8.1250000e-01f } ,
                    {   3.1250000e-01f,  1.0000000e+00f,  7.5000000e-01f } ,
                    {   3.7500000e-01f,  1.0000000e+00f,  6.8750000e-01f } ,
                    {   4.3750000e-01f,  1.0000000e+00f,  6.2500000e-01f } ,
                    {   5.0000000e-01f,  1.0000000e+00f,  5.6250000e-01f } ,
                    {   5.6250000e-01f,  1.0000000e+00f,  5.0000000e-01f } ,
                    {   6.2500000e-01f,  1.0000000e+00f,  4.3750000e-01f } ,
                    {   6.8750000e-01f,  1.0000000e+00f,  3.7500000e-01f } ,
                    {   7.5000000e-01f,  1.0000000e+00f,  3.1250000e-01f } ,
                    {   8.1250000e-01f,  1.0000000e+00f,  2.5000000e-01f } ,
                    {   8.7500000e-01f,  1.0000000e+00f,  1.8750000e-01f } ,
                    {   9.3750000e-01f,  1.0000000e+00f,  1.2500000e-01f } ,
                    {   1.0000000e+00f,  1.0000000e+00f,  6.2500000e-02f } ,
                    {   1.0000000e+00f,  1.0000000e+00f,  0.0000000e+00f } ,
                    {   1.0000000e+00f,  9.3750000e-01f,  0.0000000e+00f } ,
                    {   1.0000000e+00f,  8.7500000e-01f,  0.0000000e+00f } ,
                    {   1.0000000e+00f,  8.1250000e-01f,  0.0000000e+00f } ,
                    {   1.0000000e+00f,  7.5000000e-01f,  0.0000000e+00f } ,
                    {   1.0000000e+00f,  6.8750000e-01f,  0.0000000e+00f } ,
                    {   1.0000000e+00f,  6.2500000e-01f,  0.0000000e+00f } ,
                    {   1.0000000e+00f,  5.6250000e-01f,  0.0000000e+00f } ,
                    {   1.0000000e+00f,  5.0000000e-01f,  0.0000000e+00f } ,
                    {   1.0000000e+00f,  4.3750000e-01f,  0.0000000e+00f } ,
                    {   1.0000000e+00f,  3.7500000e-01f,  0.0000000e+00f } ,
                    {   1.0000000e+00f,  3.1250000e-01f,  0.0000000e+00f } ,
                    {   1.0000000e+00f,  2.5000000e-01f,  0.0000000e+00f } ,
                    {   1.0000000e+00f,  1.8750000e-01f,  0.0000000e+00f } ,
                    {   1.0000000e+00f,  1.2500000e-01f,  0.0000000e+00f } ,
                    {   1.0000000e+00f,  6.2500000e-02f,  0.0000000e+00f } ,
                    {   1.0000000e+00f,  0.0000000e+00f,  0.0000000e+00f } ,
                    {   9.3750000e-01f,  0.0000000e+00f,  0.0000000e+00f } ,
                    {   8.7500000e-01f,  0.0000000e+00f,  0.0000000e+00f } ,
                    {   8.1250000e-01f,  0.0000000e+00f,  0.0000000e+00f } ,
                    {   7.5000000e-01f,  0.0000000e+00f,  0.0000000e+00f } ,
                    {   6.8750000e-01f,  0.0000000e+00f,  0.0000000e+00f } ,
                    {   6.2500000e-01f,  0.0000000e+00f,  0.0000000e+00f } ,
                    {   5.6250000e-01f,  0.0000000e+00f,  0.0000000e+00f } } ;

            // the Color scheme produced by jet[][]
            static Color [] the_colors = new Color[64] ;



            // method to produce the_colors from jet[][]
            private static void set_colors()
            {

                for ( int i = 0 ; i < 64 ; i++ ) {
                    the_colors[i] = new Color( jet[i][0], jet[i][1], jet[i][2] ) ;
                }
            }

            // - - - - - - - - - - - - - - - - - - - - - - - - - -
            //
            // main method

            public static void main( String [] args )
            {


                // check command line to make sure there is exactly
                // one command line argument
                array3d A = new array3d() ;
                if ( args.length != 1 ) {
                    System.out.println( "usage:  java prog filename" ) ;
                    return ;
                }

                // read() the array3d A, on any error abort
                boolean bflag = A.read( args[0] ) ;
                if ( !bflag ) {
                    System.out.println( "main():  unable to read '" + args[0] + "'" ) ;
                    return ;
                }

                // get dimensions of 3D image from A and then create b[nrows][columns]
                // which will contain the height values
                mrows = A.get_rows()    ;
                ncolumns = A.get_columns()    ;
                ppages = A.get_pages()    ;
//        System.out.println( "mrows = " + mrows + " ncolumns = " + ncolumns + " ppages = " + ppages ) ;
                B = new int[mrows][ncolumns] ;

                // for each <row,column> in the image, find the height as given by
                // the LiDaR image, also keep with with the largest height (bmax)
                bmax = -1 ;
                for ( int i = 0 ; i < mrows ; i++ ) {
                    for ( int j = 0 ; j < ncolumns ; j++ ) {
                        int v = A.get_zmap_value( i, j ) ;
                        if ( ( v < 0 ) || ( v >= ppages ) ) {
                            System.out.println( ) ;
                            System.out.println( "Error: height value " + v +
                                    " out of range." ) ;
                            System.out.println( "       Value must be non-negative" +
                                    " and at most " + (ppages-1) + "." ) ;
                            System.out.println( ) ;
                            return ;
                        }
                        B[i][j] = v                  ;
                        if ( bmax < v ) bmax = v     ;
                    }
                }

                // create a mrowsXncolumns image that supports (int)RGB
                final BufferedImage img = new BufferedImage(mrows, ncolumns,
                        BufferedImage.TYPE_INT_RGB);

                // create a 2D graphics image on the BufferedImage
                Graphics2D g = (Graphics2D)img.getGraphics();

                set_colors()  ;

                // color each <row,column> 1x1 rectangle in the graphics image
                // with an interpolated color from the_colors[]
                float dbmax = (float) bmax  ;
                for(int i = 0; i < mrows; i++) {
                    for(int j = 0; j < ncolumns; j++) {
                        float c = 63.0f * ( ( (float) B[i][j] ) / dbmax ) ;
                        int cix = Math.round( c ) ;
                        g.setColor(the_colors[ cix ]);
                        g.fillRect(i, j, 1, 1);
                    }
                }

                // create JFrame and JPanel in which to render the 2D graphics
                // image
                JFrame frame = new JFrame("LiDAR Height Map");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                JPanel panel = new JPanel() {
                    @Override
                    protected void paintComponent(Graphics g) {
                        Graphics2D g2d = (Graphics2D)g;
                        g2d.clearRect(0, 0, getWidth(), getHeight());
                        g2d.setRenderingHint(
                                RenderingHints.KEY_INTERPOLATION,
                                RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                        // Or _BICUBIC
                        g2d.scale(3, 3);
                        g2d.drawImage(img, 0, 0, this);
                    }
                };
                panel.setPreferredSize(new Dimension( 3*mrows, 3*ncolumns ));
                frame.getContentPane().add(panel);
                frame.pack();
                frame.setVisible(true);
            }
        }
